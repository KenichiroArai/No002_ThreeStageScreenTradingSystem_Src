package kmg.im.stock.tssts.data.dao;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.domain.model.SqlPathModel;
import kmg.core.domain.model.impl.SqlPathModelImpl;
import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.core.infrastructure.types.PeriodTypeTypes;
import kmg.im.stock.tssts.data.dto.SptsptDeleteCondDto;
import kmg.im.stock.tssts.data.dto.SptsptDto;
import kmg.im.stock.tssts.data.dto.SptsptRegDto;
import kmg.im.stock.tssts.data.dto.impl.SptsptDeleteCondDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.SptsptDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.SptsptRegDtoImpl;

/**
 * 株価時系列期間の種類ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class SptsptDao {

    /** 私自身のクラス */
    private static final Class<?> MYSELF_CLASS = SptsptDao.class;

    /** 株価銘柄ＩＤと期間の種類の種類に該当するデータを削除するＳＱＬパス */
    private static final SqlPathModel DELETE_BY_SB_ID_AND_PERIOD_TYPE_TYPES_SQL_PATH = new SqlPathModelImpl(
        SptsptDao.MYSELF_CLASS, Paths.get("deleteBySbIdAndPeriodTypeTypes.sql"));

    /** 識別番号を取得するＳＱＬパス */
    private static final SqlPathModel GET_ID_SQL_PATH = new SqlPathModelImpl(SptsptDao.MYSELF_CLASS,
        Paths.get("getId.sql"));

    /** 株銘柄ＩＤと期間の種類の種類を基に挿入するＳＱＬパス */
    private static final SqlPathModel INSERT_BY_SB_ID_AND_PTT_SQL_PATH = new SqlPathModelImpl(SptsptDao.MYSELF_CLASS,
        Paths.get("insertBySbIdAndPtt.sql"));

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
    public SptsptDao(final NamedParameterJdbcTemplate jdbc) {
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
        final SptsptDeleteCondDto sptsptDeleteCondDto = new SptsptDeleteCondDtoImpl();
        sptsptDeleteCondDto.setStockBrandId(sbId);
        sptsptDeleteCondDto.setPeriodTypeId(periodTypeTypes.get());
        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsptDeleteCondDto);

        /* 削除する */
        result = this.jdbc.update(SptsptDao.DELETE_BY_SB_ID_AND_PERIOD_TYPE_TYPES_SQL_PATH.toSql(), param);

        return result;
    }

    /**
     * 識別番号を取得する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                        株銘柄ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param baseDate
     *                        基準日
     * @return 識別番号
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long getId(final long stockBrandId, final PeriodTypeTypes periodTypeTypes, final LocalDate baseDate)
        throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        final SptsptDto sptsptDto = new SptsptDtoImpl();
        sptsptDto.setStockBrandId(stockBrandId);
        sptsptDto.setPeriodTypeId(periodTypeTypes.get());
        sptsptDto.setStartDate(baseDate);

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(sptsptDto);
        result = this.jdbc.queryForObject(SptsptDao.GET_ID_SQL_PATH.toSql(), params, Long.class);

        return result;

    }

    /**
     * 株銘柄ＩＤと期間の種類の種類を基に挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *             株銘柄ＩＤ
     * @param ptt
     *             期間の種類の種類
     * @return 登録件数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long insertBySbIdAndPtt(final long sbId, final PeriodTypeTypes ptt) throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        final SptsptRegDto sptsptRegDto = new SptsptRegDtoImpl();
        sptsptRegDto.setStartDate(LocalDate.MIN);
        sptsptRegDto.setEndDate(LocalDate.MAX);
        sptsptRegDto.setLocaleId("ja"); // TODO KenichiroArai 2021/06/05 列挙型
        sptsptRegDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/06/05 後で考える
        sptsptRegDto.setCreatedDate(LocalDateTime.now());
        sptsptRegDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/06/05 後で考える
        sptsptRegDto.setUpdateDate(LocalDateTime.now());
        sptsptRegDto.setNote(KmgString.EMPTY);
        sptsptRegDto.setStockBrandId(sbId);
        sptsptRegDto.setPeriodTypeId(ptt.get());

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(sptsptRegDto);
        result = this.jdbc.update(SptsptDao.INSERT_BY_SB_ID_AND_PTT_SQL_PATH.toSql(), params);

        return result;
    }

}
