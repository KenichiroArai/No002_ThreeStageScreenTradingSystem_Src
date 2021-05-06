package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.tool.ssts.infrastructure.type.KmgString;

/**
 * 株価時系列ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class StockPriceTimeSeriesDao {

    /** 株価時系列を挿入するSQL */
    private static final String INSERT_SQL = "INSERT INTO stock_price_time_series(start_date, end_date, locale_id, creator, created_date, updater, update_date, note, name, stock_brand_id, no, date, op, hp, lp, cp, volume)"
        + " VALUES(:startDate, :endDate, :localeId, :creator, :createdDate, :updater, :updateDate, :note, :name, :stockBrandId, :no, :date, :op, :hp, :lp, :cp, :volume)";

    /** データベース接続 */
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * 株価時系列を挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesDto
     *                                株価時系列ＤＴＯ
     * @return 挿入数
     */
    public long insert(final StockPriceTimeSeriesDto stockPriceTimeSeriesDto) {

        long result = 0L;

        stockPriceTimeSeriesDto.setStartDate(LocalDate.MIN);
        stockPriceTimeSeriesDto.setEndDate(LocalDate.MAX);
        stockPriceTimeSeriesDto.setLocaleId("ja"); // TODO KenichiroArai 2021/05/01 列挙型
        stockPriceTimeSeriesDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/01 後で考える
        stockPriceTimeSeriesDto.setCreatedDate(LocalDate.now());
        stockPriceTimeSeriesDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/01 後で考える
        stockPriceTimeSeriesDto.setUpdateDate(LocalDate.now());
        stockPriceTimeSeriesDto.setNote(KmgString.EMPTY);
        stockPriceTimeSeriesDto.setName(KmgString.EMPTY);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceTimeSeriesDto);
        result = this.jdbc.update(StockPriceTimeSeriesDao.INSERT_SQL, param);

        return result;

    }
}
