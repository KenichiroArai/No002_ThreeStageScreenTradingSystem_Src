package kmg.im.stock.tssts.infrastructure.type;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 三段階スクリーン・トレーディング・システムデシマル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class TsstsDecimal {

    /** 計算用ゼロ */
    public static final BigDecimal CALC_ZERO = BigDecimal.ZERO.setScale(TsstsDecimal.CALC_SCALE);

    /** 計算用スケール */
    private static final int CALC_SCALE = 15;

    /** 計算用丸めモード */
    private static final RoundingMode CALC_ROUNDING_MODE = RoundingMode.HALF_UP;

    /** 結果用ゼロ */
    public static final BigDecimal RESULT_ZERO = BigDecimal.ZERO.setScale(TsstsDecimal.RESULT_SCALE);

    /** 結果用スケール */
    private static final int RESULT_SCALE = 3;

    /** 結果用丸めモード */
    private static final RoundingMode RESULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    /** 値 */
    private BigDecimal value;

    /**
     * 計算用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return スケール設定後の巨大デシマル
     */
    public static BigDecimal setCalcScale(final BigDecimal value) {
        final BigDecimal result = value.setScale(TsstsDecimal.CALC_SCALE, TsstsDecimal.CALC_ROUNDING_MODE);
        return result;
    }

    /**
     * 計算用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return スケール設定後の巨大デシマル
     */
    public static BigDecimal setCalcScale(final double value) {
        final BigDecimal result = TsstsDecimal.setCalcScale(new BigDecimal(value));
        return result;
    }

    /**
     * 結果用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return スケール設定後の巨大デシマル
     */
    public static BigDecimal setResultScale(final BigDecimal value) {
        final BigDecimal result = value.setScale(TsstsDecimal.RESULT_SCALE, TsstsDecimal.RESULT_ROUNDING_MODE);
        return result;
    }

    /**
     * 結果用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     * @return スケール設定後の巨大デシマル
     */
    public static BigDecimal setResultScale(final double value) {
        final BigDecimal result = TsstsDecimal.setResultScale(new BigDecimal(value));
        return result;
    }

    /**
     * 除算する<br>
     * <p>
     * num1 ÷ num2
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param num1
     *             数値１
     * @param num2
     *             数値２
     * @return 除算結果
     */
    public static BigDecimal divide(final BigDecimal num1, final BigDecimal num2) {
        final BigDecimal result = num1.divide(num2, TsstsDecimal.CALC_SCALE, TsstsDecimal.CALC_ROUNDING_MODE);
        return result;
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     */
    public TsstsDecimal(final BigDecimal value) {
        this.setCalcScale();
    }

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param value
     *              値
     */
    public TsstsDecimal(final double value) {
        final String strVal = String.valueOf(value);
        this.value = new BigDecimal(strVal);
        this.setCalcScale();
    }

    /**
     * 値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 値
     */
    public BigDecimal getValue() {
        final BigDecimal result = this.value;
        return result;
    }

    /**
     * 計算用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private void setCalcScale() {
        this.value = TsstsDecimal.setCalcScale(this.value);
    }

    /**
     * 結果用のスケールを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    private void setResultScale() {
        this.value = TsstsDecimal.setResultScale(this.value);
    }

}
