package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.service.ImportService;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.domain.service.StockPriceDataService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesDailyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesMonthlyService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesWeeklyService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * インポートサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class ImportServiceImpl implements ImportService {

    /** 株価データサービス */
    private final StockPriceDataService stockPriceDataService;

    /** 株銘柄サービス */
    private final StockBrandService stockBrandService;

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
     * @param stockPriceDataService
     *                                           株価データサービス
     * @param stockBrandService
     *                                           株銘柄サービス
     * @param stockPriceTimeSeriesDailyService
     *                                           株価時系列日足サービス
     * @param stockPriceTimeSeriesWeeklyService
     *                                           株価時系列週足サービス
     * @param stockPriceTimeSeriesMonthlyService
     *                                           株価時系列月足サービス
     */
    public ImportServiceImpl(final StockPriceDataService stockPriceDataService,
        final StockBrandService stockBrandService,
        final StockPriceTimeSeriesDailyService stockPriceTimeSeriesDailyService,
        final StockPriceTimeSeriesWeeklyService stockPriceTimeSeriesWeeklyService,
        final StockPriceTimeSeriesMonthlyService stockPriceTimeSeriesMonthlyService) {
        this.stockPriceDataService = stockPriceDataService;
        this.stockBrandService = stockBrandService;
        this.stockPriceTimeSeriesDailyService = stockPriceTimeSeriesDailyService;
        this.stockPriceTimeSeriesWeeklyService = stockPriceTimeSeriesWeeklyService;
        this.stockPriceTimeSeriesMonthlyService = stockPriceTimeSeriesMonthlyService;
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
        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataService
            .getStockPriceStockStoragePathList();

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
        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataService
            .getStockPriceStockStoragePathList();

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
        final long stockBrandCode = this.stockBrandService.getStockBrandCode(filePath);
        final long stockBrandId = this.stockBrandService.getStockBrandId(stockBrandCode);

        /* 株価データのリストを検索する */
        final List<StockPriceDataModel> stockPriceTimeSeriesModelList = this.stockPriceDataService
            .getStockPriceDataList(filePath);

        /* 株価時系列日足 */
        // 登録する
        this.stockPriceTimeSeriesDailyService.register(stockBrandId, stockPriceTimeSeriesModelList);

        /* 株価時系列週足 */
        // 登録する
        this.stockPriceTimeSeriesWeeklyService.register(stockBrandId, stockPriceTimeSeriesModelList);

        /* 株価時系列月足 */
        // 登録する
        this.stockPriceTimeSeriesMonthlyService.register(stockBrandId, stockPriceTimeSeriesModelList);
    }
}
