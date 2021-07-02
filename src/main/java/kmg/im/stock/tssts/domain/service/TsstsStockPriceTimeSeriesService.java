package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsStockPriceTimeSeriesService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     */
    void initialize(final StockPriceDataMgtModel stockPriceDataMgtModel);

    /**
     * 株価データ管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データ管理モデル
     */
    StockPriceDataMgtModel getStockPriceDataMgtModel();

    /**
     * 株価時系列日足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列日足管理モデル
     */
    StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtDailyModel();

    /**
     * 株価時系列週足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列週足管理モデル
     */
    StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtWeeklyModel();

    /**
     * 株価時系列月足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列月足管理モデル
     */
    StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtMonthlyModel();

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void register() throws TsstsDomainException;
}
