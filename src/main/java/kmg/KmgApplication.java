package kmg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import kmg.im.stock.core.ImStockApplication;

/**
 * ＫＭＧアプリケーション<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class KmgApplication {

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
    public KmgApplication(final ConfigurableApplicationContext context) {
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
