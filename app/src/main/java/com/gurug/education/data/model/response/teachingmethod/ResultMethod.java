package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultMethod implements Parcelable {

    private ContentMethod content;

    public ContentMethod getContent() {
        return content;
    }

    public void setContent(ContentMethod content) {
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

    public ResultMethod() {
    }

    protected ResultMethod(Parcel in) {
        this.content = in.readParcelable(ContentMethod.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultMethod> CREATOR = new Parcelable.Creator<ResultMethod>() {
        @Override
        public ResultMethod createFromParcel(Parcel source) {
            return new ResultMethod(source);
        }

        @Override
        public ResultMethod[] newArray(int size) {
            return new ResultMethod[size];
        }
    };
}
