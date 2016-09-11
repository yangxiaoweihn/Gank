package ws.dyt.gank.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.R;
import ws.dyt.gank.ui.activity.base.SingleFragmentActivity;
import ws.dyt.gank.ui.fragment.base.GankBaseFragment;
import ws.dyt.gank.ui.fragment.github.GithubAuthFragment;

/**
 * 干货组合界面
 */
public class GankMainFragment extends GankBaseFragment {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    public GankMainFragment() {
    }

    public static GankMainFragment newInstance() {
        return new GankMainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank_main, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        this.init();
        return view;
    }

    private void init() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);

//        String[] gankCategory = getResources().getStringArray(R.array.title_category_gank);
        String[] gankCategory = {"Android", "iOS", "前端", "休息视频", "拓展资源"};
        String[] gankCategoryTitle = {"安卓", "iOS", "前端", "视频", "拓展"};

        List<GankCategoryListFragment> fragments = new ArrayList<>();
        for (String e:gankCategory) {
             fragments.add(GankCategoryListFragment.newInstance(e));
        }

        final GankCategoryPagerAdapter adapter = new GankCategoryPagerAdapter(getChildFragmentManager(), fragments, gankCategoryTitle);
        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(adapter);

        //设置再次点击时回到顶部
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                adapter.getItem(tab.getPosition()).toListTop();
            }
        });

    }

    /**
     * 适配器
     */
    private class GankCategoryPagerAdapter extends FragmentPagerAdapter{
        private List<GankCategoryListFragment> fragments;
        private String[] titles;

        public GankCategoryPagerAdapter(FragmentManager fm, List<GankCategoryListFragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public GankCategoryListFragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
