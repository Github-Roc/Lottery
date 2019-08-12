package com.peng.lottery.mvp.ui.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.peng.lottery.R;
import com.peng.lottery.app.widget.dialog.ShowInfoDialog;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.contract.fragment.MineLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.presenter.fragment.MineLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.MineLotteryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;

public class MineLotteryFragment extends BaseFragment<MineLotteryPresenter> implements MineLotteryContract.View {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.app_recycler)
    RecyclerView mLotteryRecycler;
    @BindView(R.id.mine_lottery_bottom_layout)
    LinearLayout mBottomLayout;
    @BindView(R.id.bt_clean_lottery)
    MaterialButton mCleanLottery;
    @BindView(R.id.bt_verification_lottery)
    MaterialButton mBtVerificationLottery;

    private List<LotteryData> mLotteryList;
    private LinearLayoutManager mLayoutManager;
    private MineLotteryAdapter mLotteryAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_mine_lottery;
    }

    @Override
    protected void initView() {
        super.initView();

        initRecycler();
    }

    @Override
    protected void initListener() {
        mCleanLottery.setOnClickListener(v -> {
            if (mPresenter.isHasList(null)) {
                mActivity.showTipDialog("确定要清空所有号码吗？", view -> {
                    mPresenter.deleteAll();
                    mLotteryList.clear();
                    mLotteryAdapter.notifyDataSetChanged();
                    mLotteryAdapter.setEmptyView(R.layout.layout_empty_page);
                });
            } else {
                showToast("还没有保存号码！");
            }
        });
        mBtVerificationLottery.setOnClickListener(v -> new ShowInfoDialog(mActivity)
                .setContent("与上期开奖号码对比检测是否中奖")
                .setButtonText("大乐透", "双色球")
                .setButtonColor(Color.parseColor("#2177B8"), Color.parseColor("#2177B8"))
                .setOnClickListener(view1 -> {
                    if (mPresenter.isHasList(LOTTERY_TYPE_DLT)) {
                        mPresenter.verificationLottery(LOTTERY_TYPE_DLT);
                    } else {
                        showToast("还没有保存大乐透号码！");
                    }
                }, view2 -> {
                    if (mPresenter.isHasList(LOTTERY_TYPE_SSQ)) {
                        mPresenter.verificationLottery(LOTTERY_TYPE_SSQ);
                    } else {
                        showToast("还没有保存双色球号码！");
                    }
                }).show());
        mLotteryAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mRefreshLayout.setRefreshing(true);
            LotteryData lottery = mLotteryList.get(position);
            Map<String, String> param = new HashMap<>();
            switch (view.getId()) {
                case R.id.tv_lottery_date:
                    param.put("lotteryDate", lottery.getCreateDate());
                    break;
                case R.id.tv_lottery_label:
                    param.put("lotteryLabel", lottery.getLotteryLabel());
                    break;
                case R.id.tv_lottery_type:
                    param.put("lotteryType", lottery.getLotteryType());
                    break;
            }
            mPresenter.getMineLotteryList(param);
        });
        mLotteryAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mActivity.showTipDialog("确定要删除该号码吗？", v -> {
                LotteryData lottery = mLotteryList.get(position);
                mPresenter.deleteLottery(lottery);
                mLotteryList.remove(lottery);
                mLotteryAdapter.notifyDataSetChanged();

                if (mLotteryList.size() == 0) {
                    mPresenter.getMineLotteryList(null);
                }
            });
            return true;
        });
        mLotteryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || !mPresenter.isHasList(null)) {
                    mBottomLayout.setVisibility(View.GONE);
                } else {
                    if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                        mBottomLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        mRefreshLayout.setOnRefreshListener(this::loadData);
    }

    @Override
    protected void loadData() {
        mPresenter.getMineLotteryList(null);
    }

    private void initRecycler() {
        mLotteryList = new ArrayList<>();
        mLotteryAdapter = new MineLotteryAdapter(R.layout.item_mine_lottery, mLotteryList);
        mLotteryAdapter.bindToRecyclerView(mLotteryRecycler);
        mLayoutManager = new LinearLayoutManager(mActivity);
        mLotteryRecycler.setLayoutManager(mLayoutManager);
        mLotteryRecycler.setAdapter(mLotteryAdapter);
        mRefreshLayout.setColorSchemeResources(R.color.app_color);
    }

    @Override
    public void onLoadFinish(List<LotteryData> lotteryList) {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
        if (lotteryList != null && lotteryList.size() > 0) {
            mLotteryList.clear();
            mLotteryList.addAll(lotteryList);
            mLotteryAdapter.notifyDataSetChanged();
        } else {
            mLotteryAdapter.setEmptyView(R.layout.layout_empty_page);
        }
    }

    @Override
    public void showVerificationResult(String message) {
        new ShowInfoDialog(mActivity, message).show();
    }
}
