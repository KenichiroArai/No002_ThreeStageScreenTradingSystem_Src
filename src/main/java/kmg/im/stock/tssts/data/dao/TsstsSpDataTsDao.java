package kmg.im.stock.tssts.data.dao;

import java.nio.file.Paths;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import kmg.core.domain.model.SqlPathModel;
import kmg.core.domain.model.impl.SqlPathModelImpl;
import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.core.data.dto.SptsptDto;
import kmg.im.stock.core.data.dto.impl.SptsptDtoImpl;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価データ時系列ＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class TsstsSpDataTsDao {

    /** 株価データ時系列を削除するＳＱＬパス */
    private static final SqlPathModel DELETE_SQL_PATH = new SqlPathModelImpl(TsstsSpDataTsDao.class,
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
    public TsstsSpDataTsDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * <p>
     * 株価データ時系列を削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                             株銘柄ID
     * @param imStkPeriodTypeTypes
     *                             投資株式期間の種類の種類
     * @return 削除数
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public long delete(final long stockBrandId, final ImStkPeriodTypeTypes imStkPeriodTypeTypes)
        throws KmgDomainException {

        long result = 0L;

        final SptsptDto sptsptDto = new SptsptDtoImpl();
        sptsptDto.setPeriodTypeId(imStkPeriodTypeTypes.get());

        final SqlParameterSource param = new BeanPropertySqlParameterSource(sptsptDto);
        result = this.jdbc.update(TsstsSpDataTsDao.DELETE_SQL_PATH.toSql(), param);

        return result;
    }
}
