package kmg.im.stock.tssts.domain.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.logic.ImStkSptsptLogic;
import kmg.im.stock.core.domain.logic.ImStkStockPriceCalcValueLogic;
import kmg.im.stock.core.domain.logic.ImStkStockPriceTimeSeriesLogic;
import kmg.im.stock.core.domain.model.ImStkSpDataRegMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpDataRegModel;
import kmg.im.stock.core.domain.model.ImStkSptsRegDataModel;
import kmg.im.stock.core.domain.model.impl.ImStkSptsRegDataModelImpl;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsDailyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列日足登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSptsDailyRegServiceImpl extends AbstractTsstsSptsRegService implements TsstsSptsDailyRegService {

    /** 投資株式期間の種類の種類 */
    private static final ImStkPeriodTypeTypes PERIOD_TYPE_TYPES = ImStkPeriodTypeTypes.DAILY;

    /** 株銘柄ＩＤ */
    private long stockBrandId;

    /** 株価時系列期間の種類ID */
    private Long sptsptId;

    /** 投資株式株株価データ登録管理モデル */
    private ImStkSpDataRegMgtModel imStkSpDataRegMgtModel;

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 投資株式株価時系列期間の種類ロジック */
    private final ImStkSptsptLogic imStkSptsptLogic;

    /** 投資株式株価時系列ロジック */
    private final ImStkStockPriceTimeSeriesLogic imStkStockPriceTimeSeriesLogic;

    /** 投資株式株価計算値ロジック */
    private final ImStkStockPriceCalcValueLogic imStkStockPriceCalcValueLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsLogMessageResolver
     *                                       三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param imStkSptsptLogic
     *                                       投資株式株価時系列期間の種類ロジック
     * @param imStkStockPriceTimeSeriesLogic
     *                                       投資株式株価時系列ロジック
     * @param imStkStockPriceCalcValueLogic
     *                                       投資株式株価計算値ロジック
     */
    public TsstsSptsDailyRegServiceImpl(final TsstsLogMessageResolver tsstsLogMessageResolver,
        final ImStkSptsptLogic imStkSptsptLogic, final ImStkStockPriceTimeSeriesLogic imStkStockPriceTimeSeriesLogic,
        final ImStkStockPriceCalcValueLogic imStkStockPriceCalcValueLogic) {
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.imStkSptsptLogic = imStkSptsptLogic;
        this.imStkStockPriceTimeSeriesLogic = imStkStockPriceTimeSeriesLogic;
        this.imStkStockPriceCalcValueLogic = imStkStockPriceCalcValueLogic;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                               株銘柄ＩＤ
     * @param imStkSpDataRegMgtModel
     *                               投資株式株株価データ登録管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final long stockBrandId, final ImStkSpDataRegMgtModel imStkSpDataRegMgtModel) {
        this.stockBrandId = stockBrandId;
        this.imStkSpDataRegMgtModel = imStkSpDataRegMgtModel;
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
        final long result = 0L;

        /* 株価計算値の削除 */
        try {
            this.imStkStockPriceCalcValueLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列の削除 */
        try {
            this.imStkStockPriceTimeSeriesLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類の削除 */
        try {
            this.imStkSptsptLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        return result;
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

        /* 株価時系列期間の種類の登録 */
        try {
            this.imStkSptsptLogic.register(this.stockBrandId, TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類ID */
        try {
            this.sptsptId = this.imStkSptsptLogic.getSptsptId(this.stockBrandId,
                TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES, LocalDate.now());
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列の登録 */
        // 詰め替え
        final List<ImStkSptsRegDataModel> sptsMainDataModelList = this.toImStkSptsRegDataModelList();
        // 登録処理呼び出し
        try {
            this.imStkStockPriceTimeSeriesLogic.register(TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES,
                sptsMainDataModelList);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
    }

    /**
     * 投資株式株価時系列登録データモデルのリストにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 投資株式株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<ImStkSptsRegDataModel> toImStkSptsRegDataModelList() throws TsstsDomainException {
        final List<ImStkSptsRegDataModel> result = new ArrayList<>();
        for (final ImStkSpDataRegModel spDataRegModel : this.imStkSpDataRegMgtModel.getDataList()) {
            final ImStkSptsRegDataModel sptsMainDataModel = new ImStkSptsRegDataModelImpl();
            BeanUtils.copyProperties(spDataRegModel, sptsMainDataModel);
            sptsMainDataModel.setSptsptId(this.sptsptId);
            sptsMainDataModel.setPeriodStartDate(spDataRegModel.getDate()); // 期間開始日に日付を設定
            sptsMainDataModel.setPeriodEndDate(spDataRegModel.getDate()); // 期間終了日に日付を設定
            result.add(sptsMainDataModel);
        }
        return result;
    }
}
