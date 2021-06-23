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
 * 指数移動平均ロジックテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
public class EmaLogicImplTests {

    /** テスト対象 */
    @Autowired
    private EmaLogicImpl testTarget;

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
            new BigDecimal("454.771"), new BigDecimal("456.667"), new BigDecimal("457.419"), new BigDecimal("458.033"));

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
        actualDataList = actualDataList.stream().map(mapper -> KmgDecimal.setResultScale(mapper))
            .collect(Collectors.toList());

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
            KmgDecimal.RESULT_ZERO, new BigDecimal("485.500"), new BigDecimal("476.192"), new BigDecimal("463.393"),
            new BigDecimal("450.102"), new BigDecimal("442.702"), new BigDecimal("440.286"), new BigDecimal("436.088"),
            new BigDecimal("437.152"), new BigDecimal("437.898"), new BigDecimal("438.529"), new BigDecimal("439.063"),
            new BigDecimal("436.899"), new BigDecimal("433.530"), new BigDecimal("431.141"), new BigDecimal("429.427"),
            new BigDecimal("428.438"), new BigDecimal("426.063"), new BigDecimal("424.361"), new BigDecimal("421.382"),
            new BigDecimal("417.785"), new BigDecimal("415.357"), new BigDecimal("410.686"), new BigDecimal("406.735"),
            new BigDecimal("402.160"), new BigDecimal("394.135"), new BigDecimal("386.884"), new BigDecimal("379.363"),
            new BigDecimal("373.307"), new BigDecimal("364.799"), new BigDecimal("355.906"), new BigDecimal("344.536"),
            new BigDecimal("337.223"), new BigDecimal("334.266"), new BigDecimal("334.071"), new BigDecimal("336.214"),
            new BigDecimal("339.873"), new BigDecimal("341.431"), new BigDecimal("346.134"), new BigDecimal("351.344"));

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
        actualDataList = actualDataList.stream().map(mapper -> KmgDecimal.setResultScale(mapper))
            .collect(Collectors.toList());

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
            new BigDecimal("448.356"), new BigDecimal("445.737"), new BigDecimal("443.460"), new BigDecimal("440.611"),
            new BigDecimal("437.455"), new BigDecimal("434.829"), new BigDecimal("431.138"), new BigDecimal("427.720"),
            new BigDecimal("423.963"), new BigDecimal("418.484"), new BigDecimal("413.189"), new BigDecimal("407.620"),
            new BigDecimal("402.611"), new BigDecimal("396.343"), new BigDecimal("389.725"), new BigDecimal("381.746"),
            new BigDecimal("375.468"), new BigDecimal("371.211"), new BigDecimal("368.381"), new BigDecimal("366.871"),
            new BigDecimal("366.362"), new BigDecimal("365.150"), new BigDecimal("365.658"), new BigDecimal("366.720"));

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
        actualDataList = actualDataList.stream().map(mapper -> KmgDecimal.setResultScale(mapper))
            .collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualDataList);
        Assertions.assertEquals(expectedDataSize, actualDataSize);
        Assertions.assertEquals(expectedDataList, actualDataList);

    }
}
