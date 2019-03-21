package com.gurug.education.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

public class Response implements Parcelable {

    private Throwable httpError;

    public Throwable getHttpError() {
        return httpError;
    }

    public void setHttpError(Throwable httpError) {
        this.httpError = httpError;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.httpError);
    }

    public Response() {
    }

    protected Response(Parcel in) {
        this.httpError = (Throwable) in.readSerializable();
    }

}
