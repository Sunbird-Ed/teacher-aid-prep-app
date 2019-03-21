package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ReponseMethodBody extends Response {

    private ResultMethodBody result;

    public ResultMethodBody getResult() {
        return result;
    }

    public void setResult(ResultMethodBody result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.result, flags);
    }

    public ReponseMethodBody() {
    }

    protected ReponseMethodBody(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultMethodBody.class.getClassLoader());
    }

    public static final Creator<ReponseMethodBody> CREATOR = new Creator<ReponseMethodBody>() {
        @Override
        public ReponseMethodBody createFromParcel(Parcel source) {
            return new ReponseMethodBody(source);
        }

        @Override
        public ReponseMethodBody[] newArray(int size) {
            return new ReponseMethodBody[size];
        }
    };
}
