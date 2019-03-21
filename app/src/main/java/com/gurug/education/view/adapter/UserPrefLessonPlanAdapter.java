package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.PedagoyStepRelatedInfo;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.views.CustomTypefaceSpan;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPrefLessonPlanAdapter extends RecyclerView.Adapter<UserPrefLessonPlanAdapter.UserPrefLessonPlanViewHolder> {

    private List<Content> mContents;
    private Context mContext;
    private LessonPlanClickListener mLessonPlanClickListener;
    private boolean mIsBookMarked;

    public UserPrefLessonPlanAdapter(Context context, Fragment fragment) {
        mContext = context;
        mContents = new ArrayList<>();

        if (fragment != null) {
            mLessonPlanClickListener = (LessonPlanClickListener) fragment;
        } else {
            mLessonPlanClickListener = (LessonPlanClickListener) mContext;
        }
    }

    @NonNull
    @Override
    public UserPrefLessonPlanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserPrefLessonPlanViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lesson_plan_user_pref, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserPrefLessonPlanViewHolder lessonPlanViewHolder, int pos) {
        Content content = mContents.get(pos);
        lessonPlanViewHolder.mTvPlanDescription.setText(content.getDescription());

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

        lessonPlanViewHolder.mTvPlanTitle.setText(plan);
        lessonPlanViewHolder.mTvPlanDuration.setText(content.getTotalDuration() + AppConstants.SPACE + mContext.getString(R.string.text_mins));

        if (mIsBookMarked) {
            lessonPlanViewHolder.mIbBookMark.setVisibility(View.VISIBLE);

            if (content.isBookMarked()) {
                lessonPlanViewHolder.mIbBookMark.setImageDrawable(mContext.getDrawable(R.drawable.ic_bookmarked));
            } else {
                lessonPlanViewHolder.mIbBookMark.setImageDrawable(mContext.getDrawable(R.drawable.ic_bookmark));
            }
        } else {
            lessonPlanViewHolder.mIbBookMark.setVisibility(View.GONE);
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

        lessonPlanViewHolder.mTvTopic.setText("");
        Spannable wordTwo = new SpannableString("Topic: ");
        wordTwo.setSpan(new CustomTypefaceSpan("", ResourcesCompat.getFont(Objects.requireNonNull(mContext), R.font.roboto_bold), ContextCompat.getColor(mContext, R.color.colorBlack1)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lessonPlanViewHolder.mTvTopic.append(wordTwo);


        Spannable wordThree = new SpannableString(content.getTopic());
        wordThree.setSpan(new CustomTypefaceSpan("", ResourcesCompat.getFont(Objects.requireNonNull(mContext), R.font.roboto_regular), ContextCompat.getColor(mContext, R.color.colorBlack)), 0, wordThree.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        lessonPlanViewHolder.mTvTopic.append(wordThree);

    }

    @Override
    public int getItemCount() {
        return mContents.size();
    }

    public void update(List<Content> contents, boolean isBookMarked) {
        mContents = contents;
        mIsBookMarked = isBookMarked;
        notifyDataSetChanged();
    }

    class UserPrefLessonPlanViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_plan_description)
        TextView mTvPlanDescription;

        @BindView(R.id.tv_plan_title)
        TextView mTvPlanTitle;

        @BindView(R.id.tv_lesson_plan_time)
        TextView mTvPlanDuration;

        @BindView(R.id.tv_topic)
        TextView mTvTopic;

        @BindView(R.id.ib_book_mark)
        ImageButton mIbBookMark;

        @BindView(R.id.rl_quick_view)
        RelativeLayout mRlQuickView;

        @BindView(R.id.rl_detail_view)
        RelativeLayout mRlDetailView;

        public UserPrefLessonPlanViewHolder(@NonNull View itemView) {
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
