package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;

/**
 * 株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesService {

    /**
     * 株価銘柄コードと期間の種類IDを基にシンプルモデルを返す検索を行う<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株銘柄コード
     * @param periodTypeId
     *                       期間の種類ID
     * @return シンプル株価時系列管理モデル
     */
    SimpleSptsMgtModel findSimpleBySbcAndPti(long stockBrandCode, long periodTypeId);

}
