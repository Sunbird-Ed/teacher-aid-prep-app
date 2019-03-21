package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseMethodDetail extends Response {
    private ResultMethod result;


    public ResultMethod getResult() {
        return result;
    }

    public void setResult(ResultMethod result) {
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

    public ResponseMethodDetail() {
    }

    protected ResponseMethodDetail(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultMethod.class.getClassLoader());
    }

    public static final Creator<ResponseMethodDetail> CREATOR = new Creator<ResponseMethodDetail>() {
        @Override
        public ResponseMethodDetail createFromParcel(Parcel source) {
            return new ResponseMethodDetail(source);
        }

        @Override
        public ResponseMethodDetail[] newArray(int size) {
            return new ResponseMethodDetail[size];
        }
    };
}
