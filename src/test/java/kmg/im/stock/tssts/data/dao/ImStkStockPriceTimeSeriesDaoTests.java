package kmg.im.stock.tssts.data.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.core.data.dao.ImStkStockPriceTimeSeriesDao;
import kmg.im.stock.core.data.dto.ImStkStockPriceTimeSeriesDto;
import kmg.im.stock.core.data.dto.impl.ImStkStockPriceTimeSeriesDtoImpl;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;

/**
 * 投資株式株価時系列ＤＡＯテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@Transactional
public class ImStkStockPriceTimeSeriesDaoTests {

    /** テスト対象 */
    @Autowired
    private ImStkStockPriceTimeSeriesDao testTarget;

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
        final long expected = 1L;

        /* 準備 */
        // 株価時系列ＤＴＯの作成
        final ImStkStockPriceTimeSeriesDto imStkStockPriceTimeSeriesDto = new ImStkStockPriceTimeSeriesDtoImpl();
        imStkStockPriceTimeSeriesDto.setSptsptId(1);
        imStkStockPriceTimeSeriesDto.setNo(1L);
        imStkStockPriceTimeSeriesDto.setPeriodStartDate(LocalDate.now());
        imStkStockPriceTimeSeriesDto.setPeriodEndDate(LocalDate.now());
        imStkStockPriceTimeSeriesDto.setOp(BigDecimal.valueOf(100.0));
        imStkStockPriceTimeSeriesDto.setHp(BigDecimal.valueOf(150.0));
        imStkStockPriceTimeSeriesDto.setLp(BigDecimal.valueOf(50.0));
        imStkStockPriceTimeSeriesDto.setCp(BigDecimal.valueOf(120.0));
        imStkStockPriceTimeSeriesDto.setVolume(999L);

        /* テスト対象を呼び出す */
        final long actual = 0;
        try {
            this.testTarget.insertByPttAndSptsDto(ImStkPeriodTypeTypes.DAILY, imStkStockPriceTimeSeriesDto);
        } catch (final KmgDomainException e) {
            e.printStackTrace();
            /* 期待値と比較 */
            Assertions.assertTrue(false);

        }

        /* 期待値と比較 */
        Assertions.assertEquals(expected, actual);

    }

}
