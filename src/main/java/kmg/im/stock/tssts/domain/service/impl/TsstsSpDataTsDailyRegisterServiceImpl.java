package kmg.im.stock.tssts.domain.service.impl;

import kmg.im.stock.core.domain.model.ImStkSpDataRegMgtModel;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.domain.logic.TsstsSpDataTsRegisterLogic;
import kmg.im.stock.tssts.domain.service.TsstsSpDataTsDailyRegisterService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 三段階スクリーン・トレーディング・システム株価データ時系列日足登録サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class TsstsSpDataTsDailyRegisterServiceImpl implements TsstsSpDataTsDailyRegisterService {

    /** 投資株式期間の種類の種類 */
    private static final ImStkPeriodTypeTypes PERIOD_TYPE_TYPES = ImStkPeriodTypeTypes.DAILY;

    /** 投資株式株価データ登録管理モデル */
    private ImStkSpDataRegMgtModel imStkSpDataRegMgtModel;

    /** 三段階スクリーン・トレーディング・システム株価データ時系列登録ロジック */
    private final TsstsSpDataTsRegisterLogic tsstsSpDataTsRegisterLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsSpDataTsRegisterLogic
     *                                   三段階スクリーン・トレーディング・システム株価データ時系列登録ロジック
     */
    public TsstsSpDataTsDailyRegisterServiceImpl(final TsstsSpDataTsRegisterLogic tsstsSpDataTsRegisterLogic) {
        this.tsstsSpDataTsRegisterLogic = tsstsSpDataTsRegisterLogic;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkSpDataRegMgtModel
     *                               投資株式株価データ登録管理モデル
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final ImStkSpDataRegMgtModel imStkSpDataRegMgtModel) {
        this.imStkSpDataRegMgtModel = imStkSpDataRegMgtModel;
    }

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
    @Override
    public long delete() throws TsstsDomainException {
        final long result = 0;
        // TODO KenichiroArai 2021/09/21 未実装
        return result;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 登録数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long register() throws TsstsDomainException {
        final long result = 0;
        // TODO KenichiroArai 2021/09/21 未実装
        return result;
    }

}
