package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Category implements Parcelable {

    private String identifier;
    private String code;
    private ArrayList<Terms >terms;

    private String translations;
    private String name;
    private String description;
    private String status;
    private Integer index;

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

    public ArrayList<Terms> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<Terms> terms) {
        this.terms = terms;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.identifier);
        dest.writeString(this.code);
        dest.writeTypedList(this.terms);
        dest.writeString(this.translations);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.status);
        dest.writeValue(this.index);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.identifier = in.readString();
        this.code = in.readString();
        this.terms = in.createTypedArrayList(Terms.CREATOR);
        this.translations = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.status = in.readString();
        this.index = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
