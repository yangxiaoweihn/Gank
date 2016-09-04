package ws.dyt.pagelist.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ws.dyt.pagelist.R;
import ws.dyt.pagelist.config.EmptyStatusViewWrapper;
import ws.dyt.pagelist.config.LoadMoreStatusViewWrapper;
import ws.dyt.pagelist.controller.EmptyViewController;
import ws.dyt.pagelist.controller.LoadMoreStatusViewController;
import ws.dyt.pagelist.delegate.InitConfig;
import ws.dyt.pagelist.entity.PageIndex;
import ws.dyt.pagelist.delegate.DataStatusDelegate;
import ws.dyt.pagelist.delegate.ResponseResultDelegate;
import ws.dyt.pagelist.utils.ViewInject;
import ws.dyt.view.adapter.SuperAdapter;
import ws.dyt.view.adapter.core.base.BaseAdapter;


/**
 * @param <T_RESPONSE>   服务端返回的数组数据，用来真实分页的数据
 * @param <T_ADAPTER>   适配器装载的数据（有可能真实的数据里会添加一些别的数据进去显示）
 */
abstract
public class BasePageListFragment<T_RESPONSE extends PageIndex, T_ADAPTER> extends LazyLoadFragment
        implements InitConfig<T_RESPONSE, T_ADAPTER>, ResponseResultDelegate<T_RESPONSE, T_ADAPTER>, DataStatusDelegate {

    private static final String TAG = "lib_BaseListFragment";

    protected RecyclerView recyclerView;
    protected SwipeRefreshLayout refreshLayout;
    protected BaseAdapter<T_ADAPTER> adapter;
    protected View rootView;

    public void toListTop() {
        if (isRemoving() || isDetached() || null == recyclerView) {
            return;
        }
        if (isRequestIng()) {
            return;
        }
        recyclerView.scrollToPosition(0);//.smoothScrollToPosition(0);
    }

    @Override
    public int setPageSize() {
        return PAGE_SIZE;
    }

    @Override
    public void setUpView() {}

    private EmptyViewController emptyViewController;
    /**
     * 设置没有数据时的信息
     * @param wrapper
     */
    public void onConfigEmptyStatusViewInfo(EmptyStatusViewWrapper wrapper) {}


    private LoadMoreStatusViewController loadMoreStatusViewController;
    @CallSuper
    public void onConfigLoadMoreStatusViewInfo(LoadMoreStatusViewWrapper wrapper) {
        String[] e = getResources().getStringArray(R.array.rll_load_status);
        wrapper.TextTipsOfLoadingStatus = e;
    }


    /**
     * 数据包装器
     * @param <T_RESPONSE>
     */
    public class ResponseResultWrapper<T_RESPONSE>{
        public int StatusCode;
        public String StatusMessage;
        public List<T_RESPONSE> Data;

        public ResponseResultWrapper(int statusCode, String statusMessage, List<T_RESPONSE> data) {
            this.StatusCode = statusCode;
            this.StatusMessage = statusMessage;
            this.Data = data;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.rll_fragment_recyclerview_refresh, container, false);
        this.recyclerView = ViewInject.findView(R.id.recyclerView, rootView);
        this.refreshLayout = ViewInject.findView(R.id.refreshLayout, rootView);

        this.init(inflater);
        return rootView;
    }

    private void init(LayoutInflater inflater) {
        this.recyclerView.setLayoutManager(this.setLayoutManager());

        this.adapter = this.setAdapter();

        this.loadMoreStatusViewController = new LoadMoreStatusViewController(inflater, this.recyclerView, this.adapter);
        this.onConfigLoadMoreStatusViewInfo(this.loadMoreStatusViewController.getLoadMoreStatusViewWrapper());

        this.emptyViewController = new EmptyViewController(inflater, this.rootView, adapter);
        this.onConfigEmptyStatusViewInfo(this.emptyViewController.getEmptyStatusViewWrapper());

        this.setUpView();

        this.recyclerView.setAdapter(adapter);

        this.refreshLayout.setOnRefreshListener(onRefreshListener);
        this.recyclerView.addOnScrollListener(onScrollListener);
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            BasePageListFragment.this.pullDown();
        }
    };


    //最后一个可见item位置
    private int lastVisibleItemPosition;
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //刷新
            if (refreshLayout.isRefreshing() == false
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition == adapter.getItemCount() - 1
                    && isRequestEnd == true
                    && isAllDataDidLoad == false) {
                BasePageListFragment.this.pullUp();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
            if (null == lm) {
                return;
            }

            if (lm instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) lm).findLastVisibleItemPosition();
            } else if (lm instanceof StaggeredGridLayoutManager) {
                lastVisibleItemPosition = ((StaggeredGridLayoutManager) lm).findLastVisibleItemPositions(new int[1])[0];
            } else if (lm instanceof LinearLayoutManager) {
                lastVisibleItemPosition = ((LinearLayoutManager) lm).findLastVisibleItemPosition();
            }
        }
    };

    //标记是否为下拉刷新请求
    private boolean isPullDownRequesting = false;
    private void pullDown() {
        Log.d("GGGGG", "pullDown()");
        if (!isRequestEnd) {
            return;
        }
        this.refreshLayout.setRefreshing(true);
        this.emptyViewController.withInitLoading();

        this.isPullDownRequesting = true;
        this.pageStartIndex = 0;
        this.realDataCount = 0;
        this.isRequestEnd = false;
        this.isAllDataDidLoad = false;
        this.loadMoreStatusViewController.withPullDown();

        //针对下拉第一页进行处理
        this.pageStartIndex = this.filterPageIndexOffset(this.realDataCount, this.pageStartIndex);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//        refreshLayout.setRefreshing(true);
                sendRequest(pageStartIndex);
            }
        }, delayTest);
    }
    private static final int delayTest = 2000;


    private void pullUp() {
        if (isAllDataDidLoad || !isRequestEnd) {
            return;
        }
        Log.d(TAG, "pullUp()");
        this.isRequestEnd = false;

        this.loadMoreStatusViewController.withPullUp();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendRequest(pageStartIndex);
            }
        }, delayTest);
    }


    /**
     * 初次加载数据,或者强制刷新-都是重新获取数据
     */
    @Override
    final
    protected void fetchData() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                pullDown();
            }
        });
    }

    //一次请求是否完毕
    private boolean isRequestEnd = true;
    //是否数据已经全部加载完毕
    private boolean isAllDataDidLoad = false;

    //分页索引id
    private int pageStartIndex = 0;

    /**{@link ResponseResultDelegate}
     * ---------------------------------------------------------------------------*/
    @Override
    final
    public void setOnSuccessPath(ResponseResultWrapper<T_RESPONSE> result) {
        Log.e(TAG, "setOnSuccessPath");
        this.isRequestEnd = true;
        this.setOnSuccessPath_(result);
        this.isPullDownRequesting = false;
    }

    @Override
    final
    public void setOnFailurePath(){
        Log.e(TAG, "setOnFailurePath");
        this.isRequestEnd = true;
        this.setOnFailurePath_();
        this.isPullDownRequesting = false;
    }
    /**---------------------------------------------------------------------------*/

    private int realDataCount = 0;
    private void setOnSuccessPath_(ResponseResultWrapper<T_RESPONSE> result) {
        //接口数据异常
        if (null == result || result.StatusCode != 0) {
//            this.refreshLayout.setRefreshing(false);
//
//            //exception
//            this.emptyViewController.withException();
            this.setOnFailurePath();
            return;
        }

        final List<T_RESPONSE> datas = null == result.Data ? new ArrayList<T_RESPONSE>() : result.Data;
        final int count = datas.size();
        final int empIndexId = count == 0 ? 0 : datas.get(count - 1).getPageIndex();
        this.realDataCount += count;

        //下拉刷新后且获取到数据, 此时清除旧数据
        //这里会产生一个歧义(看情况): 刷新的时候如果未获取到数据(一切状态正常只是无数据), 此时要不要清楚旧数据的问题. 我这里采用的是清除的方式
        if (this.isPullDownRequesting) {
            this.adapter.clear();
        }

        int dataSize = adapter.getDataSectionItemCount();
        //列表无数据且未获取到数据，此时认为无数据
        if (0 == dataSize && 0 == count) {
            this.isAllDataDidLoad = true;
            this.loadMoreStatusViewController.withRemoveLoadStatusView();

            this.emptyViewController.withNoneDatas();
            this.onNoneData();
        }else {
            //有数据，需要判断数据是否已全部加载完毕

            this.emptyViewController.withRemoveEmptyView();

            if (this.isPullDownRequesting) {
                //为了确保第一页定位问题, 不然会定位到最底部
                this.adapter.notifyDataSetChanged();
            }

            //当前获取的数据少于一页数据，此时认为数据已经全部加载完毕
            if (count < setPageSize()) {
                this.isAllDataDidLoad = true;
                this.loadMoreStatusViewController.withLoadSuccess();
                this.onAllDataDidLoad();
            }else {
                this.loadMoreStatusViewController.withResetStatus();
            }
            this.adapter.addAll(this.convert(datas));
        }

        this.pageStartIndex = this.filterPageIndexOffset(this.realDataCount, empIndexId);
        this.refreshLayout.setRefreshing(false);
    }

    private void setOnFailurePath_() {
        this.refreshLayout.setRefreshing(false);

//        this.loadMoreStatusViewController.withResetStatus();
        this.loadMoreStatusViewController.withException();

        //exception
        this.emptyViewController.withException();
    }

    /**
     * 分页页码处理逻辑, 默认按照分页id
     *
     * @param realDataCount         从数据源获取到的数据累积量
     * @param lastItemPageIndexId   从数据源获取的当前页最后一项分页id
     * @return
     */
    private int filterPageIndexOffset(int realDataCount, int lastItemPageIndexId) {
        realDataCount = this.filterPageIndexOffset(realDataCount);
        return -1 == realDataCount ? lastItemPageIndexId : realDataCount;
    }

    /**
     * 重写分页标识
     *
     * @param realDataCount
     * @return
     */
    protected int filterPageIndexOffset(int realDataCount) {
        //比如通过页码分页可以这么写(分页从1...n)
        //return realDataCount / setPageSize() + 1;
        return -1;
    }

    public boolean isRequestIng() {
        return !this.isRequestEnd;
    }


    /**{@link DataStatusDelegate}
     * ---------------------------------------------------------------------------*/
    /**
     * 暂无数据回调
     */
    @Override
    public void onNoneData() {
        Log.e(TAG, "onNoneData");
    }

    /**
     * 所有数据全部加载完毕回调
     */
    @Override
    public void onAllDataDidLoad() {
        Log.e(TAG, "onAllDataDidLoad()");
//        toast("数据已全部加载完成");
    }
    /**---------------------------------------------------------------------------*/

    private void toast(String toast) {
        Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
    }
}
