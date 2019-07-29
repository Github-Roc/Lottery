package com.peng.lottery.mvp.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luck.picture.lib.rxbus2.RxBus;
import com.peng.lottery.R;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.contract.fragment.WebCollectorContract;
import com.peng.lottery.mvp.model.db.bean.WebUrl;
import com.peng.lottery.mvp.presenter.fragment.WebCollectorPresenter;
import com.peng.lottery.mvp.ui.adapter.WebUrlAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.RecyclerView.VERTICAL;
import static com.peng.lottery.app.config.TipConfig.APP_SAVE_SUCCESS;

public class CollectorFragment extends BaseFragment<WebCollectorPresenter> implements WebCollectorContract.View {

    @BindView(R.id.app_recycler)
    RecyclerView mWebUrlRecycler;

    private List<WebUrl> mWebUrlList;
    private WebUrlAdapter mWebUrlAdapter;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_collector;
    }

    @Override
    protected void initView() {
        super.initView();

        initRecycler();
    }

    @Override
    protected void initListener() {
        mWebUrlAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WebUrl item = mWebUrlList.get(position);
            switch (view.getId()) {
                case R.id.layout_content:
                    RxBus.getDefault().post(item);
                    mActivity.finish();
                    break;
                case R.id.bt_web_set_home:
                    mPresenter.setWebHome(item.getCollectionUrl());
                    ToastUtil.showToast(mActivity, APP_SAVE_SUCCESS);
                    break;
                case R.id.bt_web_url_delete:
                    mPresenter.deleteWebUrl(item);
                    mWebUrlList.remove(item);
                    mWebUrlAdapter.notifyDataSetChanged();

                    if (mWebUrlList.size() == 0) {
                        mWebUrlAdapter.setEmptyView(R.layout.layout_empty_page);
                    }
                    break;
            }
        });
    }

    @Override
    protected void loadData() {
        mPresenter.getWebUrlList();
    }

    private void initRecycler() {
        mWebUrlList = new ArrayList<>();
        mWebUrlAdapter = new WebUrlAdapter(R.layout.item_web_collector, mWebUrlList);
        mWebUrlAdapter.bindToRecyclerView(mWebUrlRecycler);
        mWebUrlRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mWebUrlRecycler.addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));
        mWebUrlRecycler.setAdapter(mWebUrlAdapter);
    }

    @Override
    public void onLoadFinish(List<WebUrl> urlList) {
        if (urlList != null && urlList.size() > 0) {
            mWebUrlList.addAll(urlList);
            mWebUrlAdapter.notifyDataSetChanged();
        } else {
            mWebUrlAdapter.setEmptyView(R.layout.layout_empty_page);
        }
    }
}
