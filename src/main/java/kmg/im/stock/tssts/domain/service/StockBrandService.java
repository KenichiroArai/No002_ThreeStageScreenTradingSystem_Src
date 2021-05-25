package kmg.im.stock.tssts.domain.service;

import java.nio.file.Path;
import java.time.LocalDate;

import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株銘柄サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockBrandService {

    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * システム日付に該当する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @return 株価銘柄ID
     */
    long getStockBrandId(long stockBrandCode);

    /**
     * 株価銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @param baseDate
     *                       基準日
     * @return 株価銘柄ID
     */
    long getStockBrandId(long stockBrandCode, final LocalDate baseDate);

    /**
     * 株価銘柄コードを返す<br>
     * <p>
     * ファイルパスに該当する株価銘柄コードを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価銘柄ID
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long getStockBrandCode(final Path filePath) throws TsstsDomainException;
}
