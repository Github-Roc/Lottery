package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.ShiYiXuanWuPresenter;

import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_SHIYIXUANWU;

/**
 * Created by Peng on 2019/01/24.
 * 11选5页面
 */
public class ShiYiXuanWuFragment extends BaseFragment<ShiYiXuanWuPresenter> {

    @BindView(R.id.spinner_type_11_choose_5)
    AppCompatSpinner spinnerShiYiXuanWu;
    @BindView(R.id.bt_get_random_number)
    MaterialButton btGetRandomNumber;
    @BindView(R.id.layout_lottery_number)
    CardView layoutLotteryNumber;
    @BindView(R.id.layout_lottery_shiyixuanwu)
    LotteryLayout layoutShiYiXuanWu;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private List<LotteryNumber> mLotteryValue;
    private String mLotteryLabel;

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_shiyixuanwu;
    }

    @Override
    protected void initListener() {
        btGetRandomNumber.setOnClickListener(v -> {
            checkShowLayout();
            mLotteryLabel = (String) spinnerShiYiXuanWu.getSelectedItem();
            mPresenter.setMaxSize(mLotteryLabel);
            mLotteryValue = mPresenter.getRandomLottery();
            layoutShiYiXuanWu.setLotteryValue(mLotteryValue, LOTTERY_TYPE_SHIYIXUANWU.type);
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mPresenter.saveLottery(mLotteryLabel, mLotteryValue, LOTTERY_TYPE_SHIYIXUANWU);
            ToastUtil.showToast(mActivity, result);
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }
}
