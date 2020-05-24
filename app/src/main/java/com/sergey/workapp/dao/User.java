package com.sergey.workapp.dao;

import android.os.Parcel;
import android.os.Parcelable;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class User implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String family;
    @DatabaseField
    private String role;
    @DatabaseField
    private String address;
    @DatabaseField
    private boolean homework;

    @DatabaseField(foreign = true)
    private Department department;

    public static User Builder(String name, String family, String role, Department department){
        User user = new User();
        user.name = name;
        user.family = family;
        user.role = role;
        user.department = department;
        return user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        if(name==null) return "";
        return name;
    }

    public String getFamily() {
        if(family==null) return "";
        return family;
    }

    public String getRole() {
        return role;
    }

    public String getAddress() {
        return address;
    }

    public boolean isHomework() {
        return homework;
    }

    public Department getDepartment() {
        return department;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHomework(boolean homework) {
        this.homework = homework;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.family);
        dest.writeString(this.role);
        dest.writeString(this.address);
        dest.writeByte(this.homework ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.department, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.family = in.readString();
        this.role = in.readString();
        this.address = in.readString();
        this.homework = in.readByte() != 0;
        this.department = in.readParcelable(Department.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}