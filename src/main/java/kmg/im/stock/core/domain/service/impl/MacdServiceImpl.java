package kmg.im.stock.core.domain.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

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
public class MacdServiceImpl implements MacdService {

    /** データリスト */
    private List<Supplier<BigDecimal>> dataList;

    /** 短期 */
    private int st;

    /** 長期 */
    private int lt;

    /** データリストの計算期間 */
    private int calcPeriod;

    /** 短期リスト */
    private List<Supplier<BigDecimal>> stList;

    /** 長期リスト */
    private List<Supplier<BigDecimal>> ltList;

    /** ＭＣＡＤラインのリスト */
    private List<Supplier<BigDecimal>> lineList;

    /** ＭＡＣＤシグナルのリスト */
    private List<Supplier<BigDecimal>> signalList;

    /** ＭＡＣＤヒストグラムのリスト */
    private List<Supplier<BigDecimal>> histogramList;

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
        this.dataList = dataList;
        this.st = st;
        this.lt = lt;
        this.calcPeriod = calcPeriod;
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

        /* 短期ＥＭＡを求める */
        this.stList = this.emaLogic.calc(this.dataList, this.st);

        /* 長期ＥＭＡを求める */
        this.ltList = this.emaLogic.calc(this.dataList, this.lt);

        /* ラインを求める */
        this.lineList = this.macdLogic.getLineList(this.dataList, this.st, this.lt);
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
        }

        this.signalList = this.macdLogic.getSignalList(this.lineList, this.calcPeriod);
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
        }

        this.histogramList = this.macdLogic.getHistogramList(this.lineList, this.signalList);
    }
}
