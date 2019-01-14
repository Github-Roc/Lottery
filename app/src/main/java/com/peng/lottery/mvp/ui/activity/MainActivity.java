package com.peng.lottery.mvp.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.base.SimpleBaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.bt_get_number)
    Button btGetNumber;
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

    private List<String> mNumberBox = new ArrayList<>();
    private String[] mRedBallNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
    private String[] mBlueBallNumbers = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        btGetNumber.setOnClickListener(v -> {
            if (layoutLotteryNumber.getVisibility() == View.GONE) {
                layoutLotteryNumber.setVisibility(View.VISIBLE);
            }

            mNumberBox.clear();
            tvRedOne.setText(getRandomNumber(mRedBallNumbers));
            tvRedTwo.setText(getRandomNumber(mRedBallNumbers));
            tvRedThree.setText(getRandomNumber(mRedBallNumbers));
            tvRedFour.setText(getRandomNumber(mRedBallNumbers));
            tvRedFive.setText(getRandomNumber(mRedBallNumbers));
            mNumberBox.clear();
            tvBlueOne.setText(getRandomNumber(mBlueBallNumbers));
            tvBlueTwo.setText(getRandomNumber(mBlueBallNumbers));
        });
    }

    private String getRandomNumber(String[] numbers) {
        String number = "";
        boolean flag = true;
        while (flag){
            number = numbers[new Random().nextInt(numbers.length)];
            if (!mNumberBox.contains(number)) {
                mNumberBox.add(number);
                flag = false;
            }
        }
        return number;
    }
}
