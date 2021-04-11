package kmg.im.stock.tssts.infrastructure.resolver;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * ログメッセージリゾルバ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class LogMessageResolver {

    /** ログメッセージソース */
    @Autowired
    private MessageSource logMessageSource;

    /**
     * メッセージを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param code
     *             ログメッセージコード
     * @param args
     *             メッセージ引数
     * @return メッセージ
     */
    public String getMessage(final LogMessageTypes code, final Object... args) {

        String result = null;

        Object[] argsArrays = args;
        if (argsArrays == null) {
            argsArrays = new Object[0];
        }

        result = String.format("[%s]%s", code.getValue(), //$NON-NLS-1$
            this.logMessageSource.getMessage(code.getValue(), argsArrays, Locale.getDefault()));

        return result;
    }

}
