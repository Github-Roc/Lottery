package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.CardView;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.LotteryUtil;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.SimpleBaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_PK10;


/**
 * Created by Peng on 2019/01/24.
 * 北京PK10页面
 */
public class PkShiFragment extends SimpleBaseFragment {

    @BindView(R.id.bt_get_random_number)
    MaterialButton btGetRandomNumber;
    @BindView(R.id.layout_lottery_number)
    CardView layoutLotteryNumber;
    @BindView(R.id.layout_lottery_pkshi)
    LotteryLayout layoutPkShi;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private LotteryUtil mUtil;
    private List<LotteryNumber> mLotteryValue;

    @Override
    protected void init() {
        super.init();

        mUtil = LotteryUtil.getInstance();
        mLotteryValue = new ArrayList<>();
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_pkshi;
    }

    @Override
    protected void initListener() {
        btGetRandomNumber.setOnClickListener(v -> {
            checkShowLayout();
            mUtil.getRandomLottery(mLotteryValue, LOTTERY_TYPE_PK10);
            layoutPkShi.setLotteryValue(mLotteryValue, LOTTERY_TYPE_PK10.type);
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mUtil.saveLottery(mLotteryValue, LOTTERY_TYPE_PK10);
            ToastUtil.showToast(mActivity, result);
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }
}
