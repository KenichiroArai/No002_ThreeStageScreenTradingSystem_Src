package kmg.im.stock.tssts.data.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import kmg.core.infrastructure.type.KmgString;
import kmg.core.infrastructure.types.DelimiterTypes;
import kmg.core.infrastructure.utils.LocalDateUtils;
import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.CharsetTypes;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 株価データＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
public class StockPriceDataDao {

    /** データなしの行末尾文字列 */
    private static final String LINE_ENDING_STRING_WITH_NO_DATA = ",,,,,"; //$NON-NLS-1$

    /** 株価銘柄格納パス */
    @Value("${import.path.stockpricestockstoragepath}")
    private Path stockPriceStockStoragePath;

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                           ログメッセージリソルバ
     */
    public StockPriceDataDao(final LogMessageResolver logMessageResolver) {
        this.logMessageResolver = logMessageResolver;
    }

    /**
     * 株価銘柄格納パスの検索する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return パスのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    public List<Path> findOfStockPriceStockStoragePath() throws TsstsDomainException {

        List<Path> result = null;

        try {
            result = Files.list(this.stockPriceStockStoragePath).filter(Files::isReadable).collect(Collectors.toList());

        } catch (final IOException e) {

            // TODO KenichiroArai 2021/05/23 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
        }

        return result;

    }

    /**
     * 株価データを検索する<br>
     * <p>
     * 株価データパスから株価データを検索する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataFilePath
     *                               株価データファイルパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     * @return 株価データのリスト
     */
    public List<StockPriceDataDto> findAllStockPriceDataList(final Path stockPriceDataFilePath)
        throws TsstsDomainException {

        final List<StockPriceDataDto> result = new ArrayList<>();

        /* ファイルを読み込む */

        try (final BufferedReader br = Files.newBufferedReader(stockPriceDataFilePath,
            CharsetTypes.MS932.toCharset());) {

            // 一行読み込む
            String line = br.readLine();
            if (KmgString.isEmpty(line)) {
                return result;
            }

            /* 先頭部分でデータがない部分を飛ばす */
            while ((line = br.readLine()) != null) {

                if (!line.endsWith(StockPriceDataDao.LINE_ENDING_STRING_WITH_NO_DATA)) {

                    break;
                }

            }

            /* データがある箇所を株価時系列に変換し、リストに追加する */
            long no = 0;
            while ((line = br.readLine()) != null) {

                // データがないか
                if (line.endsWith(StockPriceDataDao.LINE_ENDING_STRING_WITH_NO_DATA)) {
                    // ない場合

                    continue;
                }

                no++;

                // 株価時系列に変換する
                final String[] datas = DelimiterTypes.COMMA.split(line);
                final StockPriceDataDto sptsDto = new StockPriceDataDto();
                sptsDto.setNo(no); // 番号
                try {
                    sptsDto.setDate(LocalDateUtils.parseYyyyMmDd(datas[0])); // 日付
                    sptsDto.setOp(new BigDecimal(datas[1])); // 始値
                    sptsDto.setHp(new BigDecimal(datas[2])); // 高値
                    sptsDto.setLp(new BigDecimal(datas[3])); // 安値
                    sptsDto.setCp(new BigDecimal(datas[4])); // 終値
                    sptsDto.setVolume(Long.parseLong(datas[5])); // 出来高
                } catch (final ArrayIndexOutOfBoundsException e) {
                    // TODO KenichiroArai 2021/05/01 例外処理
                    System.err.println(line);
                    e.printStackTrace();
                }

                // リストに追加する
                result.add(sptsDto);

            }

        } catch (final IOException e) {

            // TODO KenichiroArai 2021/05/23 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
        }

        return result;

    }
}
