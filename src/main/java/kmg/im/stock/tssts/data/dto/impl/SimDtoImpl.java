package kmg.im.stock.tssts.data.dto.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import kmg.im.stock.tssts.data.dto.SimDto;

/**
 * シミュレーションＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SimDtoImpl implements SimDto {

    /** 株銘柄ID */
    private long stockBrandId;

    /** 株銘柄名称 */
    private String stockBrandName;

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 株価時系列期間の種類ID */
    private long sptsptId;

    /** 株価時系列期間の種類名称 */
    private String sptsptName;

    /** 期間の種類ID */
    private long periodTypeId;

    /** 期間の種類名称 */
    private String periodTypeName;

    /** 株価時系列ID */
    private long sptsId;

    /** 株価時系列名称 */
    private String sptsName;

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

    /** 株価計算値の種類ID */
    private long spcvtId;

    /** 株価計算値の種類名称 */
    private String spcvtName;

    /** 計算値 */
    private BigDecimal calcValue;

    /**
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    @Override
    public void setStockBrandId(final long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 株銘柄名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandName
     *                       株銘柄名称
     */
    @Override
    public void setStockBrandName(final String stockBrandName) {
        this.stockBrandName = stockBrandName;
    }

    /**
     * 株銘柄名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄名称
     */
    @Override
    public String getStockBrandName() {
        final String result = this.stockBrandName;
        return result;
    }

    /**
     * 株価銘柄コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     */
    @Override
    public void setStockBrandCode(final long stockBrandCode) {
        this.stockBrandCode = stockBrandCode;
    }

    /**
     * 株価銘柄コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄コード
     */
    @Override
    public long getStockBrandCode() {
        final long result = this.stockBrandCode;
        return result;
    }

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
     * 株価時系列期間の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptName
     *                   株価時系列期間の種類名称
     */
    @Override
    public void setSptsptName(final String sptsptName) {
        this.sptsptName = sptsptName;
    }

    /**
     * 株価時系列期間の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類名称
     */
    @Override
    public String getSptsptName() {
        final String result = this.sptsptName;
        return result;
    }

    /**
     * 期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeId
     *                     期間の種類ID
     */
    @Override
    public void setPeriodTypeId(final long periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    /**
     * 期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類ID
     */
    @Override
    public long getPeriodTypeId() {
        final long result = this.periodTypeId;
        return result;
    }

    /**
     * 期間の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeName
     *                       期間の種類名称
     */
    @Override
    public void setPeriodTypeName(final String periodTypeName) {
        this.periodTypeName = periodTypeName;
    }

    /**
     * 期間の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類名称
     */
    @Override
    public String getPeriodTypeName() {
        final String result = this.periodTypeName;
        return result;
    }

    /**
     * 株価時系列IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsId
     *               株価時系列ID
     */
    @Override
    public void setSptsId(final long sptsId) {
        this.sptsId = sptsId;
    }

    /**
     * 株価時系列IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列ID
     */
    @Override
    public long getSptsId() {
        final long result = this.sptsId;
        return result;
    }

    /**
     * 株価時系列名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsName
     *                 株価時系列名称
     */
    @Override
    public void setSptsName(final String sptsName) {
        this.sptsName = sptsName;
    }

    /**
     * 株価時系列名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列名称
     */
    @Override
    public String getSptsName() {
        final String result = this.sptsName;
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
     * 株価計算値の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param spcvtId
     *                株価計算値の種類ID
     */
    @Override
    public void setSpcvtId(final long spcvtId) {
        this.spcvtId = spcvtId;
    }

    /**
     * 株価計算値の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値の種類ID
     */
    @Override
    public long getSpcvtId() {
        final long result = this.spcvtId;
        return result;
    }

    /**
     * 株価計算値の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param spcvtName
     *                  株価計算値の種類名称
     */
    @Override
    public void setSpcvtName(final String spcvtName) {
        this.spcvtName = spcvtName;
    }

    /**
     * 株価計算値の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値の種類名称
     */
    @Override
    public String getSpcvtName() {
        final String result = this.spcvtName;
        return result;
    }

    /**
     * 計算値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param calcValue
     *                  計算値
     */
    @Override
    public void setCalcValue(final BigDecimal calcValue) {
        this.calcValue = calcValue;
    }

    /**
     * 計算値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算値
     */
    @Override
    public BigDecimal getCalcValue() {
        final BigDecimal result = this.calcValue;
        return result;
    }

}
