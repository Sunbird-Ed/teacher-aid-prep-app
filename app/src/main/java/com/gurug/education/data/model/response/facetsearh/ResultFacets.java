package com.gurug.education.data.model.response.facetsearh;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ResultFacets implements Parcelable {
    private List<Facet>facets;

    public List<Facet> getFacets() {
        return facets;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.facets);
    }

    public ResultFacets() {
    }

    protected ResultFacets(Parcel in) {
        this.facets = in.createTypedArrayList(Facet.CREATOR);
    }

    public static final Parcelable.Creator<ResultFacets> CREATOR = new Parcelable.Creator<ResultFacets>() {
        @Override
        public ResultFacets createFromParcel(Parcel source) {
            return new ResultFacets(source);
        }

        @Override
        public ResultFacets[] newArray(int size) {
            return new ResultFacets[size];
        }
    };
}
