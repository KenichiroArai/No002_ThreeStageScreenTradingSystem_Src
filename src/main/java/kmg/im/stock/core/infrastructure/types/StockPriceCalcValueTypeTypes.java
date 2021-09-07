package kmg.im.stock.core.infrastructure.types;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 株価計算値の種類の種類<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SuppressWarnings("nls")
public enum StockPriceCalcValueTypeTypes implements Supplier<Long> {

    /* 定義：開始 */

    /** 指定無し */
    NONE("指定無し", -1, TechIndicatorTypes.NONE),

    /** ＭＣＡＤライン */
    MCADL("ＭＣＡＤライン", 1, TechIndicatorTypes.MACD),

    /** ＭＣＡＤシグナル */
    MCADS("ＭＣＡＤシグナル", 2, TechIndicatorTypes.MACD),

    /** ＭＣＡＤヒストグラム */
    MCADH("ＭＣＡＤヒストグラム", 3, TechIndicatorTypes.MCADH),

    /** 勢力指数 */
    PI("勢力指数", 4, TechIndicatorTypes.PI),

    /** 勢力指数２ＥＭＡ */
    PI2EMA("勢力指数２ＥＭＡ", 5, TechIndicatorTypes.PI),

    /** 勢力指数１３ＥＭＡ */
    PI13EMA("勢力指数１３ＥＭＡ", 6, TechIndicatorTypes.PI),

    /** 過去３期間の最安値 */
    LOWEST_PRICE_IN_LAST3_PERIODS("過去３期間の最安値", 7, TechIndicatorTypes.LOWEST_PRICE_IN_PAST),

    /* 定義：終了 */
    ;

    /** 名称 */
    private String name;

    /** 値 */
    private Long value;

    /** テクニカル指標の種類 */
    private TechIndicatorTypes techIndicatorTypes;

    /** 種類のマップ */
    private static final Map<Long, StockPriceCalcValueTypeTypes> VALUES_MAP = new HashMap<>();

    static {

        /* 種類のマップにプット */
        for (final StockPriceCalcValueTypeTypes type : StockPriceCalcValueTypeTypes.values()) {
            StockPriceCalcValueTypeTypes.VALUES_MAP.put(type.get(), type);
        }
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *                           名称
     * @param value
     *                           値
     * @param techIndicatorTypes
     *                           テクニカル指標の種類
     */
    StockPriceCalcValueTypeTypes(final String name, final long value, final TechIndicatorTypes techIndicatorTypes) {

        this.name = name;
        this.value = value;
        this.techIndicatorTypes = techIndicatorTypes;

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
    public static StockPriceCalcValueTypeTypes getEnum(final Long value) {

        StockPriceCalcValueTypeTypes result = StockPriceCalcValueTypeTypes.VALUES_MAP.get(value);
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
    public static StockPriceCalcValueTypeTypes getInitValue() {

        final StockPriceCalcValueTypeTypes result = NONE;
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
    public static StockPriceCalcValueTypeTypes getDefault() {

        final StockPriceCalcValueTypeTypes result = NONE;
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
     * テクニカル指標の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return テクニカル指標の種類
     */
    public TechIndicatorTypes getTechIndicatorTypes() {
        final TechIndicatorTypes result = this.techIndicatorTypes;
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
