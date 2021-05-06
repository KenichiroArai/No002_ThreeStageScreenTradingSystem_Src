package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.im.stock.tssts.data.dto.StockBrandDto;

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
    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    /**
     * 識別番号を取得する<br>
     * <p>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param code
     *                 コード
     * @param baseDate
     *                 基準日
     * @return 識別番号
     */
    public long getId(final long code, final LocalDate baseDate) {

        long result = 0L;

        /* パラメータを設定する */
        final StockBrandDto stockBrandDto = new StockBrandDto();
        stockBrandDto.setCode(code);
        stockBrandDto.setStartDate(baseDate);

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(stockBrandDto);
        result = this.jdbc.queryForObject(StockBrandDao.GET_ID_SQL, params, Long.class);

        return result;

    }
}
