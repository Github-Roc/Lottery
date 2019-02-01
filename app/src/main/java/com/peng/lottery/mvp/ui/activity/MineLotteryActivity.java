package com.peng.lottery.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.peng.lottery.R;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.contract.activity.MineLotteryContract;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.presenter.activity.MineLotteryPresenter;
import com.peng.lottery.mvp.ui.adapter.LotteryListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineLotteryActivity extends BaseActivity<MineLotteryPresenter> implements MineLotteryContract.View {

    @BindView(R.id.app_recycler)
    RecyclerView mLotteryListRecycler;

    private List<LotteryData> mLotteryList;
    private LotteryListAdapter mLotteryListAdapter;

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
        mLotteryListAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            showTipDialog("您确定要删除该号码吗？", v -> {
                LotteryData lottery = mLotteryList.get(position);
                mPresenter.deleteLottery(lottery);
                mLotteryList.remove(lottery);
                mLotteryListAdapter.notifyDataSetChanged();

                if (mLotteryList.size() == 0) {
                    mLotteryListAdapter.setEmptyView(R.layout.layout_empty_page);
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
        mLotteryListAdapter = new LotteryListAdapter(R.layout.item_mine_lottery, mLotteryList);
        mLotteryListAdapter.bindToRecyclerView(mLotteryListRecycler);
        mLotteryListRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mLotteryListRecycler.setAdapter(mLotteryListAdapter);
    }

    @Override
    public void onLoadFinish(List<LotteryData> lotteryList) {
        if (lotteryList != null && lotteryList.size() > 0) {
            mLotteryList.addAll(lotteryList);
            mLotteryListAdapter.notifyDataSetChanged();
        } else {
            mLotteryListAdapter.setEmptyView(R.layout.layout_empty_page);
        }
    }
}
