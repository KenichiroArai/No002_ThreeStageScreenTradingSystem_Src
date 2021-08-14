package kmg.im.stock.core.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

/**
 * ＭＡＣＤサービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface MacdService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                   データリスト
     * @param st
     *                   短期
     * @param lt
     *                   長期
     * @param calcPeriod
     *                   データリストの計算期間
     */
    void initialize(final List<Supplier<BigDecimal>> dataList, final int st, final int lt, final int calcPeriod);

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     */
    void initialize(final List<Supplier<BigDecimal>> dataList);

    /**
     * データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリスト
     */
    List<Supplier<BigDecimal>> getDataList();

    /**
     * 短期を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 短期
     */
    int getSt();

    /**
     * 長期を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 長期
     */
    int getLt();

    /**
     * データリストの計算期間を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリストの計算期間
     */
    int getCalcPeriod();

    /**
     * 短期リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 短期リスト
     */
    List<Supplier<BigDecimal>> getStList();

    /**
     * 長期リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 長期リスト
     */
    List<Supplier<BigDecimal>> getLtList();

    /**
     * ＭＣＡＤラインのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＣＡＤラインのリスト
     */
    List<Supplier<BigDecimal>> getLineList();

    /**
     * ＭＡＣＤシグナルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＡＣＤシグナルのリスト
     */
    List<Supplier<BigDecimal>> getSignalList();

    /**
     * ＭＡＣＤヒストグラムのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＡＣＤヒストグラムのリスト
     */
    List<Supplier<BigDecimal>> getHistogramList();

    /**
     * ＭＡＣＤラインを計算する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    // TODO KenichiroArai 2021/08/11 clac→calcのスペル間違え
    void clacLine();

    /**
     * ＭＡＣＤシグナルを計算する<br>
     * <p>
     * ＭＡＣＤラインの計算が行われていない場合は、ＭＣＡＤラインの計算を行う。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clacSignal();

    /**
     * ＭＡＣＤヒストグラムを計算する<br>
     * <p>
     * ＭＡＣＤシグナルの計算が行われていない場合は、ＭＣＡＤシグナルの計算を行う。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clacHistogram();

}
