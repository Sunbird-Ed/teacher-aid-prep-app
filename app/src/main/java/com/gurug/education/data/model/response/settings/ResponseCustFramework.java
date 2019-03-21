package com.gurug.education.data.model.response.settings;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseCustFramework extends Response {
    private ResultCustFramework result;

    public ResultCustFramework getResult() {
        return result;
    }

    public void setResult(ResultCustFramework result) {
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

    public ResponseCustFramework() {
    }

    protected ResponseCustFramework(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultCustFramework.class.getClassLoader());
    }

    public static final Creator<ResponseCustFramework> CREATOR = new Creator<ResponseCustFramework>() {
        @Override
        public ResponseCustFramework createFromParcel(Parcel source) {
            return new ResponseCustFramework(source);
        }

        @Override
        public ResponseCustFramework[] newArray(int size) {
            return new ResponseCustFramework[size];
        }
    };
}
