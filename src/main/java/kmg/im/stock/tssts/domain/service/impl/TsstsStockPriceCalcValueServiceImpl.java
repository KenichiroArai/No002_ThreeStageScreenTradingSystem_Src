package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.service.MacdService;
import kmg.im.stock.core.domain.service.PowerIndexService;
import kmg.im.stock.core.infrastructure.types.StockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.domain.model.SptsMainDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
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

    /** 株価時系列メインデータ管理モデル */
    private SptsMainDataMgtModel sptsMainDataMgtModel;

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
     * @param sptsMainDataMgtModel
     *                             株価時系列メインデータ管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final SptsMainDataMgtModel sptsMainDataMgtModel) {
        this.sptsMainDataMgtModel = sptsMainDataMgtModel;
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

        /* 計算する */

        // ＭＣＡＤ
        final MacdService macdService = this.context.getBean(MacdService.class);
        macdService.initialize(this.sptsMainDataMgtModel.toSupplierDataList());
        // ＭＣＡＤライン
        macdService.clacLine();
        final StockPriceCalcValueMgtModel spcvMgtMacdlModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.MCADL, macdService.getLineList());
        // ＭＣＡＤシグナル
        macdService.clacSignal();
        final StockPriceCalcValueMgtModel spcvMgtMacdsModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.MCADS, macdService.getSignalList());
        // ＭＣＡＤヒストグラム
        macdService.clacHistogram();
        final StockPriceCalcValueMgtModel spcvMgtMacdhModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.MCADH, macdService.getHistogramList());

        // 勢力指数
        final PowerIndexService piService = this.context.getBean(PowerIndexService.class);
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        piService.initialize(stockPriceTimeSeriesMgtModel.toPowerIndexCalcModelList());
        piService.calc();
        final StockPriceCalcValueMgtModel spcvMgtPiModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.PI, piService.getCalcResultList());
        // 勢力指数２ＥＭＡ
        piService.defaultStSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi2EmaModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.PI2EMA, piService.getSmoothingList());
        // 勢力指数１３ＥＭＡ
        piService.defaultLtSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi13EmaModel = new StockPriceCalcValueMgtModelImpl(stockBrandModel,
            StockPriceCalcValueTypeTypes.PI13EMA, piService.getSmoothingList());

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
