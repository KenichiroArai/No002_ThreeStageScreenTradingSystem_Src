package kmg.im.stock.tssts.domain.logic.impl;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.tssts.data.dao.SptsptDao;
import kmg.im.stock.tssts.domain.logic.SptsptLogic;
import kmg.im.stock.tssts.domain.model.SptsptModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列期間の種類ロジックインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SptsptLogicImpl implements SptsptLogic {

    /** 株価時系列期間の種類ＤＡＯ */
    private final SptsptDao sptsptDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptDao
     *                  株価時系列期間の種類ＤＡＯ
     */
    public SptsptLogicImpl(final SptsptDao sptsptDao) {
        this.sptsptDao = sptsptDao;
    }

    /**
     * 株価銘柄ＩＤと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *                        株価銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long deleteBySbIdAndPeriodTypeTypes(final long sbId, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException {
        long result = 0;
        try {
            result = this.sptsptDao.deleteBySbIdAndPeriodTypeTypes(sbId, periodTypeTypes);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/06/11 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }
        return result;
    }

    /**
     * 株価時系列期間の種類IDを返す<br>
     * <p>
     * 該当がない場合は、nullを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                        株銘柄ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param baseDate
     *                        基準日
     * @return 株価銘柄ID
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public Long getSptsptId(final long stockBrandId, final PeriodTypeTypes periodTypeTypes, final LocalDate baseDate)
        throws TsstsDomainException {
        Long result = null;
        try {
            result = this.sptsptDao.getId(stockBrandId, periodTypeTypes, baseDate);
        } catch (@SuppressWarnings("unused") final EmptyResultDataAccessException e) {
            // 該当なし

            return result;
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/06/11 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }
        return result;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                        株銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 登録件数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long register(final long stockBrandId, final PeriodTypeTypes periodTypeTypes) throws TsstsDomainException {
        long result = 0;
        try {
            result = this.sptsptDao.insertBySbIdAndPtt(stockBrandId, periodTypeTypes);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/06/09 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }
        return result;
    }

    /**
     * 期間の種類ごとの株価時系列期間の種類のマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                        株銘柄ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @param baseDate
     *                        基準日
     * @return 株価銘柄ID
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public Map<PeriodTypeTypes, SptsptModel> findSptsptModelMap(final long stockBrandId,
        final PeriodTypeTypes periodTypeTypes, final LocalDate baseDate) throws TsstsDomainException {
        final Map<PeriodTypeTypes, SptsptModel> result = null;
        return result;
    }
}
