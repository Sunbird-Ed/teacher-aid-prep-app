package com.gurug.education.data.model.response.settings;

import android.os.Parcel;
import android.os.Parcelable;

import com.gurug.education.data.model.response.framwork.Framework;

import java.util.ArrayList;
import java.util.List;

public class ResultCustFramework implements Parcelable {

    private ChannelCustFrameWork channel;

    public ChannelCustFrameWork getChannel() {
        return channel;
    }

    public void setChannel(ChannelCustFrameWork channel) {
        this.channel = channel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.channel, flags);
    }

    public ResultCustFramework() {
    }

    protected ResultCustFramework(Parcel in) {
        this.channel = in.readParcelable(ChannelCustFrameWork.class.getClassLoader());
    }

    public static final Creator<ResultCustFramework> CREATOR = new Creator<ResultCustFramework>() {
        @Override
        public ResultCustFramework createFromParcel(Parcel source) {
            return new ResultCustFramework(source);
        }

        @Override
        public ResultCustFramework[] newArray(int size) {
            return new ResultCustFramework[size];
        }
    };
}
