package kmg.im.stock.tssts.domain.service;

import java.nio.file.Path;

import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価データ登録サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsSpDataRegisterService {

    /**
     * 全株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void registerAllSpData() throws TsstsDomainException;

    /**
     * ディレクトリにある株価データを登録する<br>
     * <p>
     * ディレクトリパスにある株価データを登録する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param directoryPath
     *                      ディレクトリパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void registerSpDataOfDirectory(Path directoryPath) throws TsstsDomainException;

    /**
     * ファイルの株価データを登録する<br>
     * <p>
     * ファイルパスに該当する株価データを登録する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void registerSpDataOfFile(Path filePath) throws TsstsDomainException;
}
