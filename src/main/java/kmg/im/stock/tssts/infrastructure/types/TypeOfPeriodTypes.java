package kmg.im.stock.tssts.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

/**
 * 期間の種類の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum TypeOfPeriodTypes {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", -1L),

    /** 日足 */
    DAILY("日足", 1L),

    /** 週足 */
    WEEKLY("週足", 2L),

    /** 月足 */
    MONTHLY("月足", 3L),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private Long value;

    /** 種類のマップ */
    private static final Map<Long, TypeOfPeriodTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final TypeOfPeriodTypes type : TypeOfPeriodTypes.values()) {
            TypeOfPeriodTypes.VALUES_MAP.put(type.getValue(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *              名称
     * @param value
     *              値
     */
    TypeOfPeriodTypes(final String name, final Long value) {

        this.name = name;
        this.value = value;

    }

    /**
     * 値に該当する種類を返す<br>
     * <p>
     * 但し、値が存在しない場合は、指定無し（NONE）を返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return 種類。指定無し（NONE）：値が存在しない場合。
     */
    public static TypeOfPeriodTypes getEnum(final Long value) {

        TypeOfPeriodTypes result = TypeOfPeriodTypes.VALUES_MAP.get(value);
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
    public static TypeOfPeriodTypes getInitValue() {

        final TypeOfPeriodTypes result = NONE;
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
    public static TypeOfPeriodTypes getDefault() {

        final TypeOfPeriodTypes result = NONE;
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
        final String result = this.value.toString();
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
    public Long getValue() {
        final Long result = this.value;
        return result;
    }
}
