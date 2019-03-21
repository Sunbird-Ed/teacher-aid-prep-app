package com.gurug.education.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gurug.education.R;
import com.gurug.education.data.repository.local.AppPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {

    @BindView(R.id.vp_help)
    ViewPager mVpHelp;

    @BindView(R.id.tl_help)
    TabLayout mTlHelp;
    @BindView(R.id.tv_skip)
    TextView mTvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        init();
    }

    @Override
    void init() {
        ButterKnife.bind(this);
        setUpViewPager();
    }

    @OnClick(R.id.tv_skip)
    public void onClickSkip() {
        AppPrefs.setHelpDone(HelpActivity.this, true);
        startActivity(new Intent(HelpActivity.this, SelectBoardMediumActivity.class));
        finish();
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(3);
        mVpHelp.setAdapter(viewPagerAdapter);
        mTlHelp.setupWithViewPager(mVpHelp, true);

        mTlHelp.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    mTvSkip.setText(getString(R.string.text_done));
                } else {
                    mTvSkip.setText(getString(R.string.text_skip));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater mLayoutInflater;
        private int mNumberActivity;

        public ViewPagerAdapter(int numbers) {
            mLayoutInflater = (LayoutInflater) HelpActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mNumberActivity = numbers;
        }

        @Override
        public int getCount() {
            return mNumberActivity;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View page = mLayoutInflater.inflate(R.layout.item_swipe_help, null);

            ImageView ivImage = page.findViewById(R.id.iv_image);
            TextView tvTitle = page.findViewById(R.id.tv_title);
            TextView tvTitleSecondary = page.findViewById(R.id.tv_secondary);
            switch (position) {
                case 0:
                    tvTitle.setText(getString(R.string.text_active_learning));
                    tvTitleSecondary.setText(getString(R.string.text_to_create_an_adaptive_learning_environment_so_that_teacher_learn_their_own));
                    break;
                case 1:
                    tvTitle.setText(getString(R.string.text_reconstruct_classroom));
                    tvTitleSecondary.setText(getString(R.string.text_make_child));

                    break;
                case 2:
                    tvTitle.setText(getString(R.string.text_all_round_interaction));
                    tvTitleSecondary.setText(getString(R.string.text_learning_management));
                    break;
            }
            container.addView(page, 0);
            return page;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (View) arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }
}
