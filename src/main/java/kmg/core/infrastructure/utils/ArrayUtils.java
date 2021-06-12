package kmg.core.infrastructure.utils;

/**
 * 配列ユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class ArrayUtils {

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private ArrayUtils() {
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
    public static boolean isEmpty(final Object[] target) {
        boolean result = true;

        if (target == null) {
            return result;
        }

        if (target.length == 0) {
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
    public static boolean isNotEmpty(final Object[] target) {
        final boolean result = !ArrayUtils.isEmpty(target);
        return result;
    }
}
