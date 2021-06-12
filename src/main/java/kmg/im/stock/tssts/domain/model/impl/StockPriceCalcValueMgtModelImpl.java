package kmg.im.stock.tssts.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueModel;

/**
 * 株価計算値管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class StockPriceCalcValueMgtModelImpl implements StockPriceCalcValueMgtModel {

    /** 株価計算値ID */
    private Long sptsId;

    /** 株価計算値リスト */
    private final List<StockPriceCalcValueModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public StockPriceCalcValueMgtModelImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 株価計算値IDを設定する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsId
     *               株価計算値ID
     */
    @Override
    public void setSptsId(final Long sptsId) {
        this.sptsId = sptsId;
    }

    /**
     * 株価計算値IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値ID
     */
    @Override
    public Long getSptsId() {
        final Long result = this.sptsId;
        return result;
    }

    /**
     * 株価計算値リストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void clearDataList() {
        this.dataList.clear();
    }

    /**
     * 株価計算値リストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    @Override
    public boolean isDataListEmpty() {
        boolean result = true;

        if (ListUtils.isEmpty(this.dataList)) {
            return result;
        }

        result = false;
        return result;
    }

    /**
     * 株価計算値リストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    @Override
    public boolean isDataListNotEmpty() {
        final boolean result = !this.isDataListEmpty();
        return result;
    }

    /**
     * 株価データを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価データ
     */
    @Override
    public void addData(final StockPriceCalcValueModel data) {
        this.dataList.add(data);
    }

    /**
     * 株価計算値リストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価計算値リスト
     */
    @Override
    public void addAllData(final List<StockPriceCalcValueModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価計算値リストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価計算値リスト
     */
    @Override
    public List<StockPriceCalcValueModel> getDataList() {
        final List<StockPriceCalcValueModel> result = this.dataList;
        return result;
    }

}
