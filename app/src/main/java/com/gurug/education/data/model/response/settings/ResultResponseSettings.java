package com.gurug.education.data.model.response.settings;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultResponseSettings implements Parcelable {

    private String id;
    private String field;
    private String value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.field);
        dest.writeString(this.value);
    }

    public ResultResponseSettings() {
    }

    protected ResultResponseSettings(Parcel in) {
        this.id = in.readString();
        this.field = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<ResultResponseSettings> CREATOR = new Parcelable.Creator<ResultResponseSettings>() {
        @Override
        public ResultResponseSettings createFromParcel(Parcel source) {
            return new ResultResponseSettings(source);
        }

        @Override
        public ResultResponseSettings[] newArray(int size) {
            return new ResultResponseSettings[size];
        }
    };
}
