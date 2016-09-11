package ws.dyt.gank.ui.fragment;


import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import retrofit.Callback;
import ws.dyt.gank.BR;
import ws.dyt.gank.GankApplication;
import ws.dyt.gank.R;
import ws.dyt.gank.databinding.GankCommitDataBinding;
import ws.dyt.gank.entity.Null;
import ws.dyt.gank.entity.Response;
import ws.dyt.gank.net.RestApi;
import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.activity.base.SingleFragmentWithToolbarActivity;
import ws.dyt.gank.utils.Toast;

/**
 * 提交干货
 */
public class GankCommitFragment extends Fragment {


    private GankCommitDataBinding dataBinding;

    public GankCommitFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_gank_commit, container, false);
        dataBinding.setEventHolder(eventHolder);
        dataBinding.setDataHolder(dataHolder);
        View view = dataBinding.getRoot();
        EventBus.getDefault().register(this);
        this.init();
        return view;
    }


    private void init() {
        getActivity().setTitle(R.string.title_gank_commit);
        setHasOptionsMenu(true);
    }

    private DataHolder dataHolder = new DataHolder();

    /**
     * 数据绑定
     */
    public class DataHolder extends BaseObservable{
        @Bindable
        public String category;
        @Bindable
        public String gankUrl;
        @Bindable
        public String gankDesc;

        public void setCategory(String category) {
            this.category = category;
            notifyPropertyChanged(BR.category);
        }

        public void setGankUrl(String gankUrl) {
            this.gankUrl = gankUrl;
            notifyPropertyChanged(BR.gankUrl);
        }

        //双向绑定需要
        public String getGankUrl() {
            return gankUrl;
        }

        public void setGankDesc(String gankDesc) {
            this.gankDesc = gankDesc;
            notifyPropertyChanged(BR.gankDesc);
        }

        public String getGankDesc() {
            return gankDesc;
        }
    }

    private EventHolder eventHolder = new EventHolder();
    /**
     * 事件监听
     */
    public class EventHolder {
        /**
         * 选择干货类型
         *
         * @param view
         */
        public void onGankCategoryEvent(View view) {
            SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, CategorySelectFragment.class, null);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_commit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_commit == item.getItemId()) {
            this.commitGank();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提交干货
     */
    private void commitGank() {
        dataHolder.gankUrl = "http://www.baidu.com";
        dataHolder.gankDesc = "测试测试测试测试测试";


        Logger.e("DEBUG", "category: "+dataHolder.category+" , url: "+dataHolder.gankUrl);

        if (!validate()) {
            return;
        }

        RestApi.getInstance().commitGankInfo(
                true,
                GankApplication.getUserInfo().id+"",
                dataHolder.category,
                dataHolder.gankUrl,
                dataHolder.gankDesc,
                new Callback<Response<Null>>() {
                    @Override
                    public void onResponse(retrofit.Response<Response<Null>> response) {

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }
        );
    }

    /**
     * 内容合法性校验
     * @return
     */
    private boolean validate() {
        if (TextUtils.isEmpty(dataHolder.category)) {
            Toast.show(getContext(), R.string.err_gank_category);
            return false;
        }
        if (TextUtils.isEmpty(dataHolder.gankUrl)) {
            Toast.show(getContext(), R.string.err_gank_url);
            return false;
        }

        if (TextUtils.isEmpty(dataHolder.gankDesc)) {
            Toast.show(getContext(), R.string.err_gank_desc);
            return false;
        }
        return true;
    }

    @Subscribe
    public void onEvent(OnCategorySelectedEvent event) {
        dataHolder.setCategory(event.category);
    }

    /**
     * 类别选中事件
     */
    public static class OnCategorySelectedEvent{
        public String category;

        public OnCategorySelectedEvent(String category) {
            this.category = category;
        }
    }

}
