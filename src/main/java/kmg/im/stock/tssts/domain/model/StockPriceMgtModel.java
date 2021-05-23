package kmg.im.stock.tssts.domain.model;

import java.nio.file.Path;
import java.util.List;

import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価管理モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceMgtModel {

    /**
     * 株価銘柄格納パスリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄格納パスリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    List<Path> getStockPriceStockStoragePathList() throws TsstsDomainException;

    /**
     * 株価データリストを返す<br>
     * <p>
     * ファイルパスに該当する株価データリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価データリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    List<StockPriceDataModel> getStockPriceDataList(final Path filePath) throws TsstsDomainException;
}
