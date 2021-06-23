package kmg.im.stock.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 投資株式<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class ImStockApplication {

    /** ロガー */
    private static Logger LOGGER = LoggerFactory.getLogger(ImStockApplication.class);

    /** 構成可能なアプリケーションコンテキスト */
    private final ConfigurableApplicationContext context;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                構成可能なアプリケーションコンテキスト
     */
    public ImStockApplication(final ConfigurableApplicationContext context) {
        this.context = context;
    }

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
        try (ConfigurableApplicationContext context = SpringApplication.run(ImStockApplication.class, args);) {
            @SuppressWarnings("unused")
            final ImStockApplication kmgCoreApplication = context.getBean(ImStockApplication.class);
            // 処理無し
        }
    }

}
