package kmg.im.stock.core.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import kmg.im.stock.core.domain.model.PowerIndexCalcModel;

/**
 * 勢力指数サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface PowerIndexService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     */
    void initialize(final List<PowerIndexCalcModel> dataList);

    /**
     * データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリスト
     */
    List<PowerIndexCalcModel> getDataList();

    /**
     * 計算する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void calc();

    /**
     * スムーズ化期間を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return スムーズ化期間
     */
    int getSmoothingPeriod();

    /**
     * 計算結果のリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算結果のリスト
     */
    List<Supplier<BigDecimal>> getCalcResultList();

    /**
     * スムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param smoothingPeriod
     *                        スムーズ化期間
     */
    void smoothing(final int smoothingPeriod);

    /**
     * デフォルトの短期のスムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void defaultStSmoothing();

    /**
     * デフォルトの長期のスムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void defaultLtSmoothing();

    /**
     * スムーズ化リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return スムーズ化リスト
     */
    List<Supplier<BigDecimal>> getSmoothingList();
}
