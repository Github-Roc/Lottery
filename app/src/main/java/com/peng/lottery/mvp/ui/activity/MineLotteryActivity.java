package com.peng.lottery.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.peng.lottery.R;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.contract.activity.MineLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.presenter.activity.MineLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.MineLotteryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineLotteryActivity extends BaseActivity<MineLotteryPresenter> implements MineLotteryContract.View {

    @BindView(R.id.app_recycler)
    RecyclerView mLotteryRecycler;

    private List<LotteryData> mLotteryList;
    private MineLotteryAdapter mLotteryAdapter;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_mine_lottery;
    }

    @Override
    protected void initView() {
        super.initView();
        mActivityTitle.setText(R.string.title_mine_lottery);

        initRecycler();
    }

    @Override
    protected void initListener() {
        mLotteryAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showTipDialog("您确定要删除该号码吗？", v -> {
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
    }

    @Override
    protected void loadData() {
        mPresenter.getMineLotteryList();
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    private void initRecycler() {
        mLotteryList = new ArrayList<>();
        mLotteryAdapter = new MineLotteryAdapter(R.layout.item_mine_lottery, mLotteryList);
        mLotteryAdapter.bindToRecyclerView(mLotteryRecycler);
        mLotteryRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mLotteryRecycler.setAdapter(mLotteryAdapter);
    }

    @Override
    public void onLoadFinish(List<LotteryData> lotteryList) {
        if (lotteryList != null && lotteryList.size() > 0) {
            mLotteryList.addAll(lotteryList);
            mLotteryAdapter.notifyDataSetChanged();
        } else {
            mLotteryAdapter.setEmptyView(R.layout.layout_empty_page);
        }
    }
}
