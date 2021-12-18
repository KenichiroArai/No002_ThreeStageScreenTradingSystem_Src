package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.model.ImStkSpcvInitMgtModel;
import kmg.im.stock.core.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.core.domain.model.impl.StockPriceCalcValueMgtModelImpl;
import kmg.im.stock.core.domain.service.ImStkLowestPriceInPastService;
import kmg.im.stock.core.domain.service.ImStkMacdService;
import kmg.im.stock.core.domain.service.ImStkPowerIndexService;
import kmg.im.stock.core.domain.service.ImStkStockPriceCalcValueService;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkStockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム投資株式株価計算値サービスインタフェース<br>
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
    private ImStkSpcvInitMgtModel imStkSpcvInitMgtModel;

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 投資株式株価計算値サービス */
    private final ImStkStockPriceCalcValueService imStkStockPriceCalcValueService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                                        アプリケーションコンテキスト
     * @param tsstsLogMessageResolver
     *                                        三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param imStkStockPriceCalcValueService
     *                                        投資株式株価計算値サービス
     */
    public TsstsStockPriceCalcValueServiceImpl(final ApplicationContext context,
        final TsstsLogMessageResolver tsstsLogMessageResolver,
        final ImStkStockPriceCalcValueService imStkStockPriceCalcValueService) {
        this.context = context;
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.imStkStockPriceCalcValueService = imStkStockPriceCalcValueService;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkSpcvInitMgtModel
     *                              投資株式株価時系列メインデータ管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final ImStkSpcvInitMgtModel imStkSpcvInitMgtModel) {
        this.imStkSpcvInitMgtModel = imStkSpcvInitMgtModel;
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
        final ImStkMacdService imStkMacdService = this.context.getBean(ImStkMacdService.class);
        imStkMacdService.initialize(this.imStkSpcvInitMgtModel.toSupplierDataList());
        // ＭＣＡＤライン
        imStkMacdService.clacLine();
        final StockPriceCalcValueMgtModel spcvMgtMacdlModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADL, imStkMacdService.getLineList());
        // ＭＣＡＤシグナル
        imStkMacdService.clacSignal();
        final StockPriceCalcValueMgtModel spcvMgtMacdsModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADS, imStkMacdService.getSignalList());
        // ＭＣＡＤヒストグラム
        imStkMacdService.clacHistogram();
        final StockPriceCalcValueMgtModel spcvMgtMacdhModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.MCADH, imStkMacdService.getHistogramList());

        // 勢力指数
        final ImStkPowerIndexService piService = this.context.getBean(ImStkPowerIndexService.class);
        piService.initialize(this.imStkSpcvInitMgtModel.toPowerIndexCalcModelList());
        piService.calc();
        final StockPriceCalcValueMgtModel spcvMgtPiModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI, piService.getCalcResultList());
        // 勢力指数２ＥＭＡ
        piService.defaultStSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi2EmaModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI2EMA, piService.getSmoothingList());
        // 勢力指数１３ＥＭＡ
        piService.defaultLtSmoothing();
        final StockPriceCalcValueMgtModel spcvMgtPi13EmaModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.PI13EMA, piService.getSmoothingList());

        // 過去３期間の最安値
        final ImStkLowestPriceInPastService imStkLowestPriceInPastService = this.context
            .getBean(ImStkLowestPriceInPastService.class);
        final List<Supplier<BigDecimal>> lowestPriceInPastCalcResultList = this.imStkSpcvInitMgtModel
            .toSupplierDataList();
        imStkLowestPriceInPastService.initialize(lowestPriceInPastCalcResultList, 3);
        imStkLowestPriceInPastService.calc();
        final StockPriceCalcValueMgtModel spcvMgtLpl3pPModel = new StockPriceCalcValueMgtModelImpl(
            this.imStkSpcvInitMgtModel, ImStkStockPriceCalcValueTypeTypes.LOWEST_PRICE_IN_LAST3_PERIODS,
            imStkLowestPriceInPastService.getClacResultList());

        /* 登録する */
        // ＭＣＡＤライン
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtMacdlModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // ＭＣＡＤシグナル
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtMacdsModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // ＭＣＡＤヒストグラム
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtMacdhModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtPiModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数２ＥＭＡ
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtPi2EmaModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 勢力指数１３ＥＭＡ
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtPi13EmaModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 過去３期間の最安値
        try {
            this.imStkStockPriceCalcValueService.register(spcvMgtLpl3pPModel);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

    }

}
