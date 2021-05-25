package kmg.im.stock.tssts.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesDailyService;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列日足サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesDailyServiceImpl implements StockPriceTimeSeriesDailyService {

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
    public StockPriceTimeSeriesDailyServiceImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                                株価銘柄ID
     * @param stockPriceDataModelList
     *                                株価データのリスト
     */
    @Override
    public void register(final long stockBrandId, final List<StockPriceDataModel> stockPriceDataModelList) {

        final List<StockPriceTimeSeriesDto> stockPriceTimeSeriesOfDialyList = new ArrayList<>();
        for (final StockPriceDataModel stockPriceDataModel : stockPriceDataModelList) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeriesOfDialy = new StockPriceTimeSeriesDto();
            BeanUtils.copyProperties(stockPriceDataModel, stockPriceTimeSeriesOfDialy);

            // 株価銘柄IDを設定する
            stockPriceTimeSeriesOfDialy.setStockBrandId(stockBrandId);
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
