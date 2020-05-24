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
import com.sergey.workapp.adapter.AdapterDep;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentDepartment extends Fragment implements AdapterDep.ClickListener {

    Unbinder unbinder;
    @BindView(R.id.list)
    RecyclerView list;

    private AdapterDep adapter;
    private ArrayList<Department> items = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                startActivity(new Intent(getActivity(), DepActivity.class));
                return true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dep_all, container, false);
        unbinder = ButterKnife.bind(this, root);
        adapter = new AdapterDep(items);
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

        List<Department> data = DaoManager.getInstance().getAllDeps();
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
    public void onItemClick(Department department) {
        // Открываем департамент
        startActivity(new Intent(getActivity(), DepActivity.class).putExtra("department", department));
    }
}
