package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.domain.logic.ImStkSptsptLogic;
import kmg.im.stock.core.domain.logic.ImStkStockPriceCalcValueLogic;
import kmg.im.stock.core.domain.logic.ImStkStockPriceTimeSeriesLogic;
import kmg.im.stock.core.domain.model.ImStkSpDataRegMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpDataRegModel;
import kmg.im.stock.core.domain.model.ImStkSptsRegDataModel;
import kmg.im.stock.core.domain.model.impl.ImStkSptsRegDataModelImpl;
import kmg.im.stock.core.domain.service.ImStkStockBrandService;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsMonthlyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列月足登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSptsMonthlyRegServiceImpl extends AbstractTsstsSptsRegService implements TsstsSptsMonthlyRegService {

    /** 投資株式期間の種類の種類 */
    private static final ImStkPeriodTypeTypes PERIOD_TYPE_TYPES = ImStkPeriodTypeTypes.MONTHLY;

    /** 株銘柄ＩＤ */
    private long stockBrandId;

    /** 株価時系列期間の種類ID */
    private Long sptsptId;

    /** 投資株式株価データ登録管理モデル */
    private ImStkSpDataRegMgtModel imStkSpDataRegMgtModel;

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 投資株式株価時系列期間の種類ロジック */
    private final ImStkSptsptLogic imStkSptsptLogic;

    /** 投資株式株価時系列ロジック */
    private final ImStkStockPriceTimeSeriesLogic imStkStockPriceTimeSeriesLogic;

    /** 投資株式株価計算値ロジック */
    private final ImStkStockPriceCalcValueLogic imStkStockPriceCalcValueLogic;

    /** 投資株式株銘柄サービス */
    private final ImStkStockBrandService imStkStockBrandService;

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
     * @param imStkStockBrandService
     *                                       投資株式株銘柄サービス
     */
    public TsstsSptsMonthlyRegServiceImpl(final TsstsLogMessageResolver tsstsLogMessageResolver,
        final ImStkSptsptLogic imStkSptsptLogic, final ImStkStockPriceTimeSeriesLogic imStkStockPriceTimeSeriesLogic,
        final ImStkStockPriceCalcValueLogic imStkStockPriceCalcValueLogic,
        final ImStkStockBrandService imStkStockBrandService) {
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.imStkSptsptLogic = imStkSptsptLogic;
        this.imStkStockPriceTimeSeriesLogic = imStkStockPriceTimeSeriesLogic;
        this.imStkStockPriceCalcValueLogic = imStkStockPriceCalcValueLogic;
        this.imStkStockBrandService = imStkStockBrandService;
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
     *                               投資株式株価データ登録管理モデル
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
                TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列の削除 */
        try {
            this.imStkStockPriceTimeSeriesLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類の削除 */
        try {
            this.imStkSptsptLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);
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
            this.imStkSptsptLogic.register(this.stockBrandId, TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類ID */
        try {
            this.sptsptId = this.imStkSptsptLogic.getSptsptId(this.stockBrandId,
                TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES, LocalDate.now());
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
            this.imStkStockPriceTimeSeriesLogic.register(TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES,
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
     * @return投資株式 株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<ImStkSptsRegDataModel> toImStkSptsRegDataModelList() throws TsstsDomainException {

        final List<ImStkSptsRegDataModel> result = new ArrayList<>();

        /* 事前チェック */
        // 投資株式株価データ登録管理モデルのデータリストが空か
        if (ListUtils.isEmpty(this.imStkSpDataRegMgtModel.getDataList())) {
            // 空の場合

            // 後続処理なし
            return result;
        }

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す

        /* 最初のデータを作成する */
        final ImStkSpDataRegModel firstImStkSpDataRegModel = this.imStkSpDataRegMgtModel.getDataList().get(0);
        ImStkSptsRegDataModel addImStkSptsRegDataModel = new ImStkSptsRegDataModelImpl(); // 追加するデータ
        addImStkSptsRegDataModel.setSptsptId(this.sptsptId);
        addImStkSptsRegDataModel.setNo(0L);
        addImStkSptsRegDataModel.setPeriodStartDate(firstImStkSpDataRegModel.getDate());
        addImStkSptsRegDataModel.setOp(firstImStkSpDataRegModel.getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = firstImStkSpDataRegModel.getLp();
        BigDecimal hp = firstImStkSpDataRegModel.getHp();
        long volume = firstImStkSpDataRegModel.getVolume();

        /* 月足のデータを作成する */
        for (int i = 1; i < this.imStkSpDataRegMgtModel.getDataList().size(); i++) {

            final ImStkSpDataRegModel imStkSpDataRegModel = this.imStkSpDataRegMgtModel.getDataList().get(i);

            // 月が異なるか
            if (imStkSpDataRegModel.getDate().getMonthValue() != addImStkSptsRegDataModel.getPeriodStartDate()
                .getMonthValue()) {
                // 月が開始の月と異なる場合

                // ひとつ前の情報を終値に設定する
                final ImStkSpDataRegModel preImStkSpDataRegModel = this.imStkSpDataRegMgtModel.getDataList().get(i - 1);
                addImStkSptsRegDataModel.setPeriodEndDate(preImStkSpDataRegModel.getDate());
                addImStkSptsRegDataModel.setCp(preImStkSpDataRegModel.getCp());
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setHp(hp);
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setVolume(volume);

                // 株価月足のリストに追加
                result.add(addImStkSptsRegDataModel);

                // 現在の情報を追加する株価時系列登録データモデルに設定する
                addImStkSptsRegDataModel = new ImStkSptsRegDataModelImpl();
                addImStkSptsRegDataModel.setSptsptId(this.sptsptId);
                addImStkSptsRegDataModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addImStkSptsRegDataModel.setPeriodStartDate(imStkSpDataRegModel.getDate());
                addImStkSptsRegDataModel.setOp(imStkSpDataRegModel.getOp());
                lp = imStkSpDataRegModel.getLp();
                hp = imStkSpDataRegModel.getHp();
                volume = imStkSpDataRegModel.getVolume();

                continue;

            }

            lp = lp.min(imStkSpDataRegModel.getLp());
            hp = hp.max(imStkSpDataRegModel.getHp());
            volume += imStkSpDataRegModel.getVolume();
        }

        /* ひとつ前の情報を終値に設定し、追加する */
        final ImStkSpDataRegModel endImStkSpDataRegModel = this.imStkSpDataRegMgtModel.getDataList()
            .get(this.imStkSpDataRegMgtModel.getDataList().size() - 1);
        addImStkSptsRegDataModel.setPeriodEndDate(endImStkSpDataRegModel.getDate());
        addImStkSptsRegDataModel.setCp(endImStkSpDataRegModel.getCp());
        addImStkSptsRegDataModel.setLp(lp);
        addImStkSptsRegDataModel.setHp(hp);
        addImStkSptsRegDataModel.setLp(lp);
        addImStkSptsRegDataModel.setVolume(volume);
        // 株価月足のリストに追加
        result.add(addImStkSptsRegDataModel);

        return result;
    }
}
