package kmg.im.stock.tssts.domain.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.SimLogic;
import kmg.im.stock.tssts.domain.model.PosModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.service.SimulationService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

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

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** シミュレーションロジック */
    private final SimLogic simLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                           ログメッセージリソルバ
     * @param simLogic
     *                           シミュレーションロジック
     */
    public SimulationServiceImpl(final LogMessageResolver logMessageResolver, final SimLogic simLogic) {
        this.posMap = new HashMap<>();
        this.logMessageResolver = logMessageResolver;
        this.simLogic = simLogic;
    }

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void simulate() throws TsstsDomainException {
        // TODO KenichiroArai 2021/08/04 実装中
        this.simulate(3053);
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
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void simulate(final long stockCode) throws TsstsDomainException {
        // TODO KenichiroArai 2021/05/25 実装中

        /* 株価時系列管理モデルを取得する */
        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel = this.simLogic
            .getStockPriceTimeSeriesMgtModel(stockCode);
        if (stockPriceTimeSeriesMgtModel == null) {

            // TODO KenichiroArai 2021/08/04 エラー対応
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE);
        }

        for (final StockPriceTimeSeriesModel stockPriceTimeSeriesModel : stockPriceTimeSeriesMgtModel.getDataList()) {

            // ポジションを取得する
            final PosModel pos = this.getPosModel();
            if (pos == null) {
                // ポジションがない場合

                // 第１のスクリーンに掛ける
                final boolean firstFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeriesModel);
                if (!firstFlg) {
                    continue;
                }

                // 第２のスクリーンに掛ける
                final boolean secondFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeriesModel);
                if (!secondFlg) {
                    continue;
                }

                // 第３のスクリーンに掛ける
                final boolean thirdFlg = this.simLogic.hangOnFirstScreen(stockPriceTimeSeriesModel);
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

    /**
     * ポジションモデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ポジションモデル
     */
    @SuppressWarnings("static-method")
    private PosModel getPosModel() {
        final PosModel result = new PosModel();
        return result;
    }

}
