package com.gurug.education.data.model.response.lessonplan;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ResultLessonPlan implements Parcelable {
    private Integer count;

    private ArrayList<Content> content;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.count);
        dest.writeTypedList(this.content);
    }

    public ResultLessonPlan() {
    }

    protected ResultLessonPlan(Parcel in) {
        this.count = (Integer) in.readValue(Integer.class.getClassLoader());
        this.content = in.createTypedArrayList(Content.CREATOR);
    }

    public static final Parcelable.Creator<ResultLessonPlan> CREATOR = new Parcelable.Creator<ResultLessonPlan>() {
        @Override
        public ResultLessonPlan createFromParcel(Parcel source) {
            return new ResultLessonPlan(source);
        }

        @Override
        public ResultLessonPlan[] newArray(int size) {
            return new ResultLessonPlan[size];
        }
    };
}
