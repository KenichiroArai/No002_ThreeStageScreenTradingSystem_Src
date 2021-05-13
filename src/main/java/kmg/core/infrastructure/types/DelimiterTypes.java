package kmg.core.infrastructure.types;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kmg.core.infrastructure.type.KmgString;

/**
 * 区切り文字の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum DelimiterTypes {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null),

    /** ピリオド */
    PERIOD("ピリオド", "."),

    /** カンマ */
    COMMA("カンマ", ","),

    /** コロン */
    COLON("コロン", "."),

    /** バーティカルバー */
    VERTICAL_BAR("バーティカルバー", "|"),

    /** アンダースコア */
    UNDERSCORE("アンダースコア", "_"),

    /** スラッシュ */
    SLASH("スラッシュ", "/"),

    /** ハイフン */
    HYPHEN("ハイフン", "-"),

    /** 半角スペース */
    HALF_SPACE("半角スペース", KmgString.HALF_SPACE),

    /** プラス */
    PLUS("プラス", "+"),

    /** 全角コロン */
    ALL_COLON("全角コロン", "："),

    /** 全角読点 */
    ALL_IDEOGRAPHIC("全角読点", "、"),

    /** 連続半角スペース */
    SERIES_HALF_SPACE("連続半角スペース", "\\s+"),

    /** 改行 */
    LINE_SEPARATOR("改行", KmgString.LINE_SEPARATOR),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 種類のマップ */
    private static final Map<String, DelimiterTypes> valuesMap = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final DelimiterTypes type : DelimiterTypes.values()) {
            DelimiterTypes.valuesMap.put(type.getValue(), type);

        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @since 1.0.0
     * @version 1.0.0
     * @param name
     *              名称
     * @param value
     *              値
     */
    DelimiterTypes(final String name, final String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * 値に該当する種類を返す<br>
     * <p>
     * 但し、値が存在しない場合は、指定無し（NONE）を返す
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return 種類。指定無し（NONE）：値が存在しない場合
     */
    public static DelimiterTypes getEnum(final String value) {

        DelimiterTypes result = DelimiterTypes.valuesMap.get(value);
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
    public static DelimiterTypes getInitValue() {

        final DelimiterTypes result = NONE;
        return result;

    }

    /**
     * デフォルトの種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 初期値
     */
    public static DelimiterTypes getDefault() {

        final DelimiterTypes result = NONE;
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
     * 結合する文字列リストにデリミタを付加して文字列を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param <T>
     *                   結合する文字列の型
     * @param targetList
     *                   結合する文字列リスト
     * @return 結合する文字列リストにデリミタを付加した文字列
     */
    public <T> String join(final List<T> targetList) {
        final String result = this.join(targetList.toArray(new Object[0]));
        return result;
    }

    /**
     * 結合する文字列にデリミタを付加して文字列を返す<br>
     * <p>
     * 但し、結合する文字列がnullまたは空文字の場合は結合しない
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param targets
     *                結合する文字列
     * @return 結合する文字列にデリミタを付加した文字列
     */
    public String join(final Object... targets) {

        // TODO KenichiroArai 2021/05/01 ストリーム形式を検討する。

        String result = null;

        final StringBuilder sb = new StringBuilder();
        for (final Object target : targets) {
            if (target == null) {
                continue;
            }
            if (KmgString.isEmpty(target.toString())) {
                continue;
            }

            sb.append(target.toString());
            sb.append(this.value);
        }

        if (sb.length() > 0) {
            result = sb.substring(0, sb.length() - 1).toString();
        }

        return result;
    }

    /**
     * 分割する文字列を分割し、文字列の配列にして返す。<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param target
     *               分割する文字列
     * @return 分割した文字列の配列
     */
    public String[] split(final String target) {

        String[] result = null;
        if (KmgString.isEmpty(target)) {
            return result;
        }

        result = target.split(this.value);

        return result;
    }

}
