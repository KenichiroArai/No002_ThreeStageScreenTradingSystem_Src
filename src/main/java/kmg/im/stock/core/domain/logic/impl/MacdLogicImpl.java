package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.logic.EmaLogic;
import kmg.im.stock.core.domain.logic.MacdLogic;

/**
 * ＭＡＣＤロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class MacdLogicImpl implements MacdLogic {

    /** 指数移動平均ロジック */
    private final EmaLogic emaLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param emaLogic
     *                 指数移動平均ロジック
     */
    public MacdLogicImpl(final EmaLogic emaLogic) {
        this.emaLogic = emaLogic;
    }

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
    @Override
    public List<Supplier<BigDecimal>> getLineList(final List<Supplier<BigDecimal>> stList,
        final List<Supplier<BigDecimal>> ltList, final int lt) {

        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        /* 長期の計算が出来るまではゼロを設定する。 */
        for (int i = 0; i < (lt - 1); i++) {
            result.add(() -> KmgDecimal.CALC_ZERO);
        }

        /* 短期ＥＭＡ―長期ＥＭＡを計算し結果に追加する */
        for (int i = lt - 1; i < ltList.size(); i++) {
            final BigDecimal diff = stList.get(i).get().subtract(ltList.get(i).get());
            final BigDecimal addDiff = KmgDecimal.setCalcScale(diff);
            result.add(() -> addDiff);
        }

        return result;
    }

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
    @Override
    public List<Supplier<BigDecimal>> getLineList(final List<Supplier<BigDecimal>> dataList, final int st,
        final int lt) {

        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        /* 短期ＥＭＡを求める */
        final List<Supplier<BigDecimal>> stList = this.emaLogic.calc(dataList, st);

        /* 長期ＥＭＡを求める */
        final List<Supplier<BigDecimal>> ltList = this.emaLogic.calc(dataList, lt);

        /* ラインを求める */
        final List<Supplier<BigDecimal>> lineList = this.getLineList(stList, ltList, lt);
        // 結果に詰める
        result.addAll(lineList);

        return result;
    }

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
    @Override
    public List<Supplier<BigDecimal>> getSignalList(final List<Supplier<BigDecimal>> lineList, final int calcPeriod) {
        final List<Supplier<BigDecimal>> result = this.emaLogic.calc(lineList, calcPeriod);
        return result;
    }

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
    @Override
    public List<Supplier<BigDecimal>> getHistogramList(final List<Supplier<BigDecimal>> lineList,
        final List<Supplier<BigDecimal>> signalList) {
        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        /* ライン―シグナルを計算し結果に追加する */
        for (int i = 0; i < lineList.size(); i++) {
            final BigDecimal line = lineList.get(i).get();
            final BigDecimal signal = signalList.get(i).get();
            final BigDecimal diff = line.subtract(signal);
            final BigDecimal addDiff = KmgDecimal.setCalcScale(diff);
            result.add(() -> addDiff);
        }
        return result;
    }

}
