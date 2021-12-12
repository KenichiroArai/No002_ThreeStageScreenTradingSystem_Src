package kmg.im.stock.tssts.domain.logic.impl;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.data.dao.TsstsSpDataTsDao;
import kmg.im.stock.tssts.domain.logic.TsstsSpDataTsRegisterLogic;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価データ時系列登録ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class TsstsSpDataTsRegisterLogicImpl implements TsstsSpDataTsRegisterLogic {

    /** 三段階スクリーン・トレーディング・システム株価データ時系列ＤＡＯ */
    private TsstsSpDataTsDao tsstsSpDataTsDao;

    /**
     * <p>
     * 株価データ時系列を削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                             株銘柄ID
     * @param imStkPeriodTypeTypes
     *                             投資株式期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long delete(final long stockBrandId, final ImStkPeriodTypeTypes imStkPeriodTypeTypes)
        throws TsstsDomainException {
        long result = 0;
        try {
            result = this.tsstsSpDataTsDao.delete(stockBrandId, imStkPeriodTypeTypes);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/06/09 例外処理
            final String errMsg = "";
            final TsstsLogMessageTypes logMsgTypes = TsstsLogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }
        return result;
    }
}
