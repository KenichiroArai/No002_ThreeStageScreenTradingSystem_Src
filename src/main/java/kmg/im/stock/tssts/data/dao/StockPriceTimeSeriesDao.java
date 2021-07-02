package kmg.im.stock.tssts.data.dao;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.domain.model.SqlPathModel;
import kmg.core.domain.model.impl.SqlPathModelImpl;
import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.tssts.data.dto.SptsptDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.SptsptDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

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

    /** 株価時系列を削除するＳＱＬパス */
    private static final SqlPathModel DELETE_SQL_PATH = new SqlPathModelImpl(StockPriceTimeSeriesDao.class,
        Paths.get("delete.sql"));

    /** 株価時系列を挿入するＳＱＬパス */
    private static final SqlPathModel INSERT_SQL_PATH = new SqlPathModelImpl(StockPriceTimeSeriesDao.class,
        Paths.get("insert.sql"));

    /** 株価時系列期間の種類IDに該当する株価時系列を検索するＳＱＬパス */
    private static final SqlPathModel FIND_BY_SPTSPT_ID_SQL_PATH = new SqlPathModelImpl(StockPriceTimeSeriesDao.class,
        Paths.get("findBySptsptId.sql"));

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
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long delete(final PeriodTypeTypes periodTypeTypes) throws KmgDomainException {

        long result = 0L;

        final SptsptDto sptsptDto = new SptsptDtoImpl();
        sptsptDto.setPeriodTypeId(periodTypeTypes.get());

        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsptDto);
        result = this.jdbc.update(StockPriceTimeSeriesDao.DELETE_SQL_PATH.toSql(), param);

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
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     * @return 挿入数
     */
    public long insert(final StockPriceTimeSeriesDto stockPriceTimeSeriesDto) throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
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
        result = this.jdbc.update(StockPriceTimeSeriesDao.INSERT_SQL_PATH.toSql(), param);

        return result;

    }

    /**
     * 株価時系列期間の種類IDに該当する株価時系列を検索する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                 株価時系列期間の種類ID
     * @return 株価時系列ＤＴＯのリスト
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public List<StockPriceTimeSeriesDto> findBySptsptId(final long sptsptId) throws KmgDomainException {

        List<StockPriceTimeSeriesDto> result = null;

        /* パラメータを設定する */
        final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDtoImpl();
        stockPriceTimeSeriesDto.setSptsptId(sptsptId);

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(stockPriceTimeSeriesDto);

        final List<StockPriceTimeSeriesDtoImpl> tmp = this.jdbc.query(
            StockPriceTimeSeriesDao.FIND_BY_SPTSPT_ID_SQL_PATH.toSql(), params,
            BeanPropertyRowMapper.newInstance(StockPriceTimeSeriesDtoImpl.class));
        result = tmp.stream().map(mapper -> (StockPriceTimeSeriesDto) mapper).collect(Collectors.toList());

//        result = this.jdbc.query(StockPriceTimeSeriesDao.FIND_BY_SPTSPT_ID_SQL_PATH.toSql(), params,
//            BeanPropertyRowMapper.newInstance(StockPriceTimeSeriesDto.class));

        return result;

    }
}
