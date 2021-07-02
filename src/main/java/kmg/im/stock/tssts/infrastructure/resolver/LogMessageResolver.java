package kmg.im.stock.tssts.infrastructure.resolver;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import kmg.core.infrastructure.type.KmgString;
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
    private final MessageSource logMessageSource;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageSource
     *                         ログメッセージソース
     */
    public LogMessageResolver(final MessageSource logMessageSource) {
        this.logMessageSource = logMessageSource;
    }

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
    @SuppressWarnings("nls")
    public String getMessage(final LogMessageTypes code, final Object... args) {

        String result = null;

        Object[] argsArrays = args;
        if (argsArrays == null) {
            argsArrays = new Object[0];
        }

        if (code == null) {
            result = KmgString.EMPTY;
            return result;
        }

        if (code == LogMessageTypes.NONE) {
            result = KmgString.EMPTY;
            return result;
        }

        try {
            result = String.format("[%s]%s", code.get(),
                this.logMessageSource.getMessage(code.get(), argsArrays, Locale.getDefault()));
        } catch (final NoSuchMessageException e) {
            // TODO KenichiroArai 2021/05/13 例外処理
            System.err.println(code.get());
            e.printStackTrace();
            return result;
        }

        return result;
    }

}
