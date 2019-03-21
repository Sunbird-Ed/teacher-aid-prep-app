package com.gurug.education.utill.views.multilevelview;

import android.view.View;

import com.gurug.education.utill.views.multilevelview.models.RecyclerViewItem;


public interface OnRecyclerItemClickListener {
    void onItemClick(View view, RecyclerViewItem item, int position);
}