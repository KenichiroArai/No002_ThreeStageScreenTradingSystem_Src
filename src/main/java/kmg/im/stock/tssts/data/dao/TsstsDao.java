package kmg.im.stock.tssts.data.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

//TODO サンプル
/**
 * 三段階スクリーン・トレーディング・システムＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
public class TsstsDao {

    /** ロガー */
    private static Logger LOGGER = LoggerFactory.getLogger(TsstsDao.class);

    /** ログメッセージリゾルバ */
    @Autowired
    private LogMessageResolver logMessageResolver;

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void run() {

        TsstsDao.LOGGER.info(this.logMessageResolver.getMessage(LogMessageTypes.I00001));

    }

}
