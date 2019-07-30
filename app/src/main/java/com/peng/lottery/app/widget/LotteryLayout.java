package com.peng.lottery.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.List;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;


public class LotteryLayout extends LinearLayout {

    private int mMaxSize;
    private String mLotteryType;

    private View mView;
    private int[] mViewIds = {
            R.id.view_one, R.id.view_two, R.id.view_three,
            R.id.view_four, R.id.view_five, R.id.view_six,
            R.id.view_seven};
    private int[] mTextViewIds = {
            R.id.tv_ball_one, R.id.tv_ball_two, R.id.tv_ball_three,
            R.id.tv_ball_four, R.id.tv_ball_five, R.id.tv_ball_six,
            R.id.tv_ball_seven, R.id.tv_ball_eight, R.id.tv_ball_nine,
            R.id.tv_ball_ten};

    public LotteryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LotteryLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void set11x5Size(int size) {
        this.mMaxSize = size;
        change11x5Layout();
    }

    public void setLotteryData(LotteryData lottery) {
        setLotteryValue(lottery.getLotteryValue(), lottery.getLotteryType());
    }

    public void setLotteryValue(List<LotteryNumber> lotteryValue, String lotteryType) {
        int layoutRes = 0;
        if (LOTTERY_TYPE_DLT.type.equals(lotteryType)) {
            layoutRes = R.layout.item_daletou_layout;
        } else if (LOTTERY_TYPE_SSQ.type.equals(lotteryType)) {
            layoutRes = R.layout.item_shuangseqiu_layout;
        } else if (LOTTERY_TYPE_11X5.type.equals(lotteryType)) {
            layoutRes = R.layout.item_shiyixuanwu_layout;
        } else if (LOTTERY_TYPE_PK10.type.equals(lotteryType)) {
            layoutRes = R.layout.item_pkshi_layout;
        }
        if (layoutRes != 0) {
            if (mView != null) {
                removeAllViews();
            }
            mView = inflate(getContext(), layoutRes, null);
            addView(mView);

            mLotteryType = lotteryType;
            if (LOTTERY_TYPE_11X5.type.equals(mLotteryType) && mMaxSize == 0) {
                mMaxSize = lotteryValue.size();
            }

            change11x5Layout();
            setValueToView(lotteryValue, lotteryType);
        }
    }

    private void change11x5Layout() {
        if (LOTTERY_TYPE_11X5.type.equals(mLotteryType)) {
            for (int i = 0; i < 8; i++) {
                TextView tv = mView.findViewById(mTextViewIds[i]);
                tv.setVisibility(i < mMaxSize ? View.VISIBLE : View.GONE);
                if (i > 0) {
                    View view = mView.findViewById(mViewIds[i - 1]);
                    view.setVisibility(i < mMaxSize ? View.VISIBLE : View.GONE);
                }
            }
        }
    }

    private void setValueToView(List<LotteryNumber> lotteryValue, String lotteryType) {
        if (lotteryValue == null || lotteryValue.size() <= 0) {
            return;
        }
        if (LOTTERY_TYPE_DLT.type.equals(lotteryType) || LOTTERY_TYPE_SSQ.type.equals(lotteryType)) {
            List<LotteryNumber> redBallList = new ArrayList<>();
            List<LotteryNumber> blueBallList = new ArrayList<>();
            for (LotteryNumber lotteryNumber : lotteryValue) {
                if (NUMBER_BALL_TYPE_RED.type.equals(lotteryNumber.getNumberType())) {
                    redBallList.add(lotteryNumber);
                } else {
                    blueBallList.add(lotteryNumber);
                }
            }
            for (int i = 0; i < redBallList.size(); i++) {
                LotteryNumber lotteryNumber = redBallList.get(i);
                TextView tv = mView.findViewById(mTextViewIds[i]);
                tv.setText(lotteryNumber.getNumberValue());
            }
            for (int i = 0; i < blueBallList.size(); i++) {
                int temp = LOTTERY_TYPE_DLT.type.equals(lotteryType) ? 5 : 6;
                LotteryNumber lotteryNumber = blueBallList.get(i);
                TextView tv = mView.findViewById(mTextViewIds[i + temp]);
                tv.setText(lotteryNumber.getNumberValue());
            }
        } else {
            for (int i = 0; i < lotteryValue.size(); i++) {
                LotteryNumber lotteryNumber = lotteryValue.get(i);
                TextView tv = mView.findViewById(mTextViewIds[i]);
                tv.setText(lotteryNumber.getNumberValue());
            }
        }
    }
}

