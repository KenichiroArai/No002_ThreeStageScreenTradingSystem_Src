package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;

/**
 * 株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesService {

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     */
    void register(final StockPriceDataMgtModel stockPriceDataMgtModel);
}
