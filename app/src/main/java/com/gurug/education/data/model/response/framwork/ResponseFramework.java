package com.gurug.education.data.model.response.framwork;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseFramework extends Response {
    private ResultFramwork result;

    public ResultFramwork getResult() {
        return result;
    }

    public void setResult(ResultFramwork result) {
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

    public ResponseFramework() {
    }

    protected ResponseFramework(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultFramwork.class.getClassLoader());
    }

    public static final Creator<ResponseFramework> CREATOR = new Creator<ResponseFramework>() {
        @Override
        public ResponseFramework createFromParcel(Parcel source) {
            return new ResponseFramework(source);
        }

        @Override
        public ResponseFramework[] newArray(int size) {
            return new ResponseFramework[size];
        }
    };
}
