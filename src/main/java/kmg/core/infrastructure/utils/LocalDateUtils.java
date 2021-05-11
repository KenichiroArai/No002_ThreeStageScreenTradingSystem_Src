package kmg.core.infrastructure.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * ローカル日付ユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class LocalDateUtils {

    /** フォーマッタパターン（yyyy/MM/dd） */
    private static final String FORMATTER_PATTERN_YYYY_MM_DD = "yyyy/MM/dd"; //$NON-NLS-1$

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private LocalDateUtils() {
        // 処理無し
    }

    /**
     * 解析yyyy/MM/dd<br>
     * <p>
     * 日付文字列（yyyy/MM/dd）を解析し、ローカル日付にして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataStr
     *                日付文字列（yyyy/MM/dd）
     * @return ローカル日付
     */
    public static LocalDate parseYyyyMmDd(final String dataStr) {
        final LocalDate result = LocalDate.parse(dataStr,
            DateTimeFormatter.ofPattern(LocalDateUtils.FORMATTER_PATTERN_YYYY_MM_DD));
        return result;
    }
}
