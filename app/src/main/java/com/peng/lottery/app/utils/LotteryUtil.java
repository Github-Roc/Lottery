package com.peng.lottery.app.utils;

import android.os.SystemClock;
import android.text.TextUtils;

import com.peng.lottery.app.config.ActionConfig;
import com.peng.lottery.mvp.model.DataManager;
import com.peng.lottery.mvp.model.db.DataBaseHelper;
import com.peng.lottery.mvp.model.db.bean.LotteryData;
import com.peng.lottery.mvp.model.db.bean.LotteryDataDao;
import com.peng.lottery.mvp.model.db.bean.LotteryNumber;
import com.peng.lottery.mvp.model.db.bean.LotteryNumberDao;
import com.peng.lottery.mvp.model.web.bean.LotteryBean;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_11X5;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_DLT;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_PK10;
import static com.peng.lottery.app.config.ActionConfig.LotteryType.LOTTERY_TYPE_SSQ;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_NULL;
import static com.peng.lottery.app.config.ActionConfig.NumberBallType.NUMBER_BALL_TYPE_RED;
import static com.peng.lottery.app.config.TipConfig.APP_SAVE_SUCCESS;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_NUMBER_ERROR;
import static com.peng.lottery.app.config.TipConfig.CREATE_LOTTERY_SAVED;

public class LotteryUtil {

    private static LotteryUtil mInstance;

    private LotteryDataDao mLotteryDataDao;
    private LotteryNumberDao mLotteryNumberDao;

    private int mMaxSize;
    private boolean isSort;
    private String mLuckyStr;
    private String mLotteryLabel;

    private LotteryUtil() {
        DataBaseHelper dataBaseHelper = DataManager.getInstance().getDataBaseHelper();

        mLotteryDataDao = dataBaseHelper.getLotteryDataDao();
        mLotteryNumberDao = dataBaseHelper.getLotteryNumberDao();
    }

    public static LotteryUtil getInstance() {
        if (mInstance == null) {
            mInstance = new LotteryUtil();
        }
        return mInstance;
    }

    /**
     * 设置输入的幸运字符串
     *
     * @param luckyStr 输入的幸运字符串
     */
    public void setLuckyStr(String luckyStr) {
        mLuckyStr = luckyStr;
    }

    /**
     * 设置11选5类型，固定号码size
     *
     * @param type 11选5类型
     * @return 该类型对应的长度
     */
    public ShiYiXuanWuTypeBean getTypeBean(String type) {
        isSort = true;
        mLotteryLabel = type;
        int bonus = 0;
        double probability = 0;
        switch (type) {
            case "任选二":
                mMaxSize = 2;
                bonus = 6;
                probability = 1 / 5.5d;
                break;
            case "任选三":
                mMaxSize = 3;
                bonus = 19;
                probability = 1 / 16.5d;
                break;
            case "任选四":
                mMaxSize = 4;
                bonus = 78;
                probability = 1 / 66d;
                break;
            case "任选五":
                mMaxSize = 5;
                bonus = 540;
                probability = 1 / 462d;
                break;
            case "任选六":
                mMaxSize = 6;
                bonus = 90;
                probability = 1 / 77d;
                break;
            case "任选七":
                mMaxSize = 7;
                bonus = 26;
                probability = 1 / 22d;
                break;
            case "任选八":
                mMaxSize = 8;
                bonus = 9;
                probability = 1 / 8.25d;
                break;
            case "前二直选":
                isSort = false;
                mMaxSize = 2;
                bonus = 65;
                probability = 1 / 55d;
                break;
            case "前三直选":
                isSort = false;
                mMaxSize = 3;
                bonus = 195;
                probability = 1 / 165d;
                break;
            case "前二组选":
                isSort = false;
                mMaxSize = 2;
                bonus = 130;
                probability = 1 / 110d;
                break;
            case "前三组选":
                isSort = false;
                mMaxSize = 3;
                bonus = 1170;
                probability = 1 / 990d;
                break;
        }

        ShiYiXuanWuTypeBean bean = new ShiYiXuanWuTypeBean();
        bean.isSort = isSort;
        bean.size = mMaxSize;
        bean.bonus = bonus;
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(5);
        numberFormat.setRoundingMode(RoundingMode.UP);
        bean.probability = numberFormat.format(probability * 100);
        return bean;
    }

