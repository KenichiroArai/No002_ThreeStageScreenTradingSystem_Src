package kmg.im.stock.tssts.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.SptsptLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceCalcValueLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.SpDataRegMgtModel;
import kmg.im.stock.tssts.domain.model.SpDataRegModel;
import kmg.im.stock.tssts.domain.model.SptsMainDataModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.impl.SptsMainDataModelImpl;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsDailyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

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
     * @param spDataRegMgtModel
     *                          株価データ登録管理モデル
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final SpDataRegMgtModel spDataRegMgtModel) {
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
        this.stockPriceCalcValueLogic.deleteBySbCdAndPeriodTypeTypes(this.spDataRegMgtModel.getStockBrandCode(),
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列の削除 */
        this.stockPriceTimeSeriesLogic.deleteBySbCdAndPeriodTypeTypes(this.spDataRegMgtModel.getStockBrandCode(),
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列期間の種類の削除 */
        this.sptsptLogic.deleteBySbCdAndPeriodTypeTypes(this.spDataRegMgtModel.getStockBrandCode(),
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
        this.sptsptLogic.register(this.spDataRegMgtModel.getStockBrandCode(),
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列の登録 */
        // 詰め替え
        final List<SptsMainDataModel> sptsMainDataModelList = this.toSptsMainDataModelList();
        // 登録処理呼び出し
        this.stockPriceTimeSeriesLogic.register(TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES, sptsMainDataModelList);
    }

    /**
     * 株価時系列管理モデルを検索する<br>
     * <p>
     * 株価時系列期間の種類IDに該当する株価時系列管理モデルを検索し、該当する株価時系列管理モデルを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockBrandModel findBySptsptId(final long sptsptId) throws TsstsDomainException {

        final StockBrandModel result = this.stockPriceTimeSeriesLogic.findBySptsptId(sptsptId,
            TsstsSptsDailyRegServiceImpl.PERIOD_TYPE_TYPES);
        return result;

    }

    /**
     * 株価時系列メインデータモデルのリストにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列メインデータモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<SptsMainDataModel> toSptsMainDataModelList() throws TsstsDomainException {
        final List<SptsMainDataModel> result = new ArrayList<>();
        for (final SpDataRegModel spDataRegModel : this.spDataRegMgtModel.getDataList()) {
            final SptsMainDataModel sptsMainDataModel = new SptsMainDataModelImpl();
            BeanUtils.copyProperties(spDataRegModel, sptsMainDataModel);
            result.add(sptsMainDataModel);
        }
        return result;
    }
}
