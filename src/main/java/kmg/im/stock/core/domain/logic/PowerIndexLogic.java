package kmg.im.stock.core.domain.logic;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import kmg.im.stock.core.domain.model.PowerIndexCalcModel;

/**
 * 勢力指数ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface PowerIndexLogic {

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
    List<Supplier<BigDecimal>> calc(List<PowerIndexCalcModel> dataList);

}
