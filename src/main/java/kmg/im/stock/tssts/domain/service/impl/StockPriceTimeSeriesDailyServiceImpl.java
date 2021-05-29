package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.logic.impl.StockPriceTimeSeriesModelImpl;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockBrandService;
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

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /** 株銘柄サービス */
    private final StockBrandService stockBrandService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesLogic
     *                                  株価時系列ロジック
     * @param stockBrandService
     *                                  株銘柄サービス
     */
    public StockPriceTimeSeriesDailyServiceImpl(final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic,
        final StockBrandService stockBrandService) {
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockBrandService = stockBrandService;
    }

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void delete() {
        this.stockPriceTimeSeriesLogic.delete(TypeOfPeriodTypes.DAILY);
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     */
    @Override
    public void register(final StockPriceDataMgtModel stockPriceDataMgtModel) {

        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel = new StockPriceTimeSeriesMgtModelImpl();
        stockPriceDataMgtModel.setStockBrandCode(stockPriceDataMgtModel.getStockBrandCode());

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandService.getStockBrandId(stockPriceDataMgtModel.getStockBrandCode());
        // 株価銘柄IDを設定する
        stockPriceTimeSeriesMgtModel.setStockBrandId(stockBrandId);

        for (final StockPriceDataModel stockPriceDataModel : stockPriceDataMgtModel.getDataList()) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesModel stockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
            BeanUtils.copyProperties(stockPriceDataModel, stockPriceTimeSeriesModel);

            // 期間の種類IDを設定する
            stockPriceTimeSeriesModel.setTypeOfPeriodId(TypeOfPeriodTypes.DAILY.getValue());
            // 期間開始日を設定する
            stockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataModel.getDate());
            // 期間終了日を設定する
            stockPriceTimeSeriesModel.setPeriodEndDate(stockPriceDataModel.getDate());

            stockPriceTimeSeriesMgtModel.addData(stockPriceTimeSeriesModel);
        }

        this.stockPriceTimeSeriesLogic.register(stockPriceTimeSeriesMgtModel);
    }
}
