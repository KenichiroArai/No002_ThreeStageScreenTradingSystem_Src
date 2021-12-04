package kmg.im.stock.tssts.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.core.domain.model.SimpleSptsMgtModel;

/**
 * 三段階スクリーン・トレーディング・システム株価計算値初期化管理モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsSpcvInitMgtModel {

    /**
     * シンプル株価時系列管理モデルを取り込む<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param simpleSptsMgtDailyModel
     *                                シンプル株価時系列管理モデル
     */
    void from(SimpleSptsMgtModel simpleSptsMgtDailyModel);

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストをクリアする<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    void clearDataList();

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストが空か<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空、false：空ではない
     */
    boolean isDataListEmpty();

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストが空ではないか<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return true：空ではない、false：空
     */
    boolean isDataListNotEmpty();

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             三段階スクリーン・トレーディング・システム株価計算値初期化モデル
     */
    void addData(TsstsSpcvInitModel data);

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリスト
     */
    void addAllData(List<TsstsSpcvInitModel> addData);

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリスト
     */
    List<TsstsSpcvInitModel> getDataList();

    /**
     * サプライヤデータリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return サプライヤデータリスト
     */
    List<Supplier<BigDecimal>> toSupplierDataList();

    /**
     * 勢力指数計算モデルリストとして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 勢力指数計算モデルリスト
     */
    List<PowerIndexCalcModel> toPowerIndexCalcModelList();
}
