package com.peng.lottery.mvp.ui.adapter.recycler;

import androidx.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.peng.lottery.R;
import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.app.utils.ScreenUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.List;

public class LotteryItemAdapter extends BaseQuickAdapter<List<LotteryNumber>, BaseViewHolder> {

    private LotteryType mLotteryType;
    private boolean isSave;

    public LotteryItemAdapter(int layoutResId, @Nullable List<List<LotteryNumber>> data) {
        super(layoutResId, data);
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.mLotteryType = lotteryType;
    }

    public void setSave(boolean isSave) {
        this.isSave = isSave;
    }

    public boolean isSave() {
        return isSave;
    }

    @Override
    protected void convert(BaseViewHolder helper, List<LotteryNumber> item) {
        LotteryLayout lotteryLayout = helper.getView(R.id.item_lottery_layout);
        lotteryLayout.setLotteryValue(item, mLotteryType.type);

        helper.setGone(R.id.tv_lottery_date, false);
        helper.setGone(R.id.tv_lottery_type, false);
        helper.setGone(R.id.tv_lottery_label, false);

        setItemMargin(helper.itemView);
    }

    private void setItemMargin(View itemView) {
        int marginBottom = ScreenUtil.dip2px(mContext, 12);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemView.getLayoutParams());
        params.setMargins(0, 0, 0, marginBottom);
        itemView.setLayoutParams(params);
    }
}
