
package kmg.core.infrastructure.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import kmg.core.infrastructure.type.KmgString;

/**
 * ローカル日時ユーティリティ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public final class LocalDateTimeUtils {

    /** フォーマッタパターン（yyyy/MM/dd HH:mm:ss.SSS） */
    private static final String FORMATTER_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy/MM/dd HH:mm:ss.SSS"; //$NON-NLS-1$

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private LocalDateTimeUtils() {
        // 処理無し
    }

    /**
     * 解析yyyy/MM/dd HH:mm:ss.SSS<br>
     * <p>
     * 日時文字列（yyyy/MM/dd HH:mm:ss.SSS）を解析し、ローカル日時にして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dateTimeStr
     *                    日時文字列（yyyy/MM/dd HH:mm:ss.SSS）
     * @return ローカル日付
     */
    public static LocalDate parseYyyyMmDdHhMmSsSss(final String dateTimeStr) {
        LocalDate result = null;
        if (KmgString.isEmpty(dateTimeStr)) {
            return result;
        }
        result = LocalDate.parse(dateTimeStr,
            DateTimeFormatter.ofPattern(LocalDateTimeUtils.FORMATTER_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS));
        return result;
    }

    /**
     * 日付からローカル日時へ/dd<br>
     * <p>
     * 日付をからローカル日時にして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param date
     *             日付
     * @return ローカル日時
     */
    public static LocalDateTime from(final Date date) {
        LocalDateTime result = null;
        if (date == null) {
            return result;
        }
        result = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return result;
    }

    /**
     * フォーマットyyyy/MM/dd HH:mm:ss.SSS<br>
     * <p>
     * ローカル日時を日時文字列（yyyy/MM/dd HH:mm:ss.SSS）にして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param localDateTime
     *                      ローカル日時
     * @return 日時文字列（yyyy/MM/dd HH:mm:ss.SSS）
     */
    public static String formatYyyyMmDdHhMmSsSss(final LocalDateTime localDateTime) {
        String result = null;
        if (localDateTime == null) {
            return result;
        }
        result = localDateTime
            .format(DateTimeFormatter.ofPattern(LocalDateTimeUtils.FORMATTER_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS));
        return result;
    }

    /**
     * フォーマットyyyy/MM/dd HH:mm:ss.SSS<br>
     * <p>
     * 日付を日時文字列（yyyy/MM/dd HH:mm:ss.SSS）にして返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param date
     *             日付
     * @return 日時文字列（yyyy/MM/dd HH:mm:ss.SSS）
     */
    public static String formatYyyyMmDdHhMmSsSss(final Date date) {
        String result = null;
        if (date == null) {
            return result;
        }
        final LocalDateTime localDateTime = LocalDateTimeUtils.from(date);
        result = localDateTime
            .format(DateTimeFormatter.ofPattern(LocalDateTimeUtils.FORMATTER_PATTERN_YYYY_MM_DD_HH_MM_SS_SSS));
        return result;
    }
}
