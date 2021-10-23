package kmg.im.stock.tssts.domain.model;

import java.util.List;

/**
 * シンプル株価時系列管理モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface SimpleSptsMgtModel {

    /**
     * シンプル株価時系列管理モデルのリストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clearDataList();

    /**
     * シンプル株価時系列管理モデルのリストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    boolean isDataListEmpty();

    /**
     * シンプル株価時系列管理モデルのリストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    boolean isDataListNotEmpty();

    /**
     * シンプル株価時系列管理モデルのを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             シンプル株価時系列管理モデル
     */
    void addData(SimpleSptsModel data);

    /**
     * シンプル株価時系列管理モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加シンプル株価時系列管理モデルのリスト
     */
    void addAllData(List<SimpleSptsModel> addData);

    /**
     * シンプル株価時系列管理モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return シンプル株価時系列管理モデルのリスト
     */
    List<SimpleSptsModel> getDataList();
}
