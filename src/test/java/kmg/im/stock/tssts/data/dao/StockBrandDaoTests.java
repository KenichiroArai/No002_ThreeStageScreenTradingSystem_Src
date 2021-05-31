package kmg.im.stock.tssts.data.dao;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    public void test001_GetId001_正常001_１件() {

        /* 期待値 */
        final long expectedInsertNum = 2;

        /* 準備 */
        final long stockBrandCode = 1001;
        final LocalDate baseDate = LocalDate.now();

        /* テスト対象を呼び出す */
        final long actualInsertNum = this.testTarget.getId(stockBrandCode, baseDate);

        /* 期待値と比較 */
        Assertions.assertEquals(expectedInsertNum, actualInsertNum);

    }

    /**
     * テスト００２_識別番号を取得する０２_正常００２_３件<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    public void test002_GetId002_正常002_３件() {

        /* 期待値 */
        final long[] expectedInsertNums = {
            1, 2, 3
        };

        /* 準備 */
        final long[] stockBrandCodes = {
            1000, 1001, 1002
        };
        final LocalDate baseDate = LocalDate.now();

        for (int i = 0; i < expectedInsertNums.length; i++) {
            /* テスト対象を呼び出す */
            final long actualInsertNum = this.testTarget.getId(stockBrandCodes[i], baseDate);

            /* 期待値と比較 */
            Assertions.assertEquals(expectedInsertNums[i], actualInsertNum);
        }
    }
}
