package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.TsstsSpcvInitMgtModel;
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
     * @param tsstsSpcvInitMgtModel
     *                              株価時系列メインデータ管理モデル
     */
    void initialize(final TsstsSpcvInitMgtModel tsstsSpcvInitMgtModel);

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
