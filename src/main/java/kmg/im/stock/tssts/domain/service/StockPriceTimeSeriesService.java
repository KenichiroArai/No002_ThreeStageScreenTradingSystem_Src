package kmg.im.stock.tssts.domain.service;

import java.util.List;

import kmg.im.stock.tssts.domain.model.StockPriceDataModel;

/**
 * 株価時系列サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceTimeSeriesService {

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                                株価銘柄ID
     * @param stockPriceDataModelList
     *                                株価データのリスト
     */
    void register(final long stockBrandId, final List<StockPriceDataModel> stockPriceDataModelList);
}
