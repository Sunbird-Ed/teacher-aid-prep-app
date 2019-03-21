package com.gurug.education.data.model.response.lessonplan;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseLessonPlan extends Response {
    private String id;
    private String ver;
    private String ts;

    private ResultLessonPlan result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public ResultLessonPlan getResult() {
        return result;
    }

    public void setResult(ResultLessonPlan result) {
        this.result = result;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.ver);
        dest.writeString(this.ts);
        dest.writeParcelable(this.result, flags);
    }

    public ResponseLessonPlan() {
    }

    protected ResponseLessonPlan(Parcel in) {
        super(in);
        this.id = in.readString();
        this.ver = in.readString();
        this.ts = in.readString();
        this.result = in.readParcelable(ResultLessonPlan.class.getClassLoader());
    }

    public static final Creator<ResponseLessonPlan> CREATOR = new Creator<ResponseLessonPlan>() {
        @Override
        public ResponseLessonPlan createFromParcel(Parcel source) {
            return new ResponseLessonPlan(source);
        }

        @Override
        public ResponseLessonPlan[] newArray(int size) {
            return new ResponseLessonPlan[size];
        }
    };
}
