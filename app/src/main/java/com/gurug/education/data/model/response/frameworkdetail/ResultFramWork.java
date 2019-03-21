package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultFramWork implements Parcelable {
    private Framework framework;

    public Framework getFramework() {
        return framework;
    }

    public void setFramework(Framework framework) {
        this.framework = framework;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.framework, flags);
    }

    public ResultFramWork() {
    }

    protected ResultFramWork(Parcel in) {
        this.framework = in.readParcelable(Framework.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultFramWork> CREATOR = new Parcelable.Creator<ResultFramWork>() {
        @Override
        public ResultFramWork createFromParcel(Parcel source) {
            return new ResultFramWork(source);
        }

        @Override
        public ResultFramWork[] newArray(int size) {
            return new ResultFramWork[size];
        }
    };
}
