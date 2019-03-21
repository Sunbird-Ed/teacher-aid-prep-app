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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedTeachAdapter extends RecyclerView.Adapter<SelectedTeachAdapter.ViewHolderTeach> {

    private Context mContext;
    private List<String> mSelectedTeach;
    private OnClickSelectedTeach mOnClickSelectedTeach;

    public SelectedTeachAdapter(Context context) {
        mContext = context;
        mSelectedTeach = new ArrayList<>();
        mOnClickSelectedTeach = (OnClickSelectedTeach) mContext;
    }

    @NonNull
    @Override
    public ViewHolderTeach onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderTeach(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_framework, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTeach viewHolderTeach, int pos) {
        String grade = mSelectedTeach.get(pos).split(AppConstants.COLON)[0];
        String subject = mSelectedTeach.get(pos).split(AppConstants.COLON)[1];
        viewHolderTeach.mTvTitle.setText(grade + "\n" + subject);
        viewHolderTeach.mIbDelete.setVisibility(View.VISIBLE);
        viewHolderTeach.mIbDelete.setOnClickListener(v -> {
            mOnClickSelectedTeach.onClickSelectedTeach(mSelectedTeach.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return mSelectedTeach.size();
    }

    public void update(List<String> values) {
        mSelectedTeach = values;
        Collections.sort(mSelectedTeach, String::compareTo);
        notifyDataSetChanged();
    }

    class ViewHolderTeach extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        @BindView(R.id.ib_delete)
        ImageButton mIbDelete;

        public ViewHolderTeach(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickSelectedTeach {
        void onClickSelectedTeach(String teach);
    }
}
