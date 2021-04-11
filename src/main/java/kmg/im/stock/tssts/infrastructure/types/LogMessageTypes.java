package kmg.im.stock.tssts.infrastructure.types;

import java.util.HashMap;
import java.util.Map;

/**
 * ログメッセージの種類
 */
@SuppressWarnings("nls")
public enum LogMessageTypes {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null),

    /** 三段階スクリーン・トレーディング・システムＤＡＯ */
    I00001("三段階スクリーン・トレーディング・システムＤＡＯ", "I00001"),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 種類のマップ */
    private static final Map<String, LogMessageTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final LogMessageTypes type : LogMessageTypes.values()) {
            LogMessageTypes.VALUES_MAP.put(type.getValue(), type);
        }
    }

    /**
     * コンストラクタ
     *
     * @param name
     *              名称
     * @param value
     *              値
     */
    LogMessageTypes(final String name, final String value) {

        this.name = name;
        this.value = value;

    }

    /**
     * 値に該当する種類を返す。<br>
     * 但し、値が存在しない場合は、指定無し（NONE）を返す。
     *
     * @param value
     *              値
     * @return 種類。指定無し（NONE）：値が存在しない場合。
     */
    public static LogMessageTypes getEnum(final String value) {

        LogMessageTypes result = LogMessageTypes.VALUES_MAP.get(value);
        if (result == null) {
            result = NONE;
            return result;
        }
        return result;
    }

    /**
     * 初期値の種類を返す。
     *
     * @return 初期値
     */
    public static LogMessageTypes getInitValue() {
        return NONE;

    }

    /**
     * デフォルトの種類を返す。
     *
     * @return デフォルト値
     */
    public static LogMessageTypes getDefault() {
        return NONE;
    }

    /**
     * 名称を返す。
     *
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 値を返す。
     *
     * @return 値
     */
    public String getValue() {
        return this.value;
    }

    /**
     * 値を返す。
     *
     * @return 値
     */
    @Override
    public String toString() {
        return this.value;
    }
}
