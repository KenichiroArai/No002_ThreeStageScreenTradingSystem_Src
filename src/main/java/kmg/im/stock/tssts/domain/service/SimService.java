package kmg.im.stock.tssts.domain.service;

/**
 * シミュレーションサービスのインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SimService {

    /**
     * 全ての銘柄をシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void simulate();

    /**
     * 指定した株コードのシミュレーションする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    void simulate(long stockCode);

}
