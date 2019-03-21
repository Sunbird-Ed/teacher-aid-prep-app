package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultMethodBody implements Parcelable {
    private ContentMethodBody content;


    public ContentMethodBody getContent() {
        return content;
    }

    public void setContent(ContentMethodBody content) {
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

    public ResultMethodBody() {
    }

    protected ResultMethodBody(Parcel in) {
        this.content = in.readParcelable(ContentMethodBody.class.getClassLoader());
    }

    public static final Parcelable.Creator<ResultMethodBody> CREATOR = new Parcelable.Creator<ResultMethodBody>() {
        @Override
        public ResultMethodBody createFromParcel(Parcel source) {
            return new ResultMethodBody(source);
        }

        @Override
        public ResultMethodBody[] newArray(int size) {
            return new ResultMethodBody[size];
        }
    };
}
