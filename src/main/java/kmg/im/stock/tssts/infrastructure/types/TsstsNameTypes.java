package kmg.im.stock.tssts.infrastructure.types;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 三段階スクリーン・トレーディング・システム名称の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum TsstsNameTypes implements Supplier<String> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", null),

    /** ディレクトリ選択 */
    KMG_IM_STOCK_TSSTS_NAME10001("ディレクトリ選択", "KMG_IM_STOCK_TSSTS_NAME10001"),

    /** エラー */
    KMG_IM_STOCK_TSSTS_NAME10002("エラー", "KMG_IM_STOCK_TSSTS_NAME10002"),

    /** ファイル選択 */
    KMG_IM_STOCK_TSSTS_NAME10003("ファイル選択", "KMG_IM_STOCK_TSSTS_NAME10003"),

    /** エラー */
    KMG_IM_STOCK_TSSTS_NAME10004("エラー", "KMG_IM_STOCK_TSSTS_NAME10004"),

    /** エラー */
    KMG_IM_STOCK_TSSTS_NAME10005("エラー", "KMG_IM_STOCK_TSSTS_NAME10005"),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private String value;

    /** 種類のマップ */
    private static final Map<String, TsstsNameTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final TsstsNameTypes type : TsstsNameTypes.values()) {
            TsstsNameTypes.VALUES_MAP.put(type.get(), type);
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
    TsstsNameTypes(final String name, final String value) {

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
    public static TsstsNameTypes getEnum(final String value) {

        TsstsNameTypes result = TsstsNameTypes.VALUES_MAP.get(value);
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
    public static TsstsNameTypes getInitValue() {

        final TsstsNameTypes result = NONE;
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
    public static TsstsNameTypes getDefault() {

        final TsstsNameTypes result = NONE;
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
