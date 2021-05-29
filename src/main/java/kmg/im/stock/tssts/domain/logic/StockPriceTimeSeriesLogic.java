package kmg.im.stock.tssts.domain.logic;

import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesLogic {

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
    void delete(TypeOfPeriodTypes typeOfPeriodTypes);

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     */
    void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel);
}
