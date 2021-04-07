package kmg.im.stock.tssts.domain.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.TsstsLogic;
import kmg.im.stock.tssts.infrastructure.dao.TsstsDao;

//TODO サンプル
/**
 * 三段階スクリーン・トレーディング・システムロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsLogicImpl implements TsstsLogic {

    /** 三段階スクリーン・トレーディング・システムＤＡＯ */
    @Autowired
    private TsstsDao tsstsDao;

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void run() {
        this.tsstsDao.run();
    }

}
