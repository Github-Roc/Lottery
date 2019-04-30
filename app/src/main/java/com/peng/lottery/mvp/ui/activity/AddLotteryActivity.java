package com.peng.lottery.mvp.ui.activity;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.peng.lottery.R;
import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.activity.AddLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.LotteryNumberBallAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;

public class AddLotteryActivity extends BaseActivity<AddLotteryPresenter> {

    @BindView(R.id.spinner_type_lottery)
    AppCompatSpinner spinnerTypeLottery;
    @BindView(R.id.lottery_ball_recycle)
    RecyclerView lotteryBallRecycle;
    @BindView(R.id.bt_complement_lottery)
    MaterialButton btComplementLottery;
    @BindView(R.id.layout_lottery_number)
    LotteryLayout layoutLotteryNumber;
    @BindView(R.id.bt_save_lottery)
    MaterialButton btSaveLottery;

    private String mLotteryType;
    private List<LotteryNumber> mLotteryNumbers;
    private LotteryNumberBallAdapter mLotteryNumberBallAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_add_lottery;
    }

    @Override
    protected void initView() {
        super.initView();
        mActivityTitle.setText(R.string.title_add_lottery);

        // 初始化彩票号码球
        mLotteryNumberBallAdapter = new LotteryNumberBallAdapter(R.layout.item_lottery_number_ball, ActionConfig.getLotteryNumberBallList(LOTTERY_TYPE_DLT));
        mLotteryNumberBallAdapter.bindToRecyclerView(lotteryBallRecycle);
        lotteryBallRecycle.setLayoutManager(new GridLayoutManager(mActivity, 7));
        lotteryBallRecycle.setAdapter(mLotteryNumberBallAdapter);
        // 初始化彩票数据
        mLotteryType = LOTTERY_TYPE_DLT.type;
        mLotteryNumbers = new ArrayList<>();
        layoutLotteryNumber.setLotteryValue(mLotteryNumbers, mLotteryType);
    }

    @Override
    protected void initListener() {
        spinnerTypeLottery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeLotteryType(LOTTERY_TYPE_DLT);
                        break;
                    case 1:
                        changeLotteryType(LOTTERY_TYPE_SSQ);
                        break;
                    case 2:
                        changeLotteryType(LOTTERY_TYPE_11X5);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btComplementLottery.setOnClickListener(v -> {

        });
        btSaveLottery.setOnClickListener(v -> {

        });
        mLotteryNumberBallAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            LotteryNumber item = mLotteryNumberBallAdapter.getData().get(position);
            if (mPresenter.checkIsAdd(mLotteryNumbers, mLotteryType, item.getNumberType())) {
                mLotteryNumbers.add(item);
                layoutLotteryNumber.setLotteryValue(mLotteryNumbers, mLotteryType);
            }
        });
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    private void changeLotteryType(ActionConfig.LotteryType lotteryType) {
        mLotteryType = lotteryType.type;
        layoutLotteryNumber.setLotteryValue(mLotteryNumbers, mLotteryType);
        mLotteryNumberBallAdapter.setNewData(ActionConfig.getLotteryNumberBallList(lotteryType));
    }
}
