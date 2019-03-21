package com.gurug.education.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gurug.education.R;
import com.gurug.education.utill.views.multilevelview.MultiLevelAdapter;
import com.gurug.education.utill.views.multilevelview.MultiLevelRecyclerView;
import com.gurug.education.utill.views.multilevelview.models.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TopicSelectionAdapter extends MultiLevelAdapter {
    private OnClickTopicItem mOnClickTopicItem;

    private Holder mViewHolder;
    private Context mContext;
    private List<Item> mListItems;
    private Item mItem;
    private MultiLevelRecyclerView mMultiLevelRecyclerView;

    public TopicSelectionAdapter(Context mContext, List<Item> mListItems, MultiLevelRecyclerView mMultiLevelRecyclerView) {
        super(mListItems);
        this.mListItems = mListItems;
        this.mContext = mContext;
        this.mMultiLevelRecyclerView = mMultiLevelRecyclerView;
        mOnClickTopicItem = (OnClickTopicItem) mContext;
    }

    private void setExpandButton(ImageView expandButton, boolean isExpanded) {
        // set the icon based on the current state
        expandButton.setImageResource(isExpanded ? R.drawable.arrow_up : R.drawable.ic_arrow_down);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mViewHolder = (Holder) holder;
        mItem = mListItems.get(position);

        switch (getItemViewType(position)) {
            case 1:
                holder.itemView.setBackgroundColor(Color.parseColor("#efefef"));
                break;
            case 2:

                holder.itemView.setBackgroundColor(Color.parseColor("#dedede"));
                break;
            default:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
        mViewHolder.mTitle.setText(mItem.getText());
        mViewHolder.mSubtitle.setText(mItem.getSecondText());

        if (mItem.hasChildren() && mItem.getChildren().size() > 0) {
            setExpandButton(mViewHolder.mExpandIcon, mItem.isExpanded());
            mViewHolder.mExpandButton.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.mExpandButton.setVisibility(View.GONE);
        }


      /*  if (!mItem.hasChildren()) {
            holder.itemView.setOnClickListener(v -> {
            });
        }*/

        Log.e("MuditLog", mItem.getLevel() + " " + mItem.getPosition() + " " + mItem.isExpanded() + "");

        // indent child items
        // Note: the parent item should start at zero to have no indentation
        // e.g. in populateFakeData(); the very first Item shold be instantiate like this: Item item = new Item(0);
        float density = mContext.getResources().getDisplayMetrics().density;
        ((ViewGroup.MarginLayoutParams) mViewHolder.mTextBox.getLayoutParams()).leftMargin = (int) ((getItemViewType(position) * 20) * density + 0.5f);
    }

    private class Holder extends RecyclerView.ViewHolder {

        TextView mTitle, mSubtitle;
        ImageView mExpandIcon;
        LinearLayout mTextBox, mExpandButton;

        Holder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title);
            mSubtitle = itemView.findViewById(R.id.subtitle);
            mExpandIcon = itemView.findViewById(R.id.image_view);
            mTextBox = itemView.findViewById(R.id.text_box);
            mExpandButton = itemView.findViewById(R.id.expand_field);

            // The following code snippets are only necessary if you set multiLevelRecyclerView.removeItemClickListeners(); in MainActivity.java
            // this enables more than one click event on an item (e.g. Click Event on the item itself and click event on the expand button)
            itemView.setOnClickListener(v -> {
                //set click event on item hereif
                if (!mListItems.get(getAdapterPosition()).hasChildren()) {
                    mOnClickTopicItem.onClickTopic(mListItems.get(getAdapterPosition()).getText());
                    // Toast.makeText(mContext, mListItems.get(getAdapterPosition()).getText(), Toast.LENGTH_SHORT).show();
                }
            });

            //set click listener on LinearLayout because the click area is bigger than the ImageView
            mExpandButton.setOnClickListener(v -> {
                // set click event on expand button here
                mMultiLevelRecyclerView.toggleItemsGroup(getAdapterPosition());
                // rotate the icon based on the current state
                // but only here because otherwise we'd see the animation on expanded items too while scrolling
                mExpandIcon.animate().rotation(mListItems.get(getAdapterPosition()).isExpanded() ? -180 : 0).start();
                //Toast.makeText(mContext, String.format(Locale.ENGLISH, "Item at position %d is expanded: %s", getAdapterPosition(), mItem.isExpanded()), Toast.LENGTH_SHORT).show();
            });
        }
    }

    public interface OnClickTopicItem {
        void onClickTopic(String topic);
    }
}


/*
 extends RecyclerView.Adapter<TopicSelectionAdapter.ViewHolderTopic> {

    private Context mContext;

    private List<Terms> mTopics;

    private OnClickTopicItem mOnClickTopicItem;

    public TopicSelectionAdapter(Context context) {
        mContext = context;
        mTopics = new ArrayList<Terms>();
        mOnClickTopicItem = (OnClickTopicItem) mContext;
    }

    @NonNull
    @Override
    public ViewHolderTopic onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolderTopic(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_framework, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTopic viewHolderTopic, int pos) {
        viewHolderTopic.mTvTitle.setText(mTopics.get(pos).getName());
        viewHolderTopic.itemView.setOnClickListener(v -> {
            mOnClickTopicItem.onClickTopic(mTopics.get(pos));
        });
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public void update(List<Terms> topics) {
        if (topics != null) {
            mTopics = topics;
            Collections.sort(mTopics, (o1, o2) -> o1.getName().compareTo(o2.getName()));
            notifyDataSetChanged();
        }
    }

    class ViewHolderTopic extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public ViewHolderTopic(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickTopicItem {
        void onClickTopic(Terms topic);
    }
 */