    /**
     * 检测彩票号码长度是否符合规范
     */
    public boolean checkLotterySize(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType) {
        if (LOTTERY_TYPE_DLT.equals(lotteryType) || LOTTERY_TYPE_SSQ.equals(lotteryType)) {
            return lotteryValue != null && lotteryValue.size() >= 7;
        } else if (LOTTERY_TYPE_11X5.equals(lotteryType)) {
            return lotteryValue != null && lotteryValue.size() >= mMaxSize;
        }
        return LOTTERY_TYPE_PK10.equals(lotteryType);
    }

    /**
     * 获取一注随机生成的彩票
     */
    public void getRandomLottery(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType) {
        if (lotteryValue == null) {
            lotteryValue = new ArrayList<>();
        } else {
            lotteryValue.clear();
        }
        complementLottery(lotteryValue, lotteryType);
    }

    /**
     * 随机生成补齐彩票号码
     *
     * @param lotteryValue 现有号码集合
     * @param lotteryType  彩票类型
     */
    public void complementLottery(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType) {
        boolean isLucky = false;
        List<LotteryNumber> tempValue = new ArrayList<>(lotteryValue);
        List<LotteryNumber> numberBallList = ActionConfig.getLotteryNumberBallList(lotteryType);
        if (LOTTERY_TYPE_DLT.equals(lotteryType) || LOTTERY_TYPE_SSQ.equals(lotteryType)) {
            List<LotteryNumber> redBallList = new ArrayList<>();
            List<LotteryNumber> blueBallList = new ArrayList<>();
            for (LotteryNumber lotteryNumber : lotteryValue) {
                if (NUMBER_BALL_TYPE_RED.type.equals(lotteryNumber.getNumberType())) {
                    redBallList.add(lotteryNumber);
                } else {
                    blueBallList.add(lotteryNumber);
                }
            }

            int redBallSize = LOTTERY_TYPE_DLT.equals(lotteryType) ? 35 : 33;
            int blueBallSize = LOTTERY_TYPE_DLT.equals(lotteryType) ? 12 : 16;
            int redBallLength = LOTTERY_TYPE_DLT.equals(lotteryType) ? 5 : 6;
            int blueBallLength = LOTTERY_TYPE_DLT.equals(lotteryType) ? 2 : 1;

            int luckyIndex = 0;
            byte[] luckyByte = new byte[]{};
            isLucky = !TextUtils.isEmpty(mLuckyStr);
            if (isLucky) {
                mLuckyStr += DateFormatUtil.getDate();
                luckyByte = MD5Util.encode(mLuckyStr).getBytes();
            }

            while ((redBallList.size() + blueBallList.size()) < 7 && lotteryValue.size() < 7) {
                if (isLucky) {
                    LotteryNumber numberBall;
                    if (lotteryValue.size() < redBallLength) {
                        numberBall = numberBallList.get(luckyByte[luckyIndex] % redBallSize);
                    } else {
                        numberBall = numberBallList.get((luckyByte[luckyIndex] % blueBallSize) + 35);
                    }
                    luckyIndex++;
                    if (!NUMBER_BALL_TYPE_NULL.type.equals(numberBall.getNumberType())
                            && !lotteryValue.contains(numberBall)) {
                        lotteryValue.add(numberBall);
                    }
                } else {
                    LotteryNumber numberBall = numberBallList.get(new Random().nextInt(numberBallList.size()));
                    if (!NUMBER_BALL_TYPE_NULL.type.equals(numberBall.getNumberType())) {
                        if (NUMBER_BALL_TYPE_RED.type.equals(numberBall.getNumberType())) {
                            if (!redBallList.contains(numberBall) && redBallList.size() < redBallLength) {
                                redBallList.add(numberBall);
                            }
                        } else {
                            if (!blueBallList.contains(numberBall) && blueBallList.size() < blueBallLength) {
                                blueBallList.add(numberBall);
                            }
                        }
                    }
                }
            }
            if (!isLucky) {
                lotteryValue.clear();
                lotteryValue.addAll(redBallList);
                lotteryValue.addAll(blueBallList);
            }
            Collections.sort(lotteryValue);
        } else if (LOTTERY_TYPE_11X5.equals(lotteryType)) {
            Random random = new Random(SystemClock.currentThreadTimeMillis());
            while (lotteryValue.size() < mMaxSize) {
                LotteryNumber numberBall = numberBallList.get(random.nextInt(numberBallList.size()));
                if (!lotteryValue.contains(numberBall)) {
                    lotteryValue.add(numberBall);
                }
            }
            if (isSort) {
                Collections.sort(lotteryValue);
            }
        } else if (LOTTERY_TYPE_PK10.equals(lotteryType)) {
            Collections.shuffle(numberBallList);
            lotteryValue.clear();
            lotteryValue.addAll(numberBallList);
        }
        if (!isLucky && checkLotteryExist(lotteryValue, lotteryType)) {
            // 生成的号码已经保存过，重新生成新的号码
            lotteryValue.clear();
            lotteryValue.addAll(tempValue);
            complementLottery(lotteryValue, lotteryType);
        }
    }

