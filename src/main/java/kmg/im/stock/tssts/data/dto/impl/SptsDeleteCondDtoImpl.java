package kmg.im.stock.tssts.data.dto.impl;

import kmg.im.stock.tssts.data.dto.SptsDeleteCondDto;

/**
 * 株価計算値削除条件ＤＴＯインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsDeleteCondDtoImpl implements SptsDeleteCondDto {

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 期間の種類ＩＤ */
    private long periodTypeId;

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
     * 期間の種類ＩＤを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeId
     *                     期間の種類ＩＤ
     */
    @Override
    public void setPeriodTypeId(final long periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    /**
     * 期間の種類ＩＤを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類ＩＤ
     */
    @Override
    public long getPeriodTypeId() {
        final long result = this.periodTypeId;
        return result;
    }

}
