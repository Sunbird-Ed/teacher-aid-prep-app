package com.gurug.education.data.model.response.framwork;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ResultFramwork implements Parcelable {
    private ArrayList<Framework>frameworks;

    public ArrayList<Framework> getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(ArrayList<Framework> frameworks) {
        this.frameworks = frameworks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.frameworks);
    }

    public ResultFramwork() {
    }

    protected ResultFramwork(Parcel in) {
        this.frameworks = new ArrayList<Framework>();
        in.readList(this.frameworks, Framework.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultFramwork> CREATOR = new Parcelable.Creator<ResultFramwork>() {
        @Override
        public ResultFramwork createFromParcel(Parcel source) {
            return new ResultFramwork(source);
        }

        @Override
        public ResultFramwork[] newArray(int size) {
            return new ResultFramwork[size];
        }
    };
}
