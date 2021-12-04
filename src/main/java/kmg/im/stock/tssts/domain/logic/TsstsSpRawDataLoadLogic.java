package kmg.im.stock.tssts.domain.logic;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import kmg.im.stock.core.domain.model.SpRawDataAcqMgtModel;
import kmg.im.stock.core.domain.model.SpRawDataAcqModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価生データ読み込みロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsSpRawDataLoadLogic {

    /**
     * 株価生データモデル取得管理モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価生データモデル取得管理モデルのマップ<株価銘柄コード, 株価生データモデル取得管理モデル>
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    Map<Long, SpRawDataAcqMgtModel> getSpDataMgtMap() throws TsstsDomainException;

    /**
     * 株価生データモデル取得管理モデルを返す<br>
     * <p>
     * ファイルパスに該当する株価生データモデル取得管理モデルを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価生データモデル取得管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    SpRawDataAcqMgtModel getSpDataMgtModel(final Path filePath) throws TsstsDomainException;

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

    /**
     * 株価生データモデル取得モデルのリストを返す<br>
     * <p>
     * ファイルパスに該当する株価生データモデル取得モデルのリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価生データモデル取得管理モデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    List<SpRawDataAcqModel> getSpDataList(final Path filePath) throws TsstsDomainException;
}
