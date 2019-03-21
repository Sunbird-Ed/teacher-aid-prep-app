package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.utill.AppConstants;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectClassAdapter extends RecyclerView.Adapter<SelectClassAdapter.ViewHolderClass> {

    private Context mContext;

    private ArrayList<String> mClasses;

    private OnClickClassItem mOnClickClassItem;

    public SelectClassAdapter(Context context) {
        mContext = context;
        mClasses = new ArrayList<>();
        mOnClickClassItem = (OnClickClassItem) mContext;
    }

    @NonNull
    @Override
    public SelectClassAdapter.ViewHolderClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderClass(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_framework, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectClassAdapter.ViewHolderClass viewHolderTopic, int pos) {
        viewHolderTopic.mTvTitle.setText(mClasses.get(pos).split(AppConstants.COLON)[0] + "\n" + mClasses.get(pos).split(AppConstants.COLON)[1]);
        viewHolderTopic.mIbDelete.setVisibility(View.GONE);
        viewHolderTopic.itemView.setOnClickListener(v -> {
            mOnClickClassItem.onClickClass(mClasses.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public void update(ArrayList<String> classes) {
        if (classes != null) {
            mClasses = classes;
            Collections.sort(mClasses, String::compareTo);

            notifyDataSetChanged();
        }
    }

    class ViewHolderClass extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @BindView(R.id.ib_delete)
        ImageButton mIbDelete;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickClassItem {
        void onClickClass(String classes);
    }
}
