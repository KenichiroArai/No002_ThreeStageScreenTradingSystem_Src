package kmg.im.stock.tssts.data.dto.impl;

import kmg.im.stock.tssts.data.dto.SimpleSptsSearchConditionDto;

/**
 * シンプル株価時系列管理検索条件モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SimpleSptsSearchConditionDtoImpl implements SimpleSptsSearchConditionDto {

    /** 株銘柄ID */
    private Long stockBrandId;

    /** 期間の種類ID */
    private Long periodTypeId;

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
    public void setStockBrandId(final Long stockBrandId) {
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
    public Long getStockBrandId() {
        final Long result = this.stockBrandId;
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
    public void setPeriodTypeId(final Long periodTypeId) {
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
    public Long getPeriodTypeId() {
        final Long result = this.periodTypeId;
        return result;
    }

}
