package kmg.im.stock.core.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.core.domain.service.PowerIndexService;

/**
 * 勢力指数サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class PowerIndexServiceImpl implements PowerIndexService {

    /** データリスト */
    private final List<PowerIndexCalcModel> dataList;

    /** 計算結果のリスト */
    private final List<Supplier<BigDecimal>> clacResultList;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public PowerIndexServiceImpl() {
        this.dataList = new ArrayList<>();
        this.clacResultList = new ArrayList<>();
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
        this.clacResultList.clear();
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

        if (this.dataList.isEmpty()) {
            return;
        }

        this.clacResultList.clear();

        /* 一つ目は計算できないため、データを0にする */
        this.clacResultList.add(() -> KmgDecimal.CALC_ZERO);

        /* 勢力指数を計算する */
        for (int i = 1; i < this.dataList.size(); i++) {
            final long todayVolume = this.dataList.get(i).getVolume(); // 今日の出来高
            final BigDecimal dayBeforeCp = this.dataList.get(i - 1).getCp(); // 前日の終値
            final BigDecimal todayCp = this.dataList.get(i).getCp(); // 今日の終値

            // 勢力指数＝今日の出来高×（今日の終値―前日の終値）
            final BigDecimal powerIndex = new BigDecimal(todayVolume).multiply(todayCp.subtract(dayBeforeCp));

            this.clacResultList.add(() -> powerIndex);
        }

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
    public List<Supplier<BigDecimal>> getClacResultList() {
        final List<Supplier<BigDecimal>> result = this.clacResultList;
        return result;
    }

}
