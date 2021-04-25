package kmg.im.stock.tssts.data.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

/**
 * 三段階スクリーン・トレーディング・システムＤＡＯ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Repository
public class TsstsDao {

    /** 株価データパス */
    @Value("${stockPriceDataPath}")
    private Path stockPriceDataPath;

    /**
     * 株価データを読み込む<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void loadStockPriceData() {

        try {
            Files.list(this.stockPriceDataPath).filter(Files::isReadable).forEach(path -> {
                try {
                    // TODO 2021/04/25 列挙型
                    Files.readAllLines(path, Charset.forName("MS932")).forEach(System.out::println);
                } catch (final IOException e) {
                    // TODO 2021/04/25 例外処理
                    e.printStackTrace();
                }
            });
        } catch (final IOException e) {
            {
                // TODO 2021/04/25 例外処理
                e.printStackTrace();
            }

        }

    }
}
