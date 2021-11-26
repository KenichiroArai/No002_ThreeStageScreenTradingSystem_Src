package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import kmg.im.stock.tssts.domain.model.SptsRegDataModel;

/**
 * 株価時系列登録データモデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsRegDataModelImpl implements SptsRegDataModel {

    /** 株価時系列期間の種類ID */
    private long sptsptId;

    /** 番号 */
    private long no;

    /** 期間開始日 */
    private LocalDate periodStartDate;

    /** 期間終了日 */
    private LocalDate periodEndDate;

    /** 始値 */
    private BigDecimal op;

    /** 高値 */
    private BigDecimal hp;

    /** 安値 */
    private BigDecimal lp;

    /** 終値 */
    private BigDecimal cp;

    /** 出来高 */
    private long volume;

    /**
     * 株価時系列期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     */
    @Override
    public void setSptsptId(final long sptsptId) {
        this.sptsptId = sptsptId;
    }

    /**
     * 株価時系列期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類ID
     */
    @Override
    public long getSptsptId() {
        final long result = this.sptsptId;
        return result;
    }

    /**
     * 番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     */
    @Override
    public void setNo(final long no) {
        this.no = no;
    }

    /**
     * 番号を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 番号
     */
    @Override
    public long getNo() {
        final long result = this.no;
        return result;
    }

    /**
     * 期間開始日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodStartDate
     *                        期間開始日
     */
    @Override
    public void setPeriodStartDate(final LocalDate periodStartDate) {
        this.periodStartDate = periodStartDate;
    }

    /**
     * 期間開始日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間開始日
     */
    @Override
    public LocalDate getPeriodStartDate() {
        final LocalDate result = this.periodStartDate;
        return result;
    }

    /**
     * 期間終了日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodEndDate
     *                      期間終了日
     */
    @Override
    public void setPeriodEndDate(final LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    /**
     * 期間終了日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間終了日
     */
    @Override
    public LocalDate getPeriodEndDate() {
        final LocalDate result = this.periodEndDate;
        return result;
    }

    /**
     * 始値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param op
     *           始値
     */
    @Override
    public void setOp(final BigDecimal op) {
        this.op = op;
    }

    /**
     * 始値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 始値
     */
    @Override
    public BigDecimal getOp() {
        final BigDecimal result = this.op;
        return result;
    }

    /**
     * 高値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param hp
     *           高値
     */
    @Override
    public void setHp(final BigDecimal hp) {
        this.hp = hp;
    }

    /**
     * 高値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 高値
     */
    @Override
    public BigDecimal getHp() {
        final BigDecimal result = this.hp;
        return result;
    }

    /**
     * 安値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lp
     *           安値
     */
    @Override
    public void setLp(final BigDecimal lp) {
        this.lp = lp;
    }

    /**
     * 安値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 安値
     */
    @Override
    public BigDecimal getLp() {
        final BigDecimal result = this.lp;
        return result;
    }

    /**
     * 終値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param cp
     *           終値
     */
    @Override
    public void setCp(final BigDecimal cp) {
        this.cp = cp;
    }

    /**
     * 終値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終値
     */
    @Override
    public BigDecimal getCp() {
        final BigDecimal result = this.cp;
        return result;
    }

    /**
     * 出来高を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param volume
     *               出来高
     */
    @Override
    public void setVolume(final long volume) {
        this.volume = volume;
    }

    /**
     * 出来高を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 出来高
     */
    @Override
    public long getVolume() {
        final long result = this.volume;
        return result;
    }

    /**
     * 終値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終値
     */
    @Override
    public BigDecimal get() {
        final BigDecimal result = this.cp;
        return result;
    }

}
