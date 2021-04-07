package kmg.im.stock.tssts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.im.stock.tssts.application.controller.TsstsController;

/**
 * 三段階スクリーン・トレーディング・システム<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class No002ThreeStageScreenTradingSystemApplication {

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
        try (ConfigurableApplicationContext context = SpringApplication
            .run(No002ThreeStageScreenTradingSystemApplication.class, args);) {

            final TsstsController tsstsController = context.getBean(TsstsController.class);
            tsstsController.run();

        }
    }

}
