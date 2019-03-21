package com.gurug.education.data.model.response.settings;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseSettings extends Response {

    private ResultSettings result;

    public ResultSettings getResult() {
        return result;
    }

    public void setResult(ResultSettings result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.result, flags);
    }

    public ResponseSettings() {
    }

    protected ResponseSettings(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultSettings.class.getClassLoader());
    }

    public static final Creator<ResponseSettings> CREATOR = new Creator<ResponseSettings>() {
        @Override
        public ResponseSettings createFromParcel(Parcel source) {
            return new ResponseSettings(source);
        }

        @Override
        public ResponseSettings[] newArray(int size) {
            return new ResponseSettings[size];
        }
    };
}