    /**
     * 根据最近50期的开奖记录 计算号码出现次数 按趋近号码平均出现概率生成彩票
     *
     * @param lotteryValue 现有号码集合
     * @param lotteryType  彩票类型
     * @param data         近50期开奖记录
     */
    public void getAILottery(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType, List<LotteryBean> data) {
        // 红色号码球出现次数
        Map<String, Integer> redBallWeightMap = new HashMap<>();
        // 蓝色号码球出现次数
        Map<String, Integer> blueBallWeightMap = new HashMap<>();
        // 根据最近50期开奖历史确认号码出现次数
        for (LotteryBean lotteryRecordItem : data) {
            String[] str = lotteryRecordItem.openCode.split("\\+");
            String[] redBall = str[0].split(",");
            String[] blueBall = lotteryType == LOTTERY_TYPE_DLT ? new String[]{str[1], str[2]} : new String[]{str[1]};
            for (String redNumber : redBall) {
                Integer weight = redBallWeightMap.get(redNumber);
                if (weight == null) {
                    weight = 0;
                }
                redBallWeightMap.put(redNumber, ++weight);
            }
            for (String blueNumber : blueBall) {
                Integer weight = blueBallWeightMap.get(blueNumber);
                if (weight == null) {
                    weight = 0;
                }
                blueBallWeightMap.put(blueNumber, ++weight);
            }
        }
        // 随机生成号码 直到符合规则的号码显示
        while (true) {
            List<LotteryNumber> lottery = new ArrayList<>();
            getRandomLottery(lottery, lotteryType);
            int matchingSize = 0;
            for (LotteryNumber number : lottery) {
                // 当前号码出现的次数
                Integer weight = number.getNumberType().equals(NUMBER_BALL_TYPE_RED.type) ?
                        redBallWeightMap.get(number.getNumberValue()) : blueBallWeightMap.get(number.getNumberValue());
                // 平均出现的次数
                int matchingWeight = number.getNumberType().equals(NUMBER_BALL_TYPE_RED.type) ?
                        LOTTERY_TYPE_DLT.equals(lotteryType) ? 7 : 9 : LOTTERY_TYPE_DLT.equals(lotteryType) ? 8 : 3;
                // 出现次数比平均次数少俩次的情况
                if (weight != null && weight >= (matchingWeight - 2) && weight < matchingWeight) {
                    matchingSize++;
                }
            }
            if (!checkLotteryExist(lottery, lotteryType) && matchingSize > 5) {
                // 符合条件的号码 显示
                lotteryValue.clear();
                lotteryValue.addAll(lottery);
                return;
            }
        }
    }

