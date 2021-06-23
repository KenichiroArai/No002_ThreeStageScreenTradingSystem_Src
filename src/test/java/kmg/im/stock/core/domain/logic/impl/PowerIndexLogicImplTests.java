package kmg.im.stock.core.domain.logic.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesModelImpl;

/**
 * 指勢力指数ロジックテスト<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Transactional
public class PowerIndexLogicImplTests {

    /** テスト対象 */
    @Autowired
    private PowerIndexLogicImpl testTarget;

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
        final int expectedDataSize = 50;
        final List<BigDecimal> expectedDataList = Arrays.asList(KmgDecimal.RESULT_ZERO, new BigDecimal("266472270.000"),
            new BigDecimal("-491712480.000"), new BigDecimal("250939430.000"), new BigDecimal("-136680125.000"),
            new BigDecimal("0.000"), new BigDecimal("9685207.000"), new BigDecimal("-4065005.000"),
            new BigDecimal("-5457600.000"), new BigDecimal("730800.000"), new BigDecimal("-4131000.000"),
            new BigDecimal("-941400.000"), new BigDecimal("-4482000.000"), new BigDecimal("-7315200.000"),
            new BigDecimal("-3062400.000"), new BigDecimal("2925000.000"), new BigDecimal("4980000.000"),
            new BigDecimal("-1654800.000"), new BigDecimal("3384000.000"), new BigDecimal("-134400.000"),
            new BigDecimal("0.000"), new BigDecimal("0.000"), new BigDecimal("-3692400.000"),
            new BigDecimal("-732000.000"), new BigDecimal("178200.000"), new BigDecimal("63600.000"),
            new BigDecimal("111600.000"), new BigDecimal("-342000.000"), new BigDecimal("34800.000"),
            new BigDecimal("-378000.000"), new BigDecimal("-218400.000"), new BigDecimal("48000.000"),
            new BigDecimal("-632400.000"), new BigDecimal("0.000"), new BigDecimal("-336000.000"),
            new BigDecimal("-8067600.000"), new BigDecimal("-315000.000"), new BigDecimal("-459000.000"),
            new BigDecimal("127200.000"), new BigDecimal("-950400.000"), new BigDecimal("-858000.000"),
            new BigDecimal("-2565000.000"), new BigDecimal("1638000.000"), new BigDecimal("1877400.000"),
            new BigDecimal("1764000.000"), new BigDecimal("1692000.000"), new BigDecimal("1180800.000"),
            new BigDecimal("-1332000.000"), new BigDecimal("1333200.000"), new BigDecimal("307200.000"));

        /* 準備 */
        final List<PowerIndexCalcModel> dataList = new ArrayList<>();
        final StockPriceTimeSeriesModel add01 = new StockPriceTimeSeriesModelImpl();
        add01.setCp(new BigDecimal("512"));
        add01.setVolume(6301206L);
        dataList.add(add01);
        final StockPriceTimeSeriesModel add02 = new StockPriceTimeSeriesModelImpl();
        add02.setCp(new BigDecimal("542"));
        add02.setVolume(8882409L);
        dataList.add(add02);
        final StockPriceTimeSeriesModel add03 = new StockPriceTimeSeriesModelImpl();
        add03.setCp(new BigDecimal("462"));
        add03.setVolume(6146406L);
        dataList.add(add03);
        final StockPriceTimeSeriesModel add04 = new StockPriceTimeSeriesModelImpl();
        add04.setCp(new BigDecimal("508"));
        add04.setVolume(5455205L);
        dataList.add(add04);
        final StockPriceTimeSeriesModel add05 = new StockPriceTimeSeriesModelImpl();
        add05.setCp(new BigDecimal("483"));
        add05.setVolume(5467205L);
        dataList.add(add05);
        final StockPriceTimeSeriesModel add06 = new StockPriceTimeSeriesModelImpl();
        add06.setCp(new BigDecimal("483"));
        add06.setVolume(1546802L);
        dataList.add(add06);
        final StockPriceTimeSeriesModel add07 = new StockPriceTimeSeriesModelImpl();
        add07.setCp(new BigDecimal("490"));
        add07.setVolume(1383601L);
        dataList.add(add07);
        final StockPriceTimeSeriesModel add08 = new StockPriceTimeSeriesModelImpl();
        add08.setCp(new BigDecimal("485"));
        add08.setVolume(813001L);
        dataList.add(add08);
        final StockPriceTimeSeriesModel add09 = new StockPriceTimeSeriesModelImpl();
        add09.setCp(new BigDecimal("473"));
        add09.setVolume(454800L);
        dataList.add(add09);
        final StockPriceTimeSeriesModel add10 = new StockPriceTimeSeriesModelImpl();
        add10.setCp(new BigDecimal("475"));
        add10.setVolume(365400L);
        dataList.add(add10);
        final StockPriceTimeSeriesModel add11 = new StockPriceTimeSeriesModelImpl();
        add11.setCp(new BigDecimal("458"));
        add11.setVolume(243000L);
        dataList.add(add11);
        final StockPriceTimeSeriesModel add12 = new StockPriceTimeSeriesModelImpl();
        add12.setCp(new BigDecimal("455"));
        add12.setVolume(313800L);
        dataList.add(add12);
        final StockPriceTimeSeriesModel add13 = new StockPriceTimeSeriesModelImpl();
        add13.setCp(new BigDecimal("425"));
        add13.setVolume(149400L);
        dataList.add(add13);
        final StockPriceTimeSeriesModel add14 = new StockPriceTimeSeriesModelImpl();
        add14.setCp(new BigDecimal("393"));
        add14.setVolume(228600L);
        dataList.add(add14);
        final StockPriceTimeSeriesModel add15 = new StockPriceTimeSeriesModelImpl();
        add15.setCp(new BigDecimal("377"));
        add15.setVolume(191400L);
        dataList.add(add15);
        final StockPriceTimeSeriesModel add16 = new StockPriceTimeSeriesModelImpl();
        add16.setCp(new BigDecimal("402"));
        add16.setVolume(117000L);
        dataList.add(add16);
        final StockPriceTimeSeriesModel add17 = new StockPriceTimeSeriesModelImpl();
        add17.setCp(new BigDecimal("427"));
        add17.setVolume(199200L);
        dataList.add(add17);
        final StockPriceTimeSeriesModel add18 = new StockPriceTimeSeriesModelImpl();
        add18.setCp(new BigDecimal("413"));
        add18.setVolume(118200L);
        dataList.add(add18);
        final StockPriceTimeSeriesModel add19 = new StockPriceTimeSeriesModelImpl();
        add19.setCp(new BigDecimal("443"));
        add19.setVolume(112800L);
        dataList.add(add19);
        final StockPriceTimeSeriesModel add20 = new StockPriceTimeSeriesModelImpl();
        add20.setCp(new BigDecimal("442"));
        add20.setVolume(134400L);
        dataList.add(add20);
        final StockPriceTimeSeriesModel add21 = new StockPriceTimeSeriesModelImpl();
        add21.setCp(new BigDecimal("442"));
        add21.setVolume(96600L);
        dataList.add(add21);
        final StockPriceTimeSeriesModel add22 = new StockPriceTimeSeriesModelImpl();
        add22.setCp(new BigDecimal("442"));
        add22.setVolume(106200L);
        dataList.add(add22);
        final StockPriceTimeSeriesModel add23 = new StockPriceTimeSeriesModelImpl();
        add23.setCp(new BigDecimal("425"));
        add23.setVolume(217200L);
        dataList.add(add23);
        final StockPriceTimeSeriesModel add24 = new StockPriceTimeSeriesModelImpl();
        add24.setCp(new BigDecimal("415"));
        add24.setVolume(73200L);
        dataList.add(add24);
        final StockPriceTimeSeriesModel add25 = new StockPriceTimeSeriesModelImpl();
        add25.setCp(new BigDecimal("418"));
        add25.setVolume(59400L);
        dataList.add(add25);
        final StockPriceTimeSeriesModel add26 = new StockPriceTimeSeriesModelImpl();
        add26.setCp(new BigDecimal("420"));
        add26.setVolume(31800L);
        dataList.add(add26);
        final StockPriceTimeSeriesModel add27 = new StockPriceTimeSeriesModelImpl();
        add27.setCp(new BigDecimal("423"));
        add27.setVolume(37200L);
        dataList.add(add27);
        final StockPriceTimeSeriesModel add28 = new StockPriceTimeSeriesModelImpl();
        add28.setCp(new BigDecimal("413"));
        add28.setVolume(34200L);
        dataList.add(add28);
        final StockPriceTimeSeriesModel add29 = new StockPriceTimeSeriesModelImpl();
        add29.setCp(new BigDecimal("415"));
        add29.setVolume(17400L);
        dataList.add(add29);
        final StockPriceTimeSeriesModel add30 = new StockPriceTimeSeriesModelImpl();
        add30.setCp(new BigDecimal("405"));
        add30.setVolume(37800L);
        dataList.add(add30);
        final StockPriceTimeSeriesModel add31 = new StockPriceTimeSeriesModelImpl();
        add31.setCp(new BigDecimal("398"));
        add31.setVolume(31200L);
        dataList.add(add31);
        final StockPriceTimeSeriesModel add32 = new StockPriceTimeSeriesModelImpl();
        add32.setCp(new BigDecimal("402"));
        add32.setVolume(12000L);
        dataList.add(add32);
        final StockPriceTimeSeriesModel add33 = new StockPriceTimeSeriesModelImpl();
        add33.setCp(new BigDecimal("385"));
        add33.setVolume(37200L);
        dataList.add(add33);
        final StockPriceTimeSeriesModel add34 = new StockPriceTimeSeriesModelImpl();
        add34.setCp(new BigDecimal("385"));
        add34.setVolume(40200L);
        dataList.add(add34);
        final StockPriceTimeSeriesModel add35 = new StockPriceTimeSeriesModelImpl();
        add35.setCp(new BigDecimal("377"));
        add35.setVolume(42000L);
        dataList.add(add35);
        final StockPriceTimeSeriesModel add36 = new StockPriceTimeSeriesModelImpl();
        add36.setCp(new BigDecimal("350"));
        add36.setVolume(298800L);
        dataList.add(add36);
        final StockPriceTimeSeriesModel add37 = new StockPriceTimeSeriesModelImpl();
        add37.setCp(new BigDecimal("347"));
        add37.setVolume(105000L);
        dataList.add(add37);
        final StockPriceTimeSeriesModel add38 = new StockPriceTimeSeriesModelImpl();
        add38.setCp(new BigDecimal("338"));
        add38.setVolume(51000L);
        dataList.add(add38);
        final StockPriceTimeSeriesModel add39 = new StockPriceTimeSeriesModelImpl();
        add39.setCp(new BigDecimal("340"));
        add39.setVolume(63600L);
        dataList.add(add39);
        final StockPriceTimeSeriesModel add40 = new StockPriceTimeSeriesModelImpl();
        add40.setCp(new BigDecimal("318"));
        add40.setVolume(43200L);
        dataList.add(add40);
        final StockPriceTimeSeriesModel add41 = new StockPriceTimeSeriesModelImpl();
        add41.setCp(new BigDecimal("307"));
        add41.setVolume(78000L);
        dataList.add(add41);
        final StockPriceTimeSeriesModel add42 = new StockPriceTimeSeriesModelImpl();
        add42.setCp(new BigDecimal("282"));
        add42.setVolume(102600L);
        dataList.add(add42);
        final StockPriceTimeSeriesModel add43 = new StockPriceTimeSeriesModelImpl();
        add43.setCp(new BigDecimal("297"));
        add43.setVolume(109200L);
        dataList.add(add43);
        final StockPriceTimeSeriesModel add44 = new StockPriceTimeSeriesModelImpl();
        add44.setCp(new BigDecimal("318"));
        add44.setVolume(89400L);
        dataList.add(add44);
        final StockPriceTimeSeriesModel add45 = new StockPriceTimeSeriesModelImpl();
        add45.setCp(new BigDecimal("333"));
        add45.setVolume(117600L);
        dataList.add(add45);
        final StockPriceTimeSeriesModel add46 = new StockPriceTimeSeriesModelImpl();
        add46.setCp(new BigDecimal("348"));
        add46.setVolume(112800L);
        dataList.add(add46);
        final StockPriceTimeSeriesModel add47 = new StockPriceTimeSeriesModelImpl();
        add47.setCp(new BigDecimal("360"));
        add47.setVolume(98400L);
        dataList.add(add47);
        final StockPriceTimeSeriesModel add48 = new StockPriceTimeSeriesModelImpl();
        add48.setCp(new BigDecimal("350"));
        add48.setVolume(133200L);
        dataList.add(add48);
        final StockPriceTimeSeriesModel add49 = new StockPriceTimeSeriesModelImpl();
        add49.setCp(new BigDecimal("372"));
        add49.setVolume(60600L);
        dataList.add(add49);
        final StockPriceTimeSeriesModel add50 = new StockPriceTimeSeriesModelImpl();
        add50.setCp(new BigDecimal("380"));
        add50.setVolume(38400L);
        dataList.add(add50);

        /* テスト対象を呼び出す */
        final List<Supplier<BigDecimal>> actualDataListTmp = this.testTarget.calc(dataList);
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
