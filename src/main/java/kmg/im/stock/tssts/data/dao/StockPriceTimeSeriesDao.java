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
import kmg.im.stock.tssts.data.dto.SptsDeleteCondDto;
import kmg.im.stock.tssts.data.dto.SptsptDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.SptsDeleteCondDtoImpl;
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

    /** 私自身のクラス */
    private static final Class<?> MYSELF_CLASS = StockPriceTimeSeriesDao.class;

    /** 株価銘柄コードと期間の種類の種類に該当するデータを削除するＳＱＬパス */
    private static final SqlPathModel DELETE_BY_SB_CD_AND_PERIOD_TYPE_TYPES_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("deleteBySbCdAndPeriodTypeTypes.sql"));

    /** 株価時系列を削除するＳＱＬパス */
    private static final SqlPathModel DELETE_SQL_PATH = new SqlPathModelImpl(StockPriceTimeSeriesDao.MYSELF_CLASS,
        Paths.get("delete.sql"));

    /** 期間の種類の種類と株価時系列ＤＴＯを基に挿入するＳＱＬパス */
    private static final SqlPathModel INSERT_BY_PTT_AND_SPTS_DTO_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("insert.sql"));

    /** 株価時系列期間の種類IDに該当する株価時系列を検索するＳＱＬパス */
    private static final SqlPathModel FIND_BY_SPTSPT_ID_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("findBySptsptId.sql"));

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
     * 株価銘柄コードと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbCd
     *                        株価銘柄コード
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long deleteBySbCdAndPeriodTypeTypes(final long sbCd, final PeriodTypeTypes periodTypeTypes)
        throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        final SptsDeleteCondDto sptsDeleteCondDto = new SptsDeleteCondDtoImpl();
        sptsDeleteCondDto.setStockBrandCode(sbCd);
        sptsDeleteCondDto.setPeriodTypeId(periodTypeTypes.get());
        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsDeleteCondDto);

        /* 削除する */
        result = this.jdbc.update(StockPriceTimeSeriesDao.DELETE_BY_SB_CD_AND_PERIOD_TYPE_TYPES_SQL_PATH.toSql(),
            param);

        return result;
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
     * 期間の種類の種類と株価時系列ＤＴＯを基に挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param ptt
     *                期間の種類の種類
     * @param sptsDto
     *                株価時系列ＤＴＯ
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     * @return 挿入数
     */
    public long insertByPttAndSptsDto(final PeriodTypeTypes ptt, final StockPriceTimeSeriesDto sptsDto)
        throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        sptsDto.setStartDate(LocalDate.MIN);
        sptsDto.setEndDate(LocalDate.MAX);
        sptsDto.setLocaleId("ja"); // TODO KenichiroArai 2021/05/01 列挙型
        sptsDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/01 後で考える
        sptsDto.setCreatedDate(LocalDateTime.now());
        sptsDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/05/01 後で考える
        sptsDto.setUpdateDate(LocalDateTime.now());
        sptsDto.setNote(KmgString.EMPTY);
        sptsDto.setName(KmgString.EMPTY);

        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsDto);
        result = this.jdbc.update(StockPriceTimeSeriesDao.INSERT_BY_PTT_AND_SPTS_DTO_SQL_PATH.toSql(), param);

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

        // TODO KenichiroArai 2021/08/17 見直しが必要
        final List<StockPriceTimeSeriesDtoImpl> tmp = this.jdbc.query(
            StockPriceTimeSeriesDao.FIND_BY_SPTSPT_ID_SQL_PATH.toSql(), params,
            BeanPropertyRowMapper.newInstance(StockPriceTimeSeriesDtoImpl.class));
        result = tmp.stream().map(mapper -> (StockPriceTimeSeriesDto) mapper).collect(Collectors.toList());

//        result = this.jdbc.query(StockPriceTimeSeriesDao.FIND_BY_SPTSPT_ID_SQL_PATH.toSql(), params,
//            BeanPropertyRowMapper.newInstance(StockPriceTimeSeriesDto.class));

        return result;

    }
}
