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
 * ＭＡＣＤロジックテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
public class MacdLogicImplTests {

    /** テスト対象 */
    @Autowired
    private MacdLogicImpl testTarget;

    /**
     * テスト００１_ＭＡＣＤラインのリストを返す_正常００１_パターン１<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００１_ＭＡＣＤラインのリストを返す_正常００１_パターン１() {

        /* 期待値 */
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, new BigDecimal("-20.958"),
            new BigDecimal("-19.918"), new BigDecimal("-19.674"), new BigDecimal("-19.099"), new BigDecimal("-19.229"),
            new BigDecimal("-19.670"), new BigDecimal("-19.472"), new BigDecimal("-20.451"), new BigDecimal("-20.985"),
            new BigDecimal("-21.803"), new BigDecimal("-24.349"), new BigDecimal("-26.305"), new BigDecimal("-28.256"),
            new BigDecimal("-29.303"), new BigDecimal("-31.545"), new BigDecimal("-33.819"), new BigDecimal("-37.209"),
            new BigDecimal("-38.245"), new BigDecimal("-36.946"), new BigDecimal("-34.310"), new BigDecimal("-30.657"),
            new BigDecimal("-26.489"), new BigDecimal("-23.719"), new BigDecimal("-19.523"), new BigDecimal("-15.376"));

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
        final int st = 12;
        final int lt = 26;

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualDataListTmp = this.testTarget.getLineList(dataList, st, lt);
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
     * テスト００２_ＭＡＣＤシグナルのリストを返す_正常００１_パターン１<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００２_ＭＡＣＤシグナルのリストを返す_正常００１_パターン１() {

        /* 期待値 */
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, new BigDecimal("-4.192"),
            new BigDecimal("-7.337"), new BigDecimal("-9.804"), new BigDecimal("-11.663"), new BigDecimal("-13.176"),
            new BigDecimal("-14.475"), new BigDecimal("-15.475"), new BigDecimal("-16.470"), new BigDecimal("-17.373"),
            new BigDecimal("-18.259"), new BigDecimal("-19.477"), new BigDecimal("-20.843"), new BigDecimal("-22.325"),
            new BigDecimal("-23.721"), new BigDecimal("-25.286"), new BigDecimal("-26.992"), new BigDecimal("-29.036"),
            new BigDecimal("-30.878"), new BigDecimal("-32.091"), new BigDecimal("-32.535"), new BigDecimal("-32.159"),
            new BigDecimal("-31.025"), new BigDecimal("-29.564"), new BigDecimal("-27.556"), new BigDecimal("-25.120"));

        /* 準備 */
        final List<Supplier<BigDecimal>> lineList = Arrays.asList(() -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> new BigDecimal("-20.957719953940600"), () -> new BigDecimal("-19.917983068631200"),
            () -> new BigDecimal("-19.674109633490000"), () -> new BigDecimal("-19.099290165690000"),
            () -> new BigDecimal("-19.228998102135100"), () -> new BigDecimal("-19.669891806163200"),
            () -> new BigDecimal("-19.472074716086500"), () -> new BigDecimal("-20.451311187210600"),
            () -> new BigDecimal("-20.985455873279900"), () -> new BigDecimal("-21.802971443581600"),
            () -> new BigDecimal("-24.348854338335600"), () -> new BigDecimal("-26.305328351737300"),
            () -> new BigDecimal("-28.256351258513700"), () -> new BigDecimal("-29.303376598483700"),
            () -> new BigDecimal("-31.544739738363500"), () -> new BigDecimal("-33.818802144384800"),
            () -> new BigDecimal("-37.209377509280400"), () -> new BigDecimal("-38.245191513010200"),
            () -> new BigDecimal("-36.945670387579000"), () -> new BigDecimal("-34.309912578337100"),
            () -> new BigDecimal("-30.657282812368800"), () -> new BigDecimal("-26.488900143164400"),
            () -> new BigDecimal("-23.718926255404000"), () -> new BigDecimal("-19.523431884590100"),
            () -> new BigDecimal("-15.375697754925700"));
        final int calcPeriod = 9;

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualSignalListTmp = this.testTarget.getSignalList(lineList, calcPeriod);
        List<BigDecimal> actualSignalList = actualSignalListTmp.stream().map(Supplier::get)
            .collect(Collectors.toList());
        final int actualSignalSize = actualSignalList.size();
        // 結果を有効桁数を期待値に合わせる
        actualSignalList = actualSignalList.stream().map(mapper -> KmgDecimal.setResultScale(mapper))
            .collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualSignalList);
        Assertions.assertEquals(expectedDataSize, actualSignalSize);
        Assertions.assertEquals(expectedDataList, actualSignalList);

    }

    /**
     * テスト００３_ＭＡＣＤヒストグラムのリストを返す_正常００１_パターン１<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Test
    @SuppressWarnings("nls")
    public void テスト００３_ＭＡＣＤヒストグラムのリストを返す_正常００１_パターン１() {

        /* 期待値 */
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO,
            KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, KmgDecimal.RESULT_ZERO, new BigDecimal("-16.766"),
            new BigDecimal("-12.581"), new BigDecimal("-9.870"), new BigDecimal("-7.436"), new BigDecimal("-6.053"),
            new BigDecimal("-5.195"), new BigDecimal("-3.998"), new BigDecimal("-3.981"), new BigDecimal("-3.612"),
            new BigDecimal("-3.544"), new BigDecimal("-4.872"), new BigDecimal("-5.463"), new BigDecimal("-5.931"),
            new BigDecimal("-5.582"), new BigDecimal("-6.259"), new BigDecimal("-6.826"), new BigDecimal("-8.174"),
            new BigDecimal("-7.368"), new BigDecimal("-4.854"), new BigDecimal("-1.775"), new BigDecimal("1.502"),
            new BigDecimal("4.536"), new BigDecimal("5.845"), new BigDecimal("8.032"), new BigDecimal("9.744"));

        /* 準備 */
        final List<Supplier<BigDecimal>> lineList = Arrays.asList(() -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO, () -> KmgDecimal.CALC_ZERO,
            () -> new BigDecimal("-20.957719953940600"), () -> new BigDecimal("-19.917983068631200"),
            () -> new BigDecimal("-19.674109633490000"), () -> new BigDecimal("-19.099290165690000"),
            () -> new BigDecimal("-19.228998102135100"), () -> new BigDecimal("-19.669891806163200"),
            () -> new BigDecimal("-19.472074716086500"), () -> new BigDecimal("-20.451311187210600"),
            () -> new BigDecimal("-20.985455873279900"), () -> new BigDecimal("-21.802971443581600"),
            () -> new BigDecimal("-24.348854338335600"), () -> new BigDecimal("-26.305328351737300"),
            () -> new BigDecimal("-28.256351258513700"), () -> new BigDecimal("-29.303376598483700"),
            () -> new BigDecimal("-31.544739738363500"), () -> new BigDecimal("-33.818802144384800"),
            () -> new BigDecimal("-37.209377509280400"), () -> new BigDecimal("-38.245191513010200"),
            () -> new BigDecimal("-36.945670387579000"), () -> new BigDecimal("-34.309912578337100"),
            () -> new BigDecimal("-30.657282812368800"), () -> new BigDecimal("-26.488900143164400"),
            () -> new BigDecimal("-23.718926255404000"), () -> new BigDecimal("-19.523431884590100"),
            () -> new BigDecimal("-15.375697754925700"));
        final List<Supplier<BigDecimal>> signalList = Arrays.asList(() -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO, () -> KmgDecimal.RESULT_ZERO,
            () -> new BigDecimal("-4.191543990788110"), () -> new BigDecimal("-7.336831806356730"),
            () -> new BigDecimal("-9.804287371783380"), () -> new BigDecimal("-11.663287930564700"),
            () -> new BigDecimal("-13.176429964878800"), () -> new BigDecimal("-14.475122333135700"),
            () -> new BigDecimal("-15.474512809725800"), () -> new BigDecimal("-16.469872485222800"),
            () -> new BigDecimal("-17.372989162834200"), () -> new BigDecimal("-18.258985618983700"),
            () -> new BigDecimal("-19.476959362854100"), () -> new BigDecimal("-20.842633160630700"),
            () -> new BigDecimal("-22.325376780207300"), () -> new BigDecimal("-23.720976743862600"),
            () -> new BigDecimal("-25.285729342762800"), () -> new BigDecimal("-26.992343903087200"),
            () -> new BigDecimal("-29.035750624325800"), () -> new BigDecimal("-30.877638802062700"),
            () -> new BigDecimal("-32.091245119166000"), () -> new BigDecimal("-32.534978611000200"),
            () -> new BigDecimal("-32.159439451273900"), () -> new BigDecimal("-31.025331589652000"),
            () -> new BigDecimal("-29.564050522802400"), () -> new BigDecimal("-27.555926795159900"),
            () -> new BigDecimal("-25.119880987113100"));

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualSignalListTmp = this.testTarget.getHistogramList(lineList, signalList);
        List<BigDecimal> actualSignalList = actualSignalListTmp.stream().map(Supplier::get)
            .collect(Collectors.toList());
        final int actualSignalSize = actualSignalList.size();
        // 結果を有効桁数を期待値に合わせる
        actualSignalList = actualSignalList.stream().map(mapper -> KmgDecimal.setResultScale(mapper))
            .collect(Collectors.toList());

        /* 期待値と比較 */
        Assertions.assertNotNull(actualSignalList);
        Assertions.assertEquals(expectedDataSize, actualSignalSize);
        Assertions.assertEquals(expectedDataList, actualSignalList);

    }
}
