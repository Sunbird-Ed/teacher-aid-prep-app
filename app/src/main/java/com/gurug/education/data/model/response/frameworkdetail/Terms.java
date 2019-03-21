package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Terms implements Parcelable {
    private String identifier;
    private String code;
    private String translations;
    private String name;
    private String description;
    private String category;
    private String status;
    private Integer index;

    private ArrayList<Associations>associations;
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public ArrayList<Associations> getAssociations() {
        return associations;
    }

    public void setAssociations(ArrayList<Associations> associations) {
        this.associations = associations;
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
        dest.writeString(this.category);
        dest.writeString(this.status);
        dest.writeValue(this.index);
        dest.writeTypedList(this.associations);
        dest.writeTypedList(this.children);
    }

    public Terms() {
    }

    protected Terms(Parcel in) {
        this.identifier = in.readString();
        this.code = in.readString();
        this.translations = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.status = in.readString();
        this.index = (Integer) in.readValue(Integer.class.getClassLoader());
        this.associations = in.createTypedArrayList(Associations.CREATOR);
        this.children = in.createTypedArrayList(FrameWorkChild.CREATOR);
    }

    public static final Creator<Terms> CREATOR = new Creator<Terms>() {
        @Override
        public Terms createFromParcel(Parcel source) {
            return new Terms(source);
        }

        @Override
        public Terms[] newArray(int size) {
            return new Terms[size];
        }
    };


}
