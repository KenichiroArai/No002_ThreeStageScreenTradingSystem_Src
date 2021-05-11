package kmg.core.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

/**
 * テンプレートの種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum TemplateTypes {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 種類のマップ */
    private static final Map<String, TemplateTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final TemplateTypes type : TemplateTypes.values()) {
            TemplateTypes.VALUES_MAP.put(type.getValue(), type);
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
    TemplateTypes(final String name, final String value) {

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
    public static TemplateTypes getEnum(final String value) {

        TemplateTypes result = TemplateTypes.VALUES_MAP.get(value);
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
    public static TemplateTypes getInitValue() {

        final TemplateTypes result = NONE;
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
    public static TemplateTypes getDefault() {

        final TemplateTypes result = NONE;
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
        final String result = this.name;
        return result;
    }
}
