package com.peng.lottery.mvp.ui.adapter.recycler;

import android.graphics.Color;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.List;

import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_BLUE;
import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_NULL;
import static com.peng.lottery.app.type.NumberBallType.NUMBER_BALL_TYPE_RED;


public class NumberBallAdapter extends BaseQuickAdapter<LotteryNumber, BaseViewHolder> {

    private boolean isSelect;
    private List<LotteryNumber> mSelectLotteryNumber;

    public NumberBallAdapter(int layoutResId, @Nullable List<LotteryNumber> data) {
        super(layoutResId, data);
    }

    public void setSelect(boolean isSelect) {
        this.isSelect = isSelect;
        mSelectLotteryNumber = isSelect ? new ArrayList<>() : null;
    }

    public List<LotteryNumber> getSelectLotteryNumber() {
        return mSelectLotteryNumber;
    }

    public int getRedBallSize() {
        int size = 0;
        if (mSelectLotteryNumber != null && mSelectLotteryNumber.size() > 0) {
            for (LotteryNumber number : mSelectLotteryNumber) {
                if (number.getNumberType().equals(NUMBER_BALL_TYPE_RED.type)) {
                    size++;
                }
            }
        }
        return size;
    }

    public int getBlueBallSize() {
        int size = 0;
        if (mSelectLotteryNumber != null && mSelectLotteryNumber.size() > 0) {
            for (LotteryNumber number : mSelectLotteryNumber) {
                if (number.getNumberType().equals(NUMBER_BALL_TYPE_BLUE.type)) {
                    size++;
                }
            }
        }
        return size;
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryNumber item) {
        TextView tvNumberBall = helper.getView(R.id.tv_lottery_number_ball);

        if (NUMBER_BALL_TYPE_NULL.type.equals(item.getNumberType())) {
            tvNumberBall.setBackgroundColor(Color.parseColor("#00000000"));
        } else {
            tvNumberBall.setText(item.getNumberValue());
            if (NUMBER_BALL_TYPE_RED.type.equals(item.getNumberType())) {
                int colorRes = isSelect && mSelectLotteryNumber.contains(item) ?
                        R.drawable.shape_red_round_selected : R.drawable.shape_red_round;
                tvNumberBall.setBackgroundResource(colorRes);
            } else {
                int colorRes = isSelect && mSelectLotteryNumber.contains(item) ?
                        R.drawable.shape_blue_round_selected : R.drawable.shape_blue_round;
                tvNumberBall.setBackgroundResource(colorRes);
            }
        }

        // 条目点击事件
        if (isSelect) {
            helper.itemView.setOnClickListener(v -> {
                if (mSelectLotteryNumber.contains(item)) {
                    mSelectLotteryNumber.remove(item);
                } else {
                    mSelectLotteryNumber.add(item);
                }
                notifyItemChanged(helper.getAdapterPosition());
            });
        }
    }
}