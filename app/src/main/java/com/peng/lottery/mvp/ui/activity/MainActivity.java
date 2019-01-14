package com.peng.lottery.mvp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.MD5Util;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.SimpleBaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.et_lucky_str)
    EditText etLuckyStr;
    @BindView(R.id.bt_get_lucky_number)
    Button btGetLuckyNumber;
    @BindView(R.id.bt_get_random_number)
    Button btGetRandomNumber;
    @BindView(R.id.tv_red_one)
    TextView tvRedOne;
    @BindView(R.id.tv_red_two)
    TextView tvRedTwo;
    @BindView(R.id.tv_red_three)
    TextView tvRedThree;
    @BindView(R.id.tv_red_four)
    TextView tvRedFour;
    @BindView(R.id.tv_red_five)
    TextView tvRedFive;
    @BindView(R.id.tv_blue_one)
    TextView tvBlueOne;
    @BindView(R.id.tv_blue_two)
    TextView tvBlueTwo;
    @BindView(R.id.layout_lottery_number)
    LinearLayout layoutLotteryNumber;

    private static String[] mRedBallNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
    private static String[] mBlueBallNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        btGetLuckyNumber.setOnClickListener(v -> {
            String luckyStr = etLuckyStr.getText().toString().trim();
            if (TextUtils.isEmpty(luckyStr)) {
                ToastUtil.showToast(mActivity, "请先输入一些内容吧！");
                return;
            }

            checkShowLayout();
            setDataToLayout(getRedNumbers(true, luckyStr), getBlueNumbers(true, luckyStr));
        });
        btGetRandomNumber.setOnClickListener(v -> {
            checkShowLayout();
            setDataToLayout(getRedNumbers(false, ""), getBlueNumbers(false, ""));
        });
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }

    private void setDataToLayout(List<String> redNumberBox, List<String> blueNumberBox) {
        if (redNumberBox.size() == 5) {
            tvRedOne.setText(redNumberBox.get(0));
            tvRedTwo.setText(redNumberBox.get(1));
            tvRedThree.setText(redNumberBox.get(2));
            tvRedFour.setText(redNumberBox.get(3));
            tvRedFive.setText(redNumberBox.get(4));
        }
        if (blueNumberBox.size() == 2) {
            tvBlueOne.setText(blueNumberBox.get(0));
            tvBlueTwo.setText(blueNumberBox.get(1));
        }
    }

    private List<String> getRedNumbers(boolean isLucky, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int luckyIndex = 0;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();
        while (numberBox.size() < 5) {
            String number;
            if (isLucky) {
                number = mRedBallNumbers[luckyByte[luckyIndex]%35];
                luckyIndex += 2;
            } else {
                number = mRedBallNumbers[new Random().nextInt(mRedBallNumbers.length)];
            }
            if (!numberBox.contains(number)) {
                numberBox.add(number);
            }
        }
        Collections.sort(numberBox);
        return numberBox;
    }

    private List<String> getBlueNumbers(boolean isLucky, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int luckyIndex = 16;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();
        while (numberBox.size() < 2) {
            String number;
            if (isLucky) {
                number = mBlueBallNumbers[luckyByte[luckyIndex]%12];
                luckyIndex += 3;
            } else {
                number = mBlueBallNumbers[new Random().nextInt(mBlueBallNumbers.length)];
            }
            if (!numberBox.contains(number)) {
                numberBox.add(number);
            }
        }
        Collections.sort(numberBox);
        return numberBox;
    }
}
