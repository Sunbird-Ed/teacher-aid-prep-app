package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.utill.textdrawble.TextDrawable;
import com.gurug.education.utill.views.ImageViewCircle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassSubjectFilterAdapter extends RecyclerView.Adapter<ClassSubjectFilterAdapter.ClassSubjectFilterViewHolder> {


    private Context mContext;

    public ClassSubjectFilterAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public ClassSubjectFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ClassSubjectFilterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_class_sbject_filter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ClassSubjectFilterViewHolder classSubjectFilterViewHolder, int possition) {
        //classSubjectFilterViewHolder.mCiClassSubjectIcon

        if (possition == 0) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("A", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);
            classSubjectFilterViewHolder.mTvClassSubjectName.setText("All");
        } else if (possition == 1) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("S", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);

            classSubjectFilterViewHolder.mTvClassSubjectName.setText("1 - Science");

        } else if (possition == 2) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("S", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);

            classSubjectFilterViewHolder.mTvClassSubjectName.setText("5 - Science");

        } else if (possition == 3) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("M", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);

            classSubjectFilterViewHolder.mTvClassSubjectName.setText("1 - Maths");

        } else if (possition == 4) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("S", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);

            classSubjectFilterViewHolder.mTvClassSubjectName.setText("3 - Sceince");

        } else if (possition == 5) {
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(50)
                    .height(50)
                    .endConfig()
                    .buildRect("M", ContextCompat.getColor(mContext, R.color.colorBlue));
            classSubjectFilterViewHolder.mCiClassSubjectIcon.setImageDrawable(drawable);

            classSubjectFilterViewHolder.mTvClassSubjectName.setText("8 - Maths");
        }


    }

    @Override
    public int getItemCount() {
        return 6;
    }

    class ClassSubjectFilterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ci_class_subject_icon)
        ImageViewCircle mCiClassSubjectIcon;

        @BindView(R.id.tv_subject_name)
        TextView mTvClassSubjectName;

        public ClassSubjectFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
