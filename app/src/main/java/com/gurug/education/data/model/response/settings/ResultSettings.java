package com.gurug.education.data.model.response.settings;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResultSettings implements Parcelable {

    private List<ResultResponseSettings>response;

    public List<ResultResponseSettings> getResponse() {
        return response;
    }

    public void setResponse(List<ResultResponseSettings> response) {
        this.response = response;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.response);
    }

    public ResultSettings() {
    }

    protected ResultSettings(Parcel in) {
        this.response = in.createTypedArrayList(ResultResponseSettings.CREATOR);
    }

    public static final Parcelable.Creator<ResultSettings> CREATOR = new Parcelable.Creator<ResultSettings>() {
        @Override
        public ResultSettings createFromParcel(Parcel source) {
            return new ResultSettings(source);
        }

        @Override
        public ResultSettings[] newArray(int size) {
            return new ResultSettings[size];
        }
    };
}
