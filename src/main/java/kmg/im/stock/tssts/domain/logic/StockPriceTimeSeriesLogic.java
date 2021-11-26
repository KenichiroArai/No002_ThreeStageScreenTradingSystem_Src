package kmg.im.stock.tssts.domain.logic;

import java.util.List;

import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.SptsRegDataModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesLogic {

    /**
     * 株価銘柄ＩＤと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *                        株価銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long deleteBySbIdAndPeriodTypeTypes(final long sbId, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException;

    /**
     * 期間の種類で株価時系列登録データモデルのリストを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                              期間の種類の種類
     * @param sptsMainDataModelList
     *                              株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void register(PeriodTypeTypes periodTypeTypes, final List<SptsRegDataModel> sptsMainDataModelList)
        throws TsstsDomainException;

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
