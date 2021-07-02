package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.domain.logic.EmaLogic;
import kmg.im.stock.core.domain.logic.SmaLogic;

/**
 * 指数移動平均ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class EmaLogicImpl implements EmaLogic {

    /** 単純移動平均ロジック */
    private final SmaLogic smaLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param smaLogic
     *                 単純移動平均ロジック
     */
    public EmaLogicImpl(final SmaLogic smaLogic) {
        this.smaLogic = smaLogic;
    }

    /**
     * 計算する<br>
     * <p>
     * データリストを計算する日数で計算し結果を返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @param n
     *                 計算する日数
     * @return 計算結果のリスト
     */
    @Override
    public List<Supplier<BigDecimal>> calc(final List<Supplier<BigDecimal>> dataList, final int n) {

        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        if (ListUtils.isEmpty(dataList)) {
            return result;
        }

        /* ＳＭＡを計算する */
        final List<Supplier<BigDecimal>> smaDataList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            final BigDecimal data = dataList.get(i).get();
            smaDataList.add(() -> data);
        }
        final List<Supplier<BigDecimal>> smaResultList = this.smaLogic.calc(smaDataList, n);
        result.addAll(smaResultList);

        // (2 / N + 1)
        final BigDecimal k = KmgDecimal.divide(new BigDecimal("2"), new BigDecimal(n + 1)); //$NON-NLS-1$
        // ＥＭＡを計算する日数の期間のＥＭＡ
        BigDecimal tmpEma = smaResultList.get(n - 1).get();
        for (int i = n; i < dataList.size(); i++) {
            final BigDecimal data = dataList.get(i).get();

            // EMA = Ptod（今日の価格） * K + EMAyest（前日のＥＭＡ） * (1 - K)
            tmpEma = data.multiply(k).add(tmpEma.multiply(BigDecimal.ONE.subtract(k)));

            final BigDecimal addEmp = KmgDecimal.setCalcScale(tmpEma);

            result.add(() -> addEmp);
        }

        return result;
    }

}
