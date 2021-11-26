package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesService {

    /**
     * 株銘柄ＩＤと期間の種類の種類を基にシンプルモデルを返す検索を行う<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *                        株銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return シンプル株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    SimpleSptsMgtModel findSimpleBySbIdAndPti(final long sbId, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException;

}
