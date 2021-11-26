package kmg.im.stock.tssts.domain.logic.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.SimpleSptsModel;
import kmg.im.stock.tssts.domain.model.SptsRegDataModel;
import kmg.im.stock.tssts.domain.model.impl.SimpleSptsMgtModelImpl;
import kmg.im.stock.tssts.domain.model.impl.SimpleSptsModelImpl;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesLogicImpl implements StockPriceTimeSeriesLogic {

    /** 株価時系列ＤＡＯ */
    private final StockPriceTimeSeriesDao stockPriceTimeSeriesDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesDao
     *                                株価時系列ＤＡＯ
     */
    public StockPriceTimeSeriesLogicImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
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
            result = this.stockPriceTimeSeriesDao.deleteBySbIdAndPeriodTypeTypes(sbId, periodTypeTypes);
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
     * 期間の種類で株価時系列登録データモデルのリストを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                              期間の種類の種類
     * @param sptsMainDataModelList
     *                              株価時系列登録データモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register(final PeriodTypeTypes periodTypeTypes, final List<SptsRegDataModel> sptsMainDataModelList)
        throws TsstsDomainException {

        if (ListUtils.isEmpty(sptsMainDataModelList)) {
            return;
        }

        for (final SptsRegDataModel sptsMainDataModel : sptsMainDataModelList) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDtoImpl();
            BeanUtils.copyProperties(sptsMainDataModel, stockPriceTimeSeriesDto);

            try {
                this.stockPriceTimeSeriesDao.insertByPttAndSptsDto(periodTypeTypes, stockPriceTimeSeriesDto);
            } catch (final KmgDomainException e) {
                // TODO KenichiroArai 2021/06/12 例外処理
                final String errMsg = "";
                final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
                final Object[] logMsgArg = {};
                throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
            }
        }
    }

    /**
     * 株銘柄ＩＤと期間の種類の種類を基にシンプルモデルを返す検索を行う<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbId
     *                        株銘柄ＩＤ
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return シンプル株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public SimpleSptsMgtModel findSimpleBySbIdAndPti(final long sbId, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException {
        final SimpleSptsMgtModel result = new SimpleSptsMgtModelImpl();

        /* 株銘柄ＩＤと期間の種類の種類を基に株価時系列ＤＴＯのリストを取得する */
        List<StockPriceTimeSeriesDto> stockPriceTimeSeriesDtoList = null;
        try {
            stockPriceTimeSeriesDtoList = this.stockPriceTimeSeriesDao.findBySbIdAndPtt(sbId, periodTypeTypes);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/11/24 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }

        /* シンプル株価時系列モデルのリストに詰め替える */
        final List<SimpleSptsModel> simpleSptsModelList = stockPriceTimeSeriesDtoList.stream().map(mapper -> {
            final SimpleSptsModel simpleSptsModel = new SimpleSptsModelImpl();
            BeanUtils.copyProperties(mapper, simpleSptsModel);
            return simpleSptsModel;
        }).collect(Collectors.toList());

        /* 詰め替えたデータを結果に追加する */
        result.addAllData(simpleSptsModelList);

        return result;
    }
}
