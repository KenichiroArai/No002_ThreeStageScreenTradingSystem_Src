package kmg.im.stock.tssts.domain.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.SimLogic;
import kmg.im.stock.tssts.domain.model.PosModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.service.SimulationService;

/**
 * シミュレーションサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SimulationServiceImpl implements SimulationService {

    /** ポジションマップ */
    private final Map<Long, PosModel> posMap;

    /** シミュレーションロジック */
    private final SimLogic simLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param simLogic
     *                 シミュレーションロジック
     */
    public SimulationServiceImpl(final SimLogic simLogic) {
        this.posMap = new HashMap<>();
        this.simLogic = simLogic;
    }

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void simulate() {
        // TODO KenichiroArai 2021/05/25 未実装
    }

    /**
     * シミュレーションする<br>
     * <p>
     * 指定した株コードのシミュレーションする
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    @Override
    public void simulate(final long stockCode) {
        // TODO KenichiroArai 2021/05/25 実装中

        /* 株価時系列のマップを取得する */
        final Map<Long, StockPriceTimeSeriesModel> stockPriceMgtModelMap = this.simLogic
            .getStockPriceTimeSeriesMap(stockCode);
        // コードのセットを取得する
        final Set<Long> codeSet = stockPriceMgtModelMap.keySet();

        /* コードごとにシミュレーションする */
        for (final Long code : codeSet) {

            final StockPriceTimeSeriesModel stockPriceTimeSeries = stockPriceMgtModelMap.get(code);

            // ポジションを取得する
            final PosModel pos = this.posMap.get(code);
            if (pos == null) {
                // ポジションがない場合

                // 第１のスクリーンに掛ける
                final boolean firstFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!firstFlg) {
                    continue;
                }

                // 第２のスクリーンに掛ける
                final boolean secondFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!secondFlg) {
                    continue;
                }

                // 第３のスクリーンに掛ける
                final boolean thirdFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeries);
                if (!thirdFlg) {
                    continue;
                }

                // 買い情報を明細に登録する
                System.out.println("買い情報を明細に登録する");
            } else {
                // ポジションがある場合

                // ロスストップに引っかかるか
                // 引っかかる場合：
                // 損切り情報を明細に登録する
                // 利確の条件か
                // 条件に一致する場合：
                // 利確情報を明細に登録する
            }
        }
    }

}
