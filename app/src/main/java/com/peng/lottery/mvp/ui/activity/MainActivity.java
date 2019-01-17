package com.peng.lottery.mvp.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peng.lottery.R;
import com.peng.lottery.app.utils.MD5Util;
import com.peng.lottery.app.utils.ToastUtil;
import com.peng.lottery.base.SimpleBaseActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;

public class MainActivity extends SimpleBaseActivity {

    @BindView(R.id.bt_type_daletou)
    Button btTypeDaLeTou;
    @BindView(R.id.bt_type_shuangseqiu)
    Button btTypeShuangSeQiu;
    @BindView(R.id.et_lucky_str)
    MaterialEditText etLuckyStr;
    @BindView(R.id.bt_get_lucky_number)
    Button btGetLuckyNumber;
    @BindView(R.id.bt_get_random_number)
    Button btGetRandomNumber;
    @BindView(R.id.bt_lottery_record)
    Button btLotteryRecord;
    @BindView(R.id.tv_ball_one)
    TextView tvBallOne;
    @BindView(R.id.tv_ball_two)
    TextView tvBallTwo;
    @BindView(R.id.tv_ball_three)
    TextView tvBallThree;
    @BindView(R.id.tv_ball_four)
    TextView tvBallFour;
    @BindView(R.id.tv_ball_five)
    TextView tvBallFive;
    @BindView(R.id.tv_ball_six)
    TextView tvBallSix;
    @BindView(R.id.tv_ball_seven)
    TextView tvBallSeven;
    @BindView(R.id.layout_lottery_number)
    LinearLayout layoutLotteryNumber;

    private static String[] mRedBallNumbers;
    private static String[] mBlueBallNumbers;

    @Override
    protected int setLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();

        changeSelectButton(btTypeDaLeTou, btTypeShuangSeQiu);
        setLotteryList();
    }

    @Override
    protected void initListener() {
        btTypeDaLeTou.setOnClickListener(v -> {
            changeSelectButton(btTypeDaLeTou, btTypeShuangSeQiu);
            setLotteryList();
        });
        btTypeShuangSeQiu.setOnClickListener(v -> {
            changeSelectButton(btTypeShuangSeQiu, btTypeDaLeTou);
            setLotteryList();
        });
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
        btLotteryRecord.setOnClickListener(v -> {
            String url;
            if (btTypeDaLeTou.isSelected()) {
                url = "http://kaijiang.500.com/dlt.shtml";
            } else {
                url = "http://kaijiang.500.com/ssq.shtml";
            }
            WebActivity.start(mActivity, url);
        });
    }

    private void changeSelectButton(Button buttonOne, Button buttonTwo) {
        buttonOne.setSelected(true);
        buttonTwo.setSelected(false);
    }

    private void setLotteryList() {
        if (btTypeDaLeTou.isSelected()) {
            mRedBallNumbers = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
            mBlueBallNumbers = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        } else {
            mRedBallNumbers = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33"};
            mBlueBallNumbers = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16"};
        }
        layoutLotteryNumber.setVisibility(View.GONE);
    }

    private void checkShowLayout() {
        if (layoutLotteryNumber.getVisibility() == View.GONE) {
            layoutLotteryNumber.setVisibility(View.VISIBLE);
        }
    }

    private void setDataToLayout(List<String> redNumberBox, List<String> blueNumberBox) {
        tvBallOne.setText(redNumberBox.get(0));
        tvBallTwo.setText(redNumberBox.get(1));
        tvBallThree.setText(redNumberBox.get(2));
        tvBallFour.setText(redNumberBox.get(3));
        tvBallFive.setText(redNumberBox.get(4));
        if (btTypeDaLeTou.isSelected()) {
            tvBallSix.setText(blueNumberBox.get(0));
            tvBallSeven.setText(blueNumberBox.get(1));
            tvBallSix.setBackgroundResource(R.drawable.shape_blue_round);
        } else {
            tvBallSix.setText(redNumberBox.get(5));
            tvBallSeven.setText(blueNumberBox.get(0));
            tvBallSix.setBackgroundResource(R.drawable.shape_red_round);
        }
    }

    /**
     * 获取红球号码
     */
    private List<String> getRedNumbers(boolean isLucky, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int luckyIndex = 0;
        int length = btTypeDaLeTou.isSelected() ? 5 : 6;
        int ballSize = btTypeDaLeTou.isSelected() ? 35 : 33;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();
        while (numberBox.size() < length) {
            String number;
            if (isLucky) {
                number = mRedBallNumbers[luckyByte[luckyIndex] % ballSize];
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

    /**
     * 获取篮球号码
     */
    private List<String> getBlueNumbers(boolean isLucky, String luckyStr) {
        List<String> numberBox = new ArrayList<>();
        int luckyIndex = 16;
        int length = btTypeDaLeTou.isSelected() ? 2 : 1;
        int ballSize = btTypeDaLeTou.isSelected() ? 12 : 16;
        byte[] luckyByte = MD5Util.encode(luckyStr).getBytes();
        while (numberBox.size() < length) {
            String number;
            if (isLucky) {
                number = mBlueBallNumbers[luckyByte[luckyIndex] % ballSize];
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
