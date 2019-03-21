package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContentMethodResouces implements Parcelable {

    private List<ChildrenMethodResouces>children;

    public List<ChildrenMethodResouces> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenMethodResouces> children) {
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

    public ContentMethodResouces() {
    }

    protected ContentMethodResouces(Parcel in) {
        this.children = in.createTypedArrayList(ChildrenMethodResouces.CREATOR);
    }

    public static final Creator<ContentMethodResouces> CREATOR = new Creator<ContentMethodResouces>() {
        @Override
        public ContentMethodResouces createFromParcel(Parcel source) {
            return new ContentMethodResouces(source);
        }

        @Override
        public ContentMethodResouces[] newArray(int size) {
            return new ContentMethodResouces[size];
        }
    };
}
