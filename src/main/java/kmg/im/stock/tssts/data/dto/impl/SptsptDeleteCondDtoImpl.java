package kmg.im.stock.tssts.data.dto.impl;

import kmg.im.stock.tssts.data.dto.SptsptDeleteCondDto;

/**
 * 株価時系列期間の種類削除条件ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsptDeleteCondDtoImpl implements SptsptDeleteCondDto {

    /** 株価銘柄ＩＤ */
    private long stockBrandId;

    /** 期間の種類ＩＤ */
    private long periodTypeId;

    /**
     * 株価銘柄ＩＤを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株価銘柄ＩＤ
     */
    @Override
    public void setStockBrandId(final long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株価銘柄ＩＤを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄ＩＤ
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
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
