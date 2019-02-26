package com.peng.lottery.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.mvp.model.db.bean.LotteryData;

import java.util.List;

public class LotteryListAdapter extends BaseQuickAdapter<LotteryData, BaseViewHolder> {

    public LotteryListAdapter(int layoutResId, @Nullable List<LotteryData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryData item) {
        LotteryLayout lotteryLayout = helper.getView(R.id.item_lottery_layout);
        lotteryLayout.setLotteryData(item);

        helper.setText(R.id.tv_lottery_date, item.getCreateData());
        helper.setText(R.id.tv_lottery_type, item.getLotteryType());

        String lotteryLabel = TextUtils.isEmpty(item.getLuckyStr()) ? item.getLotteryLabel() : item.getLuckyStr();
        helper.setText(R.id.tv_lottery_label, lotteryLabel);
        helper.setGone(R.id.tv_lottery_label, !TextUtils.isEmpty(lotteryLabel));
    }

}
