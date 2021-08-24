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
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceTimeSeriesMgtModelImpl implements StockPriceTimeSeriesMgtModel {

    /** 株銘柄ID */
    private long stockBrandId;

    /** 株価時系列期間の種類ID */
    private long sptsptId;

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 期間の種類の種類 */
    private PeriodTypeTypes PeriodTypeTypes;

    /**
     * 株価時系列マップ<br>
     * <p>
     * キー：番号、値：株価時系列モデル
     * </p>
     */
    private final SortedMap<Long, StockPriceTimeSeriesModel> dataMap;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockPriceTimeSeriesMgtModelImpl() {
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
     * 株価時系列期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     */
    @Override
    public void setSptsptId(final long sptsptId) {
        this.sptsptId = sptsptId;
    }

    /**
     * 株価時系列期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類ID
     */
    @Override
    public long getSptsptId() {
        final long result = this.sptsptId;
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
     * 期間の種類の種類を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     */
    @Override
    public void setPeriodTypeTypes(final PeriodTypeTypes periodTypeTypes) {
        this.PeriodTypeTypes = periodTypeTypes;
    }

    /**
     * 期間の種類の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類の種類
     */
    @Override
    public PeriodTypeTypes getPeriodTypeTypes() {
        final PeriodTypeTypes result = this.PeriodTypeTypes;
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
     * 株価データを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価データ
     */
    @Override
    public void addData(final StockPriceTimeSeriesModel data) {
        this.dataMap.put(data.getNo(), data);
    }

    /**
     * 株価時系列リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addDataList
     *                    追加株価時系列リスト
     */
    @Override
    public void addAllData(final List<StockPriceTimeSeriesModel> addDataList) {
        if (ListUtils.isEmpty(addDataList)) {
            return;
        }

        for (final StockPriceTimeSeriesModel addData : addDataList) {
            this.dataMap.put(addData.getNo(), addData);
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
    public SortedMap<Long, StockPriceTimeSeriesModel> getDataMap() {
        final SortedMap<Long, StockPriceTimeSeriesModel> result = this.dataMap;
        return result;
    }

    /**
     * 株価時系列リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    @Override
    public List<StockPriceTimeSeriesModel> toDataList() {
        final List<StockPriceTimeSeriesModel> result = new ArrayList<>(this.dataMap.values());
        return result;
    }

    /**
     * サプライヤデータリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return サプライヤデータリスト
     */
    @Override
    public List<Supplier<BigDecimal>> toSupplierDataList() {

        final List<Supplier<BigDecimal>> result = this.dataMap.values().stream().collect(Collectors.toList());
        return result;
    }

    /**
     * 勢力指数計算モデルリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 勢力指数計算モデルリスト
     */
    @Override
    public List<PowerIndexCalcModel> toPowerIndexCalcModelList() {
        final List<PowerIndexCalcModel> result = this.dataMap.values().stream().collect(Collectors.toList());
        return result;
    }

}
