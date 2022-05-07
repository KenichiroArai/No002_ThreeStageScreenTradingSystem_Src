package kmg.im.stock.tssts.domain.logic.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.model.ImStkStockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.logic.TsstsSimLogic;

/**
 * 三段階スクリーン・トレーディング・システムシミュレーションロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSimLogicImpl implements TsstsSimLogic {

    /**
     * 第１のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkStockPriceTimeSeriesModel
     *                                       投資株式株価時系列モデル
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnFirstScreen(final ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel) {
        final boolean result = false;
        return result;
    }

    /**
     * 第２のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkStockPriceTimeSeriesModel
     *                                       投資株式株価時系列モデル
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnSecondScreen(final ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel) {
        final boolean result = false;
        return result;
    }

    /**
     * 第３のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkStockPriceTimeSeriesModel
     *                                       投資株式株価時系列モデル
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnThirdScreen(final ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel) {
        final boolean result = false;
        return result;
    }

}
