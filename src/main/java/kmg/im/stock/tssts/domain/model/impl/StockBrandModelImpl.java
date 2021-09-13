package kmg.im.stock.tssts.domain.model.impl;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.core.infrastructure.utils.MapUtils;
import kmg.im.stock.tssts.domain.model.SptsptModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株銘柄モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockBrandModelImpl implements StockBrandModel {

    /** 株銘柄ID */
    private long stockBrandId;

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 株価時系列期間の種類のマップ */
    private final SortedMap<PeriodTypeTypes, SptsptModel> sptsptMap;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockBrandModelImpl() {
        this.sptsptMap = new TreeMap<>();
    }

    /**
     * 株銘柄IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                     株銘柄ID
     */
    @Override
    public void setStockBrandId(final long stockBrandId) {
        this.stockBrandId = stockBrandId;
    }

    /**
     * 株銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株銘柄ID
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 株価銘柄コードを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     */
    @Override
    public void setStockBrandCode(final long stockBrandCode) {
        this.stockBrandCode = stockBrandCode;
    }

    /**
     * 株価銘柄コードを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄コード
     */
    @Override
    public long getStockBrandCode() {
        final long result = this.stockBrandCode;
        return result;
    }

    /**
     * 株価時系列期間の種類のマップをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearSptspMap() {
        this.sptsptMap.clear();
    }

    /**
     * 株価時系列期間の種類のマップが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isSptspMapEmpty() {
        boolean result = true;

        if (MapUtils.isEmpty(this.sptsptMap)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価時系列期間の種類のマップが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isSptspMapNotEmpty() {
        final boolean result = !this.isSptspMapEmpty();
        return result;
    }

    /**
     * 株価時系列期間の種類モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptModel
     *                    株価時系列期間の種類モデル
     */
    @Override
    public void addSptsptModel(final SptsptModel sptsptModel) {
        this.sptsptMap.put(sptsptModel.getPeriodTypeTypes(), sptsptModel);
    }

    /**
     * 株価時系列期間の種類モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptModelList
     *                        株価時系列期間の種類モデルのリスト
     */
    @Override
    public void addAllSptsptModel(final List<SptsptModel> sptsptModelList) {
        if (ListUtils.isEmpty(sptsptModelList)) {
            return;
        }

        for (final SptsptModel sptsptModel : sptsptModelList) {
            this.addSptsptModel(sptsptModel);
        }
    }

    /**
     * 株価時系列期間の種類モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類モデルのマップ
     */
    @Override
    public SortedMap<PeriodTypeTypes, SptsptModel> getSptsptModelMap() {
        final SortedMap<PeriodTypeTypes, SptsptModel> result = this.sptsptMap;
        return result;
    }

    /**
     * 期間の種類の種類に該当する株価時系列期間の種類モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列期間の種類モデル
     */
    @Override
    public SptsptModel getSptsptModel(final PeriodTypeTypes periodTypeTypes) {
        final SptsptModel result = this.sptsptMap.get(periodTypeTypes);
        return result;
    }

    /**
     * 株価時系列期間の種類モデルのリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列期間の種類モデルのリスト
     */
    @Override
    public List<SptsptModel> toSptsptModelList() {
        final List<SptsptModel> result = this.sptsptMap.values().stream().collect(Collectors.toList());
        return result;
    }

}
