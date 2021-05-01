package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
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

        final List<Path> stockPriceDataPathList = this.stockPriceDataDao.findAllStockPriceDataPath();
        stockPriceDataPathList.forEach(path -> this.stockPriceDataDao.findAllStockPriceTimeSeriesDtoList(path)
            .forEach(dto -> this.stockPriceTimeSeriesDao.insert(dto)));

    }
}
