package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.im.stock.tssts.data.dto.StockBrandDto;
import kmg.im.stock.tssts.data.dto.impl.StockBrandDtoImpl;

/**
 * 株銘柄ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class StockBrandDao {

    /** 識別番号を取得するSQL */
    private static final String GET_ID_SQL = "SELECT id FROM stock_brand WHERE start_date <= :startDate AND end_date > :startDate AND code = :code";

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
    public StockBrandDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 識別番号を取得する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @param baseDate
     *                       基準日
     * @return 識別番号
     */
    public long getId(final long stockBrandCode, final LocalDate baseDate) {

        long result = 0L;

        /* パラメータを設定する */
        final StockBrandDto stockBrandDto = new StockBrandDtoImpl();
        stockBrandDto.setCode(stockBrandCode);
        stockBrandDto.setStartDate(baseDate);

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(stockBrandDto);
        result = this.jdbc.queryForObject(StockBrandDao.GET_ID_SQL, params, Long.class);

        return result;

    }
}
