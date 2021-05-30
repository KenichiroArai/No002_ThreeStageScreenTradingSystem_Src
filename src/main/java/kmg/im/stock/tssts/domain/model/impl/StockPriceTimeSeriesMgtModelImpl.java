package kmg.im.stock.tssts.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceTimeSeriesMgtModelImpl implements StockPriceTimeSeriesMgtModel {

    /** 株銘柄ID */
    private Long stockBrandId;

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 期間の種類の種類 */
    private TypeOfPeriodTypes typeOfPeriodTypes;

    /** 株価時系列リスト */
    private final List<StockPriceTimeSeriesModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockPriceTimeSeriesMgtModelImpl() {
        this.dataList = new ArrayList<>();
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
    public void setStockBrandId(final Long stockBrandId) {
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
    public Long getStockBrandId() {
        final Long result = this.stockBrandId;
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
     * @param typeOfPeriodTypes
     *                          期間の種類の種類
     */
    @Override
    public void setTypeOfPeriodTypes(final TypeOfPeriodTypes typeOfPeriodTypes) {
        this.typeOfPeriodTypes = typeOfPeriodTypes;
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
    public TypeOfPeriodTypes getTypeOfPeriodTypes() {
        final TypeOfPeriodTypes result = this.typeOfPeriodTypes;
        return result;
    }

    /**
     * 株価時系列リストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearDataList() {
        this.dataList.clear();
    }

    /**
     * 株価時系列リストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isDataListEmpty() {
        boolean result = true;

        if (ListUtils.isEmpty(this.dataList)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価時系列リストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isDataListNotEmpty() {
        final boolean result = !this.isDataListEmpty();
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
        this.dataList.add(data);
    }

    /**
     * 株価時系列リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価時系列リスト
     */
    @Override
    public void addAllData(final List<StockPriceTimeSeriesModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価時系列リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列リスト
     */
    @Override
    public List<StockPriceTimeSeriesModel> getDataList() {
        final List<StockPriceTimeSeriesModel> result = this.dataList;
        return result;
    }

}
