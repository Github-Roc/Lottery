package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.LotteryUtil;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.SimpleBaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.ui.activity.WebActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.TipConfig.MAIN_INPUT_TEXT;


/**
 * Created by Peng on 2019/01/24.
 * 大乐透页面
 */
public class DaLeDouFragment extends SimpleBaseFragment {

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
    @BindView(R.id.layout_lottery_daletou)
    LotteryLayout layoutDaLeTou;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private String mLuckyStr;
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
        return R.layout.fragment_daletou;
    }

    @Override
    protected void initListener() {
        btGetLuckyNumber.setOnClickListener(v -> createLotteryToView(true));
        btGetRandomNumber.setOnClickListener(v -> createLotteryToView(false));
        btLotteryRecord.setOnClickListener(v -> {
            String url = "http://kaijiang.500.com/dlt.shtml";
            WebActivity.start(mActivity, url);
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mUtil.saveLottery(mLotteryValue, LOTTERY_TYPE_DLT, "", mLuckyStr);
            ToastUtil.showToast(mActivity, result);
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }

    private void createLotteryToView(boolean isLucky) {
        mLuckyStr = isLucky ? etLuckyStr.getText().toString() : "";
        if (isLucky && TextUtils.isEmpty(mLuckyStr)) {
            ToastUtil.showToast(mActivity, MAIN_INPUT_TEXT);
            return;
        }

        checkShowLayout();
        mUtil.setLuckyStr(mLuckyStr);
        mUtil.getRandomLottery(mLotteryValue, LOTTERY_TYPE_DLT);
        layoutDaLeTou.setLotteryValue(mLotteryValue, LOTTERY_TYPE_DLT.type);
    }
}
