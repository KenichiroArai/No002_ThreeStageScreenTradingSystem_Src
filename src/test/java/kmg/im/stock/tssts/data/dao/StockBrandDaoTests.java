package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kmg.im.stock.core.data.dao.StockBrandDao;

/**
 * 株銘柄ＤＡＯテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
public class StockBrandDaoTests {

    /** テスト対象 */
    @Autowired
    private StockBrandDao testTarget;

    /**
     * テスト００１_識別番号を取得する０１_正常００１_１件<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    public void テスト００１_識別番号を取得する０１_正常００１_１件() {

        /* 期待値 */
        final long expected = 2;

        /* 準備 */
        final long stockBrandCode = 1001;
        final LocalDate baseDate = LocalDate.now();

        /* テスト対象を呼び出す */
        final long actual = this.testTarget.getId(stockBrandCode, baseDate);

        /* 期待値と比較 */
        Assertions.assertEquals(expected, actual);

    }

    /**
     * テスト００２_識別番号を取得する０２_正常００２_３件<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    public void テスト００２_識別番号を取得する０２_正常００２_３件() {

        /* 期待値 */
        final long[] expected = {
            1, 2, 3
        };

        /* 準備 */
        final long[] stockBrandCodes = {
            1000, 1001, 1002
        };
        final LocalDate baseDate = LocalDate.now();

        for (int i = 0; i < expected.length; i++) {
            /* テスト対象を呼び出す */
            final long actual = this.testTarget.getId(stockBrandCodes[i], baseDate);

            /* 期待値と比較 */
            Assertions.assertEquals(expected[i], actual);
        }
    }
}
