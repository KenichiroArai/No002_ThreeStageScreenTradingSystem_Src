package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesMgtModelImpl;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesModelImpl;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesDailyService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列日足サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesDailyServiceImpl extends StockPriceTimeSeriesServiceImpl
    implements StockPriceTimeSeriesDailyService {

    /** 期間の種類の種類 */
    private static final PeriodTypeTypes PERIOD_TYPE_TYPES = PeriodTypeTypes.DAILY;

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
        super(stockPriceTimeSeriesLogic);
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockBrandService = stockBrandService;
    }

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long delete() throws TsstsDomainException {
        final long result = this.stockPriceTimeSeriesLogic
            .delete(StockPriceTimeSeriesDailyServiceImpl.PERIOD_TYPE_TYPES);
        return result;
    }

    /**
     * 株価時系列管理モデルにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceTimeSeriesMgtModel toStockPriceTimeSeriesMgtModel(
        final StockPriceDataMgtModel stockPriceDataMgtModel) throws TsstsDomainException {

        final StockPriceTimeSeriesMgtModel result = new StockPriceTimeSeriesMgtModelImpl();
        stockPriceDataMgtModel.setStockBrandCode(stockPriceDataMgtModel.getStockBrandCode());

        // 株価銘柄IDを取得する
        final long stockBrandId = this.stockBrandService.getStockBrandId(stockPriceDataMgtModel.getStockBrandCode());
        // 株価銘柄IDを設定する
        result.setStockBrandId(stockBrandId);

        for (final StockPriceDataModel stockPriceDataModel : stockPriceDataMgtModel.getDataList()) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesModel stockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
            BeanUtils.copyProperties(stockPriceDataModel, stockPriceTimeSeriesModel);

            // 期間開始日を設定する
            stockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataModel.getDate());
            // 期間終了日を設定する
            stockPriceTimeSeriesModel.setPeriodEndDate(stockPriceDataModel.getDate());

            result.addData(StockPriceTimeSeriesDailyServiceImpl.PERIOD_TYPE_TYPES, stockPriceTimeSeriesModel);
        }

        return result;
    }

    /**
     * 株価時系列管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtModel() throws TsstsDomainException {
        final StockPriceTimeSeriesMgtModel result = null;
        // TODO KenichiroArai 2021/09/05 未使用
        return result;
    }

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
     *                 株価時系列期間の種類ID
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceTimeSeriesMgtModel findBySptsptId(final long sptsptId) throws TsstsDomainException {

        final StockPriceTimeSeriesMgtModel result = this.stockPriceTimeSeriesLogic.findBySptsptId(sptsptId,
            StockPriceTimeSeriesDailyServiceImpl.PERIOD_TYPE_TYPES);
        return result;

    }
}
