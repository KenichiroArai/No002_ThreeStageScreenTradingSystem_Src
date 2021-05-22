package kmg.im.stock.tssts.domain.logic;

import java.nio.file.Path;
import java.util.List;

import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * インポートロジックのインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface ImportLogic {

    /**
     * 株価銘柄格納パスリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄格納パスリスト
     */
    List<Path> getStockPriceStockStoragePathList();

    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * ファイルパスに該当する株価銘柄IDを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価銘柄ID
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long getStockBrandId(final Path filePath) throws TsstsDomainException;

    /**
     * 株価データリストを返す<br>
     * <p>
     * ファイルパスに該当する株価データリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価データリスト
     */
    List<StockPriceDataDto> getStockPriceDataDtoList(final Path filePath);

    /**
     * 日足の株価時系列を登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                              株価銘柄ID
     * @param stockPriceDataDtoList
     *                              株価データのリスト
     */
    void registerStockPriceDataOfDaily(final long stockBrandId, final List<StockPriceDataDto> stockPriceDataDtoList);

    /**
     * 週足の株価時系列を登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                              株価銘柄ID
     * @param stockPriceDataDtoList
     *                              株価データのリスト
     */
    void registerStockPriceDataOfWeekly(final long stockBrandId, final List<StockPriceDataDto> stockPriceDataDtoList);

    /**
     * 月足の株価時系列を登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                              株価銘柄ID
     * @param stockPriceDataDtoList
     *                              株価データのリスト
     */
    void registerStockPriceDataOfMonthly(final long stockBrandId, final List<StockPriceDataDto> stockPriceDataDtoList);

}