    /**
     * 保存彩票数据
     *
     * @param lotteryValue 彩票号码集合
     * @param lotteryType  彩票类型
     * @param params       0 彩票标签 1 输入的幸运字符串
     * @return 保存结果
     */
    public String saveLottery(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType, String... params) {
        if (!checkLotterySize(lotteryValue, lotteryType)) {
            return CREATE_LOTTERY_NUMBER_ERROR;
        }
        if (checkLotteryExist(lotteryValue, lotteryType)) {
            return CREATE_LOTTERY_SAVED;
        }
        String lotteryLabel = "", luckyStr = "";
        if (params != null) {
            if (params.length >= 1 && !TextUtils.isEmpty(params[0])) {
                lotteryLabel = params[0];
            }
            if (params.length >= 2 && !TextUtils.isEmpty(params[1])) {
                lotteryLabel = "幸运号码";
                luckyStr = params[1];
            }
        }
        LotteryData lottery = new LotteryData();
        lottery.setLotteryType(lotteryType.type);
        lottery.setCreateDate(DateFormatUtil.getSysDate());
        if (!TextUtils.isEmpty(luckyStr)) {
            lottery.setLuckyStr(luckyStr);
        }
        if (!TextUtils.isEmpty(lotteryLabel)) {
            lottery.setLotteryLabel(lotteryLabel);
        }
        mLotteryDataDao.insertOrReplace(lottery);

        for (LotteryNumber lotteryNumber : lotteryValue) {
            LotteryNumber newNumber = new LotteryNumber();
            newNumber.setLotteryId(lottery.getId());
            newNumber.setNumberType(lotteryNumber.getNumberType());
            newNumber.setNumberValue(lotteryNumber.getNumberValue());
            mLotteryNumberDao.insert(newNumber);
        }
        return APP_SAVE_SUCCESS;
    }

    /**
     * 检测彩票号码是否存在
     */
    private boolean checkLotteryExist(List<LotteryNumber> lotteryValue, ActionConfig.LotteryType lotteryType) {
        QueryBuilder<LotteryData> builder = mLotteryDataDao.queryBuilder();
        String lotteryLabel = lotteryType == LOTTERY_TYPE_11X5 ? mLotteryLabel : "";
        WhereCondition condition = TextUtils.isEmpty(lotteryLabel) ? LotteryDataDao.Properties.LotteryType.eq(lotteryType.type) :
                builder.and(LotteryDataDao.Properties.LotteryType.eq(lotteryType.type), LotteryDataDao.Properties.LotteryLabel.eq(lotteryLabel));
        List<LotteryData> lotteryList = builder
                .where(condition)
                .list();
        if (lotteryList != null && lotteryList.size() > 0) {
            for (LotteryData lottery : lotteryList) {
                for (LotteryNumber newNumber : lotteryValue) {
                    boolean isEquals = false;
                    for (LotteryNumber oldNumber : lottery.getLotteryValue()) {
                        if (oldNumber.getNumberValue().equals(newNumber.getNumberValue())
                                && oldNumber.getNumberType().equals(newNumber.getNumberType())) {
                            isEquals = true;
                            break;
                        }
                    }
                    if (isEquals) {
                        if (lotteryValue.indexOf(newNumber) == lotteryValue.size() - 1) {
                            return true;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    public class ShiYiXuanWuTypeBean {
        public int size;
        public int bonus;
        public boolean isSort;
        public String probability;
    }
}
