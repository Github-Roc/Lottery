package com.peng.lottery.mvp.ui.fragment;

import com.google.android.material.button.MaterialButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.View;

import com.peng.lottery.R;
import com.peng.lottery.app.helper.LotteryHelper;
import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.contract.fragment.MainLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.MainLotteryPresenter;
import com.peng.lottery.mvp.ui.activity.WebActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.TipConfig.MAIN_INPUT_TEXT;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_SSQ;

public class MainLotteryFragment extends BaseFragment<MainLotteryPresenter> implements MainLotteryContract.View {

    @BindView(R.id.et_lucky_str)
    MaterialEditText etLuckyStr;
    @BindView(R.id.spinner_type_11_choose_5)
    AppCompatSpinner spinnerLotteryType;
    @BindView(R.id.bt_get_lucky_number)
    MaterialButton btGetLuckyNumber;
    @BindView(R.id.bt_get_random_number)
    MaterialButton btGetRandomNumber;
    @BindView(R.id.bt_get_ai_number)
    MaterialButton btGetAINumber;
    @BindView(R.id.bt_lottery_record)
    MaterialButton btLotteryRecord;
    @BindView(R.id.layout_lottery_number)
    CardView layoutLotteryNumber;
    @BindView(R.id.lottery_layout)
    LotteryLayout lotteryLayout;
    @BindView(R.id.bt_save_lottery_number)
    MaterialButton btSaveLotteryNumber;

    private String mLuckyStr;
    private String mLotteryLabel;
    private LotteryHelper mHelper;
    private LotteryType mLotteryType;
    private List<LotteryNumber> mLotteryValue;

    @Override
    protected void init() {
        super.init();

        mHelper = LotteryHelper.getInstance();
        mLotteryValue = new ArrayList<>();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_main_lottery;
    }

    @Override
    protected void initView() {
        super.initView();

        if (mLotteryType == LOTTERY_TYPE_11X5) {
            etLuckyStr.setVisibility(View.GONE);
            btGetLuckyNumber.setVisibility(View.GONE);
            btGetAINumber.setVisibility(View.GONE);
            spinnerLotteryType.setVisibility(View.VISIBLE);
        } else if (mLotteryType == LOTTERY_TYPE_PK10) {
            etLuckyStr.setVisibility(View.GONE);
            btGetLuckyNumber.setVisibility(View.GONE);
            btGetAINumber.setVisibility(View.GONE);
            btLotteryRecord.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initListener() {
        btGetLuckyNumber.setOnClickListener(v -> createLotteryToView(true));
        btGetRandomNumber.setOnClickListener(v -> createLotteryToView(false));
        btGetAINumber.setOnClickListener(v -> {
            showLoading("正在生成号码，请稍候...");
            mPresenter.getLotteryRecord(mLotteryValue, mLotteryType);
        });
        btLotteryRecord.setOnClickListener(v -> {
            String url = "";
            if (mLotteryType == LOTTERY_TYPE_DLT) {
                url = "http://kaijiang.500.com/dlt.shtml";
            } else if (mLotteryType == LOTTERY_TYPE_SSQ) {
                url = "http://kaijiang.500.com/ssq.shtml";
            } else if (mLotteryType == LOTTERY_TYPE_11X5) {
                // url = "http://heb11x5.icaile.com/";
                url = "https://chart.ydniu.com/trend/syx5heb/";
            }
            WebActivity.start(mActivity, url);
        });
        btSaveLotteryNumber.setOnClickListener(v -> {
            String result = mHelper.saveLottery(mLotteryValue, mLotteryType, mLotteryLabel, mLuckyStr);
            ToastUtil.showToast(mActivity, result);
        });
    }

    @Override
    public void createLotteryFinish() {
        dismissLoading();

        checkShowLayout();
        mLotteryLabel = "超级号码";
        lotteryLayout.setLotteryValue(mLotteryValue, mLotteryType.type);
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.mLotteryType = lotteryType;
    }

    private void createLotteryToView(boolean isLucky) {
        mLotteryLabel = "";
        if (mLotteryType == LOTTERY_TYPE_DLT || mLotteryType == LOTTERY_TYPE_SSQ) {
            mLuckyStr = isLucky ? etLuckyStr.getText().toString() : "";
            if (isLucky && TextUtils.isEmpty(mLuckyStr)) {
                ToastUtil.showToast(mActivity, MAIN_INPUT_TEXT);
                return;
            }
            mHelper.setLuckyStr(mLuckyStr);
        } else if (mLotteryType == LOTTERY_TYPE_11X5) {
            mLotteryLabel = (String) spinnerLotteryType.getSelectedItem();
            lotteryLayout.set11x5Size(mHelper.get11x5SizeByType(mLotteryLabel));
        }

        checkShowLayout();
        mHelper.getRandomLottery(mLotteryValue, mLotteryType);
        lotteryLayout.setLotteryValue(mLotteryValue, mLotteryType.type);
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }
}
