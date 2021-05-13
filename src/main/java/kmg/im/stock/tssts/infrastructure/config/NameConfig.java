package kmg.im.stock.tssts.infrastructure.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * 名称定義<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class NameConfig {

    /**
     * 名称の定義<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 名称ソース
     */
    @SuppressWarnings({
        "static-method", "nls"
    })
    @Bean
    public MessageSource nameSource() {

        final ResourceBundleMessageSource result = new ResourceBundleMessageSource();
        result.setBasenames("name", "name_ja");
        result.setDefaultEncoding("UTF-8");
        return result;
    }
}
