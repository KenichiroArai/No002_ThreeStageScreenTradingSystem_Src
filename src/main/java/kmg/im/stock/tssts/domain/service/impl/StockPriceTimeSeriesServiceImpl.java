package kmg.im.stock.tssts.domain.service.impl;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価時系列サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceTimeSeriesServiceImpl implements StockPriceTimeSeriesService {

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesLogic
     *                                  株価時系列ロジック
     */
    public StockPriceTimeSeriesServiceImpl(final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic) {
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
    }

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
    @Override
    public long delete() throws TsstsDomainException {
        // TODO KenichiroArai 2021/09/05 未実装
        return 0;
    }

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
    @Override
    public StockBrandModel toStockPriceTimeSeriesMgtModel(final StockPriceDataMgtModel stockPriceDataMgtModel)
        throws TsstsDomainException {
        // TODO KenichiroArai 2021/09/05 未実装
        return null;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandModel
     *                        株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register(final StockBrandModel stockBrandModel) throws TsstsDomainException {

        if (stockBrandModel.isSptspMapEmpty()) {
            return;
        }

        this.stockPriceTimeSeriesLogic.register(stockBrandModel);
    }

    /**
     * 株価時系列管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockBrandModel getStockPriceTimeSeriesMgtModel() throws TsstsDomainException {
        // TODO KenichiroArai 2021/09/05 未実装
        return null;
    }

    /**
     * 株価時系列管理モデルを検索する<br>
     * <p>
     * 株価時系列期間の種類IDに該当する株価時系列管理モデルを検索し、該当する株価時系列管理モデルを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockBrandModel findBySptsptId(final long sptsptId) throws TsstsDomainException {
        // TODO KenichiroArai 2021/09/05 未実装
        return null;
    }

}
