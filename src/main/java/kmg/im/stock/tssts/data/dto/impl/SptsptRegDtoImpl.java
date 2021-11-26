package kmg.im.stock.tssts.data.dto.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import kmg.im.stock.tssts.data.dto.SptsptRegDto;

/**
 * 株価時系列期間の種類登録ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsptRegDtoImpl implements SptsptRegDto {

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

    /** 株銘柄ＩＤ */
    private Long stockBrandId;

    /** 期間の種類ID */
    private Long periodTypeId;

    /**
     * 識別番号を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param id
     *           識別番号
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
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
    @Override
    public String getName() {
        final String result = this.name;
        return result;
    }

    /**
     * 株価銘柄ＩＤを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株価銘柄ＩＤ
     */
    @Override
    public void setStockBrandId(final long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株価銘柄ＩＤを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄ＩＤ
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 期間の種類IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeId
     *                     期間の種類ID
     */
    @Override
    public void setPeriodTypeId(final Long periodTypeId) {
        this.periodTypeId = periodTypeId;
    }

    /**
     * 期間の種類IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類ID
     */
    @Override
    public Long getPeriodTypeId() {
        final Long result = this.periodTypeId;
        return result;
    }

}
