package kmg.im.stock.tssts.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.service.RegisterService;
import kmg.im.stock.tssts.domain.service.StockPriceCalcValueService;
import kmg.im.stock.tssts.domain.service.StockPriceDataService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesDailyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesMonthlyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesWeeklyService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** 株価データサービス */
    private final StockPriceDataService stockPriceDataService;

    /** 株価時系列日足サービス */
    private final StockPriceTimeSeriesDailyService stockPriceTimeSeriesDailyService;

    /** 株価時系列週足サービス */
    private final StockPriceTimeSeriesWeeklyService stockPriceTimeSeriesWeeklyService;

    /** 株価時系列月足サービス */
    private final StockPriceTimeSeriesMonthlyService stockPriceTimeSeriesMonthlyService;

    /** 株価計算値サービス */
    private final StockPriceCalcValueService stockPriceCalcValueService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                                           ログメッセージリソルバ
     * @param stockPriceDataService
     *                                           株価データサービス
     * @param stockPriceTimeSeriesDailyService
     *                                           株価時系列日足サービス
     * @param stockPriceTimeSeriesWeeklyService
     *                                           株価時系列週足サービス
     * @param stockPriceTimeSeriesMonthlyService
     *                                           株価時系列月足サービス
     * @param stockPriceCalcValueService
     *                                           株価計算値サービス
     */
    public RegisterServiceImpl(final LogMessageResolver logMessageResolver,
        final StockPriceDataService stockPriceDataService,
        final StockPriceTimeSeriesDailyService stockPriceTimeSeriesDailyService,
        final StockPriceTimeSeriesWeeklyService stockPriceTimeSeriesWeeklyService,
        final StockPriceTimeSeriesMonthlyService stockPriceTimeSeriesMonthlyService,
        final StockPriceCalcValueService stockPriceCalcValueService) {
        this.logMessageResolver = logMessageResolver;
        this.stockPriceDataService = stockPriceDataService;
        this.stockPriceTimeSeriesDailyService = stockPriceTimeSeriesDailyService;
        this.stockPriceTimeSeriesWeeklyService = stockPriceTimeSeriesWeeklyService;
        this.stockPriceTimeSeriesMonthlyService = stockPriceTimeSeriesMonthlyService;
        this.stockPriceCalcValueService = stockPriceCalcValueService;
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

        final Map<Long, StockPriceDataMgtModel> stockPriceDataMgtModelList = this.stockPriceDataService
            .getStockPriceDataMgtMap();

        /* 株価データ管理モデルを登録する */
        for (final long key : stockPriceDataMgtModelList.keySet()) {
            final StockPriceDataMgtModel stockPriceDataMgtModel = stockPriceDataMgtModelList.get(key);
            this.registerStockPriceDataMgtModel(stockPriceDataMgtModel);
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

        try {
            final Stream<Path> streamPath = Files.walk(directoryPath).filter(path -> !Files.isDirectory(path));
            final Iterator<Path> itPath = streamPath.iterator();
            while (itPath.hasNext()) {
                final Path path = itPath.next();
                this.registerStockPriceDataOfFile(path);
            }
        } catch (final IOException e) {
            // TODO KenichiroArai 2021/05/29 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
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

        /* 株価データ管理モデルを取得する */
        final StockPriceDataMgtModel stockPriceDataMgtModel = this.stockPriceDataService
            .getStockPriceDataMgtModel(filePath);

        /* 株価データ管理モデルを登録する */
        this.registerStockPriceDataMgtModel(stockPriceDataMgtModel);

    }

    /**
     * 株価データ管理モデルを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    private void registerStockPriceDataMgtModel(final StockPriceDataMgtModel stockPriceDataMgtModel)
        throws TsstsDomainException {

        /* 株価時系列日足 */
        // 削除する
        this.stockPriceTimeSeriesDailyService.delete();
        // 株価時系列管理モデルを取得
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtDailyModel = this.stockPriceTimeSeriesDailyService
            .toStockPriceTimeSeriesMgtModel(stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesDailyService.register(stockPriceTimeSeriesMgtDailyModel);

        /* 株価時系列週足 */
        // 削除する
        this.stockPriceTimeSeriesWeeklyService.delete();
        // 株価時系列管理モデルを取得
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtWeeklyModel = this.stockPriceTimeSeriesWeeklyService
            .toStockPriceTimeSeriesMgtModel(stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesWeeklyService.register(stockPriceTimeSeriesMgtWeeklyModel);

        /* 株価時系列月足 */
        // 削除する
        this.stockPriceTimeSeriesMonthlyService.delete();
        // 株価時系列管理モデルを取得
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtMonthlyModel = this.stockPriceTimeSeriesMonthlyService
            .toStockPriceTimeSeriesMgtModel(stockPriceDataMgtModel);
        // 登録する
        this.stockPriceTimeSeriesMonthlyService.register(stockPriceTimeSeriesMgtMonthlyModel);

        /* 株価計算値日足 */
        // 削除する
        this.stockPriceCalcValueService.delete(stockPriceTimeSeriesMgtDailyModel.getSptsptId());
        // 計算する
        final StockPriceCalcValueMgtModel stockPriceCalcValueMgtDailyModel = this.stockPriceCalcValueService
            .calc(stockPriceTimeSeriesMgtDailyModel);
        // 登録する
        this.stockPriceCalcValueService.register(stockPriceCalcValueMgtDailyModel);

        /* 株価計算値週足 */
        // 削除する
        this.stockPriceCalcValueService.delete(stockPriceTimeSeriesMgtWeeklyModel.getSptsptId());
        // 計算する
        final StockPriceCalcValueMgtModel stockPriceCalcValueMgtWeeklyModel = this.stockPriceCalcValueService
            .calc(stockPriceTimeSeriesMgtWeeklyModel);
        // 登録する
        this.stockPriceCalcValueService.register(stockPriceCalcValueMgtWeeklyModel);

        /* 株価計算値月足 */
        // 削除する
        this.stockPriceCalcValueService.delete(stockPriceTimeSeriesMgtMonthlyModel.getSptsptId());
        // 計算する
        final StockPriceCalcValueMgtModel stockPriceCalcValueMgtMonthlyModel = this.stockPriceCalcValueService
            .calc(stockPriceTimeSeriesMgtMonthlyModel);
        // 登録する
        this.stockPriceCalcValueService.register(stockPriceCalcValueMgtMonthlyModel);
    }
}
