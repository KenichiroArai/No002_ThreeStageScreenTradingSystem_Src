package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価計算値サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsStockPriceCalcValueService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtDailyModel
     *                                            株価時系列日足管理モデル
     * @param stockPriceTimeSeriesMgtWeeklyModel
     *                                            株価時系列週足管理モデル
     * @param stockPriceTimeSeriesMgtMonthlyModel
     *                                            株価時系列月足管理モデル
     */
    void initialize(final StockBrandModel stockPriceTimeSeriesMgtDailyModel,
        final StockBrandModel stockPriceTimeSeriesMgtWeeklyModel,
        final StockBrandModel stockPriceTimeSeriesMgtMonthlyModel);

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
