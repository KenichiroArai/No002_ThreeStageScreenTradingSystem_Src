package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.domain.logic.SimLogic;
import kmg.im.stock.tssts.domain.model.PosModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesDailyMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMonthlyMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesWeeklyMgtModel;
import kmg.im.stock.tssts.domain.service.TsstsStockService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.spring.infrastructure.constants.BeanDefaultScopeConstants;

/**
 * 三段階スクリーン・トレーディング・システム株サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
@Scope(BeanDefaultScopeConstants.PROTOTYPE)
public class TsstsStockServiceImpl implements TsstsStockService {

    /** ポジションマップ */
    private final Map<Long, PosModel> posMap;

    /** 株価データ管理モデル */
    private final StockPriceMgtModel stockPriceMgtModel;

    /** 株銘柄モデル */
    private final StockBrandModel stockBrandModel;

    /** 株価時系列日足管理モデル */
    private final StockPriceTimeSeriesDailyMgtModel stockPriceTimeSeriesDailyMgtModel;

    /** 株価時系列週足管理モデル */
    private final StockPriceTimeSeriesWeeklyMgtModel stockPriceTimeSeriesWeeklyMgtModel;

    /** 株価時系列月足管理モデル */
    private final StockPriceTimeSeriesMonthlyMgtModel stockPriceTimeSeriesMonthlyMgtModel;

    /** シミュレーションロジック */
    private final SimLogic simLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceMgtModel
     *                                            株価データ管理モデル
     * @param stockBrandModel
     *                                            株銘柄モデル
     * @param stockPriceTimeSeriesDailyMgtModel
     *                                            株価時系列日足管理モデル
     * @param stockPriceTimeSeriesWeeklyMgtModel
     *                                            株価時系列週足管理モデル
     * @param stockPriceTimeSeriesMonthlyMgtModel
     *                                            株価時系列月足管理モデル
     * @param simLogic
     *                                            シミュレーションロジック
     */
    public TsstsStockServiceImpl(final StockPriceMgtModel stockPriceMgtModel, final StockBrandModel stockBrandModel,
        final StockPriceTimeSeriesDailyMgtModel stockPriceTimeSeriesDailyMgtModel,
        final StockPriceTimeSeriesWeeklyMgtModel stockPriceTimeSeriesWeeklyMgtModel,
        final StockPriceTimeSeriesMonthlyMgtModel stockPriceTimeSeriesMonthlyMgtModel, final SimLogic simLogic) {
        this.posMap = new HashMap<>();
        this.stockPriceMgtModel = stockPriceMgtModel;
        this.stockBrandModel = stockBrandModel;
        this.stockPriceTimeSeriesDailyMgtModel = stockPriceTimeSeriesDailyMgtModel;
        this.stockPriceTimeSeriesWeeklyMgtModel = stockPriceTimeSeriesWeeklyMgtModel;
        this.stockPriceTimeSeriesMonthlyMgtModel = stockPriceTimeSeriesMonthlyMgtModel;
        this.simLogic = simLogic;
    }

    /**
     * 全株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void registerAllStockPriceData() throws TsstsDomainException {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.stockPriceMgtModel.getStockPriceStockStoragePathList();

        /* 銘柄ごとの株価データを株価時系列に挿入する */
        for (final Path stockPricePath : stockPriceStockStoragePathList) {
            this.registerStockPriceDataOfFile(stockPricePath);
        }
    }

    /**
     * ディレクトリにある株価データを登録する<br>
     * <p>
     * ディレクトリパスにある株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param directoryPath
     *                      ディレクトリパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void registerStockPriceDataOfDirectory(final Path directoryPath) throws TsstsDomainException {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.stockPriceMgtModel.getStockPriceStockStoragePathList();

        /* 銘柄ごとの株価データを株価時系列に挿入する */
        for (final Path stockPricePath : stockPriceStockStoragePathList) {
            this.registerStockPriceDataOfFile(stockPricePath);
        }
    }

    /**
     * ファイルの株価データを登録する<br>
     * <p>
     * ファイルパスに該当する株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void registerStockPriceDataOfFile(final Path filePath) throws TsstsDomainException {

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandModel.getStockBrandId(filePath);

        /* 株価データのリストを検索する */
        final List<StockPriceDataModel> stockPriceTimeSeriesModelList = this.stockPriceMgtModel
            .getStockPriceDataList(filePath);

        /* 株価時系列日足 */
        // 初期化する
        this.stockPriceTimeSeriesDailyMgtModel.initialize(stockBrandId, stockPriceTimeSeriesModelList);
        // 登録する
        this.stockPriceTimeSeriesDailyMgtModel.register();

        /* 株価時系列週足 */
        // 初期化する
        this.stockPriceTimeSeriesWeeklyMgtModel.initialize(stockBrandId, stockPriceTimeSeriesModelList);
        // 登録する
        this.stockPriceTimeSeriesWeeklyMgtModel.register();

        /* 株価時系列月足 */
        // 初期化する
        this.stockPriceTimeSeriesMonthlyMgtModel.initialize(stockBrandId, stockPriceTimeSeriesModelList);
        // 登録する
        this.stockPriceTimeSeriesMonthlyMgtModel.register();
    }

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void simulate() {
        // TODO KenichiroArai 2021/05/11 未実装
    }

    /**
     * シミュレーションする<br>
     * <p>
     * 指定した株コードのシミュレーションする
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    @Override
    public void simulate(final long stockCode) {
        // TODO KenichiroArai 2021/05/11 実装中

        /* 株価時系列のマップを取得する */
        final Map<Long, StockPriceTimeSeriesModel> stockPriceMgtModelMap = this.simLogic
            .getStockPriceTimeSeriesMap(stockCode);
        // コードのセットを取得する
        final Set<Long> codeSet = stockPriceMgtModelMap.keySet();

        /* コードごとにシミュレーションする */
        for (final Long code : codeSet) {

            final StockPriceTimeSeriesModel stockPriceTimeSeries = stockPriceMgtModelMap.get(code);

            // ポジションを取得する
            final PosModel pos = this.posMap.get(code);
            if (pos == null) {
                // ポジションがない場合

                // 第１のスクリーンに掛ける
                final boolean firstFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!firstFlg) {
                    continue;
                }

                // 第２のスクリーンに掛ける
                final boolean secondFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!secondFlg) {
                    continue;
                }

                // 第３のスクリーンに掛ける
                final boolean thirdFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!thirdFlg) {
                    continue;
                }

                // 買い情報を明細に登録する
                System.out.println("買い情報を明細に登録する");
            } else {
                // ポジションがある場合

                // ロスストップに引っかかるか
                // 引っかかる場合：
                // 損切り情報を明細に登録する
                // 利確の条件か
                // 条件に一致する場合：
                // 利確情報を明細に登録する
            }
        }
    }

    /**
     * 全ての銘柄のシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void chkSig() {
        // TODO KenichiroArai 2021/05/11 未実装
    }

    /**
     * 指定した株コードのシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    @Override
    public void chkSig(final long stockCode) {
        // TODO KenichiroArai 2021/05/11 未実装
    }

}
