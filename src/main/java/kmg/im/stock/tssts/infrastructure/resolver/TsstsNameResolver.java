package kmg.im.stock.tssts.infrastructure.resolver;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.infrastructure.types.TsstsNameTypes;

/**
 * 三段階スクリーン・トレーディング・システム名称リゾルバ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class TsstsNameResolver {

    /** 名称ソース */
    private final MessageSource nameSource;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param nameSource
     *                   名称ソース
     */
    public TsstsNameResolver(final MessageSource nameSource) {
        this.nameSource = nameSource;
    }

    /**
     * 名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param code
     *             名称コード
     * @param args
     *             メッセージ引数
     * @return メッセージ
     */
    public String getName(final TsstsNameTypes code, final Object... args) {

        String result = null;

        Object[] argsArrays = args;
        if (argsArrays == null) {
            argsArrays = new Object[0];
        }

        try {
            result = this.nameSource.getMessage(code.get(), argsArrays, Locale.getDefault());
        } catch (final NoSuchMessageException e) {
            // 例外処理
            System.err.println(code.get());
            e.printStackTrace();
            return result;
        }

        return result;
    }

}
