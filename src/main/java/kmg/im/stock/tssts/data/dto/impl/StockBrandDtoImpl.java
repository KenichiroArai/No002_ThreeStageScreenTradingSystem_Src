package kmg.im.stock.tssts.data.dto.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import kmg.im.stock.tssts.data.dto.StockBrandDto;

/**
 * 株銘柄ＤＴＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockBrandDtoImpl implements StockBrandDto {

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
    private LocalTime createdDate;

    /** 更新者 */
    private String updater;

    /** 更新日 */
    private LocalTime updateDate;

    /** 備考 */
    private String note;

    /** 名称 */
    private String name;

    /** 株市場ID */
    private Long stockMarketId;

    /** コード */
    private Long code;

    /** 業種 */
    private String industryType;

    /** 特色 */
    private String characteristic;

    /** 決算月 */
    private String settlementOfAccountsMonth;

    /** 設立日 */
    private LocalDate establishmentDay;

    /** 上場日 */
    private LocalDate listedDay;

    /** 株主数 */
    private Long stockholdersNumber;

    /** 株式数 */
    private Long stocksNumber;

    /** 単元数 */
    private Long unitsNumber;

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
    public void setCreatedDate(final LocalTime createdDate) {
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
    public LocalTime getCreatedDate() {
        final LocalTime result = this.createdDate;
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
    public void setUpdateDate(final LocalTime updateDate) {
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
    public LocalTime getUpdateDate() {
        final LocalTime result = this.updateDate;
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
     * 株市場IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockMarketId
     *                      株市場ID
     */
    @Override
    public void setStockMarketId(final Long stockMarketId) {
        this.stockMarketId = stockMarketId;
    }

    /**
     * 株市場IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株市場ID
     */
    @Override
    public Long getStockMarketId() {
        final Long result = this.stockMarketId;
        return result;
    }

    /**
     * コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param code
     *             コード
     */
    @Override
    public void setCode(final Long code) {
        this.code = code;
    }

    /**
     * コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return コード
     */
    @Override
    public Long getCode() {
        final Long result = this.code;
        return result;
    }

    /**
     * 業種を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param industryType
     *                     業種
     */
    @Override
    public void setIndustryType(final String industryType) {
        this.industryType = industryType;
    }

    /**
     * 業種を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 業種
     */
    @Override
    public String getIndustryType() {
        final String result = this.industryType;
        return result;
    }

    /**
     * 特色を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param characteristic
     *                       特色
     */
    @Override
    public void setCharacteristic(final String characteristic) {
        this.characteristic = characteristic;
    }

    /**
     * 特色を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 特色
     */
    @Override
    public String getCharacteristic() {
        final String result = this.characteristic;
        return result;
    }

    /**
     * 決算月を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param settlementOfAccountsMonth
     *                                  決算月
     */
    @Override
    public void setSettlementOfAccountsMonth(final String settlementOfAccountsMonth) {
        this.settlementOfAccountsMonth = settlementOfAccountsMonth;
    }

    /**
     * 決算月を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 決算月
     */
    @Override
    public String getSettlementOfAccountsMonth() {
        final String result = this.settlementOfAccountsMonth;
        return result;
    }

    /**
     * 設立日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param establishmentDay
     *                         設立日
     */
    @Override
    public void setEstablishmentDay(final LocalDate establishmentDay) {
        this.establishmentDay = establishmentDay;
    }

    /**
     * 設立日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 設立日
     */
    @Override
    public LocalDate getEstablishmentDay() {
        final LocalDate result = this.establishmentDay;
        return result;
    }

    /**
     * 上場日を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param listedDay
     *                  上場日
     */
    @Override
    public void setListedDay(final LocalDate listedDay) {
        this.listedDay = listedDay;
    }

    /**
     * 上場日を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 上場日
     */
    @Override
    public LocalDate getListedDay() {
        final LocalDate result = this.listedDay;
        return result;
    }

    /**
     * 株主数を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockholdersNumber
     *                           株主数
     */
    @Override
    public void setStockholdersNumber(final Long stockholdersNumber) {
        this.stockholdersNumber = stockholdersNumber;
    }

    /**
     * 株主数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株主数
     */
    @Override
    public Long getStockholdersNumber() {
        final Long result = this.stockholdersNumber;
        return result;
    }

    /**
     * 株式数を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stocksNumber
     *                     株式数
     */
    @Override
    public void setStocksNumber(final Long stocksNumber) {
        this.stocksNumber = stocksNumber;
    }

    /**
     * 株式数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株式数
     */
    @Override
    public Long getStocksNumber() {
        final Long result = this.stocksNumber;
        return result;
    }

    /**
     * 単元数を設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param unitsNumber
     *                    単元数
     */
    @Override
    public void setUnitsNumber(final Long unitsNumber) {
        this.unitsNumber = unitsNumber;
    }

    /**
     * 単元数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 単元数
     */
    @Override
    public Long getUnitsNumber() {
        final Long result = this.unitsNumber;
        return result;
    }

}
