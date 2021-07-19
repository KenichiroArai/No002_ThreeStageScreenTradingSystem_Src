package kmg.im.stock.tssts.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.service.RegisterService;
import kmg.im.stock.tssts.domain.service.StockPriceDataService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceTimeSeriesService;
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

    /** アプリケーションコンテキスト */
    private final ApplicationContext context;

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** 株価データサービス */
    private final StockPriceDataService stockPriceDataService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                              アプリケーションコンテキスト
     * @param logMessageResolver
     *                              ログメッセージリソルバ
     * @param stockPriceDataService
     *                              株価データサービス
     */
    public RegisterServiceImpl(final ApplicationContext context, final LogMessageResolver logMessageResolver,
        final StockPriceDataService stockPriceDataService) {
        this.context = context;
        this.logMessageResolver = logMessageResolver;
        this.stockPriceDataService = stockPriceDataService;

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

        // 株価時系列の登録
        final TsstsStockPriceTimeSeriesService tsstsStockPriceTimeSeriesService = this.context
            .getBean(TsstsStockPriceTimeSeriesService.class);
        tsstsStockPriceTimeSeriesService.initialize(stockPriceDataMgtModel);
        tsstsStockPriceTimeSeriesService.register();
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtDailyModel = tsstsStockPriceTimeSeriesService
            .getStockPriceTimeSeriesMgtDailyModel();
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtWeeklyModel = tsstsStockPriceTimeSeriesService
            .getStockPriceTimeSeriesMgtWeeklyModel();
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtMonthlyModel = tsstsStockPriceTimeSeriesService
            .getStockPriceTimeSeriesMgtMonthlyModel();

        // 三段階スクリーン・トレーディング・システム株価計算値サービス
        final TsstsStockPriceCalcValueService tsstsStockPriceCalcValueService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        tsstsStockPriceCalcValueService.initialize(stockPriceTimeSeriesMgtDailyModel,
            stockPriceTimeSeriesMgtWeeklyModel, stockPriceTimeSeriesMgtMonthlyModel);
        tsstsStockPriceCalcValueService.register();
    }
}