package com.peng.lottery.mvp.ui.adapter.recycler;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.app.utils.ScreenUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.mvp.model.db.bean.LotteryData;

import java.util.List;

public class MineLotteryAdapter extends BaseQuickAdapter<LotteryData, BaseViewHolder> {

    public MineLotteryAdapter(int layoutResId, @Nullable List<LotteryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryData item) {
        LotteryLayout lotteryLayout = helper.getView(R.id.item_lottery_layout);
        lotteryLayout.set11x5Size(item.getLotteryValue().size());
        lotteryLayout.setLotteryData(item);

        helper.setText(R.id.tv_lottery_date, item.getCreateDate());
        helper.setText(R.id.tv_lottery_type, item.getLotteryType());

        String lotteryLabel = TextUtils.isEmpty(item.getLuckyStr()) ? item.getLotteryLabel() : item.getLuckyStr();
        helper.setText(R.id.tv_lottery_label, lotteryLabel);
        helper.setGone(R.id.tv_lottery_label, !TextUtils.isEmpty(lotteryLabel));

        setItemMargin(helper.itemView, helper.getAdapterPosition());

        helper.addOnClickListener(R.id.tv_lottery_date);
        helper.addOnClickListener(R.id.tv_lottery_label);
        helper.addOnClickListener(R.id.tv_lottery_type);
    }

    private void setItemMargin(View itemView, int position) {
        int topValue = position != 0 ? 0 : 12;
        int bottomValue = position == getData().size() - 1 ? 50 : 12;
        int marginLeft = ScreenUtil.dip2px(mContext, 24);
        int marginTop = ScreenUtil.dip2px(mContext, topValue);
        int marginRight = ScreenUtil.dip2px(mContext, 24);
        int marginBottom = ScreenUtil.dip2px(mContext, bottomValue);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemView.getLayoutParams());
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        itemView.setLayoutParams(params);
    }
}
