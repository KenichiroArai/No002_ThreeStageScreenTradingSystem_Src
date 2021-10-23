package kmg.im.stock.tssts.domain.logic.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.SptsMainDataModel;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockBrandModelImpl;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesModelImpl;
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
     * 株価銘柄コードと期間の種類の種類に該当するデータを削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sbCd
     *                        株価銘柄コード
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long deleteBySbCdAndPeriodTypeTypes(final long sbCd, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException {
        long result = 0;
        try {
            result = this.stockPriceTimeSeriesDao.deleteBySbCdAndPeriodTypeTypes(sbCd, periodTypeTypes);
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
     * 期間の種類で株価時系列メインデータモデルのリストを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                              期間の種類の種類
     * @param sptsMainDataModelList
     *                              株価時系列メインデータモデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register(final PeriodTypeTypes periodTypeTypes, final List<SptsMainDataModel> sptsMainDataModelList)
        throws TsstsDomainException {

        if (ListUtils.isEmpty(sptsMainDataModelList)) {
            return;
        }

        for (final SptsMainDataModel sptsMainDataModel : sptsMainDataModelList) {

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
     * 株価時系列管理モデルを検索する<br>
     * <p>
     * 株価時系列期間の種類IDに該当する株価時系列管理モデルを検索し、該当する株価時系列管理モデルを返す。<br>
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param sptsptId
     *                        株価時系列期間の種類ID
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockBrandModel findBySptsptId(final long sptsptId, final PeriodTypeTypes periodTypeTypes)
        throws TsstsDomainException {

        final StockBrandModel result = new StockBrandModelImpl();

        /* 株価時系列期間の種類IDに該当する株価時系列管理モデルを検索し、該当する株価時系列ＤＴＯのリストを取得する */
        List<StockPriceTimeSeriesDto> stockPriceTimeSeriesDtoList = null;
        try {
            stockPriceTimeSeriesDtoList = this.stockPriceTimeSeriesDao.findBySptsptId(sptsptId);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/07/01 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }

        /* 株価時系列期間の種類モデルへ詰め替える */
        // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//        final List<SptsptModel> sptsptModelList = new ArrayList<>();
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesDtoList) {
            final StockPriceTimeSeriesModel stockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
            BeanUtils.copyProperties(stockPriceTimeSeriesDto, stockPriceTimeSeriesModel);
            // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//            stockPriceTimeSeriesModelList.add(stockPriceTimeSeriesModel);
//            result.addAllSptsptModel(stockPriceTimeSeriesModelList);
        }

        return result;

    }
}
