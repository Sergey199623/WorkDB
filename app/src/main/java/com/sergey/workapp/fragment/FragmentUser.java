package com.sergey.workapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sergey.workapp.R;
import com.sergey.workapp.adapter.AdapterDepTab;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentUser extends Fragment {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.content)
    View content;
    Unbinder unbinder;

    int pos = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_user_all, container, false);
        setRetainInstance(true);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onStart() {
        super.onStart();
        List<Department> deps = DaoManager.getInstance().getAllDeps();
        if (deps == null || deps.size() < 1) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            AdapterDepTab adapter = new AdapterDepTab(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT);
            for (Department dep : deps)
                adapter.addFragment(dep.getTitle(), FragmentUserByDep.newInstance(dep));
            viewPager.setAdapter(adapter);
            if (adapter.getCount() < 3) tabs.setTabMode(TabLayout.MODE_FIXED);
            tabs.setupWithViewPager(viewPager);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pos = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            viewPager.setCurrentItem(pos);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
