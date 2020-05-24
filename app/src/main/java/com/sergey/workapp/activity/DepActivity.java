package com.sergey.workapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.sergey.workapp.R;
import com.sergey.workapp.dao.DaoManager;
import com.sergey.workapp.dao.Department;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DepActivity extends AppCompatActivity {
    Department department;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_name)
    EditText itemName;
    @BindView(R.id.save)
    MaterialButton save;
    @BindView(R.id.delete)
    MaterialButton delete;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("department", department);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dep);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            department = savedInstanceState.getParcelable("department");
        } else if (getIntent() != null) {
            department = getIntent().getParcelableExtra("department");
        }

        setTitle(department != null ? "Редактирование" : "Создание");
        save.setText(department != null ? "Изменить" : "Создать");
        delete.setVisibility(department != null ? View.VISIBLE : View.GONE);
        itemName.setText(department != null ? department.getTitle() : null);

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
        if (department == null) department = new Department();
        department.setTitle(itemName.getText().toString());
        DaoManager.getInstance().addDep(department);
        onBackPressed();
    }

    @OnClick(R.id.delete)
    public void onDeleteClicked() {
        if (department != null) {
            new AlertDialog.Builder(this).setMessage("Удалить?").setNegativeButton("Нет", null).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    DaoManager.getInstance().deleteDep(department);
                    onBackPressed();
                    Toast.makeText(DepActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    }
}
