package ws.dyt.gank.ui.fragment;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ws.dyt.gank.R;
import ws.dyt.gank.entity.CourseResult;
import ws.dyt.gank.entity.DailyAdapterInfo;
import ws.dyt.gank.entity.DailyResponseInfo;
import ws.dyt.gank.entity.GankInfo;
import ws.dyt.gank.entity.Response;
import ws.dyt.gank.net.RestApi;
import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.activity.base.SingleFragmentWithToolbarActivity;
import ws.dyt.gank.ui.fragment.base.WebpageBaseFragment;
import ws.dyt.gank.ui.fragment.github.GithubAuthFragment;
import ws.dyt.gank.utils.ImageLoader;
import retrofit.Callback;
import ws.dyt.pagelist.config.EmptyStatusViewWrapper;
import ws.dyt.pagelist.config.LoadMoreStatusViewWrapper;
import ws.dyt.pagelist.fragment.BasePageListFragment;
import ws.dyt.view.adapter.SuperPinnedAdapter;
import ws.dyt.view.adapter.core.base.HeaderFooterAdapter;
import ws.dyt.view.adapter.section.SectionMultiAdapter;
import ws.dyt.view.viewholder.BaseViewHolder;

/**
 *
 * 每日干货数据
 */
public class DailyFragment extends BasePageListFragment<GankInfo, DailyAdapterInfo> {


    public DailyFragment() {
        // Required empty public constructor
    }

    public static DailyFragment newInstance() {
        return new DailyFragment();
    }

    @Override
    public void setUpView() {
        super.setUpView();
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void onConfigEmptyStatusViewInfo(EmptyStatusViewWrapper wrapper) {
        super.onConfigEmptyStatusViewInfo(wrapper);
        wrapper.IsShowEmptyViewBeforeInitLoading = false;
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

    private int pageSize = 1;
    @Override
    public int setPageSize() {
        //这里控制该接口数据只当做一页来处理
        //本来是非分页接口，但是在处理的过程中转化为了分页处理，所以在分页请求时需要处理
        //具体处理就是：获得数据后将每页数据量设为比获取到的数据量大一点就可以
        return pageSize;
    }

    @Override
    public RecyclerView.LayoutManager setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        return layoutManager;
    }

    @Override
    public SuperPinnedAdapter<DailyAdapterInfo> setAdapter() {
        final SuperPinnedAdapter<DailyAdapterInfo> adapter = new SuperPinnedAdapter<DailyAdapterInfo>(getContext(), new ArrayList<DailyAdapterInfo>()) {
            @Override
            public int getItemViewLayout(int position) {
                if (0 == position) {
                    return R.layout.item_daily_child_meizhi;
                }
                return R.layout.item_daily_child;
            }

            @Override
            public void convertPinnedHolder(BaseViewHolder holder, int position, int type) {
                String title = getItem(position).groupTitle;
                holder.setText(R.id.tv_group, title);
            }

            @Override
            public int getPinnedItemViewLayout() {
                return R.layout.item_daily_group;
            }

            @Override
            public void convert(BaseViewHolder holder, int position) {
                if (0 == position) {
                    convertMeiZhi(holder);
                    return;
                }
                holder.setText(R.id.tv_desc, getItem(position).data.desc);
            }
        };

        adapter.setOnItemClickListener(new HeaderFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                GankInfo info = adapter.getItem(position).data;

                SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, WebpageBaseFragment.class, WebpageBaseFragment.generateArgs(info.desc, info.url));

            }
        });

        return adapter;
    }

    private void convertMeiZhi(BaseViewHolder holder) {
        ViewPager viewPager = holder.getView(R.id.view_pager);

        List<View> views = new ArrayList<>();
        for (final GankInfo e:meiZhi) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_daily_viewpager_meizhi, null);
            views.add(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GankInfo info = e;
                    SingleFragmentActivity.to(getContext(), SingleFragmentWithToolbarActivity.class, WebpageBaseFragment.class, WebpageBaseFragment.generateArgs(info.desc, info.url));

                }
            });
        }

        viewPager.setAdapter(new MeizhiAdapter(views));
    }

    private class MeizhiAdapter extends PagerAdapter{
        List<View> views;
        public MeizhiAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = views.get(position);
            container.addView(view);
            ImageLoader.newInstance(getContext()).load((ImageView) view.findViewById(R.id.iv_meizhi), meiZhi.get(position).url, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public List<DailyAdapterInfo> convert(List<GankInfo> datas) {
        if (null == datas || datas.isEmpty()) {
            return null;
        }

        //进行数据转换，将服务端返回的最叶子节点的数据转换为适配器需要的数据类型
        List<DailyAdapterInfo> list = new ArrayList<>();
        for (GankInfo e:datas) {
            //用hashCode来标注每条数据所在的组
            list.add(new DailyAdapterInfo(e.type.hashCode(), e, e.type));
        }
        return list;
    }

    ArrayList<GankInfo> meiZhi;
    @Override
    public void sendRequest(int pageIndex) {
        //下拉刷新时重置下
        if (pageIndex == 0) {
            this.pageSize = 1;
        }

        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        RestApi.getInstance().getDailyGankInfo(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), new Callback<Response<DailyResponseInfo>>() {
            @Override
            public void onResponse(retrofit.Response<Response<DailyResponseInfo>> response) {

                if (null == response || null == response.body() || null == response.body().results) {
                    setOnFailurePath();
                    return;
                }
                DailyResponseInfo info = response.body().results;
                List<GankInfo> datas = info.get();
                //添加一条妹纸的数据占位
                if (info.isContainsMeiZhi()) {
                    meiZhi = info.meiZhi;
                    datas.add(0, info.meiZhi.get(0));
                }

                //表明获取的数据量比分页数据量少，即数据全部获取完毕
                pageSize = datas.size() + 1;
                setOnSuccessPath(new ResponseResultWrapper<>(0, null, datas));

            }

            @Override
            public void onFailure(Throwable t) {
                setOnFailurePath();
            }
        });
    }
}
