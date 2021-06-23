package kmg.im.stock.core.domain.logic;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

/**
 * ＭＡＣＤロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface MacdLogic {

    /**
     * ＭＡＣＤラインのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stList
     *               短期リスト
     * @param ltList
     *               長期リスト
     * @param lt
     *               長期
     * @return ＭＣＡＤラインのリスト
     */
    List<Supplier<BigDecimal>> getLineList(final List<Supplier<BigDecimal>> stList,
        final List<Supplier<BigDecimal>> ltList, final int lt);

    /**
     * ＭＡＣＤラインのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @param st
     *                 短期
     * @param lt
     *                 長期
     * @return ＭＣＡＤラインのリスト
     */
    List<Supplier<BigDecimal>> getLineList(List<Supplier<BigDecimal>> dataList, int st, int lt);

    /**
     * ＭＡＣＤシグナルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lineList
     *                   ＭＣＡＤラインのリスト
     * @param calcPeriod
     *                   データリストの計算期間
     * @return ＭＣＡＤシグナルのリスト
     */
    List<Supplier<BigDecimal>> getSignalList(List<Supplier<BigDecimal>> lineList, int calcPeriod);

    /**
     * ＭＡＣＤヒストグラムのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lineList
     *                   ＭＣＡＤラインのリスト
     * @param signalList
     *                   ＭＣＡＤシグナルのリスト
     * @return ＭＣＡＤヒストグラムのリスト
     */
    List<Supplier<BigDecimal>> getHistogramList(List<Supplier<BigDecimal>> lineList,
        List<Supplier<BigDecimal>> signalList);
}
