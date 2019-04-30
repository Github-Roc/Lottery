package com.peng.lottery.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;


public class LotteryLayout extends LinearLayout {

    private View mView;
    private int[] mViewIds = {
            R.id.view_one, R.id.view_two, R.id.view_three,
            R.id.view_four, R.id.view_five, R.id.view_six,
            R.id.view_seven, R.id.view_eight};
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

    public void setLotteryData(LotteryData lottery) {
        if (mView != null) {
            removeAllViews();
            mView = null;
        }
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
            if (mView == null) {
                mView = inflate(getContext(), layoutRes, null);
                addView(mView);
            }
            if (mView != null) {
                setValueToView(lotteryValue, lotteryType);
            }
        }
    }

    private void setValueToView(List<LotteryNumber> lotteryValue, String lotteryType) {
        if (lotteryValue == null) {
            return;
        }
        int length = LOTTERY_TYPE_11X5.type.equals(lotteryType) ? 9 : lotteryValue.size();
        for (int i = 0; i < length; i++) {
            LotteryNumber lotteryNumber = null;
            TextView tv = mView.findViewById(mTextViewIds[i]);
            if (tv != null) {
                if (i < lotteryValue.size()) {
                    lotteryNumber = lotteryValue.get(i);
                }
                if (LOTTERY_TYPE_11X5.type.equals(lotteryType)) {
                    tv.setVisibility(lotteryNumber == null ? View.GONE : View.VISIBLE);
                    if (i > 0) {
                        View view = mView.findViewById(mViewIds[i - 1]);
                        view.setVisibility(lotteryNumber == null ? View.GONE : View.VISIBLE);
                    }
                }
                if (lotteryNumber != null) {
                    tv.setText(lotteryNumber.getNumberValue());
                }
            }
        }
    }
}

