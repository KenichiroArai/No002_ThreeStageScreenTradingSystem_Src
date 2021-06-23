package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kmg.core.infrastructure.type.KmgDecimal;

/**
 * 単純移動平均ロジックテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
public class SmaLogicImplTests {

    /** テスト対象 */
    @Autowired
    private SmaLogicImpl testTarget;

    /**
     * テスト００１_計算する_正常００１_パターン１<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００１_計算する_正常００１_パターン１() {

        /* 期待値 */
        final int expectedDataSize = 14;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, new BigDecimal("453.720"),
            new BigDecimal("454.940"), new BigDecimal("455.780"), new BigDecimal("456.760"), new BigDecimal("457.590"));

        /* 準備 */
        final List<Supplier<BigDecimal>> dataList = Arrays.asList(() -> new BigDecimal("447.3"),
            () -> new BigDecimal("456.8"), () -> new BigDecimal("451.0"), () -> new BigDecimal("452.5"),
            () -> new BigDecimal("453.4"), () -> new BigDecimal("455.5"), () -> new BigDecimal("456.0"),
            () -> new BigDecimal("454.7"), () -> new BigDecimal("453.5"), () -> new BigDecimal("456.5"),
            () -> new BigDecimal("459.5"), () -> new BigDecimal("465.2"), () -> new BigDecimal("460.8"),
            () -> new BigDecimal("460.8"));
        final int n = 10;

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualDataListTmp = this.testTarget.calc(dataList, n);
        List<BigDecimal> actualDataList = actualDataListTmp.stream().map(Supplier::get).collect(Collectors.toList());
        final int actualDataSize = actualDataList.size();
        // 結果を有効桁数を期待値に合わせる
        actualDataList = actualDataList.stream().map(KmgDecimal::setResultScale).collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualDataList);
        Assertions.assertEquals(expectedDataSize, actualDataSize);
        Assertions.assertEquals(expectedDataList, actualDataList);

    }

    /**
     * テスト００２_計算する_正常００２_パターン２<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００２_計算する_正常００２_パターン２() {

        /* 期待値 */
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, new BigDecimal("485.500"), new BigDecimal("478.250"), new BigDecimal("465.833"),
            new BigDecimal("458.750"), new BigDecimal("449.917"), new BigDecimal("445.250"), new BigDecimal("439.417"),
            new BigDecimal("435.500"), new BigDecimal("431.917"), new BigDecimal("429.333"), new BigDecimal("426.583"),
            new BigDecimal("423.833"), new BigDecimal("420.500"), new BigDecimal("419.917"), new BigDecimal("422.167"),
            new BigDecimal("426.000"), new BigDecimal("426.917"), new BigDecimal("425.917"), new BigDecimal("425.250"),
            new BigDecimal("421.500"), new BigDecimal("418.167"), new BigDecimal("413.417"), new BigDecimal("408.667"),
            new BigDecimal("404.667"), new BigDecimal("399.250"), new BigDecimal("393.333"), new BigDecimal("386.500"),
            new BigDecimal("379.583"), new BigDecimal("371.667"), new BigDecimal("362.667"), new BigDecimal("352.417"),
            new BigDecimal("344.000"), new BigDecimal("337.000"), new BigDecimal("332.667"), new BigDecimal("329.583"),
            new BigDecimal("328.167"), new BigDecimal("328.167"), new BigDecimal("330.250"), new BigDecimal("333.750"));

        /* 準備 */
        final List<Supplier<BigDecimal>> dataList = Arrays.asList(() -> new BigDecimal("512"),
            () -> new BigDecimal("542"), () -> new BigDecimal("462"), () -> new BigDecimal("508"),
            () -> new BigDecimal("483"), () -> new BigDecimal("483"), () -> new BigDecimal("490"),
            () -> new BigDecimal("485"), () -> new BigDecimal("473"), () -> new BigDecimal("475"),
            () -> new BigDecimal("458"), () -> new BigDecimal("455"), () -> new BigDecimal("425"),
            () -> new BigDecimal("393"), () -> new BigDecimal("377"), () -> new BigDecimal("402"),
            () -> new BigDecimal("427"), () -> new BigDecimal("413"), () -> new BigDecimal("443"),
            () -> new BigDecimal("442"), () -> new BigDecimal("442"), () -> new BigDecimal("442"),
            () -> new BigDecimal("425"), () -> new BigDecimal("415"), () -> new BigDecimal("418"),
            () -> new BigDecimal("420"), () -> new BigDecimal("423"), () -> new BigDecimal("413"),
            () -> new BigDecimal("415"), () -> new BigDecimal("405"), () -> new BigDecimal("398"),
            () -> new BigDecimal("402"), () -> new BigDecimal("385"), () -> new BigDecimal("385"),
            () -> new BigDecimal("377"), () -> new BigDecimal("350"), () -> new BigDecimal("347"),
            () -> new BigDecimal("338"), () -> new BigDecimal("340"), () -> new BigDecimal("318"),
            () -> new BigDecimal("307"), () -> new BigDecimal("282"), () -> new BigDecimal("297"),
            () -> new BigDecimal("318"), () -> new BigDecimal("333"), () -> new BigDecimal("348"),
            () -> new BigDecimal("360"), () -> new BigDecimal("350"), () -> new BigDecimal("372"),
            () -> new BigDecimal("380"));
        final int n = 12;

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualDataListTmp = this.testTarget.calc(dataList, n);
        List<BigDecimal> actualDataList = actualDataListTmp.stream().map(Supplier::get).collect(Collectors.toList());
        final int actualDataSize = actualDataList.size();
        // 結果を有効桁数を期待値に合わせる
        actualDataList = actualDataList.stream().map(KmgDecimal::setResultScale).collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualDataList);
        Assertions.assertEquals(expectedDataSize, actualDataSize);
        Assertions.assertEquals(expectedDataList, actualDataList);

    }

    /**
     * テスト００３_計算する_正常００３_パターン３<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００３_計算する_正常００３_パターン３() {

        /* 期待値 */
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, new BigDecimal("450.385"),
            new BigDecimal("446.962"), new BigDecimal("442.000"), new BigDecimal("440.192"), new BigDecimal("436.231"),
            new BigDecimal("432.962"), new BigDecimal("429.846"), new BigDecimal("425.808"), new BigDecimal("421.962"),
            new BigDecimal("418.269"), new BigDecimal("413.462"), new BigDecimal("409.192"), new BigDecimal("404.692"),
            new BigDecimal("401.423"), new BigDecimal("398.538"), new BigDecimal("395.846"), new BigDecimal("391.231"),
            new BigDecimal("386.231"), new BigDecimal("382.577"), new BigDecimal("378.346"), new BigDecimal("374.731"),
            new BigDecimal("371.577"), new BigDecimal("368.038"), new BigDecimal("366.000"), new BigDecimal("364.654"));

        /* 準備 */
        final List<Supplier<BigDecimal>> dataList = Arrays.asList(() -> new BigDecimal("512"),
            () -> new BigDecimal("542"), () -> new BigDecimal("462"), () -> new BigDecimal("508"),
            () -> new BigDecimal("483"), () -> new BigDecimal("483"), () -> new BigDecimal("490"),
            () -> new BigDecimal("485"), () -> new BigDecimal("473"), () -> new BigDecimal("475"),
            () -> new BigDecimal("458"), () -> new BigDecimal("455"), () -> new BigDecimal("425"),
            () -> new BigDecimal("393"), () -> new BigDecimal("377"), () -> new BigDecimal("402"),
            () -> new BigDecimal("427"), () -> new BigDecimal("413"), () -> new BigDecimal("443"),
            () -> new BigDecimal("442"), () -> new BigDecimal("442"), () -> new BigDecimal("442"),
            () -> new BigDecimal("425"), () -> new BigDecimal("415"), () -> new BigDecimal("418"),
            () -> new BigDecimal("420"), () -> new BigDecimal("423"), () -> new BigDecimal("413"),
            () -> new BigDecimal("415"), () -> new BigDecimal("405"), () -> new BigDecimal("398"),
            () -> new BigDecimal("402"), () -> new BigDecimal("385"), () -> new BigDecimal("385"),
            () -> new BigDecimal("377"), () -> new BigDecimal("350"), () -> new BigDecimal("347"),
            () -> new BigDecimal("338"), () -> new BigDecimal("340"), () -> new BigDecimal("318"),
            () -> new BigDecimal("307"), () -> new BigDecimal("282"), () -> new BigDecimal("297"),
            () -> new BigDecimal("318"), () -> new BigDecimal("333"), () -> new BigDecimal("348"),
            () -> new BigDecimal("360"), () -> new BigDecimal("350"), () -> new BigDecimal("372"),
            () -> new BigDecimal("380"));
        final int n = 26;

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualDataListTmp = this.testTarget.calc(dataList, n);
        List<BigDecimal> actualDataList = actualDataListTmp.stream().map(Supplier::get).collect(Collectors.toList());
        final int actualDataSize = actualDataList.size();
        // 結果を有効桁数を期待値に合わせる
        actualDataList = actualDataList.stream().map(KmgDecimal::setResultScale).collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualDataList);
        Assertions.assertEquals(expectedDataSize, actualDataSize);
        Assertions.assertEquals(expectedDataList, actualDataList);

    }
}
