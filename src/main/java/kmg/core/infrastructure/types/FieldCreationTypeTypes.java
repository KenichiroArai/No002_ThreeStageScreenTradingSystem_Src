package kmg.core.infrastructure.types;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import kmg.core.infrastructure.type.KmgString;

/**
 * フィールド作成の型の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum FieldCreationTypeTypes {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", KmgString.EMPTY, null),

    /** 4バイト整数 */
    INTEGER("4バイト整数", "4バイト整数", Integer.class),

    /** 8バイト整数 */
    LONG("8バイト整数", "8バイト整数", Long.class),

    /** 日付型 */
    DATE("日付型", "日付型", LocalDate.class),

    /** 日時型 */
    TIME("日時型", "日時型", LocalTime.class),

    /** 文字列型 */
    STRING("文字列型", "文字列型", String.class),

    /** 自動4バイト */
    SMALLSERIAL("自動4バイト", "自動4バイト", Integer.class),

    /** 自動8バイト */
    SERIAL("自動8バイト", "自動8バイト", Long.class),

    /** 4バイト実数 */
    FLOAT("4バイト実数", "4バイト実数", Float.class),

    /** 8バイト実数 */
    DOUBLE("8バイト実数", "8バイト実数", Double.class),

    /** 8バイト実数 */
    BIG_DECIMAL("8バイト実数", "8バイト実数", BigDecimal.class),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 型 */
    private Type type;

    /** 種類のマップ */
    private static final Map<String, FieldCreationTypeTypes> valuesMap = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final FieldCreationTypeTypes type : FieldCreationTypeTypes.values()) {
            FieldCreationTypeTypes.valuesMap.put(type.getValue(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     ** @param name
     *              名称
     * @param value
     *              値
     * @param type
     *              型
     */
    FieldCreationTypeTypes(final String name, final String value, final Type type) {

        this.name = name;
        this.value = value;
        this.type = type;

    }

    /**
     * 値に該当する種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     ** @param value
     *              値
     * @return 種類
     */
    public static FieldCreationTypeTypes getEnum(final String value) {

        final FieldCreationTypeTypes result = FieldCreationTypeTypes.valuesMap.get(value);
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
     * 値を返すvs
     *
     * @return 値
     */
    public String getValue() {

        final String result = this.value;
        return result;
    }

    /**
     * 型を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 型
     */
    public Type getType() {

        final Type result = this.type;
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

}
