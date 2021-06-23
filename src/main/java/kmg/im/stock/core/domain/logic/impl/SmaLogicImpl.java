package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.logic.SmaLogic;

/**
 * 単純移動平均ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SmaLogicImpl implements SmaLogic {

    /**
     * 計算する<br>
     * <p>
     * データリストを計算日数で計算し結果を返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @param n
     *                 計算日数
     * @return 計算結果のリスト
     */
    @Override
    public List<Supplier<BigDecimal>> calc(final List<Supplier<BigDecimal>> dataList, final int n) {

        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        /* 計算する日数に達しないデータは0にする */
        for (int i = 0; i < (n - 1); i++) {
            result.add(() -> KmgDecimal.CALC_ZERO);
        }

        /* ＳＭＡを計算する */
        int index = 0;
        for (int i = n - 1; i < dataList.size(); i++) {
            BigDecimal tmpMa = KmgDecimal.CALC_ZERO;
            for (int j = 0 + index; j < (n + index); j++) {
                final BigDecimal data = dataList.get(j).get();
                tmpMa = tmpMa.add(data);
            }
            tmpMa = KmgDecimal.divide(tmpMa, new BigDecimal(n));
            final BigDecimal addMp = KmgDecimal.setCalcScale(tmpMa);
            result.add(() -> addMp);
            index++;
        }

        return result;
    }

}
