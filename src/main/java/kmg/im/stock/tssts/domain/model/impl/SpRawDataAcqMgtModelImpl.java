package kmg.im.stock.tssts.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.domain.model.SpRawDataAcqMgtModel;
import kmg.im.stock.tssts.domain.model.SpRawDataAcqModel;

/**
 * 株価生データ取得管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SpRawDataAcqMgtModelImpl implements SpRawDataAcqMgtModel {

    /** 株価銘柄コード */
    private long stockBrandCode;

    /** 株価生データ取得モデルのリスト */
    private final List<SpRawDataAcqModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public SpRawDataAcqMgtModelImpl() {
        this.dataList = new ArrayList<>();
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
     * 株価生データ取得モデルのリストをクリアする<br>
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
     * 株価生データ取得モデルのリストが空か<br>
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
     * 株価生データ取得モデルのリストが空ではないか<br>
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
     * 株価生データ取得モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             株価生データ取得モデル
     */
    @Override
    public void addData(final SpRawDataAcqModel data) {
        this.dataList.add(data);
    }

    /**
     * 株価生データ取得モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加株価生データ取得モデルのリスト
     */
    @Override
    public void addAllData(final List<SpRawDataAcqModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 株価生データ取得モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価生データ取得モデルのリスト
     */
    @Override
    public List<SpRawDataAcqModel> getDataList() {
        final List<SpRawDataAcqModel> result = this.dataList;
        return result;
    }

}
