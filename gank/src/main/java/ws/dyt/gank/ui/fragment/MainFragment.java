package ws.dyt.gank.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.R;
import ws.dyt.gank.ui.fragment.base.IRefresh;

/**
 * 主界面
 * 福利   干货  我的
 */
public class MainFragment extends Fragment {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.init();
        return view;
    }

    public void setTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    private void init() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);


        final String[] gankCategory = {"每日", "福利", "干货", "我的"};

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(DailyFragment.newInstance());
        fragments.add(GankCategoryMeiZhiListFragment.newInstance(gankCategory[1]));
        fragments.add(GankMainFragment.newInstance());
        fragments.add(UserCenterFragment.newInstance());


        final MainPagerAdapter adapter = new MainPagerAdapter(getFragmentManager(), fragments, gankCategory);

        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(adapter);


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //只针对我的页面
                if (position == 3) {
                    Fragment fragment = adapter.getItem(position);
                    if (null != fragment && fragment instanceof IRefresh) {
                        ((IRefresh) fragment).refresh();
                    }
                }

                getActivity().setTitle(adapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getActivity().setTitle(adapter.getPageTitle(0));
    }

    private class MainPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        private String[] titles;

        public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] titles) {
            super(fm);
            this.fragments = fragments;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
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
