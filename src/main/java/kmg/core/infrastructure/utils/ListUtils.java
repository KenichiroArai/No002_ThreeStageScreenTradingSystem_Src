package kmg.core.infrastructure.utils;

import java.util.List;

/**
 * リストユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class ListUtils {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private ListUtils() {
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
    public static boolean isEmpty(final List<?> target) {
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
    public static boolean isNotEmpty(final List<?> target) {
        final boolean result = !ListUtils.isEmpty(target);
        return result;
    }
}
