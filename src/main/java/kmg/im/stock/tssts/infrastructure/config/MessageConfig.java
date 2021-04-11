package kmg.im.stock.tssts.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * メッセージ定義
 */
@Component
public class MessageConfig {

    /**
     * <p>
     * メッセージの定義
     * </p>
     *
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
