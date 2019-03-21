package com.gurug.education.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.data.model.response.lessonplan.TeachingMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.utill.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuickViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int ITEM = 0;
    private int FOOTER = 1;

    private Context mContext;
    private IOnClickQuickView mIOnClickQuickView;

    private List<ChildrenMethod> mTeachingMethods;

    public QuickViewAdapter(Context context) {
        mContext = context;
        mIOnClickQuickView = (IOnClickQuickView) mContext;
        mTeachingMethods = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == FOOTER) {
            return new ViewHolderQuickViewFooter(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quick_view_footer, viewGroup, false));
        } else {
            return new ViewHolderQuickView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_quick_view, viewGroup, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        if (viewHolder instanceof ViewHolderQuickView) {
            ((ViewHolderQuickView) viewHolder).mTvMethodName.setText(mTeachingMethods.get(pos).getMethodtype());
            ((ViewHolderQuickView) viewHolder).mTvStep.setText(mTeachingMethods.get(pos).getPedagogyStep());
            ((ViewHolderQuickView) viewHolder).mTvShortDescription.setText(mTeachingMethods.get(pos).getDescription());
            ((ViewHolderQuickView) viewHolder).mTvMethodDuration.setText(mTeachingMethods.get(pos).getDuration() + AppConstants.SPACE + mContext.getString(R.string.text_mins));
            ((ViewHolderQuickView) viewHolder).itemView.setOnClickListener(v -> {mIOnClickQuickView.onClickMethod(mTeachingMethods.get(pos),pos);});

        } else {
            ((ViewHolderQuickViewFooter) viewHolder).mTvMarkAsDone.setOnClickListener(v -> {
                mIOnClickQuickView.onClickMarkAsDone();
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (position == mTeachingMethods.size()) {
            return FOOTER;
        } else {
            return ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return mTeachingMethods.size() + 1;
    }

    public void update(ArrayList<TeachingMethod> teachingMethods) {
        /*if (teachingMethods != null) {
            mTeachingMethods = teachingMethods;
            notifyDataSetChanged();
        }*/
    }

    public void update(ResponseMethodDetail responseMethodDetail) {
        if (responseMethodDetail.getResult() != null && responseMethodDetail.getResult().getContent() != null && responseMethodDetail.getResult().getContent().getChildren() != null) {
            mTeachingMethods = responseMethodDetail.getResult().getContent().getChildren();
            notifyDataSetChanged();
        }
    }

    class ViewHolderQuickView extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_step)
        TextView mTvStep;

        @BindView(R.id.tv_method_name)
        TextView mTvMethodName;

        @BindView(R.id.tv_plan_description)
        TextView mTvShortDescription;

        @BindView(R.id.tv_method_duration)
        TextView mTvMethodDuration;

        public ViewHolderQuickView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderQuickViewFooter extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_mark_as_done)
        TextView mTvMarkAsDone;

        public ViewHolderQuickViewFooter(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface IOnClickQuickView {
        void onClickMarkAsDone();

        void onClickMethod(ChildrenMethod teachingMethod,int pos);
    }
}
