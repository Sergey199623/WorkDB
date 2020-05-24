package com.sergey.workapp.dao;

import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import java.util.List;

public class DaoManager {
    static private DaoManager instance;
    private DaoHelper helper;

    private DaoManager(Context ctx) {
        helper = new DaoHelper(ctx);
    }

    static public void init(Context ctx) {
        if (null == instance) {
            instance = new DaoManager(ctx);
        }
    }

    static public DaoManager getInstance() {
        return instance;
    }

    private DaoHelper getHelper() {
        return helper;
    }

    public static void terminate() {
        instance = null;
    }


    public void refreshDep(Department department){
        try {
            getHelper().getDepDAO().refresh(department);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }
    public List<Department> getAllDeps() {
        List<Department> items = null;
        try {
            items = getHelper().getDepDAO().getAll();
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
        return items;
    }

    public List<User> getAllUsers() {
        List<User> items = null;
        try {
            items = getHelper().getUserDAO().getAll();
        } catch (SQLException | java.sql.SQLException e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
        return items;
    }

    public List<User> getUsersByDep(Department department) {
        List<User> items = null;
        try {
            items = getHelper().getUserDAO().queryBuilder().where().eq("department_id",department.getId()).query();
        } catch (SQLException | java.sql.SQLException e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
        return items;
    }


    public void addDep(Department department) {
        try {
            getHelper().getDepDAO().createOrUpdate(department);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }
    
    public void deleteDep(Department department) {
        try {
            getHelper().getDepDAO().delete(department);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }
    public void addUser(User user) {
        try {
            getHelper().getUserDAO().createOrUpdate(user);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }
    
    public void deleteUser(User user) {
        try {
            getHelper().getUserDAO().delete(user);
        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }

}
