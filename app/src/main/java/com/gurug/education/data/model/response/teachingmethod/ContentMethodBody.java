package com.gurug.education.data.model.response.teachingmethod;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class ContentMethodBody implements Parcelable{
    private String methodtype;
    private String pedagogyStep;
    private String description;
    private String duration;
    @NonNull
    @PrimaryKey
    private String identifier;
    private String body;


    public String getMethodtype() {
        return methodtype;
    }

    public void setMethodtype(String methodtype) {
        this.methodtype = methodtype;
    }

    public String getPedagogyStep() {
        return pedagogyStep;
    }

    public void setPedagogyStep(String pedagogyStep) {
        this.pedagogyStep = pedagogyStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.methodtype);
        dest.writeString(this.pedagogyStep);
        dest.writeString(this.description);
        dest.writeString(this.duration);
        dest.writeString(this.identifier);
        dest.writeString(this.body);
    }

    public ContentMethodBody() {
    }

    protected ContentMethodBody(Parcel in) {
        this.methodtype = in.readString();
        this.pedagogyStep = in.readString();
        this.description = in.readString();
        this.duration = in.readString();
        this.identifier = in.readString();
        this.body = in.readString();
    }

    public static final Creator<ContentMethodBody> CREATOR = new Creator<ContentMethodBody>() {
        @Override
        public ContentMethodBody createFromParcel(Parcel source) {
            return new ContentMethodBody(source);
        }

        @Override
        public ContentMethodBody[] newArray(int size) {
            return new ContentMethodBody[size];
        }
    };
}
