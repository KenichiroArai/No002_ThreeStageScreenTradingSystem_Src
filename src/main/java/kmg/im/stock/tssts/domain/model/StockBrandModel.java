package kmg.im.stock.tssts.domain.model;

import java.util.List;
import java.util.SortedMap;

import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株銘柄モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockBrandModel {

    /**
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    void setStockBrandId(long stockBrandId);

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    long getStockBrandId();

    /**
     * 株価銘柄コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     */
    void setStockBrandCode(final long stockBrandCode);

    /**
     * 株価銘柄コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄コード
     */
    long getStockBrandCode();

    /**
     * 株価時系列期間の種類のマップをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clearSptspMap();

    /**
     * 株価時系列期間の種類のマップが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    boolean isSptspMapEmpty();

    /**
     * 株価時系列期間の種類のマップが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    boolean isSptspMapNotEmpty();

    /**
     * 株価時系列期間の種類モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptModel
     *                    株価時系列期間の種類モデル
     */
    void addSptsptModel(final SptsptModel sptsptModel);

    /**
     * 株価時系列期間の種類モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptModelList
     *                        株価時系列期間の種類モデルのリスト
     */
    void addAllSptsptModel(final List<SptsptModel> sptsptModelList);

    /**
     * 株価時系列期間の種類モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類のマップ
     */
    SortedMap<PeriodTypeTypes, SptsptModel> getSptsptModelMap();

    /**
     * 期間の種類の種類に該当する株価時系列期間の種類モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列期間の種類モデル
     */
    SptsptModel getSptsptModel(final PeriodTypeTypes periodTypeTypes);

    /**
     * 株価時系列期間の種類モデルのリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類モデルのリスト
     */
    List<SptsptModel> toSptsptModelList();

}
