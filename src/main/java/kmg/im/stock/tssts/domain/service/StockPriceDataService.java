package kmg.im.stock.tssts.domain.service;

import java.nio.file.Path;
import java.util.Map;

import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価データサービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface StockPriceDataService {

    /**
     * 株価データ管理マップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データ管理マップ<株価銘柄コード, 株価データ管理>
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    Map<Long, StockPriceDataMgtModel> getStockPriceDataMgtMap() throws TsstsDomainException;

    /**
     * 株価データ管理モデルを返す<br>
     * <p>
     * ファイルパスに該当する株価データ管理モデルを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価データ管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    StockPriceDataMgtModel getStockPriceDataMgtModel(final Path filePath) throws TsstsDomainException;
}
