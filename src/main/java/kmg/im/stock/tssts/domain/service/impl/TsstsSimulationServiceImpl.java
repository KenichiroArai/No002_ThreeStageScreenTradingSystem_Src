package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.KmgListUtils;
import kmg.im.stock.core.domain.logic.ImStkSimLogic;
import kmg.im.stock.core.domain.model.ImStkPosModel;
import kmg.im.stock.core.domain.model.ImStkSptsptModel;
import kmg.im.stock.core.domain.model.ImStkStockBrandModel;
import kmg.im.stock.core.domain.model.ImStkStockPriceCalcValueModel;
import kmg.im.stock.core.domain.model.ImStkStockPriceTimeSeriesModel;
import kmg.im.stock.core.domain.service.ImStkSimulationService;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.resolver.ImStkLogMessageResolver;
import kmg.im.stock.core.infrastructure.types.ImStkLogMessageTypes;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.core.infrastructure.types.ImStkStockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.domain.logic.TsstsSimLogic;

/**
 * 三段階スクリーン・トレーディング・システムシミュレーションサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSimulationServiceImpl implements ImStkSimulationService {

    /** ポジションマップ */
    private final Map<Long, ImStkPosModel> posMap;

    /** 投資株式ログメッセージリゾルバ */
    private final ImStkLogMessageResolver imStkLogMessageResolver;

    /** 投資株式シミュレーションロジック */
    private final ImStkSimLogic imStkSimLogic;

    /** 三段階スクリーン・トレーディング・システムシミュレーションロジック */
    private final TsstsSimLogic tsstsSimLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkLogMessageResolver
     *                                投資株式ログメッセージリゾルバ
     * @param imStkSimLogic
     *                                投資株式シミュレーションロジック
     * @param tsstsSimLogic
     *                                三段階スクリーン・トレーディング・システムシミュレーションロジック
     */
    public TsstsSimulationServiceImpl(final ImStkLogMessageResolver imStkLogMessageResolver,
        final ImStkSimLogic imStkSimLogic, final TsstsSimLogic tsstsSimLogic) {
        this.posMap = new HashMap<>();
        this.imStkLogMessageResolver = imStkLogMessageResolver;
        this.imStkSimLogic = imStkSimLogic;
        this.tsstsSimLogic = tsstsSimLogic;
    }

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws ImStkDomainException
     *                              投資株式ドメイン例外
     */
    @Override
    public void simulate() throws ImStkDomainException {
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
     * @throws ImStkDomainException
     *                              投資株式ドメイン例外
     */
    @Override
    public void simulate(final long stockCode) throws ImStkDomainException {
        // TODO KenichiroArai 2021/05/25 実装中

        /* シミュレーションで使用するデータを取得する */

        // 株コードに該当する投資株式株銘柄モデルを取得する
        final ImStkStockBrandModel sbModel = this.imStkSimLogic.getImStkStockBrandModel(stockCode);
        if (sbModel == null) {

            // TODO KenichiroArai 2021/08/04 エラー対応
            final String errMsg = this.imStkLogMessageResolver.getMessage(ImStkLogMessageTypes.NONE);
            throw new ImStkDomainException(errMsg, ImStkLogMessageTypes.NONE);
        }

        // 週足の投資株式株価時系列期間のリストを取得する
        final ImStkSptsptModel sptsptWeeklyModel = sbModel.getImStkSptsptModel(ImStkPeriodTypeTypes.WEEKLY);
        if (sptsptWeeklyModel == null) {

            // TODO KenichiroArai 2022/01/01 例外処理
            final String errMsg = String.format("週足の投資株式株価時系列期間のリストの取得に失敗しました。空で取得されました。株コード=[%s]", stockCode);
            final ImStkLogMessageTypes logMsgTypes = ImStkLogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new ImStkDomainException(errMsg, logMsgTypes, logMsgArg);
        }

        // 週足の投資株式株価時系列モデルのリストを取得する
        final List<ImStkStockPriceTimeSeriesModel> stptsWeeklyModel = sptsptWeeklyModel.toAllSptsModelList();
        if (KmgListUtils.isEmpty(stptsWeeklyModel)) {

            // TODO KenichiroArai 2022/01/02 例外処理
            final String errMsg = "足の投資株式株価時系列モデルのリストを取得に失敗しました。空で取得されました。";
            final ImStkLogMessageTypes logMsgTypes = ImStkLogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new ImStkDomainException(errMsg, logMsgTypes, logMsgArg);
        }

        /* シミュレーションを行う */
        ImStkPosModel dummy = null;
        ImStkStockPriceTimeSeriesModel preSptsWeeklyModel = stptsWeeklyModel.get(0);
        ImStkStockPriceCalcValueModel preSpcvMcadhWeeklyModel = preSptsWeeklyModel
            .getImStkStockPriceCalcValueModel(ImStkStockPriceCalcValueTypeTypes.MCADH);
        for (int i = 1; i < stptsWeeklyModel.size(); i++) {

            final ImStkStockPriceTimeSeriesModel targetSptsWeeklyModel = stptsWeeklyModel.get(i);
            final ImStkStockPriceCalcValueModel targetSpcvMcadhWeeklyModel = targetSptsWeeklyModel
                .getImStkStockPriceCalcValueModel(ImStkStockPriceCalcValueTypeTypes.MCADH);

            try {

                // TODO KenichiroArai 2022/01/08 仕掛けか手仕舞いかの判定。シミュレーショントランザクションモデルの有無で判断する。
                if (dummy == null) {
                    /* 仕掛り */

                    /* 第一のスクリーン */
                    // 直近の週足２本のＭＡＣＤヒストグラムが変化なしまたは下向きか
                    if (targetSpcvMcadhWeeklyModel.get().compareTo(preSpcvMcadhWeeklyModel.get()) <= 0) {
                        // 変化なしまたは下向きである場合

                        // 第一のスクリーンを突破しない
                        continue;
                    }

                    /* 第二のスクリーン */
                    final ImStkStockPriceCalcValueModel targetSpcvPi2emaWeeklyModel = targetSptsWeeklyModel
                        .getImStkStockPriceCalcValueModel(ImStkStockPriceCalcValueTypeTypes.PI2EMA);
                    // 勢力指数２ＥＭＡが中心線よりも上以上か
                    if (targetSpcvPi2emaWeeklyModel.get().compareTo(BigDecimal.ZERO) >= 0) {
                        // 上以上である場合

                        // 第二のスクリーンを突破しない
                        continue;
                    }
                    final ImStkStockPriceCalcValueModel targetSpcvLpil3pModel = targetSptsWeeklyModel
                        .getImStkStockPriceCalcValueModel(
                            ImStkStockPriceCalcValueTypeTypes.LOWEST_PRICE_IN_LAST3_PERIODS);
                    // 安値が過去３期間の最安値より安いか
                    if (targetSptsWeeklyModel.getLp().compareTo(targetSpcvLpil3pModel.get()) < 0) {
                        // 安い場合

                        // 第二のスクリーンを突破しない
                        continue;
                    }

                    /* 第三のスクリーン */
                    // 高値が前日の高値から変化なしまたは安いか
                    if (targetSptsWeeklyModel.getHp().compareTo(preSptsWeeklyModel.getHp()) <= 0) {
                        // 変化なしまたは安い場合

                        // 第三のスクリーンを突破しない
                        continue;
                    }

                    // TODO KenichiroArai 2022/01/04 ログ
                    System.out.println(String.format(
                        "株価時系列ID=[%d], 株価系列名称=[%s], 番号=[%d], 期間開始日=[%s], 期間終了日=[%s], 始値=[%f], 高値=[%f], 安値=[%f], 終値=[%f], 出来高=[%d], 一つ前のＭＣＡＤヒストグラム=[%f], ＭＣＡＤヒストグラム=[%f], 勢力指数２ＥＭＡ=[%f], 過去３期間の最安値=[%f], 一つ前の高値=[%f]",
                        targetSptsWeeklyModel.getId(), targetSptsWeeklyModel.getName(), targetSptsWeeklyModel.getNo(),
                        targetSptsWeeklyModel.getPeriodStartDate(), targetSptsWeeklyModel.getPeriodEndDate(),
                        targetSptsWeeklyModel.getOp(), targetSptsWeeklyModel.getHp(), targetSptsWeeklyModel.getLp(),
                        targetSptsWeeklyModel.getCp(), targetSptsWeeklyModel.getVolume(), preSpcvMcadhWeeklyModel.get(),
                        targetSpcvMcadhWeeklyModel.get(), targetSpcvPi2emaWeeklyModel.get(),
                        targetSpcvLpil3pModel.get(), preSptsWeeklyModel.getHp()));

                    /* 買う */
                    // TODO KenichiroArai 2022/01/08 買う処理未実装。どの条件の時に買うのか？どのような情報を保持しておく必要があるか？

                    dummy = new ImStkPosModel();

                } else {
                    /* 手仕舞い */

                    // ストップロス

                    // 利確

                    dummy = null;

                }

            } finally {
                preSptsWeeklyModel = stptsWeeklyModel.get(i);
                preSpcvMcadhWeeklyModel = preSptsWeeklyModel
                    .getImStkStockPriceCalcValueModel(ImStkStockPriceCalcValueTypeTypes.MCADH);
            }
        }
    }

    /**
     * 投資株式ポジションモデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 投資株式ポジションモデル
     */
    @SuppressWarnings("static-method")
    private ImStkPosModel getImStkPosModel() {
        final ImStkPosModel result = new ImStkPosModel();
        return result;
    }

}
