package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceCalcValueLogic;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
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
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceCalcValueMgtModel
     *                                    株価計算値管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register(final StockPriceCalcValueMgtModel stockPriceCalcValueMgtModel) throws TsstsDomainException {
        this.stockPriceCalcValueLogic.register(stockPriceCalcValueMgtModel);
    }

}
