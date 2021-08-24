package kmg.im.stock.tssts.data.dao;

import java.nio.file.Paths;
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
import kmg.im.stock.tssts.data.dto.SimDto;
import kmg.im.stock.tssts.data.dto.StockBrandDto;
import kmg.im.stock.tssts.data.dto.impl.SimDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.StockBrandDtoImpl;

/**
 * シミュレーションＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
@SuppressWarnings("nls")
public class SimDao {

    /** 検索するSQL */
    private static final SqlPathModel FIND_SQL_PATH = new SqlPathModelImpl(SimDao.class, Paths.get("find.sql"));

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
    public SimDao(final NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * 検索する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株価コード
     * @return シミュレーションＤＴＯのリスト
     * @throws KmgDomainException
     *                            ＫＭＧドメイン例外
     */
    public List<SimDto> find(final long stockCode) throws KmgDomainException {

        List<SimDto> result = null;

        /* パラメータを設定する */
        final StockBrandDto stockBrandDto = new StockBrandDtoImpl();
        stockBrandDto.setCode(stockCode);

        /* DBを実行する */
        final SqlParameterSource params = new BeanPropertySqlParameterSource(stockBrandDto);
        final List<SimDtoImpl> tmp = this.jdbc.query(SimDao.FIND_SQL_PATH.toSql(), params,
            BeanPropertyRowMapper.newInstance(SimDtoImpl.class));
        result = tmp.stream().map(mapper -> (SimDto) mapper).collect(Collectors.toList());

        return result;

    }
}
