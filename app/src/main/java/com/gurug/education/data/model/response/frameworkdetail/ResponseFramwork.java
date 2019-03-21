package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseFramwork extends Response {

    private String id;
    private String ver;
    private String ts;
    private ResultFramWork result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public ResultFramWork getResult() {
        return result;
    }

    public void setResult(ResultFramWork result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.ver);
        dest.writeString(this.ts);
        dest.writeParcelable(this.result, flags);
    }

    public ResponseFramwork() {
    }

    protected ResponseFramwork(Parcel in) {
        super(in);
        this.id = in.readString();
        this.ver = in.readString();
        this.ts = in.readString();
        this.result = in.readParcelable(ResultFramWork.class.getClassLoader());
    }

    public static final Creator<ResponseFramwork> CREATOR = new Creator<ResponseFramwork>() {
        @Override
        public ResponseFramwork createFromParcel(Parcel source) {
            return new ResponseFramwork(source);
        }

        @Override
        public ResponseFramwork[] newArray(int size) {
            return new ResponseFramwork[size];
        }
    };
}
