package kmg.im.stock.core.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

/**
 * 単純移動平均サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SmaService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @param calcDays
     *                 計算日数
     */
    void initialize(final List<Supplier<BigDecimal>> dataList, final int calcDays);

    /**
     * データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリスト
     */
    List<Supplier<BigDecimal>> getDataList();

    /**
     * 計算日数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算日数
     */
    int getCalcDays();

    /**
     * 計算結果のリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算結果のリスト
     */
    List<Supplier<BigDecimal>> getClacResultList();

    /**
     * 計算する<br>
     * <p>
     * データリストを計算日数で計算し結果を返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void calc();
}
