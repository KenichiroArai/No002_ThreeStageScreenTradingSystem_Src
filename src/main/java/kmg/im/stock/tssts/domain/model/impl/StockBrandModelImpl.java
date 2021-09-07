package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.core.infrastructure.utils.MapUtils;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株銘柄モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockBrandModelImpl implements StockBrandModel {

    /** 株銘柄ID */
    private long stockBrandId;

    /** 株価銘柄コード */
    private long stockBrandCode;

    /*
     * TODO KenichiroArai 2021/09/06
     * 株価時系列期間の種類のモデルを配下にする。株価時系列期間の種類の配下に株価時系列のデータを配下にする。
     */

    /**
     * 株価時系列マップ<br>
     * <p>
     * キー：期間の種類の種類<br>
     * 値：キー：番号、値：株価時系列モデル<br>
     * </p>
     */
    private final SortedMap<PeriodTypeTypes, SortedMap<Long, StockPriceTimeSeriesModel>> dataMap;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockBrandModelImpl() {
        this.dataMap = new TreeMap<>();
    }

    /**
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    @Override
    public void setStockBrandId(final long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 株価銘柄コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     */
    @Override
    public void setStockBrandCode(final long stockBrandCode) {
        this.stockBrandCode = stockBrandCode;
    }

    /**
     * 株価銘柄コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄コード
     */
    @Override
    public long getStockBrandCode() {
        final long result = this.stockBrandCode;
        return result;
    }

    /**
     * 株価時系列マップをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearDataMap() {
        this.dataMap.clear();
    }

    /**
     * 株価時系列マップが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isDataMapEmpty() {
        boolean result = true;

        if (MapUtils.isEmpty(this.dataMap)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価時系列マップが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isDataMapNotEmpty() {
        final boolean result = !this.isDataMapEmpty();
        return result;
    }

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
    @Override
    public void addData(final PeriodTypeTypes periodTypeTypes,
        final SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap) {
        this.dataMap.put(periodTypeTypes, dataNoMap);
    }

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
    @Override
    public void addData(final PeriodTypeTypes periodTypeTypes, final StockPriceTimeSeriesModel data) {
        final SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap = this.dataMap.get(periodTypeTypes);
        dataNoMap.put(data.getNo(), data);
        this.addData(periodTypeTypes, dataNoMap);
    }

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
    @Override
    public void addAllData(final PeriodTypeTypes periodTypeTypes, final List<StockPriceTimeSeriesModel> addDataList) {
        if (ListUtils.isEmpty(addDataList)) {
            return;
        }

        for (final StockPriceTimeSeriesModel addData : addDataList) {
            this.addData(periodTypeTypes, addData);
        }
    }

    /**
     * 株価時系列マップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    @Override
    public SortedMap<PeriodTypeTypes, SortedMap<Long, StockPriceTimeSeriesModel>> getDataMap() {
        final SortedMap<PeriodTypeTypes, SortedMap<Long, StockPriceTimeSeriesModel>> result = this.dataMap;
        return result;
    }

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
    @Override
    public SortedMap<Long, StockPriceTimeSeriesModel> getDataNoMap(final PeriodTypeTypes periodTypeTypes) {
        final SortedMap<Long, StockPriceTimeSeriesModel> result = this.dataMap.get(periodTypeTypes);
        return result;
    }

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
    @Override
    public List<StockPriceTimeSeriesModel> toDataList(final PeriodTypeTypes periodTypeTypes) {
        List<StockPriceTimeSeriesModel> result = null;
        final SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap = this.dataMap.get(periodTypeTypes);
        result = new ArrayList<>(dataNoMap.values());
        return result;
    }

    /**
     * 株価時系列全リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    @Override
    public List<StockPriceTimeSeriesModel> toAllDataList() {
        final List<StockPriceTimeSeriesModel> result = new ArrayList<>();
        this.dataMap.forEach((key, value) -> {
            value.forEach((key2, value2) -> {
                result.add(value2);
            });
        });
        return result;
    }

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
    @Override
    public List<Supplier<BigDecimal>> toSupplierDataList(final PeriodTypeTypes periodTypeTypes) {
        final SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap = this.dataMap.get(periodTypeTypes);
        final List<Supplier<BigDecimal>> result = dataNoMap.values().stream().collect(Collectors.toList());
        return result;
    }

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
    @Override
    public List<PowerIndexCalcModel> toPowerIndexCalcModelList(final PeriodTypeTypes periodTypeTypes) {
        final SortedMap<Long, StockPriceTimeSeriesModel> dataNoMap = this.dataMap.get(periodTypeTypes);
        final List<PowerIndexCalcModel> result = dataNoMap.values().stream().collect(Collectors.toList());
        return result;
    }

}
