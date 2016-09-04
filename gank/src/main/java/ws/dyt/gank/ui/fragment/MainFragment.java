package ws.dyt.gank.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ws.dyt.gank.R;

/**
 * 主界面
 * 福利   干货  我的
 */
public class MainFragment extends Fragment {


    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

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

    private void init() {
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);


        String[] gankCategory = {"每日", "福利", "干货", "我的"};

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(DailyFragment.newInstance());
        fragments.add(GankCategoryMeiZhiListFragment.newInstance(gankCategory[1]));
        fragments.add(GankMainFragment.newInstance());
        fragments.add(GankMainFragment.newInstance());


        MainPagerAdapter adapter = new MainPagerAdapter(getFragmentManager(), fragments, gankCategory);

        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(adapter);


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
