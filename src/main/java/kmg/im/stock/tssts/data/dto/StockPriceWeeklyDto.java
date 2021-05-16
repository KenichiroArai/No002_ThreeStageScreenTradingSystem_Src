package kmg.im.stock.tssts.data.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 株価週足ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
//TODO KenichiroArai 2021/05/16 ベースとなるクラスを作成する
public class StockPriceWeeklyDto {

    /** 識別番号 */
    private Long id;

    /** 開始日 */
    private LocalDate startDate;

    /** 終了日 */
    private LocalDate endDate;

    /** ロケールID */
    private String localeId;

    /** 作成者 */
    private String creator;

    /** 作成日 */
    private LocalDateTime createdDate;

    /** 更新者 */
    private String updater;

    /** 更新日 */
    private LocalDateTime updateDate;

    /** 備考 */
    private String note;

    /** 名称 */
    private String name;

    /** 株銘柄ID */
    private Long stockBrandId;

    /** 番号 */
    private Long no;

    /** 週開始日付 */
    private LocalDate weeklyStartDate;

    /** 週終了日付 */
    private LocalDate weeklyEndDate;

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
     * 識別番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param id
     *           識別番号
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * 識別番号を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 識別番号
     */
    public Long getId() {
        final Long result = this.id;
        return result;
    }

    /**
     * 開始日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param startDate
     *                  開始日
     */
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * 開始日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 開始日
     */
    public LocalDate getStartDate() {
        final LocalDate result = this.startDate;
        return result;
    }

    /**
     * 終了日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param endDate
     *                終了日
     */
    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * 終了日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 終了日
     */
    public LocalDate getEndDate() {
        final LocalDate result = this.endDate;
        return result;
    }

    /**
     * ロケールIDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param localeId
     *                 ロケールID
     */
    public void setLocaleId(final String localeId) {
        this.localeId = localeId;
    }

    /**
     * ロケールIDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return ロケールID
     */
    public String getLocaleId() {
        final String result = this.localeId;
        return result;
    }

    /**
     * 作成者を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param creator
     *                作成者
     */
    public void setCreator(final String creator) {
        this.creator = creator;
    }

    /**
     * 作成者を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 作成者
     */
    public String getCreator() {
        final String result = this.creator;
        return result;
    }

    /**
     * 作成日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param createdDate
     *                    作成日
     */
    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 作成日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 作成日
     */
    public LocalDateTime getCreatedDate() {
        final LocalDateTime result = this.createdDate;
        return result;
    }

    /**
     * 更新者を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param updater
     *                更新者
     */
    public void setUpdater(final String updater) {
        this.updater = updater;
    }

    /**
     * 更新者を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 更新者
     */
    public String getUpdater() {
        final String result = this.updater;
        return result;
    }

    /**
     * 更新日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param updateDate
     *                   更新日
     */
    public void setUpdateDate(final LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 更新日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 更新日
     */
    public LocalDateTime getUpdateDate() {
        final LocalDateTime result = this.updateDate;
        return result;
    }

    /**
     * 備考を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param note
     *             備考
     */
    public void setNote(final String note) {
        this.note = note;
    }

    /**
     * 備考を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 備考
     */
    public String getNote() {
        final String result = this.note;
        return result;
    }

    /**
     * 名称を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param name
     *             名称
     */
    public void setName(final String name) {
        this.name = name;
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
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    public void setStockBrandId(final Long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    public Long getStockBrandId() {
        final Long result = this.stockBrandId;
        return result;
    }

    /**
     * 番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     */
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
    public Long getNo() {
        final Long result = this.no;
        return result;
    }

    /**
     * 週開始日付を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param weeklyStartDate
     *                        週開始日付
     */
    public void setWeeklyStartDate(final LocalDate weeklyStartDate) {
        this.weeklyStartDate = weeklyStartDate;
    }

    /**
     * 週開始日付を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 週開始日付
     */
    public LocalDate getWeeklyStartDate() {
        final LocalDate result = this.weeklyStartDate;
        return result;
    }

    /**
     * 週終了日付を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param weeklyEndDate
     *                      週終了日付
     */
    public void setWeeklyEndDate(final LocalDate weeklyEndDate) {
        this.weeklyEndDate = weeklyEndDate;
    }

    /**
     * 週終了日付を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 週終了日付
     */
    public LocalDate getWeeklyEndDate() {
        final LocalDate result = this.weeklyEndDate;
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
    public Long getVolume() {
        final Long result = this.volume;
        return result;
    }

}
