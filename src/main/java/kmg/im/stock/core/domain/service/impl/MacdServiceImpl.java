package kmg.im.stock.core.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.domain.logic.EmaLogic;
import kmg.im.stock.core.domain.logic.MacdLogic;
import kmg.im.stock.core.domain.service.MacdService;

/**
 * ＭＡＣＤサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
@Scope("prototype")
public class MacdServiceImpl implements MacdService {

    /** デフォルトの短期 */
    private static final int DEFAULT_ST = 12;

    /** デフォルトの長期 */
    private static final int DEFAULT_LT = 26;

    /** デフォルトのデータリストの計算期間 */
    private static final int DEFAULT_CALC_PERIOD = 9;

    /** データリスト */
    private final List<Supplier<BigDecimal>> dataList;

    /** 短期 */
    private int st;

    /** 長期 */
    private int lt;

    /** データリストの計算期間 */
    private int calcPeriod;

    /** 短期リスト */
    private final List<Supplier<BigDecimal>> stList;

    /** 長期リスト */
    private final List<Supplier<BigDecimal>> ltList;

    /** ＭＣＡＤラインのリスト */
    private final List<Supplier<BigDecimal>> lineList;

    /** ＭＡＣＤシグナルのリスト */
    private final List<Supplier<BigDecimal>> signalList;

    /** ＭＡＣＤヒストグラムのリスト */
    private final List<Supplier<BigDecimal>> histogramList;

    /** 指数移動平均ロジック */
    private final EmaLogic emaLogic;

    /** ＭＡＣＤロジック */
    private final MacdLogic macdLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param emaLogic
     *                  指数移動平均ロジック
     * @param macdLogic
     *                  ＭＡＣＤロジック
     */
    public MacdServiceImpl(final EmaLogic emaLogic, final MacdLogic macdLogic) {
        this.emaLogic = emaLogic;
        this.macdLogic = macdLogic;
        this.dataList = new ArrayList<>();
        this.stList = new ArrayList<>();
        this.ltList = new ArrayList<>();
        this.lineList = new ArrayList<>();
        this.signalList = new ArrayList<>();
        this.histogramList = new ArrayList<>();
    }

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
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final List<Supplier<BigDecimal>> dataList, final int st, final int lt,
        final int calcPeriod) {

        this.dataList.clear();
        this.stList.clear();
        this.ltList.clear();
        this.lineList.clear();
        this.signalList.clear();
        this.histogramList.clear();

        this.dataList.addAll(dataList);
        this.st = st;
        this.lt = lt;
        this.calcPeriod = calcPeriod;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final List<Supplier<BigDecimal>> dataList) {
        this.initialize(dataList, MacdServiceImpl.DEFAULT_ST, MacdServiceImpl.DEFAULT_LT,
            MacdServiceImpl.DEFAULT_CALC_PERIOD);
    }

    /**
     * データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getDataList() {
        final List<Supplier<BigDecimal>> result = this.dataList;
        return result;
    }

    /**
     * 短期を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 短期
     */
    @Override
    public int getSt() {
        final int result = this.st;
        return result;
    }

    /**
     * 長期を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 長期
     */
    @Override
    public int getLt() {
        final int result = this.lt;
        return result;
    }

    /**
     * データリストの計算期間を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリストの計算期間
     */
    @Override
    public int getCalcPeriod() {
        final int result = this.calcPeriod;
        return result;
    }

    /**
     * 短期リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 短期リスト
     */
    @Override
    public List<Supplier<BigDecimal>> getStList() {
        final List<Supplier<BigDecimal>> result = this.stList;
        return result;
    }

    /**
     * 長期リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 長期リスト
     */
    @Override
    public List<Supplier<BigDecimal>> getLtList() {
        final List<Supplier<BigDecimal>> result = this.ltList;
        return result;
    }

    /**
     * ＭＣＡＤラインのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＣＡＤラインのリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getLineList() {
        final List<Supplier<BigDecimal>> result = this.lineList;
        return result;
    }

    /**
     * ＭＡＣＤシグナルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＡＣＤシグナルのリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getSignalList() {
        final List<Supplier<BigDecimal>> result = this.signalList;
        return result;
    }

    /**
     * ＭＡＣＤヒストグラムのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ＭＡＣＤヒストグラムのリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getHistogramList() {
        final List<Supplier<BigDecimal>> result = this.histogramList;
        return result;
    }

    /**
     * ＭＡＣＤラインを計算する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clacLine() {

        this.stList.clear();
        this.ltList.clear();
        this.lineList.clear();
        this.signalList.clear();
        this.histogramList.clear();

        /* 短期ＥＭＡを求める */
        final List<Supplier<BigDecimal>> tmpStList = this.emaLogic.calc(this.dataList, this.st);
        this.stList.addAll(tmpStList);

        /* 長期ＥＭＡを求める */
        final List<Supplier<BigDecimal>> tmpLtList = this.emaLogic.calc(this.dataList, this.lt);
        this.ltList.addAll(tmpLtList);

        /* ラインを求める */
        final List<Supplier<BigDecimal>> tmpLineList = this.macdLogic.getLineList(this.dataList, this.st, this.lt);
        this.lineList.addAll(tmpLineList);
    }

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
    @Override
    public void clacSignal() {

        if (ListUtils.isEmpty(this.lineList)) {
            this.clacLine();
        } else {
            this.signalList.clear();
        }

        final List<Supplier<BigDecimal>> tmpList = this.macdLogic.getSignalList(this.lineList, this.calcPeriod);
        this.signalList.addAll(tmpList);
    }

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
    @Override
    public void clacHistogram() {

        if (ListUtils.isEmpty(this.signalList)) {
            this.clacSignal();
        } else {
            this.signalList.clear();
        }

        final List<Supplier<BigDecimal>> tmpList = this.macdLogic.getHistogramList(this.lineList, this.signalList);
        this.histogramList.addAll(tmpList);
    }
}
