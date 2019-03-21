package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Framework implements Parcelable {

    private String identifier;
    private String code;
    private String translations;
    private String name;
    private String description;
    private String type;
    private String objectType;

    private ArrayList<Category>categories;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
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
        dest.writeString(this.type);
        dest.writeString(this.objectType);
        dest.writeTypedList(this.categories);
    }

    public Framework() {
    }

    protected Framework(Parcel in) {
        this.identifier = in.readString();
        this.code = in.readString();
        this.translations = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.type = in.readString();
        this.objectType = in.readString();
        this.categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Parcelable.Creator<Framework> CREATOR = new Parcelable.Creator<Framework>() {
        @Override
        public Framework createFromParcel(Parcel source) {
            return new Framework(source);
        }

        @Override
        public Framework[] newArray(int size) {
            return new Framework[size];
        }
    };
}
