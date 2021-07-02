package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.service.MacdService;
import kmg.im.stock.core.domain.service.PowerIndexService;
import kmg.im.stock.core.infrastructure.types.StockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceCalcValueMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockPriceCalcValueService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価計算値サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsStockPriceCalcValueServiceImpl implements TsstsStockPriceCalcValueService {

    /** アプリケーションコンテキスト */
    private final ApplicationContext context;

    /** 株価時系列日足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtDailyModel;

    /** 株価時系列週足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtWeeklyModel;

    /** 株価時系列月足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtMonthlyModel;

    /** 株価計算値サービス */
    private final StockPriceCalcValueService stockPriceCalcValueService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                                   アプリケーションコンテキスト
     * @param stockPriceCalcValueService
     *                                   株価計算値サービス
     */
    public TsstsStockPriceCalcValueServiceImpl(final ApplicationContext context,
        final StockPriceCalcValueService stockPriceCalcValueService) {
        this.context = context;
        this.stockPriceCalcValueService = stockPriceCalcValueService;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtDailyModel
     *                                            株価時系列日足管理モデル
     * @param stockPriceTimeSeriesMgtWeeklyModel
     *                                            株価時系列週足管理モデル
     * @param stockPriceTimeSeriesMgtMonthlyModel
     *                                            株価時系列月足管理モデル
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtDailyModel,
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtWeeklyModel,
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtMonthlyModel) {
        this.stockPriceTimeSeriesMgtDailyModel = stockPriceTimeSeriesMgtDailyModel;
        this.stockPriceTimeSeriesMgtWeeklyModel = stockPriceTimeSeriesMgtWeeklyModel;
        this.stockPriceTimeSeriesMgtMonthlyModel = stockPriceTimeSeriesMgtMonthlyModel;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register() throws TsstsDomainException {

        /* 株価計算値月足 */
        // 削除する
        this.stockPriceCalcValueService.delete(this.stockPriceTimeSeriesMgtMonthlyModel.getSptsptId());
        // 計算する
        final MacdService monthlyMacdService = this.context.getBean(MacdService.class);
        monthlyMacdService.initialize(this.stockPriceTimeSeriesMgtMonthlyModel.toSupplierDataList());
        monthlyMacdService.clacHistogram();
        final StockPriceCalcValueMgtModel stockPriceCalcValueMgtMonthlyModel = new StockPriceCalcValueMgtModelImpl(
            this.stockPriceTimeSeriesMgtMonthlyModel, StockPriceCalcValueTypeTypes.MCADH,
            monthlyMacdService.getHistogramList());
        // 登録する
        this.stockPriceCalcValueService.register(stockPriceCalcValueMgtMonthlyModel);

        /* 株価計算値週足 */
        // 削除する
        this.stockPriceCalcValueService.delete(this.stockPriceTimeSeriesMgtWeeklyModel.getSptsptId());
        // 計算する
        final PowerIndexService powerIndexService = this.context.getBean(PowerIndexService.class);
        powerIndexService.initialize(this.stockPriceTimeSeriesMgtWeeklyModel.toPowerIndexCalcModelList());
        powerIndexService.defaultLtSmoothing();
        final StockPriceCalcValueMgtModel stockPriceCalcValueMgtWeeklyModel = new StockPriceCalcValueMgtModelImpl(
            this.stockPriceTimeSeriesMgtWeeklyModel, StockPriceCalcValueTypeTypes.PI2EMA,
            powerIndexService.getSmoothingList());
        // 登録する
        this.stockPriceCalcValueService.register(stockPriceCalcValueMgtWeeklyModel);

    }

}
