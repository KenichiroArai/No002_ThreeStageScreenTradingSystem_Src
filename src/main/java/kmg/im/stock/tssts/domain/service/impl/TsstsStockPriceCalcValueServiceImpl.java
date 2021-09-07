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

        /* 株価計算値日足 */
        this.register(this.stockPriceTimeSeriesMgtDailyModel);

        /* 株価計算値週足 */
        this.register(this.stockPriceTimeSeriesMgtWeeklyModel);

        /* 株価計算値月足 */
        this.register(this.stockPriceTimeSeriesMgtMonthlyModel);

    }

    /**
     * 株価計算値の登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    private void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) throws TsstsDomainException {

        /* 削除する */
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        this.stockPriceCalcValueService.delete(stockPriceTimeSeriesMgtModel.getSptsptId());

        /* 計算する */

        // ＭＣＡＤ
        final MacdService macdService = this.context.getBean(MacdService.class);
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        macdService.initialize(stockPriceTimeSeriesMgtModel.toSupplierDataList());
        // ＭＣＡＤライン
        macdService.clacLine();
        final StockPriceCalcValueMgtModel spcvMgtMacdlModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.MCADL, macdService.getLineList());
        // ＭＣＡＤシグナル
        macdService.clacSignal();
        final StockPriceCalcValueMgtModel spcvMgtMacdsModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.MCADS, macdService.getSignalList());
        // ＭＣＡＤヒストグラム
        macdService.clacHistogram();
        final StockPriceCalcValueMgtModel spcvMgtMacdhModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.MCADH, macdService.getHistogramList());

        // 勢力指数
        final PowerIndexService piService = this.context.getBean(PowerIndexService.class);
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        piService.initialize(stockPriceTimeSeriesMgtModel.toPowerIndexCalcModelList());
        piService.calc();
        final StockPriceCalcValueMgtModel spcvMgtPiModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.PI, piService.getCalcResultList());
        // 勢力指数２ＥＭＡ
        piService.defaultStSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi2EmaModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.PI2EMA, piService.getSmoothingList());
        // 勢力指数１３ＥＭＡ
        piService.defaultLtSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi13EmaModel = new StockPriceCalcValueMgtModelImpl(
            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.PI13EMA, piService.getSmoothingList());

        // 過去３期間の最安値
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        final LowestPriceInPastService lowestPriceInPastService = this.context.getBean(LowestPriceInPastService.class);
//        final List<Supplier<BigDecimal>> lowestPriceInPastCalcResultList = stockPriceTimeSeriesMgtModel.getDataMap()
//            .values().stream().map(mapper -> {
//                final Supplier<BigDecimal> supplier = () -> mapper.getLp();
//                return supplier;
//            }).collect(Collectors.toList());
//        lowestPriceInPastService.initialize(lowestPriceInPastCalcResultList, 3);
//        lowestPriceInPastService.calc();
//        final StockPriceCalcValueMgtModel spcvMgtLpl3pPModel = new StockPriceCalcValueMgtModelImpl(
//            stockPriceTimeSeriesMgtModel, StockPriceCalcValueTypeTypes.LOWEST_PRICE_IN_LAST3_PERIODS,
//            lowestPriceInPastService.getClacResultList());

        /* 登録する */
        // ＭＣＡＤライン
        this.stockPriceCalcValueService.register(spcvMgtMacdlModel);
        // ＭＣＡＤシグナル
        this.stockPriceCalcValueService.register(spcvMgtMacdsModel);
        // ＭＣＡＤヒストグラム
        this.stockPriceCalcValueService.register(spcvMgtMacdhModel);
        // 勢力指数
        this.stockPriceCalcValueService.register(spcvMgtPiModel);
        // 勢力指数２ＥＭＡ
        this.stockPriceCalcValueService.register(spcvMgtPi2EmaModel);
        // 勢力指数１３ＥＭＡ
        this.stockPriceCalcValueService.register(spcvMgtPi13EmaModel);
        // 過去３期間の最安値
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        this.stockPriceCalcValueService.register(spcvMgtLpl3pPModel);
    }

}
