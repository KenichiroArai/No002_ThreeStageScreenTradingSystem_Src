package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesDailyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesMonthlyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesWeeklyService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceTimeSeriesService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
@Scope("prototype")
public class TsstsStockPriceTimeSeriesServiceImpl implements TsstsStockPriceTimeSeriesService {

    /** 株価データ管理モデル */
    private StockPriceDataMgtModel stockPriceDataMgtModel;

    /** 株価時系列日足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtDailyModel;

    /** 株価時系列週足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtWeeklyModel;

    /** 株価時系列月足管理モデル */
    private StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtMonthlyModel;

    /** 株価時系列日足サービス */
    private final StockPriceTimeSeriesDailyService stockPriceTimeSeriesDailyService;

    /** 株価時系列週足サービス */
    private final StockPriceTimeSeriesWeeklyService stockPriceTimeSeriesWeeklyService;

    /** 株価時系列月足サービス */
    private final StockPriceTimeSeriesMonthlyService stockPriceTimeSeriesMonthlyService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesDailyService
     *                                           株価時系列日足サービス
     * @param stockPriceTimeSeriesWeeklyService
     *                                           株価時系列週足サービス
     * @param stockPriceTimeSeriesMonthlyService
     *                                           株価時系列月足サービス
     */
    public TsstsStockPriceTimeSeriesServiceImpl(final StockPriceTimeSeriesDailyService stockPriceTimeSeriesDailyService,
        final StockPriceTimeSeriesWeeklyService stockPriceTimeSeriesWeeklyService,
        final StockPriceTimeSeriesMonthlyService stockPriceTimeSeriesMonthlyService) {
        this.stockPriceTimeSeriesDailyService = stockPriceTimeSeriesDailyService;
        this.stockPriceTimeSeriesWeeklyService = stockPriceTimeSeriesWeeklyService;
        this.stockPriceTimeSeriesMonthlyService = stockPriceTimeSeriesMonthlyService;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final StockPriceDataMgtModel stockPriceDataMgtModel) {
        this.stockPriceDataMgtModel = stockPriceDataMgtModel;
    }

    /**
     * 株価データ管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データ管理モデル
     */
    @Override
    public StockPriceDataMgtModel getStockPriceDataMgtModel() {
        final StockPriceDataMgtModel result = this.stockPriceDataMgtModel;
        return result;
    }

    /**
     * 株価時系列日足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列日足管理モデル
     */
    @Override
    public StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtDailyModel() {
        final StockPriceTimeSeriesMgtModel result = this.stockPriceTimeSeriesMgtDailyModel;
        return result;
    }

    /**
     * 株価時系列週足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列週足管理モデル
     */
    @Override
    public StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtWeeklyModel() {
        final StockPriceTimeSeriesMgtModel result = this.stockPriceTimeSeriesMgtWeeklyModel;
        return result;
    }

    /**
     * 株価時系列月足管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列月足管理モデル
     */
    @Override
    public StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtMonthlyModel() {
        final StockPriceTimeSeriesMgtModel result = this.stockPriceTimeSeriesMgtMonthlyModel;
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

        /* 株価時系列日足 */
        // 削除する
        this.stockPriceTimeSeriesDailyService.delete();
        // 株価時系列管理モデルを取得
        this.stockPriceTimeSeriesMgtDailyModel = this.stockPriceTimeSeriesDailyService
            .toStockPriceTimeSeriesMgtModel(this.stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesDailyService.register(this.stockPriceTimeSeriesMgtDailyModel);
        // 登録データを取得
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        this.stockPriceTimeSeriesMgtDailyModel = this.stockPriceTimeSeriesDailyService
//            .findBySptsptId(this.stockPriceTimeSeriesMgtDailyModel.getSptsptId());

        /* 株価時系列週足 */
        // 削除する
        this.stockPriceTimeSeriesWeeklyService.delete();
        // 株価時系列管理モデルを取得
        this.stockPriceTimeSeriesMgtWeeklyModel = this.stockPriceTimeSeriesWeeklyService
            .toStockPriceTimeSeriesMgtModel(this.stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesWeeklyService.register(this.stockPriceTimeSeriesMgtWeeklyModel);
        // 登録データを取得
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        this.stockPriceTimeSeriesMgtWeeklyModel = this.stockPriceTimeSeriesWeeklyService
//            .findBySptsptId(this.stockPriceTimeSeriesMgtWeeklyModel.getSptsptId());

        /* 株価時系列月足 */
        // 削除する
        this.stockPriceTimeSeriesMonthlyService.delete();
        // 株価時系列管理モデルを取得
        this.stockPriceTimeSeriesMgtMonthlyModel = this.stockPriceTimeSeriesMonthlyService
            .toStockPriceTimeSeriesMgtModel(this.stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesMonthlyService.register(this.stockPriceTimeSeriesMgtMonthlyModel);
        // 登録データを取得
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        this.stockPriceTimeSeriesMgtMonthlyModel = this.stockPriceTimeSeriesMonthlyService
//            .findBySptsptId(this.stockPriceTimeSeriesMgtMonthlyModel.getSptsptId());
    }
}
