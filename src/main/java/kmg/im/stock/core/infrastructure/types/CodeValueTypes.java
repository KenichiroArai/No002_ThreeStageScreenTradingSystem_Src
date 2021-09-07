package kmg.im.stock.core.infrastructure.types;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * コード値の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum CodeValueTypes implements Supplier<Long> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", -1, CodeKindTypes.NONE),

    /** 日足 */
    DAILY("日足", 1, CodeKindTypes.TYPE_OF_PERIOD),

    /** 週足 */
    WEEKLY("週足", 2, CodeKindTypes.TYPE_OF_PERIOD),

    /** 月足 */
    MONTHLY("月足", 3, CodeKindTypes.TYPE_OF_PERIOD),

    /** トレンドフォロー型 */
    TREND_FOLLOW_TYPE("トレンドフォロー型", 4, CodeKindTypes.INDICATOR_TYPE),

    /** オシレーター */
    OSCILLATOR("オシレーター", 5, CodeKindTypes.INDICATOR_TYPE),

    /** 計算その他 */
    CALC_OTHER("計算その他", 6, CodeKindTypes.INDICATOR_TYPE),

    /** 統計 */
    STATISTICS("統計", 7, CodeKindTypes.INDICATOR_TYPE),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private Long value;

    /** コード種類の種類 */
    private CodeKindTypes codeKindTypes;

    /** 種類のマップ */
    private static final Map<Long, CodeValueTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final CodeValueTypes type : CodeValueTypes.values()) {
            CodeValueTypes.VALUES_MAP.put(type.get(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *                      名称
     * @param value
     *                      値
     * @param codeKindTypes
     *                      コード種類の種類
     */
    CodeValueTypes(final String name, final long value, final CodeKindTypes codeKindTypes) {

        this.name = name;
        this.value = value;
        this.codeKindTypes = codeKindTypes;

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
    public static CodeValueTypes getEnum(final Long value) {

        CodeValueTypes result = CodeValueTypes.VALUES_MAP.get(value);
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
    public static CodeValueTypes getInitValue() {

        final CodeValueTypes result = NONE;
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
    public static CodeValueTypes getDefault() {

        final CodeValueTypes result = NONE;
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
        final long result = this.value;
        return result;
    }

    /**
     * コード種類の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return コード種類の種類
     */
    public CodeKindTypes getCodeKindTypes() {
        final CodeKindTypes result = this.codeKindTypes;
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
    public Long get() {
        final long result = this.value;
        return result;
    }

}
