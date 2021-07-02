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
import kmg.im.stock.tssts.data.dto.SptsptDto;
import kmg.im.stock.tssts.data.dto.impl.SptsptDtoImpl;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

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

    /** 識別番号を取得するSQL */
    private static final SqlPathModel GET_ID_SQL_PATH = new SqlPathModelImpl(SptsptDao.class, Paths.get("getId.sql"));

    /** 挿入するSQLパス */
    private static final SqlPathModel INSERT_SQL_PATH = new SqlPathModelImpl(SptsptDao.class, Paths.get("insert.sql"));

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
     * 挿入する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                        株銘柄ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 登録件数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long insert(final long stockBrandId, final PeriodTypeTypes periodTypeTypes) throws KmgDomainException {

        long result = 0L;

        /* パラメータを設定する */
        final SptsptDto sptsptDto = new SptsptDtoImpl();
        sptsptDto.setStartDate(LocalDate.MIN);
        sptsptDto.setEndDate(LocalDate.MAX);
        sptsptDto.setLocaleId("ja"); // TODO KenichiroArai 2021/06/05 列挙型
        sptsptDto.setCreator("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/06/05 後で考える
        sptsptDto.setCreatedDate(LocalDateTime.now());
        sptsptDto.setUpdater("TSSTS_MAIN_USER"); // TODO KenichiroArai 2021/06/05 後で考える
        sptsptDto.setUpdateDate(LocalDateTime.now());
        sptsptDto.setNote(KmgString.EMPTY);
        sptsptDto.setStockBrandId(stockBrandId);
        sptsptDto.setPeriodTypeId(periodTypeTypes.get());

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(sptsptDto);
        result = this.jdbc.update(SptsptDao.INSERT_SQL_PATH.toSql(), params);

        return result;
    }
}
