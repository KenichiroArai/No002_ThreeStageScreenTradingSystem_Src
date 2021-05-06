package kmg.im.stock.tssts.data.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.tool.ssts.infrastructure.type.KmgString;
import kmg.tool.ssts.infrastructure.types.DelimiterTypes;

//TODO KenichiroArai 2021/05/06 株価時系列ＤＡＯにまとめる
/**
 * 株価データＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
public class StockPriceDataDao {

    /** 株価銘柄格納パス */
    @Value("${stockPriceStockStoragePath}")
    private Path stockPriceStockStoragePath;

    /**
     * 株価データパスを検索する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return パスのリスト
     */
    public List<Path> findAllStockPriceStockStoragePath() {

        List<Path> result = null;

        try {
            result = Files.list(this.stockPriceStockStoragePath).filter(Files::isReadable).collect(Collectors.toList());

        } catch (final IOException e) {
            {
                // TODO 2021/05/01 例外処理
                e.printStackTrace();
            }

        }

        return result;

    }

    /**
     * 株価時系列を検索する<br>
     * <p>
     * 株価データパスから株価時系列を検索する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataFilePath
     *                               株価データファイルパス
     * @return 株価時系列のリスト
     */
    @SuppressWarnings("static-method")
    public List<StockPriceTimeSeriesDto> findAllStockPriceTimeSeriesDtoList(final Path stockPriceDataFilePath) {

        final List<StockPriceTimeSeriesDto> result = new ArrayList<>();

        /* ファイルを読み込む */

        // TODO KenichiroArai 2021/05/01 列挙型
        try (final BufferedReader br = Files.newBufferedReader(stockPriceDataFilePath, Charset.forName("MS932"));) { //$NON-NLS-1$

            // 一行読み込む
            String line = br.readLine();
            if (KmgString.isEmpty(line)) {
                return result;
            }

            /* 先頭部分でデータがない部分を飛ばす */
            while ((line = br.readLine()) != null) {

                if (!line.endsWith(",,,,,")) { // TODO KenichiroArai 2021/05/01 定数
                    break;
                }

            }

            /* データがある箇所を株価時系列に変換し、リストに追加する */
            long no = 0;
            while ((line = br.readLine()) != null) {

                // データがないか
                if (line.endsWith(",,,,,")) { // TODO KenichiroArai 2021/05/01 定数
                    // ない場合

                    continue;
                }

                no++;

                // 株価時系列に変換する
                final String[] datas = DelimiterTypes.COMMA.split(line);
                // TODO KenichiroArai 2021/05/01 LocalDateのユーティリティに変える
                final StockPriceTimeSeriesDto sptsDto = new StockPriceTimeSeriesDto();
                sptsDto.setNo(no); // 番号
                try {
                    sptsDto.setDate(LocalDate.parse(datas[0], DateTimeFormatter.ofPattern("yyyy/MM/dd"))); // 日付
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
            // TODO KenichiroArai 2021/05/01 例外処理
            e.printStackTrace();
        }

        return result;

    }
}
