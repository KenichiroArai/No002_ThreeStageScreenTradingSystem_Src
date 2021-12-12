package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.core.domain.model.ImStkSpcvInitMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 投資株式株価計算値サービスインタフェース<br>
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
     * @param imStkSpcvInitMgtModel
     *                              株価時系列メインデータ管理モデル
     */
    void initialize(final ImStkSpcvInitMgtModel imStkSpcvInitMgtModel);

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
