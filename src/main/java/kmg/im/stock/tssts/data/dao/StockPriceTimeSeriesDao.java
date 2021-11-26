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
import kmg.im.stock.tssts.data.dto.SimpleSptsSearchConditionDto;
import kmg.im.stock.tssts.data.dto.SptsDeleteCondDto;
import kmg.im.stock.tssts.data.dto.SptsptDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.SimpleSptsSearchConditionDtoImpl;
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

    /** 株価銘柄ＩＤと期間の種類の種類に該当するデータを削除するＳＱＬパス */
    private static final SqlPathModel DELETE_BY_SB_ID_AND_PERIOD_TYPE_TYPES_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("deleteBySbIdAndPeriodTypeTypes.sql"));

    /** 株価時系列を削除するＳＱＬパス */
    private static final SqlPathModel DELETE_SQL_PATH = new SqlPathModelImpl(StockPriceTimeSeriesDao.MYSELF_CLASS,
        Paths.get("delete.sql"));

    /** 期間の種類の種類と株価時系列ＤＴＯを基に挿入するＳＱＬパス */
    private static final SqlPathModel INSERT_BY_PTT_AND_SPTS_DTO_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("insertByPttAndSptsDto.sql"));

    /** 株銘柄ＩＤと期間の種類の種類を基に株価時系列ＤＴＯのリストを返す検索するＳＱＬパス */
    private static final SqlPathModel FIND_BY_SB_ID_AND_PTT_SQL_PATH = new SqlPathModelImpl(
        StockPriceTimeSeriesDao.MYSELF_CLASS, Paths.get("findBySbIdAndPtt.sql"));

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
     * 株価銘柄ＩＤと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *                        株価銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long deleteBySbIdAndPeriodTypeTypes(final long sbId, final PeriodTypeTypes periodTypeTypes)
        throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        final SptsDeleteCondDto sptsDeleteCondDto = new SptsDeleteCondDtoImpl();
        sptsDeleteCondDto.setStockBrandId(sbId);
        sptsDeleteCondDto.setPeriodTypeId(periodTypeTypes.get());
        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsDeleteCondDto);

        /* 削除する */
        result = this.jdbc.update(StockPriceTimeSeriesDao.DELETE_BY_SB_ID_AND_PERIOD_TYPE_TYPES_SQL_PATH.toSql(),
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
     * 株銘柄ＩＤと期間の種類の種類を基に株価時系列ＤＴＯのリストを返す検索する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *             株銘柄ＩＤ
     * @param ptt
     *             期間の種類の種類
     * @return 株価時系列ＤＴＯのリスト
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public List<StockPriceTimeSeriesDto> findBySbIdAndPtt(final long sbId, final PeriodTypeTypes ptt)
        throws KmgDomainException {

        List<StockPriceTimeSeriesDto> result = null;

        /* パラメータを設定する */
        final SimpleSptsSearchConditionDto simpleSptsSearchConditionDto = new SimpleSptsSearchConditionDtoImpl();
        simpleSptsSearchConditionDto.setStockBrandId(sbId);
        simpleSptsSearchConditionDto.setPeriodTypeId(ptt.get());

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(simpleSptsSearchConditionDto);

        final List<StockPriceTimeSeriesDtoImpl> stockPriceTimeSeriesDtoImplList = this.jdbc.query(
            StockPriceTimeSeriesDao.FIND_BY_SB_ID_AND_PTT_SQL_PATH.toSql(), params,
            BeanPropertyRowMapper.newInstance(StockPriceTimeSeriesDtoImpl.class));
        result = stockPriceTimeSeriesDtoImplList.stream().collect(Collectors.toList());

        return result;

    }
}
