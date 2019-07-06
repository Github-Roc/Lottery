package com.peng.lottery.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.peng.lottery.R;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseActivity;
import com.peng.lottery.mvp.presenter.activity.SettingPresenter;

import java.util.List;

import butterknife.BindView;

import static com.peng.lottery.app.config.TipConfig.APP_SAVE_SUCCESS;

public class SettingActivity extends BaseActivity<SettingPresenter> {

    @BindView(R.id.rl_setting_image)
    RelativeLayout rlSettingImage;
    @BindView(R.id.rl_setting_slogan)
    RelativeLayout rlSettingSlogan;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        super.initView();

        mActivityTitle.setText(R.string.title_setting);
    }

    @Override
    protected void initListener() {
        rlSettingImage.setOnClickListener(v -> {
            PictureSelector.create(mActivity)
                    .openGallery(PictureMimeType.ofImage())
                    .theme(R.style.PictureSelectorTheme)
                    .maxSelectNum(1)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
        rlSettingSlogan.setOnClickListener(v -> {
            String slogan = mPresenter.getSlogan();
            InputTextActivity.start(mActivity, getResources().getString(R.string.text_setting_slogan), slogan, AppConfig.REQUEST_INPUT_SLOGAN);
        });
    }

    @Override
    protected boolean enableSlidingFinish() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == AppConfig.REQUEST_INPUT_SLOGAN) {
                String slogan = data.getStringExtra(InputTextActivity.INPUT_TEXT);
                mPresenter.saveSlogan(slogan);
                ToastUtil.showToast(mActivity, APP_SAVE_SUCCESS);
            } else if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() > 0) {
                    LocalMedia localMedia = selectList.get(0);
                    mPresenter.saveImagePath(localMedia.getPath());
                    ToastUtil.showToast(mActivity, APP_SAVE_SUCCESS);
                }
            }
        }
    }
}
