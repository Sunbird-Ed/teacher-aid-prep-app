package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultMethodResources implements Parcelable {

    private ContentMethodResouces content;

    public ContentMethodResouces getContent() {
        return content;
    }

    public void setContent(ContentMethodResouces content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.content, flags);
    }

    public ResultMethodResources() {
    }

    protected ResultMethodResources(Parcel in) {
        this.content = in.readParcelable(ContentMethod.class.getClassLoader());
    }

    public static final Creator<ResultMethodResources> CREATOR = new Creator<ResultMethodResources>() {
        @Override
        public ResultMethodResources createFromParcel(Parcel source) {
            return new ResultMethodResources(source);
        }

        @Override
        public ResultMethodResources[] newArray(int size) {
            return new ResultMethodResources[size];
        }
    };
}
