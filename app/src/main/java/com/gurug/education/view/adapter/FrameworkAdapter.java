package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.data.model.response.frameworkdetail.Terms;
import com.gurug.education.data.model.response.framwork.Framework;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FrameworkAdapter extends RecyclerView.Adapter<FrameworkAdapter.ViewHolderFramework> {

    private ArrayList<Framework> mFrameworks;

    private Context mContext;

    private IFrameWorkClick mIFrameWorkClick;

    public FrameworkAdapter(Context context) {
        mContext = context;
        mFrameworks = new ArrayList<>();

        mIFrameWorkClick = (IFrameWorkClick) context;
    }

    @NonNull
    @Override
    public ViewHolderFramework onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderFramework(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_framework, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFramework viewHolderFramework, int pos) {
        Framework framework = mFrameworks.get(pos);
        viewHolderFramework.mTvTitle.setText(framework.getName());

        viewHolderFramework.mTvTitle.setOnClickListener(v -> {
            mIFrameWorkClick.onClickFrameWork(framework);
        });
    }

    @Override
    public int getItemCount() {
        return mFrameworks.size();
    }

    public void update(ArrayList<Framework> terms) {
        mFrameworks = terms;
        Collections.sort(mFrameworks, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        notifyDataSetChanged();
    }

    class ViewHolderFramework extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public ViewHolderFramework(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IFrameWorkClick {
        void onClickFrameWork(Framework framework);
    }

}
