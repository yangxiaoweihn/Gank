package ws.dyt.gank.ui.fragment;


import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.GankApplication;
import ws.dyt.gank.R;
import ws.dyt.gank.entity.GithubUserInfo;
import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.activity.base.SingleFragmentWithToolbarActivity;
import ws.dyt.gank.ui.fragment.base.GankBaseFragment;
import ws.dyt.gank.ui.fragment.github.GithubAuthFragment;
import ws.dyt.gank.utils.DipUtil;
import ws.dyt.gank.utils.ImageLoader;
import ws.dyt.view.adapter.ItemWrapper;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * 用户中心
 */
public class UserCenterFragment extends GankBaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private ImageView headerImageView;
    //用来展示登陆状态及用户名
    private TextView loginStatusTv;

    private SuperAdapter<DataWrapper> adapter;

    public UserCenterFragment() {
        // Required empty public constructor
    }

    public static UserCenterFragment newInstance() {
        return new UserCenterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_center, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        this.init(inflater);
        return view;
    }

    private void init(LayoutInflater inflater) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DataItemDecoration());

        this.initAdapter();

        mRecyclerView.setAdapter(adapter);

        initUserHeader(inflater);

        this.refresh();

        adapter.addAll(generateData());

        adapter.setOnItemClickListener(onItemClickListener);
    }


    private void initUserHeader(LayoutInflater inflater) {
        View header = inflater.inflate(R.layout.item_usercenter_header, mRecyclerView, false);
        adapter.addHeaderView(header);
        headerImageView = (ImageView) header.findViewById(R.id.iv_head);
        loginStatusTv = (TextView) header.findViewById(R.id.tv_name);

        loginStatusTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GithubUserInfo userInfo = GankApplication.getUserInfo();
                if (isAuthed(userInfo)) {
                    return;
                }

                authGithub(-1);
            }
        });
    }

    private SuperAdapter<DataWrapper> initAdapter() {
        return adapter = new SuperAdapter<DataWrapper>(getContext(), null, R.layout.item_usercenter) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                holder.setText(R.id.tv_category, getItem(position).data);
            }

        };
    }

    private interface DataType{
        int DT_GANK_COLLECTION    = 1;
        int DT_GANK_COMMIT        = 2;
    }
    private List<DataWrapper> generateData() {
        List<DataWrapper> dataWrappers = new ArrayList<>();
        DataWrapper favorite = new DataWrapper(DataType.DT_GANK_COLLECTION, "我的收藏", 1);
        dataWrappers.add(favorite);

        favorite = new DataWrapper(DataType.DT_GANK_COMMIT, "提交干货", 1);
        dataWrappers.add(favorite);

        return dataWrappers;
    }


    private HeaderFooterAdapter.OnItemClickListener onItemClickListener = new HeaderFooterAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            final int type = adapter.getItem(position).type;
            if (type == DataType.DT_GANK_COLLECTION) {
            }else if (type == DataType.DT_GANK_COMMIT) {
                //认证完需要跳转到提交干货界面
                authGithub(10);
            }
        }
    };


    private void authGithub(int flag) {
        SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, GithubAuthFragment.class, GithubAuthFragment.generateArgs(flag));
    }

    /**
     * 包装下显示数据
     */
    private class DataWrapper extends ItemWrapper<String> {
        //所属组
        public int group;
        public DataWrapper(int type, String data) {
            super(type, data);
        }

        public DataWrapper(int type, String data, int group) {
            super(type, data);
            this.group = group;
        }
    }

    private class DataItemDecoration extends RecyclerView.ItemDecoration {
        private final int dividerSection;
        private final int dividerItem;
        public DataItemDecoration() {
            dividerItem = DipUtil.dip2px(getContext(), 0.5f);
            dividerSection = DipUtil.dip2px(getContext(), 10f);
        }

        private int preItemGroup = -1;
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (0 >= position) {
                return;
            }

            position = position - adapter.getHeaderViewCount() - adapter.getSysHeaderViewCount();

            DataWrapper item = adapter.getItem(position);

            if (preItemGroup != item.group) {
                outRect.top = dividerSection;
            }else {
                outRect.top = dividerItem;
            }

            preItemGroup = item.group;
        }
    }

    private boolean isAuthed(GithubUserInfo userInfo) {
        return !TextUtils.isEmpty(userInfo.login);
    }

    @Override
    public void refresh() {
        if (isDetached() || isRemoving() || null == getView()) {
            return;
        }

        GithubUserInfo userInfo = GankApplication.getUserInfo();
        Logger.e("DEBUG", "#### "+userInfo.login);
        if (!isAuthed(userInfo)) {
            //未登陆状态
            loginStatusTv.setText("Github验证");
            return;
        }
        loginStatusTv.setText(userInfo.login);

        if (null != headerImageView) {
            ImageLoader.newInstance(getContext()).load(headerImageView, userInfo.avatar_url, 0);
        }
    }


    @Subscribe
    public void refreshUserInfo(GithubAuthFragment.OnAuthOkEvent event){
        this.refresh();

        if (event.flag == 10) {
            SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, GankCommitFragment.class, null);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
