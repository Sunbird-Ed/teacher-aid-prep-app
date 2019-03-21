package com.gurug.education.data.model.response.lessonplan;

import android.os.Parcel;
import android.os.Parcelable;

public class Topic implements Parcelable {
    /*
    "id": "topic1",
    "topic": "To learn and understand the postulates of Dalton’s atomic theory.To describe J.J. Thomson’s experiment on the discovery of electrons.",
    "subject": "Science",
    "class": "class 8",
    "board": "State (Tamil Nadu)",
    "medium": "English"
     */

    private String id;
    private String topic;
    private String subject;
    private String grade;
    private String board;
    private String medium;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.topic);
        dest.writeString(this.subject);
        dest.writeString(this.grade);
        dest.writeString(this.board);
        dest.writeString(this.medium);
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.topic = in.readString();
        this.subject = in.readString();
        this.grade = in.readString();
        this.board = in.readString();
        this.medium = in.readString();
    }

    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        @Override
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        @Override
        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
