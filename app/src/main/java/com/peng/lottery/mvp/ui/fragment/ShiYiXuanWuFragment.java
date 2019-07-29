package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.LotteryUtil;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.app.widget.dialog.ShowInfoDialog;
import com.peng.lottery.base.SimpleBaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.ui.activity.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;


/**
 * Created by Peng on 2019/01/24.
 * 11选5页面
 */
public class ShiYiXuanWuFragment extends SimpleBaseFragment {

    @BindView(R.id.spinner_type_11_choose_5)
    AppCompatSpinner spinnerShiYiXuanWu;
    @BindView(R.id.bt_get_random_number)
    MaterialButton btGetRandomNumber;
    @BindView(R.id.bt_chart_11_choose_5)
    MaterialButton btLotteryChart;
    @BindView(R.id.layout_lottery_number)
    CardView layoutLotteryNumber;
    @BindView(R.id.layout_lottery_shiyixuanwu)
    LotteryLayout layoutShiYiXuanWu;
    @BindView(R.id.bt_analyse_lottery_number)
    MaterialButton btAnalyseLotteryNumber;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private String mLotteryLabel;
    private LotteryUtil mUtil;
    private LotteryUtil.ShiYiXuanWuTypeBean mTypeBean;
    private List<LotteryNumber> mLotteryValue;

    @Override
    protected void init() {
        super.init();

        mUtil = LotteryUtil.getInstance();
        mLotteryValue = new ArrayList<>();
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
            mTypeBean = mUtil.getTypeBean(mLotteryLabel);
            layoutShiYiXuanWu.set11x5Size(mTypeBean.size);
            mUtil.getRandomLottery(mLotteryValue, LOTTERY_TYPE_11X5);
            layoutShiYiXuanWu.setLotteryValue(mLotteryValue, LOTTERY_TYPE_11X5.type);
        });
        btAnalyseLotteryNumber.setOnClickListener(v -> {
            mLotteryLabel = (String) spinnerShiYiXuanWu.getSelectedItem();
            mTypeBean = mUtil.getTypeBean(mLotteryLabel);
            String stringBuilder = "中奖几率：" +
                    mTypeBean.probability +
                    "%\n中奖金额：" +
                    mTypeBean.bonus +
                    "元";
            new ShowInfoDialog(mActivity, stringBuilder).show();
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mUtil.saveLottery(mLotteryValue, LOTTERY_TYPE_11X5, mLotteryLabel);
            ToastUtil.showToast(mActivity, result);
        });
        btLotteryChart.setOnClickListener(v -> {
//            String url = "http://heb11x5.icaile.com/";
            String url = "https://chart.ydniu.com/trend/syx5heb/";
            WebActivity.start(mActivity, url);
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }
}
