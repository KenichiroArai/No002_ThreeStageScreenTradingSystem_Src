package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.impl.SimpleSptsMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesService;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesServiceImpl implements StockPriceTimeSeriesService {

    /**
     * 株価銘柄コードと期間の種類IDを基にシンプルモデルを返す検索を行う<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                        株銘柄コード
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return シンプル株価時系列管理モデル
     */
    @Override
    public SimpleSptsMgtModel findSimpleBySbcAndPti(final long stockBrandCode, final PeriodTypeTypes periodTypeTypes) {
        final SimpleSptsMgtModel result = new SimpleSptsMgtModelImpl();
        return result;
    }

}
