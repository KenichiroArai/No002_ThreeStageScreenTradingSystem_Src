package kmg.im.stock.tssts.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * 三段階スクリーン・トレーディング・システムメッセージ定義<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class TsstsMessageConfig {

    /**
     * メッセージの定義<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return メッセージソース
     */
    @SuppressWarnings({
        "static-method", "nls"
    })
    @Bean
    public MessageSource logMessageSource() {

        final ResourceBundleMessageSource result = new ResourceBundleMessageSource();
        result.setBasenames("messages-log", "messages-log_ja");
        result.setDefaultEncoding("UTF-8");
        return result;
    }
}
