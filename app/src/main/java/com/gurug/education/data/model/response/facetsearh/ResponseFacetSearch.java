package com.gurug.education.data.model.response.facetsearh;

import android.os.Parcel;

import com.gurug.education.data.model.response.Response;

public class ResponseFacetSearch extends Response {
    private ResultFacets result;

    public ResultFacets getResult() {
        return result;
    }

    public void setResult(ResultFacets result) {
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

    public ResponseFacetSearch() {
    }

    protected ResponseFacetSearch(Parcel in) {
        super(in);
        this.result = in.readParcelable(ResultFacets.class.getClassLoader());
    }

    public static final Creator<ResponseFacetSearch> CREATOR = new Creator<ResponseFacetSearch>() {
        @Override
        public ResponseFacetSearch createFromParcel(Parcel source) {
            return new ResponseFacetSearch(source);
        }

        @Override
        public ResponseFacetSearch[] newArray(int size) {
            return new ResponseFacetSearch[size];
        }
    };
}
