package com.gurug.education.data.model.response.facetsearh;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Facet implements Parcelable {
    private List<Values>values;
    private String name;

    public List<Values> getValues() {
        return values;
    }

    public void setValues(List<Values> values) {
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.values);
        dest.writeString(this.name);
    }

    public Facet() {
    }

    protected Facet(Parcel in) {
        this.values = in.createTypedArrayList(Values.CREATOR);
        this.name = in.readString();
    }

    public static final Creator<Facet> CREATOR = new Creator<Facet>() {
        @Override
        public Facet createFromParcel(Parcel source) {
            return new Facet(source);
        }

        @Override
        public Facet[] newArray(int size) {
            return new Facet[size];
        }
    };
}
