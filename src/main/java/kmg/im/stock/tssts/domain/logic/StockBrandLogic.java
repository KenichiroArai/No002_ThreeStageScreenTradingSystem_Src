package kmg.im.stock.tssts.domain.logic;

import java.time.LocalDate;

/**
 * 株銘柄ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockBrandLogic {

    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * システム日付に該当する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @return 株価銘柄ID
     */
    long getStockBrandId(long stockBrandCode);

    /**
     * 株価銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @param baseDate
     *                       基準日
     * @return 株価銘柄ID
     */
    long getStockBrandId(long stockBrandCode, final LocalDate baseDate);
}
