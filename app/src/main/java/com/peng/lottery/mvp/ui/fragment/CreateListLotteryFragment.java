package com.peng.lottery.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.peng.lottery.R;
import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.app.config.TipConfig;
import com.peng.lottery.app.listener.SpannerItemListener;
import com.peng.lottery.app.utils.LotteryUtil;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.contract.fragment.CreateListLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.CreateListLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.recycler.LotteryItemAdapter;
import com.peng.lottery.mvp.ui.adapter.recycler.NumberBallAdapter;

import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_SIZE_BIG;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_SIZE_NULL;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_SIZE_SMALL;
import static com.peng.lottery.app.widget.dialog.LoadingDialog.DIALOG_TYPE_LOADING;

public class CreateListLotteryFragment extends BaseFragment<CreateListLotteryPresenter> implements CreateListLotteryContract.View {

    @BindView(R.id.spinner_type_lottery)
    AppCompatSpinner lotteryType;
    @BindView(R.id.lottery_ball_recycler)
    RecyclerView mBallRecycler;
    @BindView(R.id.et_lottery_size)
    EditText createLotterySize;
    @BindView(R.id.spinner_type_create_lottery)
    AppCompatSpinner createLotteryType;
    @BindView(R.id.bt_start_create_lottery)
    MaterialButton startCreateLottery;
    @BindView(R.id.bt_save_all_lottery)
    MaterialButton saveAllLottery;
    @BindView(R.id.lottery_item_recycler)
    RecyclerView mLotteryRecycler;

    private ActionConfig.LotteryType mLotteryType;
    private NumberBallAdapter mNumberBallAdapter;
    private LotteryItemAdapter mLotteryAdapter;

    @Override
    protected void init() {
        super.init();

        mLotteryType = LOTTERY_TYPE_DLT;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_create_list_lottery;
    }

    @Override
    protected void initView() {
        super.initView();

        // 初始化彩票号码球
        mNumberBallAdapter = new NumberBallAdapter(R.layout.item_number_ball, ActionConfig.getLotteryNumberBallList(LOTTERY_TYPE_DLT));
        mNumberBallAdapter.bindToRecyclerView(mBallRecycler);
        mBallRecycler.setLayoutManager(new GridLayoutManager(mActivity, 7));
        mBallRecycler.setAdapter(mNumberBallAdapter);
        // 初始化生成彩票列表
        mLotteryAdapter = new LotteryItemAdapter(R.layout.item_lottery_info, null);
        mLotteryAdapter.bindToRecyclerView(mLotteryRecycler);
        mLotteryAdapter.setLotteryType(mLotteryType);
        mLotteryRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mLotteryRecycler.setAdapter(mLotteryAdapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initListener() {
        lotteryType.setOnItemSelectedListener(new SpannerItemListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        changeLotteryType(LOTTERY_TYPE_DLT);
                        break;
                    case 1:
                        changeLotteryType(LOTTERY_TYPE_SSQ);
                        break;
                }
            }
        });
        startCreateLottery.setOnClickListener(v -> {
            String sizeStr = createLotterySize.getText().toString();
            if (TextUtils.isEmpty(sizeStr)) {
                ToastUtil.showToast(mActivity, CREATE_LOTTERY_SIZE_NULL);
                return;
            }
            int createSize = Integer.parseInt(sizeStr);
            if (createSize <= 0) {
                ToastUtil.showToast(mActivity, CREATE_LOTTERY_SIZE_SMALL);
                createLotterySize.setText("1");
                return;
            }
            if (createSize > 50) {
                ToastUtil.showToast(mActivity, CREATE_LOTTERY_SIZE_BIG);
                createLotterySize.setText("50");
                return;
            }
            String createType = (String) createLotteryType.getSelectedItem();

            showLoading(DIALOG_TYPE_LOADING, "正在生成号码，请稍候...");
            mPresenter.startCreateLottery(mLotteryType, createType, createSize);
        });
        saveAllLottery.setOnClickListener(v -> {
            if (mLotteryAdapter.isSave() || mLotteryAdapter.getData().size() == 0) {
                return;
            }
            List<List<LotteryNumber>> lotteryList = mLotteryAdapter.getData();
            for (List<LotteryNumber> lotteryValue : lotteryList) {
                LotteryUtil.getInstance().saveLottery(lotteryValue, mLotteryType);
            }
            mLotteryAdapter.setSave(true);
            ToastUtil.showToast(mActivity, TipConfig.APP_SAVE_SUCCESS);
        });
    }

    @Override
    public void createLotteryFinish(List<List<LotteryNumber>> lotteryData) {
        mLotteryAdapter.setNewData(lotteryData);
        mLotteryAdapter.setSave(false);
    }

    private void changeLotteryType(ActionConfig.LotteryType lotteryType) {
        mLotteryType = lotteryType;
        mNumberBallAdapter.setNewData(ActionConfig.getLotteryNumberBallList(lotteryType));
        mLotteryAdapter.setLotteryType(mLotteryType);
    }

}
