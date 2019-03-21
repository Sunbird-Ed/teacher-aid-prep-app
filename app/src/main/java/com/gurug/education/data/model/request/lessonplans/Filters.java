package com.gurug.education.data.model.request.lessonplans;

import java.util.ArrayList;

public class Filters {

    private ArrayList<String> contentType;
    private ArrayList<String> status;
    private ArrayList<String> objectType;
    private ArrayList<String> board;
    private ArrayList<String> gradeLevel;
    private ArrayList<String> subject;
    private ArrayList<String> medium;
    private ArrayList<String> resourceType;

    private String topics;

    public ArrayList<String> getContentType() {
        return contentType;
    }

    public void setContentType(ArrayList<String> contentType) {
        this.contentType = contentType;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }

    public ArrayList<String> getObjectType() {
        return objectType;
    }

    public void setObjectType(ArrayList<String> objectType) {
        this.objectType = objectType;
    }

    public ArrayList<String> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<String> board) {
        this.board = board;
    }

    public ArrayList<String> getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(ArrayList<String> gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public ArrayList<String> getSubject() {
        return subject;
    }

    public void setSubject(ArrayList<String> subject) {
        this.subject = subject;
    }

    public ArrayList<String> getMedium() {
        return medium;
    }

    public void setMedium(ArrayList<String> medium) {
        this.medium = medium;
    }

    public ArrayList<String> getResourceType() {
        return resourceType;
    }

    public void setResourceType(ArrayList<String> resourceType) {
        this.resourceType = resourceType;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }
}
