package com.peng.lottery.mvp.ui.adapter.recycler;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_NULL;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;

public class NumberBallAdapter extends BaseQuickAdapter<LotteryNumber, BaseViewHolder> {

    public NumberBallAdapter(int layoutResId, @Nullable List<LotteryNumber> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryNumber item) {
        TextView tvNumberBall = helper.getView(R.id.tv_lottery_number_ball);

        if (NUMBER_BALL_TYPE_NULL.type.equals(item.getNumberType())) {
            tvNumberBall.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            tvNumberBall.setText(item.getNumberValue());
            if (NUMBER_BALL_TYPE_RED.type.equals(item.getNumberType())) {
                tvNumberBall.setBackgroundResource(R.drawable.shape_red_round);
            } else {
                tvNumberBall.setBackgroundResource(R.drawable.shape_blue_round);
            }
        }
    }
}