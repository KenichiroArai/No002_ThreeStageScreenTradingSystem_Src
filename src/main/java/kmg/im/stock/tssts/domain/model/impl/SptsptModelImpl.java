package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.core.infrastructure.utils.MapUtils;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.tssts.domain.model.SptsptModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列期間の種類モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsptModelImpl implements SptsptModel {

    // TODO KenichiroArai 2021/09/12 期間の種類の種類の論理名と物理名を株価時系列期間の種類の種類に変更する
    /** 期間の種類の種類 */
    private final PeriodTypeTypes periodTypeTypes;

    /**
     * 株価時系列モデルのマップ<br>
     * <p>
     * キー：番号<br>
     * 値：株価時系列モデル<br>
     * </p>
     */
    private final SortedMap<Long, StockPriceTimeSeriesModel> sptsModelMap;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     */
    public SptsptModelImpl(final PeriodTypeTypes periodTypeTypes) {
        this.sptsModelMap = new TreeMap<>();
        this.periodTypeTypes = periodTypeTypes;
    }

    /**
     * 期間の種類の種類を返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 期間の種類の種類
     */
    @Override
    public PeriodTypeTypes getPeriodTypeTypes() {
        final PeriodTypeTypes result = this.periodTypeTypes;
        return result;
    }

    /**
     * 株価時系列モデルのマップをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearSptsModelMap() {
        this.sptsModelMap.clear();
    }

    /**
     * 株価時系列モデルのマップが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isSptsModelMapEmpty() {
        boolean result = true;

        if (MapUtils.isEmpty(this.sptsModelMap)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価時系列モデルのマップが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isSptsModelMapNotEmpty() {
        final boolean result = !this.isSptsModelMapEmpty();
        return result;
    }

    /**
     * 株価時系列モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsModel
     *                  株価時系列モデル
     */
    @Override
    public void addSptsModel(final StockPriceTimeSeriesModel sptsModel) {
        this.sptsModelMap.put(sptsModel.getNo(), sptsModel);
    }

    /**
     * 株価時系列モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsModelList
     *                      株価時系列モデルのリスト
     */
    @Override
    public void addAllSptsModelList(final List<StockPriceTimeSeriesModel> sptsModelList) {
        if (ListUtils.isEmpty(sptsModelList)) {
            return;
        }

        for (final StockPriceTimeSeriesModel sptsModel : sptsModelList) {
            this.addSptsModel(sptsModel);
        }
    }

    /**
     * 株価時系列モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列マップ
     */
    @Override
    public SortedMap<Long, StockPriceTimeSeriesModel> getSptsModelMap() {
        final SortedMap<Long, StockPriceTimeSeriesModel> result = this.sptsModelMap;
        return result;
    }

    /**
     * 番号に該当する株価時系列モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param no
     *           番号
     * @return 株価時系列マップ
     */
    @Override
    public StockPriceTimeSeriesModel getSptsModel(final long no) {
        final StockPriceTimeSeriesModel result = this.sptsModelMap.get(no);
        return result;
    }

    /**
     * 株価時系列モデルの全リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列モデルのリスト
     */
    @Override
    public List<StockPriceTimeSeriesModel> toAllSptsModelList() {
        final List<StockPriceTimeSeriesModel> result = this.sptsModelMap.values().stream().collect(Collectors.toList());
        return result;
    }

    /**
     * サプライヤの全リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return サプライヤリスト
     */
    @Override
    public List<Supplier<BigDecimal>> toAllSupplierList() {
        final List<Supplier<BigDecimal>> result = this.sptsModelMap.values().stream().collect(Collectors.toList());
        return result;
    }

    /**
     * 勢力指数計算モデルの全リストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 勢力指数計算モデルのリスト
     */
    @Override
    public List<PowerIndexCalcModel> toAllPowerIndexCalcModelList() {
        final List<PowerIndexCalcModel> result = this.sptsModelMap.values().stream().collect(Collectors.toList());
        return result;
    }

}
