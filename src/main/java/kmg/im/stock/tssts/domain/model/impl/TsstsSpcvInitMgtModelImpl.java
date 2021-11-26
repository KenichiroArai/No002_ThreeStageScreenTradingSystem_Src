package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.domain.model.PowerIndexCalcModel;
import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.SimpleSptsModel;
import kmg.im.stock.tssts.domain.model.TsstsSpcvInitMgtModel;
import kmg.im.stock.tssts.domain.model.TsstsSpcvInitModel;

/**
 * 三段階スクリーン・トレーディング・システム株価計算値初期化管理モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class TsstsSpcvInitMgtModelImpl implements TsstsSpcvInitMgtModel {

    /** 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリスト */
    private final List<TsstsSpcvInitModel> dataList;

    /**
     * デフォルトコンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public TsstsSpcvInitMgtModelImpl() {
        this.dataList = new ArrayList<>();
    }

    /**
     * シンプル株価時系列管理モデルを取り込む<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param simpleSptsMgtDailyModel
     *                                シンプル株価時系列管理モデル
     */
    @Override
    public void from(final SimpleSptsMgtModel simpleSptsMgtDailyModel) {
        for (final SimpleSptsModel simpleSptsModel : simpleSptsMgtDailyModel.getDataList()) {
            final TsstsSpcvInitModel tsstsSpcvInitModel = new TsstsSpcvInitModelImpl();
            BeanUtils.copyProperties(simpleSptsModel, tsstsSpcvInitModel);
            this.dataList.add(tsstsSpcvInitModel);
        }
    }

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストをクリアする<br>
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
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストが空か<br>
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
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストが空ではないか<br>
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
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルを追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param data
     *             三段階スクリーン・トレーディング・システム株価計算値初期化モデル
     */
    @Override
    public void addData(final TsstsSpcvInitModel data) {
        this.dataList.add(data);
    }

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストを全て追加する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param addData
     *                追加三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリスト
     */
    @Override
    public void addAllData(final List<TsstsSpcvInitModel> addData) {
        if (ListUtils.isEmpty(addData)) {
            return;
        }

        this.dataList.addAll(addData);
    }

    /**
     * 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 三段階スクリーン・トレーディング・システム株価計算値初期化モデルのリスト
     */
    @Override
    public List<TsstsSpcvInitModel> getDataList() {
        final List<TsstsSpcvInitModel> result = this.dataList;
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
