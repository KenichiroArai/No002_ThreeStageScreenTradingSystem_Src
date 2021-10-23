package kmg.im.stock.tssts.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 株価生データ取得モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SpRawDataAcqModel {

    /**
     * 番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     */
    void setNo(final Long no);

    /**
     * 番号を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 番号
     */
    Long getNo();

    /**
     * 日付を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param date
     *             日付
     */
    void setDate(final LocalDate date);

    /**
     * 日付を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 日付
     */
    LocalDate getDate();

    /**
     * 始値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param op
     *           始値
     */
    void setOp(final BigDecimal op);

    /**
     * 始値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 始値
     */
    BigDecimal getOp();

    /**
     * 高値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param hp
     *           高値
     */
    void setHp(final BigDecimal hp);

    /**
     * 高値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 高値
     */
    BigDecimal getHp();

    /**
     * 安値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lp
     *           安値
     */
    void setLp(final BigDecimal lp);

    /**
     * 安値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 安値
     */
    BigDecimal getLp();

    /**
     * 終値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param cp
     *           終値
     */
    void setCp(final BigDecimal cp);

    /**
     * 終値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終値
     */
    BigDecimal getCp();

    /**
     * 出来高を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param volume
     *               出来高
     */
    void setVolume(final Long volume);

    /**
     * 出来高を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 出来高
     */
    Long getVolume();

}
