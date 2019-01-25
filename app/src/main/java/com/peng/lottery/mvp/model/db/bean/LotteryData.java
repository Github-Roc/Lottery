package com.peng.lottery.mvp.model.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class LotteryData {

    @Id(autoincrement = true)
    private Long Id;
    //cardId为Photo类中定义的一个属性
    @ToMany(referencedJoinProperty = "lotteryId")
    private List<LotteryNumber> lotteryValue;

    // 彩票类型
    private String lotteryType;
    // 是否是幸运号码
    private String luckyType;
    // 创建时间
    private String createData;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 460360553)
    private transient LotteryDataDao myDao;
    @Generated(hash = 1038688054)
    public LotteryData(Long Id, String lotteryType, String luckyType,
            String createData) {
        this.Id = Id;
        this.lotteryType = lotteryType;
        this.luckyType = luckyType;
        this.createData = createData;
    }
    @Generated(hash = 1125241785)
    public LotteryData() {
    }
    public Long getId() {
        return this.Id;
    }
    public void setId(Long Id) {
        this.Id = Id;
    }
    public String getLotteryType() {
        return this.lotteryType;
    }
    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }
    public String getLuckyType() {
        return this.luckyType;
    }
    public void setLuckyType(String luckyType) {
        this.luckyType = luckyType;
    }
    public String getCreateData() {
        return this.createData;
    }
    public void setCreateData(String createData) {
        this.createData = createData;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 954677559)
    public List<LotteryNumber> getLotteryValue() {
        if (lotteryValue == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LotteryNumberDao targetDao = daoSession.getLotteryNumberDao();
            List<LotteryNumber> lotteryValueNew = targetDao
                    ._queryLotteryData_LotteryValue(Id);
            synchronized (this) {
                if (lotteryValue == null) {
                    lotteryValue = lotteryValueNew;
                }
            }
        }
        return lotteryValue;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 211163649)
    public synchronized void resetLotteryValue() {
        lotteryValue = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 654243956)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLotteryDataDao() : null;
    }

}
