package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContentMethod implements Parcelable {

    private List<ChildrenMethod>children;

    public List<ChildrenMethod> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenMethod> children) {
        this.children = children;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.children);
    }

    public ContentMethod() {
    }

    protected ContentMethod(Parcel in) {
        this.children = in.createTypedArrayList(ChildrenMethod.CREATOR);
    }

    public static final Parcelable.Creator<ContentMethod> CREATOR = new Parcelable.Creator<ContentMethod>() {
        @Override
        public ContentMethod createFromParcel(Parcel source) {
            return new ContentMethod(source);
        }

        @Override
        public ContentMethod[] newArray(int size) {
            return new ContentMethod[size];
        }
    };
}
