package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.domain.logic.SptsptLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceCalcValueLogic;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.SpDataRegMgtModel;
import kmg.im.stock.tssts.domain.model.SpDataRegModel;
import kmg.im.stock.tssts.domain.model.SptsMainDataModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.impl.SptsMainDataModelImpl;
import kmg.im.stock.tssts.domain.service.AbstractTsstsSptsRegService;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.domain.service.TsstsSptsMonthlyRegService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列月足登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSptsMonthlyRegServiceImpl extends AbstractTsstsSptsRegService implements TsstsSptsMonthlyRegService {

    /** 期間の種類の種類 */
    private static final PeriodTypeTypes PERIOD_TYPE_TYPES = PeriodTypeTypes.MONTHLY;

    /** 株価データ登録管理モデル */
    private SpDataRegMgtModel spDataRegMgtModel;

    /** 株価時系列期間の種類ロジック */
    private final SptsptLogic sptsptLogic;

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /** 株価計算値ロジック */
    private final StockPriceCalcValueLogic stockPriceCalcValueLogic;

    /** 株銘柄サービス */
    private final StockBrandService stockBrandService;

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
     * @param stockBrandService
     *                                  株銘柄サービス
     */
    public TsstsSptsMonthlyRegServiceImpl(final SptsptLogic sptsptLogic,
        final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic,
        final StockPriceCalcValueLogic stockPriceCalcValueLogic, final StockBrandService stockBrandService) {
        this.sptsptLogic = sptsptLogic;
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockPriceCalcValueLogic = stockPriceCalcValueLogic;
        this.stockBrandService = stockBrandService;
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
            TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列の削除 */
        this.stockPriceTimeSeriesLogic.deleteBySbCdAndPeriodTypeTypes(this.spDataRegMgtModel.getStockBrandCode(),
            TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列期間の種類の削除 */
        this.sptsptLogic.deleteBySbCdAndPeriodTypeTypes(this.spDataRegMgtModel.getStockBrandCode(),
            TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);

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
            TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);

        /* 株価時系列の登録 */
        // 詰め替え
        final List<SptsMainDataModel> sptsMainDataModelList = this.toSptsMainDataModelList();
        // 登録処理呼び出し
        this.stockPriceTimeSeriesLogic.register(TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES,
            sptsMainDataModelList);
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
        SptsMainDataModel addSptsMainDataModel = new SptsMainDataModelImpl(); // 追加するデータ
        addSptsMainDataModel.setNo(0L);
        addSptsMainDataModel.setPeriodStartDate(firstSpDataRegModel.getDate());
        addSptsMainDataModel.setOp(firstSpDataRegModel.getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = firstSpDataRegModel.getLp();
        BigDecimal hp = firstSpDataRegModel.getHp();
        long volume = firstSpDataRegModel.getVolume();

        /* 月足のデータを作成する */
        for (int i = 1; i < this.spDataRegMgtModel.getDataList().size(); i++) {

            final SpDataRegModel spDataRegModel = this.spDataRegMgtModel.getDataList().get(i);

            // 月が異なるか
            if (spDataRegModel.getDate().getMonthValue() != addSptsMainDataModel.getPeriodStartDate().getMonthValue()) {
                // 月が開始の月と異なる場合

                // ひとつ前の情報を終値に設定する
                final SpDataRegModel preSpDataRegModel = this.spDataRegMgtModel.getDataList().get(i - 1);
                addSptsMainDataModel.setPeriodEndDate(preSpDataRegModel.getDate());
                addSptsMainDataModel.setCp(preSpDataRegModel.getCp());
                addSptsMainDataModel.setLp(lp);
                addSptsMainDataModel.setHp(hp);
                addSptsMainDataModel.setLp(lp);
                addSptsMainDataModel.setVolume(volume);

                // 株価月足のリストに追加
                result.add(addSptsMainDataModel);

                // 現在の情報を追加する株価時系列メインデータモデルに設定する
                addSptsMainDataModel = new SptsMainDataModelImpl();
                addSptsMainDataModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addSptsMainDataModel.setPeriodStartDate(spDataRegModel.getDate());
                addSptsMainDataModel.setOp(spDataRegModel.getOp());
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
        addSptsMainDataModel.setPeriodEndDate(endSpDataRegModel.getDate());
        addSptsMainDataModel.setCp(endSpDataRegModel.getCp());
        addSptsMainDataModel.setLp(lp);
        addSptsMainDataModel.setHp(hp);
        addSptsMainDataModel.setLp(lp);
        addSptsMainDataModel.setVolume(volume);
        // 株価月足のリストに追加
        result.add(addSptsMainDataModel);

        return result;
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
            TsstsSptsMonthlyRegServiceImpl.PERIOD_TYPE_TYPES);
        return result;

    }
}
