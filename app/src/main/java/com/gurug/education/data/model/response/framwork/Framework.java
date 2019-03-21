package com.gurug.education.data.model.response.framwork;

import android.os.Parcel;
import android.os.Parcelable;

public class Framework implements Parcelable {
    private String identifier;
    private String name;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
        dest.writeString(this.identifier);
        dest.writeString(this.name);
    }

    public Framework() {
    }

    protected Framework(Parcel in) {
        this.identifier = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Framework> CREATOR = new Parcelable.Creator<Framework>() {
        @Override
        public Framework createFromParcel(Parcel source) {
            return new Framework(source);
        }

        @Override
        public Framework[] newArray(int size) {
            return new Framework[size];
        }
    };
}
