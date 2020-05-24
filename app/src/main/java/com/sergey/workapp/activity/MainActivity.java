package com.sergey.workapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.sergey.workapp.R;
import com.sergey.workapp.fragment.FragmentDepartment;
import com.sergey.workapp.fragment.FragmentInfo;
import com.sergey.workapp.fragment.FragmentUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Drawer result = null;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        result.saveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initDrawer(savedInstanceState);
    }

    private void initDrawer(Bundle savedInstanceState) {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withProfileImagesVisible(false)
                .withSelectionListEnabled(false)
                .withHeaderBackground(R.color.colorAccent)
                .build();

        headerResult.addProfile(new ProfileDrawerItem().withName(getString(R.string.admin_name)).withEmail(getString(R.string.admin_role)), 0);
        result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withTranslucentStatusBar(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(getString(R.string.dep_title)).withTag(getString(R.string.dep_title)).withIdentifier(0).withIcon(FontAwesome.Icon.faw_building),
                        new PrimaryDrawerItem().withName(getString(R.string.user_title)).withTag(getString(R.string.user_title)).withIdentifier(1).withIcon(FontAwesome.Icon.faw_users),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.fragment_title)).withTag(getString(R.string.fragment_title)).withIdentifier(2).withIcon(FontAwesome.Icon.faw_info)

                )
                .withSavedInstance(savedInstanceState)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null)
                            onFragment(drawerItem);
                        return false;
                    }
                })
                .build();
        result.setSelection(0);
    }

    private void onFragment(IDrawerItem drawerItem) {
        int id = (int) drawerItem.getIdentifier();
        Fragment fragment;
        switch (id) {
            default:
                if(getSupportActionBar()!=null)getSupportActionBar().setElevation(4f);
                fragment = new FragmentDepartment();
                break;
            case 1:
                if(getSupportActionBar()!=null)getSupportActionBar().setElevation(0f);
                fragment = new FragmentUser();
                break;
            case 2:
                if(getSupportActionBar()!=null)getSupportActionBar().setElevation(4f);
                fragment = new FragmentInfo();
                break;
        }
        if (drawerItem.getTag() != null) setTitle(drawerItem.getTag().toString());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen())
            result.closeDrawer();
        else
            super.onBackPressed();
    }
}
