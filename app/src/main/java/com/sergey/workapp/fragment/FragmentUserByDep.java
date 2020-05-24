package com.sergey.workapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sergey.workapp.R;
import com.sergey.workapp.activity.DepActivity;
import com.sergey.workapp.activity.UserActivity;
import com.sergey.workapp.adapter.AdapterUser;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;
import com.sergey.workapp.dao.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentUserByDep extends Fragment  implements AdapterUser.ClickListener {

    Unbinder unbinder;
    @BindView(R.id.list)
    RecyclerView list;

    private AdapterUser adapter;
    private ArrayList<User> items = new ArrayList<>();
    private Department department;

    public static FragmentUserByDep newInstance(Department department){
        FragmentUserByDep fragment = new FragmentUserByDep();
        Bundle bundle = new Bundle();
        bundle.putParcelable("department",department);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("department",department);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState!=null) department = savedInstanceState.getParcelable("department");
        else if(getArguments()!=null) department = getArguments().getParcelable("department");
        else department = new Department();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
            case R.id.action_add:
                startActivity(new Intent(getActivity(), UserActivity.class).putExtra("department",department));
                return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_by_dep, container, false);
        unbinder = ButterKnife.bind(this, root);
        adapter = new AdapterUser(items);
        adapter.setOnItemClickListener(this);
        list.setAdapter(adapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {

        List<User> data = DaoManager.getInstance().getUsersByDep(department);
        if (data == null || data.size() < 1) {
            list.setVisibility(View.GONE);
        } else {
            items.clear();
            items.addAll(data);
            adapter.notifyDataSetChanged();
            list.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(User user) {
        // Открываем юзера
        startActivity(new Intent(getActivity(), UserActivity.class).putExtra("user", user));
    }
}
