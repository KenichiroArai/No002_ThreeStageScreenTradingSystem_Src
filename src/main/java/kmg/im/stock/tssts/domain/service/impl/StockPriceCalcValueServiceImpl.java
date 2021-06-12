package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.service.StockPriceCalcValueService;

/**
 * 株価計算値サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceCalcValueServiceImpl implements StockPriceCalcValueService {
    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void delete() {
        // TODO KenichiroArai 2021/06/04 未実装

    }

    /**
     * 株価時系列管理モデルにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     * @return 株価時系列管理モデル
     */
    @Override
    public StockPriceCalcValueMgtModel calc(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) {
        // TODO KenichiroArai 2021/06/04 未実装
        return null;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceCalcValueMgtModel
     *                                    株価計算値管理モデル
     */
    @Override
    public void register(final StockPriceCalcValueMgtModel stockPriceCalcValueMgtModel) {
        // TODO KenichiroArai 2021/06/04 未実装
    }

}
