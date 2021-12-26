package kmg.im.stock.tssts.domain.service;

import java.util.List;

import kmg.im.stock.core.domain.model.ImStkSpDataRegMgtModel;
import kmg.im.stock.core.domain.model.ImStkSptsRegDataModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価時系列登録サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public interface TsstsSptsRegService {

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                               株銘柄ＩＤ
     * @param imStkSpDataRegMgtModel
     *                               投資株式株価データ登録管理モデル
     */
    void initialize(long stockBrandId, ImStkSpDataRegMgtModel imStkSpDataRegMgtModel);

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    long delete() throws TsstsDomainException;

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    void register() throws TsstsDomainException;

    /**
     * 投資株式株価時系列登録データモデルのリストにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 投資株式株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    List<ImStkSptsRegDataModel> toImStkSptsRegDataModelList() throws TsstsDomainException;
}
