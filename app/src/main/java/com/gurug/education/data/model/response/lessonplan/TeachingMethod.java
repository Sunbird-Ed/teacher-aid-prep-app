package com.gurug.education.data.model.response.lessonplan;

import android.os.Parcel;
import android.os.Parcelable;

public class TeachingMethod implements Parcelable {
    private String id;
    private String shortDescription;
    private String longDescription;
    private String duration;
    private String pedagogyStep;
    private String methodType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPedagogyStep() {
        return pedagogyStep;
    }

    public void setPedagogyStep(String pedagogyStep) {
        this.pedagogyStep = pedagogyStep;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.longDescription);
        dest.writeString(this.duration);
        dest.writeString(this.pedagogyStep);
        dest.writeString(this.methodType);
    }

    public TeachingMethod() {
    }

    protected TeachingMethod(Parcel in) {
        this.id = in.readString();
        this.shortDescription = in.readString();
        this.longDescription = in.readString();
        this.duration = in.readString();
        this.pedagogyStep = in.readString();
        this.methodType = in.readString();
    }

    public static final Creator<TeachingMethod> CREATOR = new Creator<TeachingMethod>() {
        @Override
        public TeachingMethod createFromParcel(Parcel source) {
            return new TeachingMethod(source);
        }

        @Override
        public TeachingMethod[] newArray(int size) {
            return new TeachingMethod[size];
        }
    };
}
