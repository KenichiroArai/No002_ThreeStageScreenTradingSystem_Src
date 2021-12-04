package kmg.im.stock.tssts.domain.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.model.SpDataRegMgtModel;
import kmg.im.stock.core.domain.model.SpDataRegModel;
import kmg.im.stock.core.domain.model.SptsRegDataModel;
import kmg.im.stock.core.domain.model.impl.SptsRegDataModelImpl;
import kmg.im.stock.core.infrastructure.types.PeriodTypeTypes;
import kmg.im.stock.tssts.domain.logic.SptsptLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceCalcValueLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsDailyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列日足登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSptsDailyRegServiceImpl extends AbstractTsstsSptsRegService implements TsstsSptsDailyRegService {

    /** 期間の種類の種類 */
    private static final PeriodTypeTypes PERIOD_TYPE_TYPES = PeriodTypeTypes.DAILY;

    /** 株銘柄ＩＤ */
    private long stockBrandId;

    /** 株価時系列期間の種類ID */
    private Long sptsptId;

    /** 株価データ登録管理モデル */
    private SpDataRegMgtModel spDataRegMgtModel;

    /** 株価時系列期間の種類ロジック */
    private final SptsptLogic sptsptLogic;

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /** 株価計算値ロジック */
    private final StockPriceCalcValueLogic stockPriceCalcValueLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptLogic
     *                                  株価時系列期間の種類ロジック
     * @param stockPriceTimeSeriesLogic
     *                                  株価時系列ロジック
     * @param stockPriceCalcValueLogic
     *                                  株価計算値ロジック
     */
    public TsstsSptsDailyRegServiceImpl(final SptsptLogic sptsptLogic,
        final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic,
        final StockPriceCalcValueLogic stockPriceCalcValueLogic) {
        this.sptsptLogic = sptsptLogic;
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockPriceCalcValueLogic = stockPriceCalcValueLogic;
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
        this.stockPriceCalcValueLogic.deleteBySbIdAndPeriodTypeTypes(this.stockBrandId,
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列の削除 */
        this.stockPriceTimeSeriesLogic.deleteBySbIdAndPeriodTypeTypes(this.stockBrandId,
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列期間の種類の削除 */
        this.sptsptLogic.deleteBySbIdAndPeriodTypeTypes(this.stockBrandId,
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

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
        this.sptsptLogic.register(this.stockBrandId, TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列期間の種類ID */
        this.sptsptId = this.sptsptLogic.getSptsptId(this.stockBrandId, TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES,
            LocalDate.now());

        /* 株価時系列の登録 */
        // 詰め替え
        final List<SptsRegDataModel> sptsMainDataModelList = this.toSptsRegDataModelList();
        // 登録処理呼び出し
        this.stockPriceTimeSeriesLogic.register(TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES, sptsMainDataModelList);
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
        for (final SpDataRegModel spDataRegModel : this.spDataRegMgtModel.getDataList()) {
            final SptsRegDataModel sptsMainDataModel = new SptsRegDataModelImpl();
            BeanUtils.copyProperties(spDataRegModel, sptsMainDataModel);
            sptsMainDataModel.setSptsptId(this.sptsptId);
            sptsMainDataModel.setPeriodStartDate(spDataRegModel.getDate()); // 期間開始日に日付を設定
            sptsMainDataModel.setPeriodEndDate(spDataRegModel.getDate()); // 期間終了日に日付を設定
            result.add(sptsMainDataModel);
        }
        return result;
    }
}
