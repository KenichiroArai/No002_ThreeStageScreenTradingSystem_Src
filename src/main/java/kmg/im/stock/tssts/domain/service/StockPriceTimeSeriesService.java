package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesService {

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long delete() throws TsstsDomainException;

    /**
     * 株価時系列管理モデルにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    StockPriceTimeSeriesMgtModel toStockPriceTimeSeriesMgtModel(StockPriceDataMgtModel stockPriceDataMgtModel)
        throws TsstsDomainException;

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void register(StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) throws TsstsDomainException;
}
