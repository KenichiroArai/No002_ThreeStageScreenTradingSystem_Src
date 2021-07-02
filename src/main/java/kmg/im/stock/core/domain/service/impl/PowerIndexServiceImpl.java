package kmg.im.stock.core.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.core.domain.service.EmaService;
import kmg.im.stock.core.domain.service.PowerIndexService;

/**
 * 勢力指数サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
@Scope("prototype")
public class PowerIndexServiceImpl implements PowerIndexService {

    /** デフォルトのスムーズ化の短期 */
    public static final int DEFAULT_SMOOTHING_ST = 2;

    /** デフォルトのスムーズ化の長期 */
    public static final int DEFAULT_SMOOTHING_LT = 13;

    /** アプリケーションコンテキスト */
    private final ApplicationContext context;

    /** スムーズ化期間 */
    private int smoothingPeriod;

    /** データリスト */
    private final List<PowerIndexCalcModel> dataList;

    /** 計算結果のリスト */
    private final List<Supplier<BigDecimal>> calcResultList;

    /** スムーズ化リスト */
    private final List<Supplier<BigDecimal>> smoothingList;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                アプリケーションコンテキスト
     */
    public PowerIndexServiceImpl(final ApplicationContext context) {
        this.context = context;
        this.smoothingPeriod = PowerIndexServiceImpl.DEFAULT_SMOOTHING_ST;
        this.dataList = new ArrayList<>();
        this.calcResultList = new ArrayList<>();
        this.smoothingList = new ArrayList<>();
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
    public void initialize(final List<PowerIndexCalcModel> dataList) {

        this.dataList.clear();

        this.dataList.addAll(dataList);
        this.calcResultList.clear();
        this.smoothingList.clear();
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
    public List<PowerIndexCalcModel> getDataList() {
        final List<PowerIndexCalcModel> result = this.dataList;
        return result;
    }

    /**
     * 計算する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void calc() {

        /* 前提チェック */
        // データリストが空か
        if (this.dataList.isEmpty()) {
            // 空の場合

            // 計算元のデータがないため、計算処理をしない
            return;
        }

        /* 準備処理 */
        this.calcResultList.clear();

        /* 一つ目は計算できないため、データを0にする */
        this.calcResultList.add(() -> KmgDecimal.CALC_ZERO);

        /* 勢力指数を計算する */
        for (int i = 1; i < this.dataList.size(); i++) {
            final long todayVolume = this.dataList.get(i).getVolume(); // 今日の出来高
            final BigDecimal dayBeforeCp = this.dataList.get(i - 1).getCp(); // 前日の終値
            final BigDecimal todayCp = this.dataList.get(i).getCp(); // 今日の終値

            // 勢力指数＝今日の出来高×（今日の終値―前日の終値）
            final BigDecimal powerIndex = new BigDecimal(todayVolume).multiply(todayCp.subtract(dayBeforeCp));

            this.calcResultList.add(() -> powerIndex);
        }

    }

    /**
     * スムーズ化期間を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return スムーズ化期間
     */
    @Override
    public int getSmoothingPeriod() {
        final int result = this.smoothingPeriod;
        return result;
    }

    /**
     * 計算結果のリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算結果のリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getCalcResultList() {
        final List<Supplier<BigDecimal>> result = this.calcResultList;
        return result;
    }

    /**
     * スムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param smoothingPeriod
     *                        スムーズ化期間
     */
    @Override
    @SuppressWarnings("hiding")
    public void smoothing(final int smoothingPeriod) {

        /* パラメータの設定 */
        this.smoothingPeriod = smoothingPeriod;

        /* 前提チェック */
        // データリストが空か
        if (this.calcResultList.isEmpty()) {
            this.calc();
        }

        /* 準備処理 */
        this.smoothingList.clear();

        /* スムーズ化 */
        final EmaService emaService = this.context.getBean(EmaService.class);
        emaService.initialize(this.calcResultList, this.smoothingPeriod);
        emaService.calc();
        final List<Supplier<BigDecimal>> smoothingTmpList = emaService.getClacResultList();

        /* スムーズ化の設定 */
        this.smoothingList.addAll(smoothingTmpList);
    }

    /**
     * デフォルトの短期のスムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void defaultStSmoothing() {

        this.smoothing(PowerIndexServiceImpl.DEFAULT_SMOOTHING_ST);
    }

    /**
     * デフォルトの長期のスムーズ化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void defaultLtSmoothing() {

        this.smoothing(PowerIndexServiceImpl.DEFAULT_SMOOTHING_LT);
    }

    /**
     * スムーズ化リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return スムーズ化リスト
     */
    @Override
    public List<Supplier<BigDecimal>> getSmoothingList() {
        final List<Supplier<BigDecimal>> result = this.smoothingList;
        return result;
    }

}
