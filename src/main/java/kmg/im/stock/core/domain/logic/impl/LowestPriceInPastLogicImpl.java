package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.type.KmgDecimal;
import kmg.im.stock.core.domain.logic.LowestPriceInPastLogic;

/**
 * 過去の最安値ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class LowestPriceInPastLogicImpl implements LowestPriceInPastLogic {

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
        for (int i = 0; i < n; i++) {
            result.add(() -> KmgDecimal.RESULT_ZERO);
        }

        /* 過去計算日数で最安値を求め、最安値を追加する */
        for (int i = n; i < dataList.size(); i++) {
            Supplier<BigDecimal> pastData = dataList.get(i - 1); // ひとつ前のデータ値を取得
            Supplier<BigDecimal> lowestPrice = pastData; // 最安値をひとつ前のデータ値に設定する

            // 過去計算日数の最安値を設定する
            // ひとつ前のデータを取得済みなので、二つ前から取得し、計算日数まで遡る
            for (int j = 2; j <= n; j++) {
                pastData = dataList.get(i - j);

                // 最安値を更新しないか
                if (lowestPrice.get().compareTo(pastData.get()) <= 0) {
                    // 更新しない場合

                    continue;
                }

                // 最安値を更新する
                lowestPrice = pastData;
            }

            // 最安値を追加する
            result.add(lowestPrice);
        }

        return result;
    }

}
