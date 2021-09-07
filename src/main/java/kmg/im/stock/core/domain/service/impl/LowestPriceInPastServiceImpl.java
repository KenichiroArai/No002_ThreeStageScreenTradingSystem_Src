package kmg.im.stock.core.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import kmg.im.stock.core.domain.logic.LowestPriceInPastLogic;
import kmg.im.stock.core.domain.service.LowestPriceInPastService;

/**
 * 過去の最安値サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
@Scope("prototype")
public class LowestPriceInPastServiceImpl implements LowestPriceInPastService {

    /** 過去の最安値ロジック */
    private final LowestPriceInPastLogic lowestPriceInPastLogic;

    /** データリスト */
    private final List<Supplier<BigDecimal>> dataList;

    /** 計算日数 */
    private int calcDays;

    /** 計算結果のリスト */
    private final List<Supplier<BigDecimal>> clacResultList;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param lowestPriceInPastLogic
     *                               過去の最安値ロジック
     */
    public LowestPriceInPastServiceImpl(final LowestPriceInPastLogic lowestPriceInPastLogic) {
        this.lowestPriceInPastLogic = lowestPriceInPastLogic;
        this.dataList = new ArrayList<>();
        this.clacResultList = new ArrayList<>();
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param dataList
     *                 データリスト
     * @param calcDays
     *                 計算日数
     */
    @Override
    @SuppressWarnings("hiding")
    public void initialize(final List<Supplier<BigDecimal>> dataList, final int calcDays) {

        this.dataList.clear();

        this.dataList.addAll(dataList);
        this.calcDays = calcDays;
    }

    /**
     * データリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return データリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getDataList() {
        final List<Supplier<BigDecimal>> result = this.dataList;
        return result;
    }

    /**
     * 計算日数を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算日数
     */
    @Override
    public int getCalcDays() {
        final int result = this.calcDays;
        return result;
    }

    /**
     * 計算結果のリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 計算結果のリスト
     */
    @Override
    public List<Supplier<BigDecimal>> getClacResultList() {
        final List<Supplier<BigDecimal>> result = this.clacResultList;
        return result;
    }

    /**
     * 計算する<br>
     * <p>
     * データリストを計算日数で計算し結果を返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void calc() {
        this.clacResultList.clear();
        final List<Supplier<BigDecimal>> tmpList = this.lowestPriceInPastLogic.calc(this.dataList, this.calcDays);
        this.clacResultList.addAll(tmpList);
    }

}
