package kmg.spring.infrastructure.constants;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Bean標準スコープの定数<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public final class BeanDefaultScopeConstants {

    /** シングルトン */
    public static final String SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    /** プロトタイプ */
    public static final String PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    /** セッション */
    public static final String SESSION = "session";

    /** リクエスト */
    public static final String REQUEST = "request";

    /** グローバルセッション */
    public static final String GLOBAL_SEESION = "globalSeesion";

    /** アプリケーション */
    public static final String APPLICATION = "application";

    /**
     * デフォルトコンストラクタ<br>
     */
    private BeanDefaultScopeConstants() {
        // 処理無し
    }

}
