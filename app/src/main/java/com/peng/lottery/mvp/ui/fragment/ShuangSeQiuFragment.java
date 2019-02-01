package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.ShuangSeQiuPresenter;
import com.peng.lottery.mvp.ui.activity.WebActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.base.BaseLotteryPresenter.LotteryType.LOTTERY_TYPE_SHUANGSEQIU;

/**
 * Created by Peng on 2019/01/24.
 * 双色球页面
 */
public class ShuangSeQiuFragment extends BaseFragment<ShuangSeQiuPresenter> {

    @BindView(R.id.et_lucky_str)
    MaterialEditText etLuckyStr;
    @BindView(R.id.bt_get_lucky_number)
    MaterialButton btGetLuckyNumber;
    @BindView(R.id.bt_get_random_number)
    MaterialButton btGetRandomNumber;
    @BindView(R.id.bt_lottery_record)
    MaterialButton btLotteryRecord;
    @BindView(R.id.layout_lottery_number)
    CardView layoutLotteryNumber;
    @BindView(R.id.layout_lottery_shuangseqiu)
    LotteryLayout layoutShuangSeQiu;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private String mLuckyStr;
    private List<LotteryNumber> mLotteryValue;

    @Override
    public void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_shuangseqiu;
    }

    @Override
    protected void initListener() {
        btGetLuckyNumber.setOnClickListener(v -> {
            mLuckyStr = etLuckyStr.getText().toString().trim();
            if (TextUtils.isEmpty(mLuckyStr)) {
                ToastUtil.showToast(mActivity, "请先输入一些内容吧！");
                return;
            }

            checkShowLayout();
            mLotteryValue = mPresenter.getLotteryNumber(mLuckyStr);
            layoutShuangSeQiu.setLotteryValue(mLotteryValue, LOTTERY_TYPE_SHUANGSEQIU.type);
        });
        btGetRandomNumber.setOnClickListener(v -> {
            mLuckyStr = "";
            checkShowLayout();
            mLotteryValue = mPresenter.getRandomLottery();
            layoutShuangSeQiu.setLotteryValue(mLotteryValue, LOTTERY_TYPE_SHUANGSEQIU.type);
        });
        btLotteryRecord.setOnClickListener(v -> {
            String url = "http://kaijiang.500.com/ssq.shtml";
            WebActivity.start(mActivity, url);
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mPresenter.saveLottery(mLotteryValue, LOTTERY_TYPE_SHUANGSEQIU, mLuckyStr);
            ToastUtil.showToast(mActivity, result);
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }
}

