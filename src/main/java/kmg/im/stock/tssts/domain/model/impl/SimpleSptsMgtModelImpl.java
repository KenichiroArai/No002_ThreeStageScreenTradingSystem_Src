package kmg.im.stock.tssts.domain.model.impl;

import java.util.ArrayList;
import java.util.List;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.SimpleSptsModel;

/**
 * シンプル株価時系列管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class SimpleSptsMgtModelImpl implements SimpleSptsMgtModel {

    /** シンプル株価時系列管理モデルのリスト */
    private final List<SimpleSptsModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public SimpleSptsMgtModelImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * シンプル株価時系列管理モデルのリストをクリアする<br>
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
     * シンプル株価時系列管理モデルのリストが空か<br>
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
     * シンプル株価時系列管理モデルのリストが空ではないか<br>
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
     * シンプル株価時系列管理モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             シンプル株価時系列管理モデル
     */
    @Override
    public void addData(final SimpleSptsModel data) {
        this.dataList.add(data);
    }

    /**
     * シンプル株価時系列管理モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加シンプル株価時系列管理モデルのリスト
     */
    @Override
    public void addAllData(final List<SimpleSptsModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * シンプル株価時系列管理モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return シンプル株価時系列管理モデルのリスト
     */
    @Override
    public List<SimpleSptsModel> getDataList() {
        final List<SimpleSptsModel> result = this.dataList;
        return result;
    }
}
