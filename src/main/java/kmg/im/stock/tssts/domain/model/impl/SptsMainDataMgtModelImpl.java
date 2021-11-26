package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.tssts.domain.model.SptsMainDataMgtModel;
import kmg.im.stock.tssts.domain.model.SptsRegDataModel;

/**
 * 株価時系列登録データ管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SptsMainDataMgtModelImpl implements SptsMainDataMgtModel {

    /** 株価時系列登録データ管理モデルのリスト */
    private final List<SptsRegDataModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public SptsMainDataMgtModelImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * 株価時系列登録データ管理モデルのリストをクリアする<br>
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
     * 株価時系列登録データ管理モデルのリストが空か<br>
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
     * 株価時系列登録データ管理モデルのリストが空ではないか<br>
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
     * 株価時系列登録データ管理モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価時系列登録データ管理モデル
     */
    @Override
    public void addData(final SptsRegDataModel data) {
        this.dataList.add(data);
    }

    /**
     * 株価時系列登録データ管理モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価時系列登録データ管理モデルのリスト
     */
    @Override
    public void addAllData(final List<SptsRegDataModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価時系列登録データ管理モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価時系列登録データ管理モデルのリスト
     */
    @Override
    public List<SptsRegDataModel> getDataList() {
        final List<SptsRegDataModel> result = this.dataList;
        return result;
    }

    /**
     * サプライヤデータリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return サプライヤデータリスト
     */
    @Override
    public List<Supplier<BigDecimal>> toSupplierDataList() {

        final List<Supplier<BigDecimal>> result = this.dataList.stream().collect(Collectors.toList());
        return result;
    }

    /**
     * 勢力指数計算モデルリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 勢力指数計算モデルリスト
     */
    @Override
    public List<PowerIndexCalcModel> toPowerIndexCalcModelList() {
        final List<PowerIndexCalcModel> result = this.dataList.stream().collect(Collectors.toList());
        return result;
    }

}
