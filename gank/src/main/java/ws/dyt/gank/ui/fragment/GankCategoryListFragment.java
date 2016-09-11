package ws.dyt.gank.ui.fragment;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import ws.dyt.gank.R;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.Response;
import ws.dyt.gank.net.RestApi;
import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.activity.base.SingleFragmentWithToolbarActivity;
import ws.dyt.gank.ui.fragment.base.GankBasePageListFragment;
import ws.dyt.gank.ui.fragment.base.WebpageBaseFragment;
import ws.dyt.gank.utils.DipUtil;
import ws.dyt.gank.utils.ImageLoader;
import ws.dyt.pagelist.config.EmptyStatusViewWrapper;
import ws.dyt.pagelist.config.LoadMoreStatusViewWrapper;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 * 分类列表数据
 */
public class GankCategoryListFragment extends GankBasePageListFragment<GankInfo, GankInfo> {


    public GankCategoryListFragment() {
        // Required empty public constructor
    }

    private static final String PARAM_CATEGORY = "param_category";
    public static Bundle newArgs(String category) {
        Bundle arg = new Bundle();
        arg.putString(PARAM_CATEGORY, category);
        return arg;
    }
    public static GankCategoryListFragment newInstance(String category) {
        GankCategoryListFragment fragment = new GankCategoryListFragment();
        fragment.setArguments(newArgs(category));
        return fragment;
    }


    private String category = null;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        category = getArguments().getString(PARAM_CATEGORY);
    }

    @Override
    public void onConfigEmptyStatusViewInfo(EmptyStatusViewWrapper wrapper) {
        super.onConfigEmptyStatusViewInfo(wrapper);
    }

    @Override
    public void onConfigLoadMoreStatusViewInfo(LoadMoreStatusViewWrapper wrapper) {
        super.onConfigLoadMoreStatusViewInfo(wrapper);
        wrapper.IsShowStatusWhenRefresh = false;
        wrapper.IsShowStatusWhenAllDataDidLoad = true;
    }

    @Override
    public boolean isLazyLoadEnabled() {
        return true;
    }

    @Override
    public void setUpView() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        recyclerView.addItemDecoration(getItemDecoration());

        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                GankInfo info = adapter.getItem(position);
                SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, WebpageBaseFragment.class, WebpageBaseFragment.generateArgs(info.desc, info.url));
            }
        });
    }

    @Override
    public RecyclerView.LayoutManager setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public SuperAdapter<GankInfo> setAdapter() {
        return new SuperAdapter<GankInfo>(getContext(), new ArrayList<GankInfo>(), getItemLayoutId()) {
            @Override
            public void convert(BaseViewHolder holder, int position) {
                GankInfo info = getItem(position);
                String about = "";

                if (!TextUtils.isEmpty(info.who)) {
                    about += "发布者:"+info.who;
                    about += "  ";
                }
                if (!TextUtils.isEmpty(info.source)) {
                    about += "来源:"+info.source;
                    about += "  ";
                }

                holder
                .setText(R.id.tv_desc, info.desc)
                .setText(R.id.tv_info, about)
                .setText(R.id.tv_date_publish, TextUtils.isEmpty(info.publishedAt) ? "" : info.publishedAt.substring(0, info.publishedAt.indexOf("T")));

                if (info.type.equals("福利")) {
                    ImageView iv = holder.getView(R.id.iv_meizhi);
                    if (null == iv) {
                        return;
                    }
                    ImageLoader.newInstance(getContext()).load(iv, info.url, 0);
                }
            }
        };
    }

    protected @LayoutRes int getItemLayoutId() {
        return R.layout.item_gank_category_res;
    }

    @Override
    public List<GankInfo> convert(List<GankInfo> datas) {
        return datas;
    }

    @Override
    protected int filterPageIndexOffset(int realDataCount) {
        return realDataCount / setPageSize() + 1;
    }

    @Override
    public void sendRequest(int pageIndex) {
        Logger.e("DEBUG", ">>>>> pageOffset: "+pageIndex);
        RestApi.getInstance().getCagetoryList(category, setPageSize(), pageIndex, new Callback<Response<ArrayList<GankInfo>>>() {
            @Override
            public void onResponse(retrofit.Response<Response<ArrayList<GankInfo>>> response) {

                Log.e("DEBUG", "onResponse");
                Response<ArrayList<GankInfo>> response1 = response.body();
                setOnSuccessPath(new ResponseResultWrapper<>(0, "", null == response1 ? null : response1.results));
                if (null == response1) {
                    Log.e("DEBUG", "null == response1");
                    return;
                }
                List<GankInfo> datas = response1.results;
                if (null == datas) {
                    Log.e("DEBUG", "null == datas");
                    return;
                }
                Log.e("DEBUG", "ok");

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("DEBUG", "onFailure");

                setOnFailurePath();
            }
        });
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new ItemDecoration();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        private final int divider;
        public ItemDecoration() {
            divider = DipUtil.dip2px(getContext(), 0.5f);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (0 == position) {
                return;
            }

            outRect.top = divider;
//            outRect.right = 10;
        }
    }
}
