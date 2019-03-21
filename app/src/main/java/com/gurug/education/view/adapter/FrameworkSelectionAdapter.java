package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.data.model.response.facetsearh.Facet;
import com.gurug.education.data.model.response.facetsearh.Values;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.utill.AppConstants;
import com.gurug.education.utill.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameworkSelectionAdapter extends RecyclerView.Adapter<FrameworkSelectionAdapter.ViewHolderFramework> {

    private ArrayList<Values> mTermsArrayList;

    private Context mContext;

    private IFrameWorkClick mIFrameWorkClick;
    private boolean mIsFromTopic;

    public FrameworkSelectionAdapter(Context context) {
        mContext = context;
        mTermsArrayList = new ArrayList<>();
        mIsFromTopic = false;

        mIFrameWorkClick = (IFrameWorkClick) context;
    }

    @NonNull
    @Override
    public ViewHolderFramework onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderFramework(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_framework, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFramework viewHolderFramework, int pos) {
        Values terms = mTermsArrayList.get(pos);
        String title = terms.getName();
        if (mIsFromTopic) {
            title += AppConstants.SPACE + AppConstants.OPENING_SMALL_BRACKET + terms.getCount() + AppConstants.CLOSING_SMALL_BRACKET;
        }
        viewHolderFramework.mTvTitle.setText(Utility.toTitleCase(title));
        viewHolderFramework.mTvTitle.setOnClickListener(v -> {
            mIFrameWorkClick.onClickFrameWork(terms);
        });
    }

    @Override
    public int getItemCount() {
        return mTermsArrayList.size();
    }

    public void update(ArrayList<Terms> terms) {
        /*mTermsArrayList = terms;
        Collections.sort(mTermsArrayList, (o1, o2) -> o1.getIndex().compareTo(o2.getIndex()));
        notifyDataSetChanged();
    */
    }

    public void update(List<Values> values) {
        mTermsArrayList = (ArrayList<Values>) values;
        Collections.sort(mTermsArrayList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        notifyDataSetChanged();
    }

    public void update(List<Values> values, boolean isFromTopic) {
        mTermsArrayList = (ArrayList<Values>) values;
        mIsFromTopic = true;
        Collections.sort(mTermsArrayList, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        notifyDataSetChanged();

    }

    class ViewHolderFramework extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_count)
        TextView mTvCount;

        public ViewHolderFramework(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IFrameWorkClick {
        void onClickFrameWork(Values terms);
    }

}
