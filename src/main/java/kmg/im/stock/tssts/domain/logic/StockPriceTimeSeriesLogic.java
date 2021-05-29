package kmg.im.stock.tssts.domain.logic;

import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;

/**
 * 株価時系列ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesLogic {

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     */
    void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel);
}
