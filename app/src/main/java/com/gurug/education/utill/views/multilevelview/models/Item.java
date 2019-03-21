package com.gurug.education.utill.views.multilevelview.models;

public class Item extends RecyclerViewItem {

    String text="";

    String secondText = "";

    public String getSecondText() {
        return secondText;
    }

    public void setSecondText(String secondText) {
        this.secondText = secondText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    public Item(int level) {
        super(level);
    }
}
