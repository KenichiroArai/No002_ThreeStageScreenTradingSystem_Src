package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価計算値サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceCalcValueService {

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long delete(long sptsptId) throws TsstsDomainException;

    /**
     * 計算する<br>
     * <p>
     * 計算し、計算結果として株価時系列管理モデルにして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     * @return 株価時系列管理モデル
     */
    StockPriceCalcValueMgtModel calc(StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel);

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceCalcValueMgtModel
     *                                    株価計算値管理モデル
     */
    void register(StockPriceCalcValueMgtModel stockPriceCalcValueMgtModel);
}
