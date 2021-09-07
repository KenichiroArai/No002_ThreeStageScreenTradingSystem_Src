package kmg.im.stock.tssts.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.function.Supplier;

import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
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
     * 株価時系列マップをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clearDataMap();

    /**
     * 株価時系列マップが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    boolean isDataMapEmpty();

    /**
     * 株価時系列マップが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    boolean isDataMapNotEmpty();

    /**
     * 株価データ番号マップを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param dataNoMap
     *                        株価データ番号マップ
     */
    void addData(PeriodTypeTypes periodTypeTypes, SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap);

    /**
     * 株価データを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param data
     *                        株価データ
     */
    void addData(PeriodTypeTypes periodTypeTypes, final StockPriceTimeSeriesModel data);

    /**
     * 株価時系列リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param addDataList
     *                        追加株価時系列リスト
     */
    void addAllData(PeriodTypeTypes periodTypeTypes, final List<StockPriceTimeSeriesModel> addDataList);

    /**
     * 株価時系列マップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    SortedMap<PeriodTypeTypes, SortedMap<Long, StockPriceTimeSeriesModel>> getDataMap();

    /**
     * 株価時系列番号マップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列マップ
     */
    SortedMap<Long, StockPriceTimeSeriesModel> getDataNoMap(PeriodTypeTypes periodTypeTypes);

    /**
     * 株価時系列リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列マップ
     */
    List<StockPriceTimeSeriesModel> toDataList(PeriodTypeTypes periodTypeTypes);

    /**
     * 株価時系列全リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    List<StockPriceTimeSeriesModel> toAllDataList();

    /**
     * サプライヤデータリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return サプライヤデータリスト
     */
    List<Supplier<BigDecimal>> toSupplierDataList(PeriodTypeTypes periodTypeTypes);

    /**
     * 勢力指数計算モデルリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 勢力指数計算モデルリスト
     */
    List<PowerIndexCalcModel> toPowerIndexCalcModelList(PeriodTypeTypes periodTypeTypes);

}
