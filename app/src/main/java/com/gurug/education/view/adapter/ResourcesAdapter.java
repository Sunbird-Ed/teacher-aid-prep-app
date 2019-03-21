package com.gurug.education.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gurug.education.R;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.utill.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ViewHolderResources> {

    private ResourceItemListeners mResourceItemListeners;
    private Context mContext;
    private List<ChildrenMethodResouces> mResources;

    public ResourcesAdapter(Context context) {
        mContext = context;
        mResourceItemListeners = (ResourceItemListeners) mContext;
        mResources = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderResources onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderResources(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resources, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderResources viewHolderResources, int pos) {


        Glide.with(mContext)
                .setDefaultRequestOptions(Utility.getDefaultOption(R.drawable.ic_default, R.drawable.ic_default))
                .load(mResources.get(pos).getPosterImage())
                .into(viewHolderResources.mIvResourceThumb);

        viewHolderResources.mTvResourceType.setText(mResources.get(pos).getContentType());
        viewHolderResources.mTvResourceName.setText(mResources.get(pos).getName());
        viewHolderResources.itemView.setOnClickListener(v -> {
            mResourceItemListeners.onClickResourceItem(mResources.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return mResources.size();
    }

    public void update(List<ChildrenMethodResouces> children) {
        mResources = children;
        notifyDataSetChanged();
    }

    class ViewHolderResources extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_resource_name)
        TextView mTvResourceName;

        @BindView(R.id.tv_resource_type)
        TextView mTvResourceType;

        @BindView(R.id.iv_resource_thumb)
        ImageView mIvResourceThumb;

        public ViewHolderResources(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ResourceItemListeners {
        void onClickResourceItem(ChildrenMethodResouces childrenMethod);
    }
}
