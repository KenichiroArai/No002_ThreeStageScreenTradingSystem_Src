package kmg.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ＫＭＧ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class KmgCoreApplication {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(KmgCoreApplication.class);

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
    public KmgCoreApplication(final ConfigurableApplicationContext context) {
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
        try (ConfigurableApplicationContext context = SpringApplication.run(KmgCoreApplication.class, args);) {
            @SuppressWarnings("unused")
            final KmgCoreApplication kmgCoreApplication = context.getBean(KmgCoreApplication.class);
            // 処理無し
        }
    }

}
