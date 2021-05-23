package kmg.im.stock.tssts.domain.model;

import java.nio.file.Path;

import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株銘柄モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public interface StockBrandModel {

    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * ファイルパスに該当する株価銘柄IDを返す。
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
    long getStockBrandId(final Path filePath) throws TsstsDomainException;
}
