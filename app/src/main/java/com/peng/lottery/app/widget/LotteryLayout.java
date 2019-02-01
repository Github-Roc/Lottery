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

import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_DALETOU;
import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_PKSHI;
import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_SHIYIXUANWU;
import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_SHUANGSEQIU;

public class LotteryLayout extends LinearLayout {

    private View mView;
    private int[] mViewIds = {
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
        if (LOTTERY_TYPE_DALETOU.type.equals(lotteryType)) {
            layoutRes = R.layout.item_daletou_layout;
        } else if (LOTTERY_TYPE_SHUANGSEQIU.type.equals(lotteryType)) {
            layoutRes = R.layout.item_shuangseqiu_layout;
        } else if (LOTTERY_TYPE_SHIYIXUANWU.type.equals(lotteryType)) {
            layoutRes = R.layout.item_shiyixuanwu_layout;
        } else if (LOTTERY_TYPE_PKSHI.type.equals(lotteryType)) {
            layoutRes = R.layout.item_pkshi_layout;
        }
        if (layoutRes != 0) {
            if (mView == null) {
               mView = inflate(getContext(), layoutRes, null);
               addView(mView);
            }
            if (mView != null) {
                setValueToView(lotteryValue);
            }
        }
    }

    private void setValueToView(List<LotteryNumber> lotteryValue) {
        for (int i = 0; i < lotteryValue.size(); i++) {
            TextView tv = mView.findViewById(mViewIds[i]);
            if (tv != null) {
                tv.setText(lotteryValue.get(i).getNumberValue());
            }
        }
    }
}

