package kmg.im.stock.tssts.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesDailyMgtModel;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;
import kmg.spring.infrastructure.constants.BeanDefaultScopeConstants;

/**
 * 株価時系列日足管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
@Scope(BeanDefaultScopeConstants.PROTOTYPE)
public class StockPriceTimeSeriesDailyMgtModelImpl implements StockPriceTimeSeriesDailyMgtModel {

    /** 株価銘柄ID */
    private long stockBrandId;

    /** 株価データのリスト */
    private List<StockPriceDataModel> stockPriceDataModelList;

    /** 株価時系列ＤＡＯ */
    private final StockPriceTimeSeriesDao stockPriceTimeSeriesDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesDao
     *                                株価時系列ＤＡＯ
     */
    public StockPriceTimeSeriesDailyMgtModelImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                                株価銘柄ID
     * @param stockPriceDataModelList
     *                                株価データのリスト
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final long stockBrandId, final List<StockPriceDataModel> stockPriceDataModelList) {
        this.stockBrandId = stockBrandId;
        this.stockPriceDataModelList = stockPriceDataModelList;
    }

    /**
     * 株価銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄ID
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 株価データのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データのリスト
     */
    @Override
    public List<StockPriceDataModel> getStockPriceDataModelList() {
        final List<StockPriceDataModel> result = this.stockPriceDataModelList;
        return result;
    }

    /**
     * 日足の株価時系列を登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void register() {

        final List<StockPriceTimeSeriesDto> stockPriceTimeSeriesOfDialyList = new ArrayList<>();
        for (final StockPriceDataModel stockPriceDataModel : this.stockPriceDataModelList) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeriesOfDialy = new StockPriceTimeSeriesDto();
            BeanUtils.copyProperties(stockPriceDataModel, stockPriceTimeSeriesOfDialy);

            // 株価銘柄IDを設定する
            stockPriceTimeSeriesOfDialy.setStockBrandId(this.stockBrandId);
            // 期間の種類IDを設定する
            stockPriceTimeSeriesOfDialy.setTypeOfPeriodId(TypeOfPeriodTypes.DAILY.getValue());
            // 期間開始日を設定する
            stockPriceTimeSeriesOfDialy.setPeriodStartDate(stockPriceDataModel.getDate());
            // 期間終了日を設定する
            stockPriceTimeSeriesOfDialy.setPeriodEndDate(stockPriceDataModel.getDate());

            stockPriceTimeSeriesOfDialyList.add(stockPriceTimeSeriesOfDialy);
        }
        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesOfDialyList) {
            this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
        }
    }

}
