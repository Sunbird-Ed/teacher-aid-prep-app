package com.gurug.education.data.model.request.lessonplans;

import java.util.ArrayList;
import java.util.List;

public class Request {
    private Filters filters;
    private ArrayList<String> fields;
    private Integer limit = 200;
    private Integer offset = 0;

    private List<String>facets;

    public List<String> getFacets() {
        return facets;
    }

    public void setFacets(List<String> facets) {
        this.facets = facets;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
