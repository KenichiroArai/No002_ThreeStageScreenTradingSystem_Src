package kmg.im.stock.tssts.data.dao;

import java.nio.file.Paths;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.domain.model.SqlPathModel;
import kmg.core.domain.model.impl.SqlPathModelImpl;
import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;

/**
 * 株価計算値ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
public class StockPriceCalcValueDao {

    /** 株価時系列を削除するSQLパス */
    @SuppressWarnings("nls")
    private static final SqlPathModel DELETE_SQL_PATH = new SqlPathModelImpl(StockPriceCalcValueDao.class,
        Paths.get("delete.sql"));

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
    public StockPriceCalcValueDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 削除する<br>
     * <p>
     * 株価時系列期間の種類IDに該当する株価時系列を取得し、それ該当するデータを削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     * @return 削除数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long delete(final long sptsptId) throws KmgDomainException {

        long result = 0L;

        final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDtoImpl();
        stockPriceTimeSeriesDto.setSptsptId(sptsptId);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(stockPriceTimeSeriesDto);
        result = this.jdbc.update(StockPriceCalcValueDao.DELETE_SQL_PATH.toSql(), param);

        return result;
    }
}
