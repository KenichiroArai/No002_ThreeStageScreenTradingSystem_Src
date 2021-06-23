package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceCalcValueLogic;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.service.StockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価計算値サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceCalcValueServiceImpl implements StockPriceCalcValueService {

    /** 登録ロジック */
    private final StockPriceCalcValueLogic stockPriceCalcValueLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceCalcValueLogic
     *                                 登録ロジック
     */
    public StockPriceCalcValueServiceImpl(final StockPriceCalcValueLogic stockPriceCalcValueLogic) {
        this.stockPriceCalcValueLogic = stockPriceCalcValueLogic;
    }

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
    @Override
    public long delete(final long sptsptId) throws TsstsDomainException {
        final long result = this.stockPriceCalcValueLogic.delete(sptsptId);
        return result;
    }

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
    @Override
    public StockPriceCalcValueMgtModel calc(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) {
        // TODO KenichiroArai 2021/06/04 未実装
        return null;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceCalcValueMgtModel
     *                                    株価計算値管理モデル
     */
    @Override
    public void register(final StockPriceCalcValueMgtModel stockPriceCalcValueMgtModel) {
        // TODO KenichiroArai 2021/06/04 未実装
    }

}
