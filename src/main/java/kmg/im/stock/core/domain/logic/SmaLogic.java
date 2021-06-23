package kmg.im.stock.core.domain.logic;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

/**
 * 単純移動平均ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SmaLogic {

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
    List<Supplier<BigDecimal>> calc(List<Supplier<BigDecimal>> dataList, int n);
}
