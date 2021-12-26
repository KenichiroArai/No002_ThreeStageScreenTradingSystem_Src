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
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsWeeklyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列週足登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSptsWeeklyRegServiceImpl extends AbstractTsstsSptsRegService implements TsstsSptsWeeklyRegService {

    /** 投資株式期間の種類の種類 */
    private static final ImStkPeriodTypeTypes PERIOD_TYPE_TYPES = ImStkPeriodTypeTypes.WEEKLY;

    /** 株銘柄ＩＤ */
    private long stockBrandId;

    /** 株価時系列期間の種類ID */
    private Long sptsptId;

    /** 投資株式株価データ登録管理モデル */
    private ImStkSpDataRegMgtModel imStkkSpDataRegMgtModel;

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 投資株式株投資株式株価時系列期間の種類ロジック */
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
     *                                       投資株式株投資株式株価時系列期間の種類ロジック
     * @param imStkStockPriceTimeSeriesLogic
     *                                       投資株式株価時系列ロジック
     * @param imStkStockPriceCalcValueLogic
     *                                       投資株式株価計算値ロジック
     */
    public TsstsSptsWeeklyRegServiceImpl(final TsstsLogMessageResolver tsstsLogMessageResolver,
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
     *                                株銘柄ＩＤ
     * @param imStkkSpDataRegMgtModel
     *                                投資株式株価データ登録管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final long stockBrandId, final ImStkSpDataRegMgtModel imStkkSpDataRegMgtModel) {
        this.stockBrandId = stockBrandId;
        this.imStkkSpDataRegMgtModel = imStkkSpDataRegMgtModel;
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
                TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列の削除 */
        try {
            this.imStkStockPriceTimeSeriesLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類の削除 */
        try {
            this.imStkSptsptLogic.deleteBySbIdAndImStkPeriodTypeTypes(this.stockBrandId,
                TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES);
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
            this.imStkSptsptLogic.register(this.stockBrandId, TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価時系列期間の種類ID */
        try {
            this.sptsptId = this.imStkSptsptLogic.getSptsptId(this.stockBrandId,
                TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES, LocalDate.now());
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
            this.imStkStockPriceTimeSeriesLogic.register(TsstsSptsWeeklyRegServiceImpl.PERIOD_TYPE_TYPES,
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

        /* 事前チェック */
        // 投資株式株価データ登録管理モデルのデータリストが空か
        if (ListUtils.isEmpty(this.imStkkSpDataRegMgtModel.getDataList())) {
            // 空の場合

            // 後続処理なし
            return result;
        }

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す

        /* 最初のデータを作成する */
        final ImStkSpDataRegModel firstImStkSpDataRegModel = this.imStkkSpDataRegMgtModel.getDataList().get(0);
        ImStkSptsRegDataModel addImStkSptsRegDataModel = new ImStkSptsRegDataModelImpl(); // 追加するデータ
        addImStkSptsRegDataModel.setSptsptId(this.sptsptId);
        addImStkSptsRegDataModel.setNo(0L);
        addImStkSptsRegDataModel.setPeriodStartDate(firstImStkSpDataRegModel.getDate());
        addImStkSptsRegDataModel.setOp(firstImStkSpDataRegModel.getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = firstImStkSpDataRegModel.getLp();
        BigDecimal hp = firstImStkSpDataRegModel.getHp();
        long volume = firstImStkSpDataRegModel.getVolume();

        /* 週足のデータを作成する */
        for (int i = 1; i < this.imStkkSpDataRegMgtModel.getDataList().size(); i++) {

            final ImStkSpDataRegModel spDataRegModel = this.imStkkSpDataRegMgtModel.getDataList().get(i);

            // 週が異なるか
            if ((spDataRegModel.getDate().getDayOfWeek()
                .compareTo(addImStkSptsRegDataModel.getPeriodStartDate().getDayOfWeek()) <= 0)) {
                // 曜日が開始の曜日よりも同じまたは前の場合

                // ひとつ前の情報を終値に設定する
                final ImStkSpDataRegModel preImStkSpDataRegModel = this.imStkkSpDataRegMgtModel.getDataList()
                    .get(i - 1);
                addImStkSptsRegDataModel.setPeriodEndDate(preImStkSpDataRegModel.getDate());
                addImStkSptsRegDataModel.setCp(preImStkSpDataRegModel.getCp());
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setHp(hp);
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setVolume(volume);

                // 株価週足のリストに追加
                result.add(addImStkSptsRegDataModel);

                // 現在の情報を追加する投資株式株価時系列登録データモデルに設定する
                addImStkSptsRegDataModel = new ImStkSptsRegDataModelImpl();
                addImStkSptsRegDataModel.setSptsptId(this.sptsptId);
                addImStkSptsRegDataModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addImStkSptsRegDataModel.setPeriodStartDate(spDataRegModel.getDate());
                addImStkSptsRegDataModel.setOp(spDataRegModel.getOp());
                lp = spDataRegModel.getLp();
                hp = spDataRegModel.getHp();
                volume = spDataRegModel.getVolume();

                continue;

            } else if (spDataRegModel.getDate()
                .compareTo(addImStkSptsRegDataModel.getPeriodStartDate().plusDays(7)) >= 0) {
                // 開始の7日以降の場合

                // TODO KenichiroArai 2021/05/16 曜日の判定と処理が同じなので、まとめる

                // ひとつ前の情報を終値に設定する
                final ImStkSpDataRegModel preImStkSpDataRegModel = this.imStkkSpDataRegMgtModel.getDataList()
                    .get(i - 1);
                addImStkSptsRegDataModel.setPeriodEndDate(preImStkSpDataRegModel.getDate());
                addImStkSptsRegDataModel.setCp(preImStkSpDataRegModel.getCp());
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setHp(hp);
                addImStkSptsRegDataModel.setLp(lp);
                addImStkSptsRegDataModel.setVolume(volume);

                // 株価週足のリストに追加
                result.add(addImStkSptsRegDataModel);

                // 現在の情報を追加する株価週足ＤＴＯに設定する
                addImStkSptsRegDataModel = new ImStkSptsRegDataModelImpl();
                addImStkSptsRegDataModel.setSptsptId(this.sptsptId);
                addImStkSptsRegDataModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addImStkSptsRegDataModel.setPeriodStartDate(spDataRegModel.getDate());
                addImStkSptsRegDataModel.setOp(spDataRegModel.getOp());
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
        final ImStkSpDataRegModel endImStkSpDataRegModel = this.imStkkSpDataRegMgtModel.getDataList()
            .get(this.imStkkSpDataRegMgtModel.getDataList().size() - 1);
        addImStkSptsRegDataModel.setPeriodEndDate(endImStkSpDataRegModel.getDate());
        addImStkSptsRegDataModel.setCp(endImStkSpDataRegModel.getCp());
        addImStkSptsRegDataModel.setLp(lp);
        addImStkSptsRegDataModel.setHp(hp);
        addImStkSptsRegDataModel.setLp(lp);
        addImStkSptsRegDataModel.setVolume(volume);
        // 株価週足のリストに追加
        result.add(addImStkSptsRegDataModel);

        return result;
    }

}
