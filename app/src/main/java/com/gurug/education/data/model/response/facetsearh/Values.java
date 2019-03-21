package com.gurug.education.data.model.response.facetsearh;

import android.os.Parcel;
import android.os.Parcelable;

public class Values implements Parcelable {
    private String name;
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.count);
    }

    public Values() {
    }

    protected Values(Parcel in) {
        this.name = in.readString();
        this.count = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Values> CREATOR = new Parcelable.Creator<Values>() {
        @Override
        public Values createFromParcel(Parcel source) {
            return new Values(source);
        }

        @Override
        public Values[] newArray(int size) {
            return new Values[size];
        }
    };
}
