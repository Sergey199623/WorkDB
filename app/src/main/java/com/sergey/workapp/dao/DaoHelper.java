package com.sergey.workapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class DaoHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "DB.sqlite";
    private static final int DATABASE_VERSION = 1;

    private DepDAO depDao = null;
    private UserDAO userDAO = null;

    public DaoHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, Department.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);

            // Тестовые данные
            Department department1 = Department.Builder("Начальство");
            Department department2 = Department.Builder("Доставка");
            Department department3 = Department.Builder("Департамент цветов");
            getDepDAO().createOrUpdate(department1);
            getDepDAO().createOrUpdate(department2);
            getDepDAO().createOrUpdate(department3);

            getUserDAO().createOrUpdate(User.Builder("Стас","Михайлов","Первый зам",department1));
            getUserDAO().createOrUpdate(User.Builder("Григорий","Лепс","Секретарь",department1));
            getUserDAO().createOrUpdate(User.Builder("Валерий","Леонтьев","Кладовщик",department2));
            getUserDAO().createOrUpdate(User.Builder("Гришаева","Нонна","Продавец",department3));
            //

        } catch (Exception e) {
            if (e.getMessage() != null) Log.e("DEV", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        ///
    }

    public DepDAO getDepDAO() {
        if (null == depDao) {
            try {
                depDao = new DepDAO(getConnectionSource(), Department.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return depDao;
    }

    public UserDAO getUserDAO() {
        if (null == userDAO) {
            try {
                userDAO = new UserDAO(getConnectionSource(), User.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return userDAO;
    }

    public static class DepDAO extends BaseDaoImpl<Department, Integer> {

        protected DepDAO(ConnectionSource connectionSource, Class<Department> dataClass) throws java.sql.SQLException {
            super(connectionSource, dataClass);
        }

        public List<Department> getAll() throws java.sql.SQLException {
            return this.queryForAll();
        }

        public void createOrUpdateDepartment(Department dep) throws java.sql.SQLException {
            QueryBuilder<Department, Integer> queryBuilder = queryBuilder();
            queryBuilder.where().eq("id", dep.getId());
            PreparedQuery<Department> preparedQuery = queryBuilder.prepare();
            List<Department> finded = query(preparedQuery);
            if (finded.isEmpty()) {
                this.createOrUpdate(dep);
            } else this.update(dep);
        }
    }

    public static class UserDAO extends BaseDaoImpl<User, Integer> {

        protected UserDAO(ConnectionSource connectionSource, Class<User> dataClass) throws java.sql.SQLException {
            super(connectionSource, dataClass);
        }

        public List<User> getAll() throws java.sql.SQLException {
            return this.queryForAll();
        }

        public void createOrUpdateUser(User user) throws java.sql.SQLException {
            QueryBuilder<User, Integer> queryBuilder = queryBuilder();
            queryBuilder.where().eq("id", user.getId());
            PreparedQuery<User> preparedQuery = queryBuilder.prepare();
            List<User> finded = query(preparedQuery);
            if (finded.isEmpty()) {
                this.createOrUpdate(user);
            } else this.update(user);
        }
    }
}
