package com.gurug.education.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.PedagoyStepRelatedInfo;
import com.gurug.education.data.model.response.lessonplan.TeachingMethod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.gurug.education.utill.AppConstants;


public class LessonPlanAdapter extends RecyclerView.Adapter<LessonPlanAdapter.LessonPlanViewHolder> {

    private ArrayList<Content> mContents;

    private Context mContext;

    private LessonPlanClickListener mLessonPlanClickListener;

    public LessonPlanAdapter(Context context, Fragment fragment) {
        mContents = new ArrayList<>();
        mContext = context;

        if (fragment != null) {
            mLessonPlanClickListener = (LessonPlanClickListener) fragment;
        } else {
            mLessonPlanClickListener = (LessonPlanClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public LessonPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LessonPlanViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lesson_plan, viewGroup, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LessonPlanViewHolder lessonPlanViewHolder, int pos) {
        Content content = mContents.get(pos);
        lessonPlanViewHolder.mTvPlanDescription.setText(content.getDescription());

        if (content.isDone()) {
            lessonPlanViewHolder.mTvCompleted.setVisibility(View.VISIBLE);
        } else {
            lessonPlanViewHolder.mTvCompleted.setVisibility(View.GONE);
        }
        String pedagogyStepJson = content.getPedagogyFlow();
        //pedagogyStepJson = pedagogyStepJson.replace("\\", "");
        Type listType = new TypeToken<ArrayList<PedagoyStepRelatedInfo>>() {
        }.getType();
        List<PedagoyStepRelatedInfo> pedagoyStepRelatedInfoList = new ArrayList<>();
        List<PedagoyStepRelatedInfo> pedagoyStepRelatedInfoList1 = new Gson().fromJson(pedagogyStepJson, listType);

        for (PedagoyStepRelatedInfo pedagoyStepRelatedInfo : pedagoyStepRelatedInfoList1) {
            if (!TextUtils.isEmpty(pedagoyStepRelatedInfo.getMethodType())) {
                pedagoyStepRelatedInfoList.add(pedagoyStepRelatedInfo);
            }
        }
        String plan = "";

        for (int i = 0; i < pedagoyStepRelatedInfoList.size(); i++) {
            if (i < pedagoyStepRelatedInfoList.size() - 1) {

                plan += pedagoyStepRelatedInfoList.get(i).getMethodType() + " -> ";
            } else {
                plan += pedagoyStepRelatedInfoList.get(i).getMethodType();
            }
        }

        lessonPlanViewHolder.mTvPlanTitle.setText(plan);
        lessonPlanViewHolder.mTvPlanDuration.setText(content.getTotalDuration() + AppConstants.SPACE + mContext.getString(R.string.text_mins));

        if (content.isBookMarked()) {
            lessonPlanViewHolder.mIbBookMark.setImageDrawable(mContext.getDrawable(R.drawable.ic_bookmarked));
        } else {
            lessonPlanViewHolder.mIbBookMark.setImageDrawable(mContext.getDrawable(R.drawable.ic_bookmark));
        }
/*
        //Enable to load Demo content For demo's
        String plan = "";

        for (int i = 0; i < content.getTeachingMethods().size(); i++) {
            if (i < content.getTeachingMethods().size() - 1) {
                plan += content.getTeachingMethods().get(i).getMethodType() + " -> ";
            } else {
                plan += content.getTeachingMethods().get(i).getMethodType();
            }

        }
        lessonPlanViewHolder.mTvPlanTitle.setText(plan);
        lessonPlanViewHolder.mTvPlanDuration.setText(content.getTotalDuration());

*/
        lessonPlanViewHolder.mIbBookMark.setOnClickListener(v -> mLessonPlanClickListener.onClickBookMark(content, pos));
        lessonPlanViewHolder.mRlQuickView.setOnClickListener(v -> mLessonPlanClickListener.onClickQuickView(content));
        lessonPlanViewHolder.mRlDetailView.setOnClickListener(v -> mLessonPlanClickListener.onClickDetailView(content));
    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public void update(ArrayList<Content> validPlans) {
        if (validPlans != null) {
            mContents = validPlans;
            notifyDataSetChanged();
        }
    }

    public void updatePos(Content content, int pos) {
        mContents.get(pos).setBookMarked(content.isBookMarked());
        notifyItemChanged(pos);
    }

    class LessonPlanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_plan_description)
        TextView mTvPlanDescription;

        @BindView(R.id.tv_completed)
        TextView mTvCompleted;

        @BindView(R.id.tv_plan_title)
        TextView mTvPlanTitle;

        @BindView(R.id.tv_lesson_plan_time)
        TextView mTvPlanDuration;

        @BindView(R.id.ib_book_mark)
        ImageButton mIbBookMark;

        @BindView(R.id.rl_quick_view)
        RelativeLayout mRlQuickView;

        @BindView(R.id.rl_detail_view)
        RelativeLayout mRlDetailView;

        public LessonPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface LessonPlanClickListener {
        void onClickQuickView(Content content);

        void onClickDetailView(Content content);

        void onClickBookMark(Content content, int pos);

    }
}
