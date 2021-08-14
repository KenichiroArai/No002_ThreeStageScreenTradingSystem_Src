package kmg.core.infrastructure.utils;

import java.util.Map;

/**
 * マップユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class MapUtils {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private MapUtils() {
        // 処理無し
    }

    /**
     * 対象が空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象
     * @return true：空、false：空ではない
     */
    public static boolean isEmpty(final Map<?, ?> target) {
        boolean result = true;

        if (target == null) {
            return result;
        }

        if (target.isEmpty()) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 対象が空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               対象
     * @return true：空ではない、false：空
     */
    public static boolean isNotEmpty(final Map<?, ?> target) {
        final boolean result = !MapUtils.isEmpty(target);
        return result;
    }
}
