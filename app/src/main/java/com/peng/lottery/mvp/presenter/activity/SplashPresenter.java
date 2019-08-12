package com.peng.lottery.mvp.presenter.activity;

import android.text.TextUtils;

import com.peng.lottery.R;
import com.peng.lottery.app.config.AppConfig;
import com.peng.lottery.app.config.SharedPreferencesConfig;
import com.peng.lottery.base.BasePresenter;
import com.peng.lottery.mvp.model.DataManager;

import java.util.Random;

import javax.inject.Inject;

public class SplashPresenter extends BasePresenter {

    private static int[] mSplashImgRes = {
            R.mipmap.bg_app_splash01,
            R.mipmap.bg_app_splash02,
            R.mipmap.bg_app_splash03,
            R.mipmap.bg_app_splash04,
            R.mipmap.bg_app_splash05,
    };

    private static String[] mSloganArr = {
            "下一注就中奖！",
            "欲戴皇冠，必承其重。",
            "没有伞的孩子，只能奋力奔跑。",
            "走在黑暗里不可怕，只要心里有光。",
            "不要把这个世界让给那些你所鄙视的人!",
            "那些杀不死我的，终将使我变得更强大！",
            "天再高又怎样，踮起脚尖就更接近阳光。",
            "弱小和无知，不是生存的障碍，傲慢才是。",
            "除自己以外，没有人能哄骗你离开最后的成功。",
            "我们都是阴沟里的虫子,但总还是得有人仰望星空。",
            "我爱你，与你有何相干？毁灭你，又与你有何相干？",
            "人生之光荣，不在于永不失败，而在于能屡仆屡起。",
            "有的梦，纵然遥不可及，却也不是不可能实现，只要你足够强大。",
            "给岁月以文明，而不是给文明岁月，给时光以生命而不是给生命以时光。",
            "上帝会给每一个故事安排美好的结局，如果它还不够美好，说明他还不是最后的结局。",
            "死亡是一座永恒的灯塔，不管你驶向何方，最终都会朝它转向。一切都将逝去，只有死神永生。",
    };

    @Inject
    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    public int getSplashImgRes() {
        return mSplashImgRes[new Random().nextInt(mSplashImgRes.length)];
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
