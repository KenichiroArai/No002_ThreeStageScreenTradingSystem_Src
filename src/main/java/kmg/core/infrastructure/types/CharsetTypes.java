package kmg.core.infrastructure.types;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import kmg.core.infrastructure.type.KmgString;

/**
 * 文字セットの種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum CharsetTypes implements Supplier<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null),

    /** MS932 */
    MS932("MS932", "MS932"),

    /** UTF-8 */
    UTF8("UTF-8", "UTF-8"),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 文字セット */
    private Charset charset;

    /** 種類のマップ */
    private static final Map<String, CharsetTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final CharsetTypes type : CharsetTypes.values()) {
            CharsetTypes.VALUES_MAP.put(type.get(), type);
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
    CharsetTypes(final String name, final String value) {

        this.name = name;
        this.value = value;

        if (KmgString.isEmpty(this.value)) {
            this.charset = null;
        } else {
            this.charset = Charset.forName(this.value);
        }
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
    public static CharsetTypes getEnum(final String value) {

        CharsetTypes result = CharsetTypes.VALUES_MAP.get(value);
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
    public static CharsetTypes getInitValue() {

        final CharsetTypes result = NONE;
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
    public static CharsetTypes getDefault() {

        final CharsetTypes result = NONE;
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
     * 文字セットを返す<br>
     * <p>
     * NONEの場合は、nullを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 文字セット
     */
    public Charset toCharset() {
        final Charset result = this.charset;
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
