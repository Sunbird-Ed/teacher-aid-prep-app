package com.gurug.education.data.model.response.frameworkdetail;

import android.os.Parcel;
import android.os.Parcelable;

public class Associations implements Parcelable {

    private String identifier;
    private String code;
    private String translations;
    private String name;
    private String description;
    private String category;
    private String status;

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
    }

    public Associations() {
    }

    protected Associations(Parcel in) {
        this.identifier = in.readString();
        this.code = in.readString();
        this.translations = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.category = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Associations> CREATOR = new Parcelable.Creator<Associations>() {
        @Override
        public Associations createFromParcel(Parcel source) {
            return new Associations(source);
        }

        @Override
        public Associations[] newArray(int size) {
            return new Associations[size];
        }
    };
}
