package com.peng.lottery.mvp.ui.fragment;

import android.content.Intent;
import androidx.annotation.Nullable;
import android.widget.RelativeLayout;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.BaseFragment;
import com.peng.lottery.mvp.presenter.fragment.SettingPresenter;
import com.peng.lottery.mvp.ui.activity.InputTextActivity;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.peng.lottery.app.config.TipConfig.APP_SAVE_SUCCESS;

public class SettingFragment extends BaseFragment<SettingPresenter> {

    private static final int REQUEST_INPUT_SLOGAN = 100 >> 2;

    @BindView(R.id.rl_setting_slogan)
    RelativeLayout rlSettingSlogan;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initListener() {
        rlSettingSlogan.setOnClickListener(v -> {
            String slogan = mPresenter.getSlogan();
            InputTextActivity.start(mActivity, getResources().getString(R.string.text_setting_slogan), slogan, REQUEST_INPUT_SLOGAN);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_INPUT_SLOGAN) {
                String slogan = data.getStringExtra(InputTextActivity.INPUT_TEXT);
                mPresenter.saveSlogan(slogan);
                ToastUtil.showToast(mActivity, APP_SAVE_SUCCESS);
            }
        }
    }
}
