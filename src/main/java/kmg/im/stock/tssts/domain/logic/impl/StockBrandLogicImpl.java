package kmg.im.stock.tssts.domain.logic.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kmg.im.stock.core.data.dao.StockBrandDao;
import kmg.im.stock.tssts.domain.logic.StockBrandLogic;

/**
 * 株銘柄ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockBrandLogicImpl implements StockBrandLogic {

    /** 株銘柄ＤＡＯ */
    private final StockBrandDao stockBrandDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandDao
     *                      株銘柄ＤＡＯ
     */
    public StockBrandLogicImpl(final StockBrandDao stockBrandDao) {
        this.stockBrandDao = stockBrandDao;
    }

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
    @Override
    public long getStockBrandId(final long stockBrandCode) {

        /* 株価銘柄IDを取得する */
        // TODO KenichiroArai 2021/05/24 システム日付から取得する
        final long result = this.getStockBrandId(stockBrandCode, LocalDate.now());

        return result;
    }

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
    @Override
    public long getStockBrandId(final long stockBrandCode, final LocalDate baseDate) {

        /* 株価銘柄IDを取得する */
        final long result = this.stockBrandDao.getId(stockBrandCode, baseDate);

        return result;
    }

}
