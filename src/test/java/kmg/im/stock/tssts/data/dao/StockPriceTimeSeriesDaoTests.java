package kmg.im.stock.tssts.data.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;

/**
 * 株価時系列ＤＡＯテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@Transactional
public class StockPriceTimeSeriesDaoTests {

    /** テスト対象 */
    @Autowired
    private StockPriceTimeSeriesDao testTarget;

    /**
     * 株価時系列を挿入するをテストする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    public void testInsert() {

        /* 期待値 */
        final long expectedInsertNum = 1L;

        /* 準備 */
        // 株価時系列ＤＴＯの作成
        final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDto();
        stockPriceTimeSeriesDto.setStockBrandId(897L);
        stockPriceTimeSeriesDto.setNo(1L);
        stockPriceTimeSeriesDto.setDate(LocalDate.now());
        stockPriceTimeSeriesDto.setOp(BigDecimal.valueOf(100.0));
        stockPriceTimeSeriesDto.setHp(BigDecimal.valueOf(150.0));
        stockPriceTimeSeriesDto.setLp(BigDecimal.valueOf(50.0));
        stockPriceTimeSeriesDto.setCp(BigDecimal.valueOf(120.0));
        stockPriceTimeSeriesDto.setVolume(999L);

        /* テスト対象を呼び出す */
        final long insertNum = this.testTarget.insert(stockPriceTimeSeriesDto);

        /* 期待値と比較 */
        Assertions.assertEquals(expectedInsertNum, insertNum);

    }

}
