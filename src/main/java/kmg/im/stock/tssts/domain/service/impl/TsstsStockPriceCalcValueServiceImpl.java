package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.model.ImStkSpcvInitMgtModel;
import kmg.im.stock.core.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.core.domain.model.impl.StockPriceCalcValueMgtModelImpl;
import kmg.im.stock.core.domain.service.LowestPriceInPastService;
import kmg.im.stock.core.domain.service.MacdService;
import kmg.im.stock.core.domain.service.PowerIndexService;
import kmg.im.stock.core.domain.service.StockPriceCalcValueService;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkStockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

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

    /** 投資株式株価計算値初期化管理モデル */
    private ImStkSpcvInitMgtModel tsstsSpcvInitMgtModel;

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

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
     * @param tsstsLogMessageResolver
     *                                   三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param stockPriceCalcValueService
     *                                   株価計算値サービス
     */
    public TsstsStockPriceCalcValueServiceImpl(final ApplicationContext context,
        final TsstsLogMessageResolver tsstsLogMessageResolver,
        final StockPriceCalcValueService stockPriceCalcValueService) {
        this.context = context;
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.stockPriceCalcValueService = stockPriceCalcValueService;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsSpcvInitMgtModel
     *                              株価時系列メインデータ管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final ImStkSpcvInitMgtModel tsstsSpcvInitMgtModel) {
        this.tsstsSpcvInitMgtModel = tsstsSpcvInitMgtModel;
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
        macdService.initialize(this.tsstsSpcvInitMgtModel.toSupplierDataList());
        // ＭＣＡＤライン
        macdService.clacLine();
        final StockPriceCalcValueMgtModel spcvMgtMacdlModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADL, macdService.getLineList());
        // ＭＣＡＤシグナル
        macdService.clacSignal();
        final StockPriceCalcValueMgtModel spcvMgtMacdsModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADS, macdService.getSignalList());
        // ＭＣＡＤヒストグラム
        macdService.clacHistogram();
        final StockPriceCalcValueMgtModel spcvMgtMacdhModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADH, macdService.getHistogramList());

        // 勢力指数
        final PowerIndexService piService = this.context.getBean(PowerIndexService.class);
        piService.initialize(this.tsstsSpcvInitMgtModel.toPowerIndexCalcModelList());
        piService.calc();
        final StockPriceCalcValueMgtModel spcvMgtPiModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI, piService.getCalcResultList());
        // 勢力指数２ＥＭＡ
        piService.defaultStSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi2EmaModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI2EMA, piService.getSmoothingList());
        // 勢力指数１３ＥＭＡ
        piService.defaultLtSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi13EmaModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI13EMA, piService.getSmoothingList());

        // 過去３期間の最安値
        final LowestPriceInPastService lowestPriceInPastService = this.context.getBean(LowestPriceInPastService.class);
        final List<Supplier<BigDecimal>> lowestPriceInPastCalcResultList = this.tsstsSpcvInitMgtModel
            .toSupplierDataList();
        lowestPriceInPastService.initialize(lowestPriceInPastCalcResultList, 3);
        lowestPriceInPastService.calc();
        final StockPriceCalcValueMgtModel spcvMgtLpl3pPModel = new StockPriceCalcValueMgtModelImpl(
            this.tsstsSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.LOWEST_PRICE_IN_LAST3_PERIODS,
            lowestPriceInPastService.getClacResultList());

        /* 登録する */
        // ＭＣＡＤライン
        try {
            this.stockPriceCalcValueService.register(spcvMgtMacdlModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // ＭＣＡＤシグナル
        try {
            this.stockPriceCalcValueService.register(spcvMgtMacdsModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // ＭＣＡＤヒストグラム
        try {
            this.stockPriceCalcValueService.register(spcvMgtMacdhModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数
        try {
            this.stockPriceCalcValueService.register(spcvMgtPiModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数２ＥＭＡ
        try {
            this.stockPriceCalcValueService.register(spcvMgtPi2EmaModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数１３ＥＭＡ
        try {
            this.stockPriceCalcValueService.register(spcvMgtPi13EmaModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 過去３期間の最安値
        try {
            this.stockPriceCalcValueService.register(spcvMgtLpl3pPModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

    }

}
