package com.peng.lottery.mvp.ui.fragment;

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

public class MineLotteryFragment extends BaseFragment<MineLotteryPresenter> implements MineLotteryContract.View {
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.app_recycler)
    RecyclerView mLotteryRecycler;
    @BindView(R.id.mine_lottery_bottom_layout)
    LinearLayout miBottomLayout;
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
        mCleanLottery.setOnClickListener(v -> mActivity.showTipDialog("您确定要清空所有号码吗？", view -> {
            mPresenter.deleteAll();
            mLotteryList.clear();
            mLotteryAdapter.notifyDataSetChanged();
            mLotteryAdapter.setEmptyView(R.layout.layout_empty_page);
        }));
        mBtVerificationLottery.setOnClickListener(v ->
                mActivity.showTipDialog("与上期开奖的双色球号码对比判断是否中奖（目前只支持双色球）", view ->
                        mPresenter.verificationLottery()));
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
            mActivity.showTipDialog("您确定要删除该号码吗？", v -> {
                LotteryData lottery = mLotteryList.get(position);
                mPresenter.deleteLottery(lottery);
                mLotteryList.remove(lottery);
                mLotteryAdapter.notifyDataSetChanged();

                if (mLotteryList.size() == 0) {
                    mLotteryAdapter.setEmptyView(R.layout.layout_empty_page);
                }
            });
            return true;
        });
        mLotteryRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    miBottomLayout.setVisibility(View.GONE);
                } else {
                    if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                        miBottomLayout.setVisibility(View.VISIBLE);
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