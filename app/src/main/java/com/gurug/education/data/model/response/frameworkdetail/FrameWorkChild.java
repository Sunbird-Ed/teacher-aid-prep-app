package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FrameWorkChild implements Parcelable {

    private String identifier;
    private String code;
    private String translations;
    private String name;
    private String description;
    private Integer index;
    private String category;
    private String status;

    private ArrayList<FrameWorkChild>children = new ArrayList<>();


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<FrameWorkChild> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<FrameWorkChild> children) {
        this.children = children;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.identifier);
        dest.writeString(this.code);
        dest.writeString(this.translations);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeValue(this.index);
        dest.writeString(this.category);
        dest.writeString(this.status);
        dest.writeTypedList(this.children);
    }

    public FrameWorkChild() {
    }

    protected FrameWorkChild(Parcel in) {
        this.identifier = in.readString();
        this.code = in.readString();
        this.translations = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.index = (Integer) in.readValue(Integer.class.getClassLoader());
        this.category = in.readString();
        this.status = in.readString();
        this.children = in.createTypedArrayList(FrameWorkChild.CREATOR);
    }

    public static final Creator<FrameWorkChild> CREATOR = new Creator<FrameWorkChild>() {
        @Override
        public FrameWorkChild createFromParcel(Parcel source) {
            return new FrameWorkChild(source);
        }

        @Override
        public FrameWorkChild[] newArray(int size) {
            return new FrameWorkChild[size];
        }
    };
}
