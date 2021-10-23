package kmg.im.stock.tssts.data.dto.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import kmg.im.stock.tssts.data.dto.SpRawDataAcqDto;

/**
 * 株価生データ取得ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SpRawDataAcqDtoImpl implements SpRawDataAcqDto {

    /** 番号 */
    private Long no;

    /** 日付 */
    private LocalDate date;

    /** 始値 */
    private BigDecimal op;

    /** 高値 */
    private BigDecimal hp;

    /** 安値 */
    private BigDecimal lp;

    /** 終値 */
    private BigDecimal cp;

    /** 出来高 */
    private Long volume;

    /**
     * 番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     */
    @Override
    public void setNo(final Long no) {
        this.no = no;
    }

    /**
     * 番号を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 番号
     */
    @Override
    public Long getNo() {
        final Long result = this.no;
        return result;
    }

    /**
     * 日付を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param date
     *             日付
     */
    @Override
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * 日付を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 日付
     */
    @Override
    public LocalDate getDate() {
        final LocalDate result = this.date;
        return result;
    }

    /**
     * 始値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param op
     *           始値
     */
    @Override
    public void setOp(final BigDecimal op) {
        this.op = op;
    }

    /**
     * 始値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 始値
     */
    @Override
    public BigDecimal getOp() {
        final BigDecimal result = this.op;
        return result;
    }

    /**
     * 高値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param hp
     *           高値
     */
    @Override
    public void setHp(final BigDecimal hp) {
        this.hp = hp;
    }

    /**
     * 高値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 高値
     */
    @Override
    public BigDecimal getHp() {
        final BigDecimal result = this.hp;
        return result;
    }

    /**
     * 安値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lp
     *           安値
     */
    @Override
    public void setLp(final BigDecimal lp) {
        this.lp = lp;
    }

    /**
     * 安値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 安値
     */
    @Override
    public BigDecimal getLp() {
        final BigDecimal result = this.lp;
        return result;
    }

    /**
     * 終値を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param cp
     *           終値
     */
    @Override
    public void setCp(final BigDecimal cp) {
        this.cp = cp;
    }

    /**
     * 終値を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終値
     */
    @Override
    public BigDecimal getCp() {
        final BigDecimal result = this.cp;
        return result;
    }

    /**
     * 出来高を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param volume
     *               出来高
     */
    @Override
    public void setVolume(final Long volume) {
        this.volume = volume;
    }

    /**
     * 出来高を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 出来高
     */
    @Override
    public Long getVolume() {
        final Long result = this.volume;
        return result;
    }

}
