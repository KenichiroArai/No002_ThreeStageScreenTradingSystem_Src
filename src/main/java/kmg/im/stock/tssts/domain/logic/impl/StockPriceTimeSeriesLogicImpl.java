package kmg.im.stock.tssts.domain.logic.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesMgtDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesMgtDtoImpl;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesLogicImpl implements StockPriceTimeSeriesLogic {

    /** 株価時系列ＤＡＯ */
    private final StockPriceTimeSeriesDao stockPriceTimeSeriesDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesDao
     *                                株価時系列ＤＡＯ
     */
    public StockPriceTimeSeriesLogicImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
    }

    /**
     * 削除する<br>
     * <p>
     * 期間の種類に該当するデータを削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param typeOfPeriodTypes
     *                          期間の種類の種類
     */
    @Override
    public void delete(final TypeOfPeriodTypes typeOfPeriodTypes) {
        this.stockPriceTimeSeriesDao.delete(typeOfPeriodTypes);
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     */
    @Override
    public void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) {

        final StockPriceTimeSeriesMgtDto stockPriceTimeSeriesMgtDto = new StockPriceTimeSeriesMgtDtoImpl();
        for (final StockPriceTimeSeriesModel stockPriceTimeSeriesModel : stockPriceTimeSeriesMgtModel.getDataList()) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeries = new StockPriceTimeSeriesDtoImpl();
            BeanUtils.copyProperties(stockPriceTimeSeriesModel, stockPriceTimeSeries);

            // 株価銘柄IDを設定する
            stockPriceTimeSeries.setStockBrandId(stockPriceTimeSeriesMgtModel.getStockBrandId());

            stockPriceTimeSeriesMgtDto.addData(stockPriceTimeSeries);
        }
        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesMgtDto.getDataList()) {
            this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
        }
    }
}
