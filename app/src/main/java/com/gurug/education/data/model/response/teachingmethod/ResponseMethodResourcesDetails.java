package com.gurug.education.data.model.response.teachingmethod;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseMethodResourcesDetails extends Response {
    private ResultMethodResources result;


    public ResultMethodResources getResult() {
        return result;
    }

    public void setResult(ResultMethodResources result) {
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

    public ResponseMethodResourcesDetails() {
    }

    protected ResponseMethodResourcesDetails(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultMethod.class.getClassLoader());
    }

    public static final Creator<ResponseMethodResourcesDetails> CREATOR = new Creator<ResponseMethodResourcesDetails>() {
        @Override
        public ResponseMethodResourcesDetails createFromParcel(Parcel source) {
            return new ResponseMethodResourcesDetails(source);
        }

        @Override
        public ResponseMethodResourcesDetails[] newArray(int size) {
            return new ResponseMethodResourcesDetails[size];
        }
    };
}
