package kmg.im.stock.tssts.domain.logic;

import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価計算値ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceCalcValueLogic {

    /**
     * 株価銘柄コードと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbCd
     *                        株価銘柄コード
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long deleteBySbCdAndPeriodTypeTypes(final long sbCd, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException;

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
    void register(final StockPriceCalcValueMgtModel stockPriceCalcValueMgtModel) throws TsstsDomainException;
}
