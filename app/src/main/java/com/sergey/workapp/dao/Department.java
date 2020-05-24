package com.sergey.workapp.dao;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Department implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String title;

    public static Department Builder(String title){
        Department department = new Department();
        department.title = title;
        return department;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    public Department() {
    }

    protected Department(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Department> CREATOR = new Parcelable.Creator<Department>() {
        @Override
        public Department createFromParcel(Parcel source) {
            return new Department(source);
        }

        @Override
        public Department[] newArray(int size) {
            return new Department[size];
        }
    };
}
