package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.service.SigService;

/**
 * シグナルサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SigServiceImpl implements SigService {

    /**
     * 全ての銘柄のシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void chkSig() {
        // TODO KenichiroArai 2021/05/11 未実装
    }

    /**
     * 指定した株コードのシグナルを確認する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株コード
     */
    @Override
    public void chkSig(final long stockCode) {
        // TODO KenichiroArai 2021/05/11 未実装
    }
}
