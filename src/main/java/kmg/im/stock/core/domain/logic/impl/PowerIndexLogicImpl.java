package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.logic.PowerIndexLogic;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;

/**
 * 勢力指数ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class PowerIndexLogicImpl implements PowerIndexLogic {

    /**
     * 計算する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @return 計算結果のリスト
     */
    @Override
    public List<Supplier<BigDecimal>> calc(final List<PowerIndexCalcModel> dataList) {

        final List<Supplier<BigDecimal>> result = new ArrayList<>();

        if (dataList.isEmpty()) {
            return result;
        }

        /* 一つ目は計算できないため、データを0にする */
        result.add(() -> KmgDecimal.CALC_ZERO);

        /* 勢力指数を計算する */
        for (int i = 1; i < dataList.size(); i++) {
            final long todayVolume = dataList.get(i).getVolume(); // 今日の出来高
            final BigDecimal dayBeforeCp = dataList.get(i - 1).getCp(); // 前日の終値
            final BigDecimal todayCp = dataList.get(i).getCp(); // 今日の終値

            // 勢力指数＝今日の出来高×（今日の終値―前日の終値）
            final BigDecimal powerIndex = new BigDecimal(todayVolume).multiply(todayCp.subtract(dayBeforeCp));

            result.add(() -> powerIndex);
        }

        return result;
    }

}
