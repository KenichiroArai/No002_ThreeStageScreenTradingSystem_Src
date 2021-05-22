package kmg.im.stock.tssts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.im.stock.tssts.application.controller.TsstsController;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class No002ThreeStageScreenTradingSystemApplication {

    /** ロガー */
    private static Logger LOGGER = LoggerFactory.getLogger(No002ThreeStageScreenTradingSystemApplication.class);

    /** ログメッセージリゾルバ */
    @Autowired
    private LogMessageResolver logMessageResolver;

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {
        final No002ThreeStageScreenTradingSystemApplication no002ThreeStageScreenTradingSystemApplication = new No002ThreeStageScreenTradingSystemApplication();
        no002ThreeStageScreenTradingSystemApplication.run();
    }

    /**
     * 実行する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void run() {

        try (ConfigurableApplicationContext context = SpringApplication
            .run(No002ThreeStageScreenTradingSystemApplication.class);) {

            final TsstsController tsstsCtl = context.getBean(TsstsController.class);

            /** 全株価データを登録する。 */
            tsstsCtl.registerAllStockPriceData();

        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            No002ThreeStageScreenTradingSystemApplication.LOGGER.error(logMsg, e);
        }
    }

}
