package com.peng.lottery.mvp.ui.fragment;

import android.annotation.SuppressLint;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.peng.lottery.R;
import com.peng.lottery.app.type.LotteryType;
import com.peng.lottery.app.listener.SpannerItemListener;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.contract.fragment.CreateListLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.presenter.fragment.CreateListLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.recycler.LotteryItemAdapter;
import com.peng.lottery.mvp.ui.adapter.recycler.NumberBallAdapter;

import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.AppConfig.getLotteryNumberBallList;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.type.LotteryType.LOTTERY_TYPE_SSQ;


public class CreateListLotteryFragment extends BaseFragment<CreateListLotteryPresenter> implements CreateListLotteryContract.View {

    @BindView(R.id.spinner_type_lottery)
    AppCompatSpinner lotteryType;
    @BindView(R.id.spinner_create_lottery_size)
    AppCompatSpinner createLotterySize;
    @BindView(R.id.spinner_create_lottery_type)
    AppCompatSpinner createLotteryType;
    @BindView(R.id.lottery_ball_recycler)
    RecyclerView mBallRecycler;
    @BindView(R.id.bt_start_create_lottery)
    MaterialButton startCreateLottery;
    @BindView(R.id.bt_save_all_lottery)
    MaterialButton saveAllLottery;
    @BindView(R.id.lottery_item_recycler)
    RecyclerView mLotteryRecycler;

    private LotteryType mLotteryType;
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
        mNumberBallAdapter = new NumberBallAdapter(R.layout.item_number_ball, getLotteryNumberBallList(LOTTERY_TYPE_DLT));
        mNumberBallAdapter.bindToRecyclerView(mBallRecycler);
        mNumberBallAdapter.setSelect(true);
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
        createLotteryType.setOnItemSelectedListener(new SpannerItemListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBallRecycler.setVisibility(position > 1 ? View.VISIBLE : View.GONE);
            }
        });
        startCreateLottery.setOnClickListener(v -> {
            int createSizePosition = createLotterySize.getSelectedItemPosition();
            if (createSizePosition == 0) {
                ToastUtil.showToast(mActivity, getString(R.string.tip_create_lottery_size));
                return;
            }
            int createSize = Integer.parseInt((String) createLotterySize.getSelectedItem());
            int createTypePosition = createLotteryType.getSelectedItemPosition();
            if (createTypePosition == 0) {
                ToastUtil.showToast(mActivity, getString(R.string.tip_create_lottery_type));
                return;
            }
            List<LotteryNumber> selectNumbers = mNumberBallAdapter.getSelectLotteryNumber();
            if (createTypePosition == 2) {
                int maxRedSize = LOTTERY_TYPE_DLT.equals(mLotteryType) ? 28 : 25;
                int maxBlueSize = LOTTERY_TYPE_DLT.equals(mLotteryType) ? 8 : 13;
                if (mNumberBallAdapter.getRedBallSize() > maxRedSize || mNumberBallAdapter.getBlueBallSize() > maxBlueSize) {
                    ToastUtil.showToast(mActivity, "所剩号码不足以批量生成号码！");
                    return;
                }
            }
            if (createTypePosition == 3) {
                int minRedSize = LOTTERY_TYPE_DLT.equals(mLotteryType) ? 7 : 8;
                int minBlueSize = LOTTERY_TYPE_DLT.equals(mLotteryType) ? 4 : 3;
                if (mNumberBallAdapter.getRedBallSize() < minRedSize || mNumberBallAdapter.getBlueBallSize() < minBlueSize) {
                    ToastUtil.showToast(mActivity, "所选号码不足以批量生成号码！");
                    return;
                }
            }

            showLoading("正在生成号码，请稍候...");
            mPresenter.startCreateLottery(mLotteryType, selectNumbers, createSize, createTypePosition);
        });
        saveAllLottery.setOnClickListener(v -> {
            if (mLotteryAdapter.isSave() || mLotteryAdapter.getData().size() == 0) {
                return;
            }

            showLoading("正在保存号码，请稍候...");
            mPresenter.saveLotteryList(mLotteryAdapter.getData(), mLotteryType);
        });
    }

    @Override
    public void createLotteryFinish(List<List<LotteryNumber>> lotteryData) {
        mLotteryAdapter.setNewData(lotteryData);
        mLotteryAdapter.setSave(false);
    }

    @Override
    public void saveLotterySuccess() {
        mLotteryAdapter.setSave(true);
    }

    private void changeLotteryType(LotteryType lotteryType) {
        mLotteryType = lotteryType;
        mNumberBallAdapter.setSelect(true);
        mNumberBallAdapter.setNewData(getLotteryNumberBallList(lotteryType));
        mLotteryAdapter.setLotteryType(mLotteryType);
    }

}
