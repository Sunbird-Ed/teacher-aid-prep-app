package com.gurug.education.data.model.response.settings;

import android.os.Parcel;
import android.os.Parcelable;

import com.gurug.education.data.model.response.framwork.Framework;

import java.util.ArrayList;

public class ChannelCustFrameWork implements Parcelable {


    private ArrayList<Framework> frameworks;

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
        dest.writeTypedList(this.frameworks);
    }

    public ChannelCustFrameWork() {
    }

    protected ChannelCustFrameWork(Parcel in) {
        this.frameworks = in.createTypedArrayList(Framework.CREATOR);
    }

    public static final Parcelable.Creator<ChannelCustFrameWork> CREATOR = new Parcelable.Creator<ChannelCustFrameWork>() {
        @Override
        public ChannelCustFrameWork createFromParcel(Parcel source) {
            return new ChannelCustFrameWork(source);
        }

        @Override
        public ChannelCustFrameWork[] newArray(int size) {
            return new ChannelCustFrameWork[size];
        }
    };
}
