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
import kmg.im.stock.core.domain.model.SpDataRegMgtModel;
import kmg.im.stock.core.domain.model.SpDataRegModel;
import kmg.im.stock.core.domain.model.SptsRegDataModel;
import kmg.im.stock.core.domain.model.impl.SptsRegDataModelImpl;
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

    /** 株価データ登録管理モデル */
    private SpDataRegMgtModel spDataRegMgtModel;

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
     *                          株銘柄ＩＤ
     * @param spDataRegMgtModel
     *                          株価データ登録管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final long stockBrandId, final SpDataRegMgtModel spDataRegMgtModel) {
        this.stockBrandId = stockBrandId;
        this.spDataRegMgtModel = spDataRegMgtModel;
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
        final List<SptsRegDataModel> sptsMainDataModelList = this.toSptsRegDataModelList();
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
     * 株価時系列登録データモデルのリストにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<SptsRegDataModel> toSptsRegDataModelList() throws TsstsDomainException {

        final List<SptsRegDataModel> result = new ArrayList<>();

        /* 事前チェック */
        // 株価データ登録管理モデルのデータリストが空か
        if (ListUtils.isEmpty(this.spDataRegMgtModel.getDataList())) {
            // 空の場合

            // 後続処理なし
            return result;
        }

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す

        /* 最初のデータを作成する */
        final SpDataRegModel firstSpDataRegModel = this.spDataRegMgtModel.getDataList().get(0);
        SptsRegDataModel addSptsRegDataModel = new SptsRegDataModelImpl(); // 追加するデータ
        addSptsRegDataModel.setSptsptId(this.sptsptId);
        addSptsRegDataModel.setNo(0L);
        addSptsRegDataModel.setPeriodStartDate(firstSpDataRegModel.getDate());
        addSptsRegDataModel.setOp(firstSpDataRegModel.getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = firstSpDataRegModel.getLp();
        BigDecimal hp = firstSpDataRegModel.getHp();
        long volume = firstSpDataRegModel.getVolume();

        /* 月足のデータを作成する */
        for (int i = 1; i < this.spDataRegMgtModel.getDataList().size(); i++) {

            final SpDataRegModel spDataRegModel = this.spDataRegMgtModel.getDataList().get(i);

            // 月が異なるか
            if (spDataRegModel.getDate().getMonthValue() != addSptsRegDataModel.getPeriodStartDate().getMonthValue()) {
                // 月が開始の月と異なる場合

                // ひとつ前の情報を終値に設定する
                final SpDataRegModel preSpDataRegModel = this.spDataRegMgtModel.getDataList().get(i - 1);
                addSptsRegDataModel.setPeriodEndDate(preSpDataRegModel.getDate());
                addSptsRegDataModel.setCp(preSpDataRegModel.getCp());
                addSptsRegDataModel.setLp(lp);
                addSptsRegDataModel.setHp(hp);
                addSptsRegDataModel.setLp(lp);
                addSptsRegDataModel.setVolume(volume);

                // 株価月足のリストに追加
                result.add(addSptsRegDataModel);

                // 現在の情報を追加する株価時系列登録データモデルに設定する
                addSptsRegDataModel = new SptsRegDataModelImpl();
                addSptsRegDataModel.setSptsptId(this.sptsptId);
                addSptsRegDataModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addSptsRegDataModel.setPeriodStartDate(spDataRegModel.getDate());
                addSptsRegDataModel.setOp(spDataRegModel.getOp());
                lp = spDataRegModel.getLp();
                hp = spDataRegModel.getHp();
                volume = spDataRegModel.getVolume();

                continue;

            }

            lp = lp.min(spDataRegModel.getLp());
            hp = hp.max(spDataRegModel.getHp());
            volume += spDataRegModel.getVolume();
        }

        /* ひとつ前の情報を終値に設定し、追加する */
        final SpDataRegModel endSpDataRegModel = this.spDataRegMgtModel.getDataList()
            .get(this.spDataRegMgtModel.getDataList().size() - 1);
        addSptsRegDataModel.setPeriodEndDate(endSpDataRegModel.getDate());
        addSptsRegDataModel.setCp(endSpDataRegModel.getCp());
        addSptsRegDataModel.setLp(lp);
        addSptsRegDataModel.setHp(hp);
        addSptsRegDataModel.setLp(lp);
        addSptsRegDataModel.setVolume(volume);
        // 株価月足のリストに追加
        result.add(addSptsRegDataModel);

        return result;
    }
}
