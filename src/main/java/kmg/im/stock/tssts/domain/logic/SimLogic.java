package kmg.im.stock.tssts.domain.logic;

import java.util.Map;

import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;

/**
 * シミュレーションロジックのインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SimLogic {

    /**
     * 株価コードに該当する株価時系列のマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株価コード
     * @return 株価時系列
     */
    Map<Long, StockPriceTimeSeriesModel> getStockPriceTimeSeriesMap(long stockCode);

    /**
     * 第１のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    boolean hangOnFirstScreen(StockPriceTimeSeriesModel stockPriceTimeSeries);

    /**
     * 第２のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    boolean hangOnSecondScreen(StockPriceTimeSeriesModel stockPriceTimeSeries);

    /**
     * 第３のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    boolean hangOnThirdScreen(StockPriceTimeSeriesModel stockPriceTimeSeries);

}
