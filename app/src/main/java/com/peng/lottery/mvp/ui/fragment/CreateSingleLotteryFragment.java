package com.peng.lottery.mvp.ui.fragment;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.peng.lottery.R;
import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.listener.SpannerItemListener;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.app.widget.LotteryLayout;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.CreateSingleLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.recycler.NumberBallAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_CHECK_NUMBER;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_CLEAN_DATA;

public class CreateSingleLotteryFragment extends BaseFragment<CreateSingleLotteryPresenter> {

    @BindView(R.id.spinner_type_lottery)
    AppCompatSpinner spinnerTypeLottery;
    @BindView(R.id.spinner_type_11x5)
    AppCompatSpinner spinnerType11x5;
    @BindView(R.id.lottery_ball_recycler)
    RecyclerView mBallRecycler;
    @BindView(R.id.layout_lottery_number)
    LotteryLayout layoutLotteryNumber;
    @BindView(R.id.bt_clean_lottery)
    MaterialButton btCleanLottery;
    @BindView(R.id.bt_complement_lottery)
    MaterialButton btComplementLottery;
    @BindView(R.id.bt_save_lottery)
    MaterialButton btSaveLottery;

    private ActionConfig.LotteryType mLotteryType;
    private List<LotteryNumber> mLotteryValue;
    private NumberBallAdapter mNumberBallAdapter;

    @Override
    protected void init() {
        super.init();

        mLotteryType = LOTTERY_TYPE_DLT;
        mLotteryValue = new ArrayList<>();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_create_single_lottery;
    }

    @Override
    protected void initView() {
        super.initView();

        // 初始化彩票号码球
        mNumberBallAdapter = new NumberBallAdapter(R.layout.item_number_ball, ActionConfig.getLotteryNumberBallList(LOTTERY_TYPE_DLT));
        mNumberBallAdapter.bindToRecyclerView(mBallRecycler);
        mBallRecycler.setLayoutManager(new GridLayoutManager(mActivity, 7));
        mBallRecycler.setAdapter(mNumberBallAdapter);
        // 初始化彩票数据
        layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
    }

    @Override
    protected void initListener() {
        spinnerTypeLottery.setOnItemSelectedListener(new SpannerItemListener() {
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
                        change11x5Type();
                        changeLotteryType(LOTTERY_TYPE_11X5);
                        break;
                }
            }
        });
        spinnerType11x5.setOnItemSelectedListener(new SpannerItemListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mLotteryType.equals(LOTTERY_TYPE_11X5)) {
                    change11x5Type();
                }
            }
        });
        mNumberBallAdapter.setOnItemClickListener((adapter, view, position) -> {
            LotteryNumber item = mNumberBallAdapter.getData().get(position);
            if (mPresenter.checkIsAdd(mLotteryValue, mLotteryType, item)) {
                mLotteryValue.add(item);
                mPresenter.sortList(mLotteryValue, mLotteryType);
                layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
            }
        });
        btCleanLottery.setOnClickListener(v -> {
            mLotteryValue.clear();
            layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
        });
        btComplementLottery.setOnClickListener(v -> {
            if (mLotteryValue.size() == 0) {
                ToastUtil.showToast(mActivity, CREATE_LOTTERY_CHECK_NUMBER);
                return;
            }
            if (mPresenter.getLotteryUtil().checkLotterySize(mLotteryValue, mLotteryType)) {
                ToastUtil.showToast(mActivity, CREATE_LOTTERY_CLEAN_DATA);
                return;
            }
            mPresenter.getLotteryUtil().complementLottery(mLotteryValue, mLotteryType);
            layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
        });
        btSaveLottery.setOnClickListener(v -> {
            String label = mLotteryType.equals(LOTTERY_TYPE_11X5) ? (String) spinnerType11x5.getSelectedItem() : "";
            String result = mPresenter.getLotteryUtil().saveLottery(mLotteryValue, mLotteryType, label);
            ToastUtil.showToast(mActivity, result);
        });
    }

    private void changeLotteryType(ActionConfig.LotteryType lotteryType) {
        mLotteryType = lotteryType;
        mLotteryValue.clear();
        spinnerType11x5.setVisibility(LOTTERY_TYPE_11X5.equals(lotteryType) ? View.VISIBLE : View.GONE);
        mNumberBallAdapter.setNewData(ActionConfig.getLotteryNumberBallList(lotteryType));
        layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
    }

    private void change11x5Type() {
        mLotteryValue.clear();
        String type = (String) spinnerType11x5.getSelectedItem();
        layoutLotteryNumber.set11x5Size(mPresenter.getTypeBean(type).size);
        layoutLotteryNumber.setLotteryValue(mLotteryValue, mLotteryType.type);
    }
}
