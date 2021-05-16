package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.tssts.data.dto.StockPriceMonthlyDto;

/**
 * 株価月足ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class StockPriceMonthlyDao {

    /** 株価時系列を挿入するSQL */
    private static final String INSERT_SQL = "INSERT INTO stock_price_monthly(start_date, end_date, locale_id, creator, created_date, updater, update_date, note, name, stock_brand_id, no, monthly_start_date, monthly_end_date, op, hp, lp, cp, volume)"
        + " VALUES(:startDate, :endDate, :localeId, :creator, :createdDate, :updater, :updateDate, :note, :name, :stockBrandId, :no, :monthlyStartDate, :monthlyEndDate, :op, :hp, :lp, :cp, :volume)";

    /** データベース接続 */
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * 株価月足を挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceMonthlyDto
     *                             株価月足ＤＴＯ
     * @return 挿入数
     */
    public long insert(final StockPriceMonthlyDto stockPriceMonthlyDto) {

        long result = 0L;

        stockPriceMonthlyDto.setStartDate(LocalDate.MIN);
        stockPriceMonthlyDto.setEndDate(LocalDate.MAX);
        stockPriceMonthlyDto.setLocaleId("ja"); // TODO KenichiroArai 2021/05/16 列挙型
        stockPriceMonthlyDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/16 後で考える
        stockPriceMonthlyDto.setCreatedDate(LocalDateTime.now());
        stockPriceMonthlyDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/16 後で考える
        stockPriceMonthlyDto.setUpdateDate(LocalDateTime.now());
        stockPriceMonthlyDto.setNote(KmgString.EMPTY);
        stockPriceMonthlyDto.setName(KmgString.EMPTY);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceMonthlyDto);
        result = this.jdbc.update(StockPriceMonthlyDao.INSERT_SQL, param);

        return result;

    }
}
