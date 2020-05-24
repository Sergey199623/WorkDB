package com.sergey.workapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sergey.workapp.R;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;
import com.sergey.workapp.dao.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends AppCompatActivity {
    User user;
    Department department;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_name)
    EditText itemName;
    @BindView(R.id.item_family)
    EditText itemFamily;
    @BindView(R.id.item_role)
    EditText itemRole;
    @BindView(R.id.item_address)
    EditText itemAddress;
    @BindView(R.id.item_dep)
    MaterialSpinner itemDep;
    @BindView(R.id.item_homework)
    Switch itemHomework;
    @BindView(R.id.save)
    MaterialButton save;
    @BindView(R.id.delete)
    MaterialButton delete;
    List<Department> deps;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("user", user);
        outState.putParcelable("department", department);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            user = savedInstanceState.getParcelable("user");
            department = savedInstanceState.getParcelable("department");
        } else if (getIntent() != null) {
            user = getIntent().getParcelableExtra("user");
            department = getIntent().getParcelableExtra("department");
        }

        setTitle(user != null ? "Редактирование" : "Создание");
        save.setText(user != null ? "Изменить" : "Создать");
        delete.setVisibility(user != null ? View.VISIBLE : View.GONE);

        itemName.setText(user != null ? user.getName() : null);
        itemFamily.setText(user != null ? user.getFamily() : null);
        itemRole.setText(user != null ? user.getRole() : null);
        itemAddress.setText(user != null ? user.getAddress() : null);

        deps = DaoManager.getInstance().getAllDeps();
        itemDep.setItems(deps);
        if (department != null || user != null) {
            int index = 0;
            for (int i = 0; i < deps.size(); i++) {
                Department dep = deps.get(i);
                if (department != null) {
                    if (dep.getId() == department.getId()) {
                        index = i;
                        break;
                    }
                } else if (user != null && user.getDepartment() != null) {
                    if (dep.getId() == user.getDepartment().getId()) {
                        index = i;
                        break;
                    }
                }

            }
            itemDep.setSelectedIndex(index);
        }
        itemHomework.setChecked(user != null && user.isHomework());

        //Log.i("DEV",DaoManager.getInstance().getUsersByDep(department).size()+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.save)
    public void onSaveClicked() {
        if (user == null) user = new User();
        user.setName(itemName.getText().toString());
        user.setFamily(itemFamily.getText().toString());
        user.setRole(itemRole.getText().toString());
        user.setAddress(itemAddress.getText().toString());
        user.setHomework(itemHomework.isChecked());
        user.setDepartment((Department) itemDep.getItems().get(itemDep.getSelectedIndex()));
        DaoManager.getInstance().addUser(user);
        onBackPressed();
    }

    @OnClick(R.id.delete)
    public void onDeleteClicked() {
        if (user != null) {
            new AlertDialog.Builder(this).setMessage("Удалить?").setNegativeButton("Нет", null).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DaoManager.getInstance().deleteUser(user);
                    onBackPressed();
                    Toast.makeText(UserActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    }
}
