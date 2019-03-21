package com.gurug.education.data.model.response.teachingmethod;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity
public class ChildrenMethodResouces implements Parcelable {
    private String methodtype;
    private String pedagogyStep;
    private String description;
    private String duration;
    @NonNull
    @PrimaryKey
    private String identifier;
    private String contentType;

    private String name;
    private String posterImage;
    private String pedagogyId;


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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getPedagogyId() {
        return pedagogyId;
    }

    public void setPedagogyId(String pedagogyId) {
        this.pedagogyId = pedagogyId;
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
        dest.writeString(this.contentType);
        dest.writeString(this.name);
        dest.writeString(this.posterImage);
        dest.writeString(this.pedagogyId);
    }

    public ChildrenMethodResouces() {
    }

    protected ChildrenMethodResouces(Parcel in) {
        this.methodtype = in.readString();
        this.pedagogyStep = in.readString();
        this.description = in.readString();
        this.duration = in.readString();
        this.identifier = in.readString();
        this.contentType = in.readString();
        this.name = in.readString();
        this.posterImage = in.readString();
        this.pedagogyId = in.readString();
    }

    public static final Creator<ChildrenMethodResouces> CREATOR = new Creator<ChildrenMethodResouces>() {
        @Override
        public ChildrenMethodResouces createFromParcel(Parcel source) {
            return new ChildrenMethodResouces(source);
        }

        @Override
        public ChildrenMethodResouces[] newArray(int size) {
            return new ChildrenMethodResouces[size];
        }
    };
}
