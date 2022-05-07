package kmg.im.stock.tssts.domain.logic;

import kmg.im.stock.core.domain.model.ImStkStockPriceTimeSeriesModel;

/**
 * 三段階スクリーン・トレーディング・システムシミュレーションロジックのインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsSimLogic {

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
    boolean hangOnFirstScreen(ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel);

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
    boolean hangOnSecondScreen(ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel);

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
    boolean hangOnThirdScreen(ImStkStockPriceTimeSeriesModel imStkStockPriceTimeSeriesModel);

}
