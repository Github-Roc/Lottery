package com.peng.lottery.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.peng.lottery.R;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.presenter.activity.SplashPresenter;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class SplashActivity extends BaseActivity<SplashPresenter> {

    @BindView(R.id.iv_splash_image)
    ImageView ivSplashImage;
    @BindView(R.id.tv_start_next)
    TextView tvStartNext;
    @BindView(R.id.tv_splash_slogan)
    TextView tvSplashSlogan;
    @BindView(R.id.tv_splash_ending)
    TextView tvSplashEnding;

    private Timer mTimer;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_splash;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        super.initView();

        File splashImage = mPresenter.getSplashImage();
        if (splashImage != null) {
            Glide.with(mActivity).load(splashImage).into(ivSplashImage);
        }
        tvSplashSlogan.setText(mPresenter.getSloganText());
        tvSplashEnding.setText(mPresenter.getEndingText());

        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startNext();
            }
        }, 3000);
    }

    @Override
    protected void initListener() {
        tvStartNext.setOnClickListener(v -> {
            mTimer.cancel();
            startNext();
        });
    }

    @Override
    protected boolean enableInitToolBar() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mTimer.cancel();
    }

    private void startNext() {
        Intent intent = new Intent(mActivity, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

