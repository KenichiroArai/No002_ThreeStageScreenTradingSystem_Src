package kmg.im.stock.tssts.domain.service;

/**
 * シグナル確認サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SigChkService {

    /**
     * 全ての銘柄のシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void chkSig();

    /**
     * 指定した株コードのシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    void chkSig(final long stockCode);
}
