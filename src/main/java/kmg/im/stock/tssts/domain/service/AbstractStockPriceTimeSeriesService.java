package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価時系列サービス抽象クラス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public abstract class AbstractStockPriceTimeSeriesService implements StockPriceTimeSeriesService {

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
    public AbstractStockPriceTimeSeriesService(final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic) {
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
    }

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
    @Override
    public void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) throws TsstsDomainException {

        if (stockPriceTimeSeriesMgtModel.isDataListEmpty()) {
            return;
        }

        this.stockPriceTimeSeriesLogic.register(stockPriceTimeSeriesMgtModel);
    }

}
