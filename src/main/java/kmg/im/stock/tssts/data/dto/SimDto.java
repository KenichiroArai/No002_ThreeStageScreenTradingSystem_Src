package kmg.im.stock.tssts.data.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * シミュレーションＤＴＯインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SimDto {
    /**
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    void setStockBrandId(long stockBrandId);

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    long getStockBrandId();

    /**
     * 株銘柄名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandName
     *                       株銘柄名称
     */
    void setStockBrandName(String stockBrandName);

    /**
     * 株銘柄名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄名称
     */
    String getStockBrandName();

    /**
     * 株価銘柄コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     */
    void setStockBrandCode(long stockBrandCode);

    /**
     * 株価銘柄コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄コード
     */
    long getStockBrandCode();

    /**
     * 株価時系列期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     */
    void setSptsptId(long sptsptId);

    /**
     * 株価時系列期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類ID
     */
    long getSptsptId();

    /**
     * 株価時系列期間の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptName
     *                   株価時系列期間の種類名称
     */
    void setSptsptName(String sptsptName);

    /**
     * 株価時系列期間の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類名称
     */
    String getSptsptName();

    /**
     * 期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeId
     *                     期間の種類ID
     */
    void setPeriodTypeId(long periodTypeId);

    /**
     * 期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類ID
     */
    long getPeriodTypeId();

    /**
     * 期間の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeName
     *                       期間の種類名称
     */
    void setPeriodTypeName(String periodTypeName);

    /**
     * 期間の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類名称
     */
    String getPeriodTypeName();

    /**
     * 株価時系列IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsId
     *               株価時系列ID
     */
    void setSptsId(long sptsId);

    /**
     * 株価時系列IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列ID
     */
    long getSptsId();

    /**
     * 株価時系列名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsName
     *                 株価時系列名称
     */
    void setSptsName(String sptsName);

    /**
     * 株価時系列名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列名称
     */
    String getSptsName();

    /**
     * 番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     */
    void setNo(long no);

    /**
     * 番号を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 番号
     */
    long getNo();

    /**
     * 期間開始日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodStartDate
     *                        期間開始日
     */
    void setPeriodStartDate(LocalDate periodStartDate);

    /**
     * 期間開始日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間開始日
     */
    LocalDate getPeriodStartDate();

    /**
     * 期間終了日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodEndDate
     *                      期間終了日
     */
    void setPeriodEndDate(LocalDate periodEndDate);

    /**
     * 期間終了日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間終了日
     */
    LocalDate getPeriodEndDate();

    /**
     * 始値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param op
     *           始値
     */
    void setOp(BigDecimal op);

    /**
     * 始値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 始値
     */
    BigDecimal getOp();

    /**
     * 高値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param hp
     *           高値
     */
    void setHp(BigDecimal hp);

    /**
     * 高値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 高値
     */
    BigDecimal getHp();

    /**
     * 安値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lp
     *           安値
     */
    void setLp(BigDecimal lp);

    /**
     * 安値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 安値
     */
    BigDecimal getLp();

    /**
     * 終値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param cp
     *           終値
     */
    void setCp(BigDecimal cp);

    /**
     * 終値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終値
     */
    BigDecimal getCp();

    /**
     * 出来高を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param volume
     *               出来高
     */
    void setVolume(long volume);

    /**
     * 出来高を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 出来高
     */
    long getVolume();

    /**
     * 株価計算値の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param spcvtId
     *                株価計算値の種類ID
     */
    void setSpcvtId(long spcvtId);

    /**
     * 株価計算値の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値の種類ID
     */
    long getSpcvtId();

    /**
     * 株価計算値の種類名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param spcvtName
     *                  株価計算値の種類名称
     */
    void setSpcvtName(String spcvtName);

    /**
     * 株価計算値の種類名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値の種類名称
     */
    String getSpcvtName();

    /**
     * 計算値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param calcValue
     *                  計算値
     */
    void setCalcValue(BigDecimal calcValue);

    /**
     * 計算値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算値
     */
    BigDecimal getCalcValue();

}
