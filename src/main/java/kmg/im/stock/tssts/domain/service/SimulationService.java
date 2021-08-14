package kmg.im.stock.tssts.domain.service;

import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * シミュレーションサービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SimulationService {

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void simulate() throws TsstsDomainException;

    /**
     * シミュレーションする<br>
     * <p>
     * 指定した株コードのシミュレーションする
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void simulate(final long stockCode) throws TsstsDomainException;

}
