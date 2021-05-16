package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.tssts.data.dto.StockPriceWeeklyDto;

/**
 * 株価週足ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class StockPriceWeeklyDao {

    /** 株価時系列を挿入するSQL */
    private static final String INSERT_SQL = "INSERT INTO stock_price_weekly(start_date, end_date, locale_id, creator, created_date, updater, update_date, note, name, stock_brand_id, no, weekly_start_date, weekly_end_date, op, hp, lp, cp, volume)"
        + " VALUES(:startDate, :endDate, :localeId, :creator, :createdDate, :updater, :updateDate, :note, :name, :stockBrandId, :no, :weeklyStartDate, :weeklyEndDate, :op, :hp, :lp, :cp, :volume)";

    /** データベース接続 */
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * 株価週足を挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceWeeklyDto
     *                            株価週足ＤＴＯ
     * @return 挿入数
     */
    public long insert(final StockPriceWeeklyDto stockPriceWeeklyDto) {

        long result = 0L;

        stockPriceWeeklyDto.setStartDate(LocalDate.MIN);
        stockPriceWeeklyDto.setEndDate(LocalDate.MAX);
        stockPriceWeeklyDto.setLocaleId("ja"); // TODO KenichiroArai 2021/05/16 列挙型
        stockPriceWeeklyDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/16 後で考える
        stockPriceWeeklyDto.setCreatedDate(LocalDateTime.now());
        stockPriceWeeklyDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/16 後で考える
        stockPriceWeeklyDto.setUpdateDate(LocalDateTime.now());
        stockPriceWeeklyDto.setNote(KmgString.EMPTY);
        stockPriceWeeklyDto.setName(KmgString.EMPTY);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceWeeklyDto);
        result = this.jdbc.update(StockPriceWeeklyDao.INSERT_SQL, param);

        return result;

    }
}
