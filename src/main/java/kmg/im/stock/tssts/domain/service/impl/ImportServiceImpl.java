package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.domain.logic.ImportLogic;
import kmg.im.stock.tssts.domain.service.ImportService;
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

    /** インポートロジック */
    @Autowired
    private ImportLogic importLogicLogic;

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
        final List<Path> stockPriceStockStoragePathList = this.importLogicLogic.getStockPriceStockStoragePathList();

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
        final List<Path> stockPriceStockStoragePathList = this.importLogicLogic.getStockPriceStockStoragePathList();

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
        final long stockBrandId = this.importLogicLogic.getStockBrandId(filePath);

        /* 株価データのリストを検索する */
        final List<StockPriceDataDto> stockPriceDataDtoList = this.importLogicLogic.getStockPriceDataDtoList(filePath);

        /* 日足の株価時系列を登録する */
        this.importLogicLogic.registerStockPriceDataOfDaily(stockBrandId, stockPriceDataDtoList);

        /* 週足の株価時系列を登録する */
        this.importLogicLogic.registerStockPriceDataOfWeekly(stockBrandId, stockPriceDataDtoList);

        /* 月足の株価時系列を登録する */
        this.importLogicLogic.registerStockPriceDataOfMonthly(stockBrandId, stockPriceDataDtoList);
    }

}
