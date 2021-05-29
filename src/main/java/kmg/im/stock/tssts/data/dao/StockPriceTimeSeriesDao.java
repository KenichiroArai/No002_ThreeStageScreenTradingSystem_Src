package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

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

    /** 株価時系列を削除するSQL */
    private static final String DELETE_SQL = "DELETE FROM stock_price_time_series" + " WHERE "
        + "type_of_period_id = :typeOfPeriodId";

    /** 株価時系列を挿入するSQL */
    private static final String INSERT_SQL = "INSERT INTO stock_price_time_series("
        + "start_date, end_date, locale_id, creator, created_date, updater, update_date, note, name, stock_brand_id, no, type_of_period_id, period_start_date, period_end_date, op, hp, lp, cp, volume"
        + ")" + " VALUES("
        + ":startDate, :endDate, :localeId, :creator, :createdDate, :updater, :updateDate, :note, :name, :stockBrandId, :no, :typeOfPeriodId, :periodStartDate, :periodEndDate, :op, :hp, :lp, :cp, :volume"
        + ")";

    /** データベース接続 */
    private final NamedParameterJdbcTemplate jdbc;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param jdbc
     *             データベース接続
     */
    public StockPriceTimeSeriesDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 削除する<br>
     * <p>
     * 期間の種類に該当するデータを削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param typeOfPeriodTypes
     *                          期間の種類の種類
     * @return 削除数
     */
    public long delete(final TypeOfPeriodTypes typeOfPeriodTypes) {

        long result = 0L;

        final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDtoImpl();
        stockPriceTimeSeriesDto.setTypeOfPeriodId(typeOfPeriodTypes.getValue());

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceTimeSeriesDto);
        result = this.jdbc.update(StockPriceTimeSeriesDao.DELETE_SQL, param);

        return result;
    }

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
        stockPriceTimeSeriesDto.setCreatedDate(LocalDateTime.now());
        stockPriceTimeSeriesDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/01 後で考える
        stockPriceTimeSeriesDto.setUpdateDate(LocalDateTime.now());
        stockPriceTimeSeriesDto.setNote(KmgString.EMPTY);
        stockPriceTimeSeriesDto.setName(KmgString.EMPTY);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceTimeSeriesDto);
        result = this.jdbc.update(StockPriceTimeSeriesDao.INSERT_SQL, param);

        return result;

    }
}
