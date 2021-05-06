package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.data.dao.StockBrandDao;
import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.logic.TsstsLogic;

/**
 * 三段階スクリーン・トレーディング・システムロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsLogicImpl implements TsstsLogic {

    /** 株価データＤＡＯ */
    @Autowired
    private StockPriceDataDao stockPriceDataDao;

    /** 株銘柄ＤＡＯ */
    @Autowired
    private StockBrandDao stockBrandDao;

    /** 株価時系列ＤＡＯ */
    @Autowired
    private StockPriceTimeSeriesDao stockPriceTimeSeriesDao;

    /**
     * 株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void registerStockPriceData() {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataDao.findAllStockPriceStockStoragePath();

        /* 銘柄ごとの株価データを株価時系列に挿入する */
        for (final Path stockPriceStockStoragePath : stockPriceStockStoragePathList) {

            // コードを取得する
            long code = 0L;
            try {
                // TODO KenichiroArai 2021/05/07 ユーティリティ化する
                final String fullFileName = stockPriceStockStoragePath.getFileName().toString();
                final String fileName     = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
                code = Integer.parseInt(fileName);
            } catch (final NumberFormatException e) {
                // TODO KenichiroArai 2021/05/07 例外処理

                e.printStackTrace();
                continue;
            }

            // 株価銘柄IDを取得する
            final long stockBrandId = this.stockBrandDao.getId(code, LocalDate.now());

            // 株価時系列を取得する
            final List<StockPriceTimeSeriesDto> stockPriceTimeSeriesDtoList = this.stockPriceDataDao
                .findAllStockPriceTimeSeriesDtoList(stockPriceStockStoragePath);

            // 株価銘柄IDを設定する
            stockPriceTimeSeriesDtoList.forEach(dto -> dto.setStockBrandId(stockBrandId));

            // 株価時列を登録する
            for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesDtoList) {
                this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
            }
        }

    }
}
