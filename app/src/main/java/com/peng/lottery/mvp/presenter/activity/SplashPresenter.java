package com.peng.lottery.mvp.presenter.activity;

import android.text.TextUtils;

import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.config.SharedPreferencesConfig;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;

import java.io.File;
import java.util.Random;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter {

    private static String[] mSloganArr = {
            "欲戴皇冠，必承其重。",
            "没有伞的孩子，只能奋力奔跑。",
            "走在黑暗里不可怕，只要心里有光。",
            "不要把这个世界让给那些你所鄙视的人!",
            "那些杀不死我的，终将使我变得更强大！",
            "天再高又怎样，踮起脚尖就更接近阳光。",
            "除自己以外，没有人能哄骗你离开最后的成功。",
            "人生之光荣，不在于永不失败，而在于能屡仆屡起。",
            "有的梦，纵然遥不可及，却也不是不可能实现，只要你足够强大。",
            "上帝会给每一个故事安排美好的结局，如果它还不够美好，说明他还不是最后的结局。",
    };

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public File getSplashImage() {
        String splashImagePath = (String) mSpHelper.get(SharedPreferencesConfig.SPLASH_IMAGE, "");
        if (!TextUtils.isEmpty(splashImagePath)) {
            File imageFile = new File(splashImagePath);
            if (imageFile.exists()) {
                return imageFile;
            }
        }
        return null;
    }

    public String getSloganText() {
        String sloganText = (String) mSpHelper.get(SharedPreferencesConfig.SPLASH_SLOGAN, "");
        if (TextUtils.isEmpty(sloganText)) {
            sloganText = mSloganArr[new Random().nextInt(mSloganArr.length)];
        }
        return sloganText;
    }

    public String getEndingText() {
        String endingText = (String) mSpHelper.get(SharedPreferencesConfig.SPLASH_ENDING, "");
        if (TextUtils.isEmpty(endingText)) {
            endingText = AppConfig.APP_TAG;
        }
        return endingText;
    }
}
