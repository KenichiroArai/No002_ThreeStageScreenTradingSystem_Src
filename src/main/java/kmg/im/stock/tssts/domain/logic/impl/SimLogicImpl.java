package kmg.im.stock.tssts.domain.logic.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.SimLogic;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;

/**
 * シミュレーションロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SimLogicImpl implements SimLogic {

    /**
     * 株価コードに該当する株価時系列管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株価コード
     * @return 株価時系列管理
     */
    @Override
    public StockPriceTimeSeriesMgtModel getStockPriceTimeSeriesMgtModel(final long stockCode) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

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
    @Override
    public boolean hangOnFirstScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

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
    @Override
    public boolean hangOnSecondScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

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
    @Override
    public boolean hangOnThirdScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO 自動生成されたメソッド・スタブ
        return false;
    }

}
