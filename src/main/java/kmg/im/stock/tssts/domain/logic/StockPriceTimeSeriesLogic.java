package kmg.im.stock.tssts.domain.logic;

import java.util.List;

import kmg.im.stock.tssts.domain.model.SptsMainDataModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
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
     * 期間の種類で株価時系列メインデータモデルのリストを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                              期間の種類の種類
     * @param sptsMainDataModelList
     *                              株価時系列メインデータモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void register(PeriodTypeTypes periodTypeTypes, final List<SptsMainDataModel> sptsMainDataModelList)
        throws TsstsDomainException;

    /**
     * 株価時系列管理モデルを検索する<br>
     * <p>
     * 株価時系列期間の種類IDに該当する株価時系列管理モデルを検索し、該当する株価時系列管理モデルを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                        株価時系列期間の種類ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    StockBrandModel findBySptsptId(long sptsptId, PeriodTypeTypes periodTypeTypes) throws TsstsDomainException;
}
