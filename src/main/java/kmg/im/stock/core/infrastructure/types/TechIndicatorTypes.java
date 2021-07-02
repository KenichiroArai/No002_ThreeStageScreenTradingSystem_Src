package kmg.im.stock.core.infrastructure.types;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * テクニカル指標の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum TechIndicatorTypes implements Supplier<Long> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", -1, CodeValueTypes.NONE),

    /** 単純移動平均（ＳＭＡ） */
    SMA("単純移動平均（ＳＭＡ）", 1, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** 指数移動平均（ＥＭＡ） */
    EMA("指数移動平均（ＥＭＡ）", 2, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ＭＡＣＤ */
    MACD("ＭＡＣＤ", 3, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ＭＣＡＤヒストグラム */
    MCADH("ＭＣＡＤヒストグラム", 4, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ディレクショナル・システム */
    DS("ディレクショナル・システム", 5, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ＯＢＶ（オン・バランス・ボリューム） */
    OBV("ＯＢＶ（オン・バランス・ボリューム）", 6, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** アキュミュレーション */
    ACCUMULATION("アキュミュレーション", 7, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ディストリビューション */
    DISTRIBUTION("ディストリビューション", 8, CodeValueTypes.TREND_FOLLOW_TYPE),

    /** ストキャスティック */
    DTOCASTER("ストキャスティック", 9, CodeValueTypes.OSCILLATOR),

    /** ＲＯＣ（スムーズ化された価格変化率） */
    ROC("ＲＯＣ（スムーズ化された価格変化率）", 10, CodeValueTypes.OSCILLATOR),

    /** モメンタム */
    MOMENTUM("モメンタム", 11, CodeValueTypes.OSCILLATOR),

    /** ＲＳＩ（相対力指数） */
    RSI("ＲＳＩ（相対力指数）", 12, CodeValueTypes.OSCILLATOR),

    /** エルダー線 */
    EL("エルダー線", 13, CodeValueTypes.OSCILLATOR),

    /** 勢力指数 */
    PI("勢力指数", 14, CodeValueTypes.OSCILLATOR),

    /** ウィリアムズの％Ｒ */
    WMPR("ウィリアムズの％Ｒ", 15, CodeValueTypes.OSCILLATOR),

    /** コモディティー・チャネル指数 */
    CCI("コモディティー・チャネル指数", 16, CodeValueTypes.OSCILLATOR),

    /** 新高値―新安値指数 */
    NHNLI("新高値―新安値指数", 17, CodeValueTypes.OTHER),

    /** プット―コール比率 */
    PCR("プット―コール比率", 18, CodeValueTypes.OTHER),

    /** 強気のコンセンサス */
    BC("強気のコンセンサス", 19, CodeValueTypes.OTHER),

    /** ＣｏＴ（コミットメント・オブ・トレーダーズ） */
    COT("ＣｏＴ（コミットメント・オブ・トレーダーズ）", 20, CodeValueTypes.OTHER),

    /** 騰落指数 */
    RFI("騰落指数", 21, CodeValueTypes.OTHER),

    /** トレーダー指数 */
    TI("トレーダー指数", 22, CodeValueTypes.OTHER),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private Long value;

    /** コード値の種類 */
    private CodeValueTypes codeValueTypes;

    /** 種類のマップ */
    private static final Map<Long, TechIndicatorTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final TechIndicatorTypes type : TechIndicatorTypes.values()) {
            TechIndicatorTypes.VALUES_MAP.put(type.get(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *                       名称
     * @param value
     *                       値
     * @param codeValueTypes
     *                       コード値の種類
     */
    TechIndicatorTypes(final String name, final long value, final CodeValueTypes codeValueTypes) {

        this.name = name;
        this.value = value;
        this.codeValueTypes = codeValueTypes;

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
    public static TechIndicatorTypes getEnum(final Long value) {

        TechIndicatorTypes result = TechIndicatorTypes.VALUES_MAP.get(value);
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
    public static TechIndicatorTypes getInitValue() {

        final TechIndicatorTypes result = NONE;
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
    public static TechIndicatorTypes getDefault() {

        final TechIndicatorTypes result = NONE;
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
     * コード値の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return コード値の種類
     */
    public CodeValueTypes getCodeValueTypes() {
        final CodeValueTypes result = this.codeValueTypes;
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
