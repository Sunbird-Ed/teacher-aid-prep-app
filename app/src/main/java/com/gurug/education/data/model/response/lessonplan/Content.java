package com.gurug.education.data.model.response.lessonplan;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Content implements Parcelable {


    private String total_duration;
    private String name;
    private String lessPlanFlow;
    private String objectType;

    private String id;
    @NonNull
    @PrimaryKey
    private String identifier;
    @TypeConverters(Converters.class) // add here
    private List<String> gradeLevel;
    private String subject;
    @SerializedName("topics")
    private String topic;
    private String channel;
    private String description;
    @SerializedName("pedagogySteps")
    private String pedagogyFlow;
    private String medium;
    private String board;
    private String totalDuration = "45";

    private String grade;

    private boolean isBookMarked;
    private boolean isDone;
/*

    @Ignore
    ArrayList<TeachingMethod> teachingMethods;
*/

    public String getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(String total_duration) {
        this.total_duration = total_duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLessPlanFlow() {
        return lessPlanFlow;
    }

    public void setLessPlanFlow(String lessPlanFlow) {
        this.lessPlanFlow = lessPlanFlow;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<String> getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(List<String> gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPedagogyFlow() {
        return pedagogyFlow;
    }

    public void setPedagogyFlow(String pedagogyFlow) {
        this.pedagogyFlow = pedagogyFlow;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    /*
    public ArrayList<TeachingMethod> getTeachingMethods() {
        return teachingMethods;
    }

    public void setTeachingMethods(ArrayList<TeachingMethod> teachingMethods) {
        this.teachingMethods = teachingMethods;
    }*/


    public Content() {
    }

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.total_duration);
        dest.writeString(this.name);
        dest.writeString(this.lessPlanFlow);
        dest.writeString(this.objectType);
        dest.writeString(this.id);
        dest.writeString(this.identifier);
        dest.writeStringList(this.gradeLevel);
        dest.writeString(this.subject);
        dest.writeString(this.topic);
        dest.writeString(this.channel);
        dest.writeString(this.description);
        dest.writeString(this.pedagogyFlow);
        dest.writeString(this.medium);
        dest.writeString(this.board);
        dest.writeString(this.totalDuration);
        dest.writeString(this.grade);
        dest.writeByte(this.isBookMarked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDone ? (byte) 1 : (byte) 0);
    }

    protected Content(Parcel in) {
        this.total_duration = in.readString();
        this.name = in.readString();
        this.lessPlanFlow = in.readString();
        this.objectType = in.readString();
        this.id = in.readString();
        this.identifier = in.readString();
        this.gradeLevel = in.createStringArrayList();
        this.subject = in.readString();
        this.topic = in.readString();
        this.channel = in.readString();
        this.description = in.readString();
        this.pedagogyFlow = in.readString();
        this.medium = in.readString();
        this.board = in.readString();
        this.totalDuration = in.readString();
        this.grade = in.readString();
        this.isBookMarked = in.readByte() != 0;
        this.isDone = in.readByte() != 0;
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };
}
