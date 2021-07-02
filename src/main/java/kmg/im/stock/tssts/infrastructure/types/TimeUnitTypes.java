package kmg.im.stock.tssts.infrastructure.types;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 時間単位の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum TimeUnitTypes implements Supplier<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null, "指定無し", BigDecimal.ZERO),

    /** 秒 */
    SECONDS("秒", "seconds", "秒", BigDecimal.ONE),

    /** ミリ秒 */
    MILLISECOND("ミリ秒", "millisecond,", "ミリ秒", new BigDecimal("0.001")),

    /** マイクロ秒 */
    MICROSECONDS("マイクロ秒", "microseconds", "マイクロ秒", new BigDecimal("0.000001")),

    /** ナノ秒 */
    NANOSECONDS("ナノ秒", "nanoseconds", "ナノ秒", new BigDecimal("0.000000001")),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 単位名 */
    private String unitName;

    /** 単位値 */
    private BigDecimal unitValue;

    /** 種類のマップ */
    private static final Map<String, TimeUnitTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final TimeUnitTypes type : TimeUnitTypes.values()) {
            TimeUnitTypes.VALUES_MAP.put(type.get(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *                  名称
     * @param value
     *                  値
     * @param unitName
     *                  単位名
     * @param unitValue
     *                  単位値
     */
    TimeUnitTypes(final String name, final String value, final String unitName, final BigDecimal unitValue) {

        this.name = name;
        this.value = value;
        this.unitName = unitName;
        this.unitValue = unitValue;
    }

    /**
     * 値に該当する種類を返す<br>
     * <p>
     * 但し、値が存在しない場合は、指定無し（NONE）を返す。
     * </p>
     * <br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return 種類。指定無し（NONE）：値が存在しない場合。
     */
    public static TimeUnitTypes getEnum(final String value) {

        TimeUnitTypes result = TimeUnitTypes.VALUES_MAP.get(value);
        if (result == null) {
            result = NONE;
            return result;
        }
        return result;
    }

    /**
     * 初期値の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 初期値
     */
    public static TimeUnitTypes getInitValue() {

        final TimeUnitTypes result = NONE;
        return result;

    }

    /**
     * デフォルトの種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return デフォルト値
     */
    public static TimeUnitTypes getDefault() {

        final TimeUnitTypes result = NONE;
        return result;
    }

    /**
     * 値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 値
     */
    @Override
    public String toString() {
        final String result = this.value;
        return result;
    }

    /**
     * 名称を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 名称
     */
    public String getName() {
        final String result = this.name;
        return result;
    }

    /**
     * 値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 値
     */
    public String getValue() {
        final String result = this.value;
        return result;
    }

    /**
     * 単位名を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 単位名
     */
    public String getUnitName() {
        final String result = this.unitName;
        return result;
    }

    /**
     * 単位値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 単位値
     */
    public BigDecimal getUnitValue() {
        final BigDecimal result = this.unitValue;
        return result;
    }

    /**
     * 種類の値<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 種類の値
     */
    @Override
    public String get() {
        final String result = this.value;
        return result;
    }
}
