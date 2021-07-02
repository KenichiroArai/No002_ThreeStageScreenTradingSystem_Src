package kmg.im.stock.tssts.domain.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesMgtDto;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesDtoImpl;
import kmg.im.stock.tssts.data.dto.impl.StockPriceTimeSeriesMgtDtoImpl;
import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesMgtModelImpl;
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
     * 削除する<br>
     * <p>
     * 期間の種類に該当するデータを削除する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param periodTypeTypes
     *                        期間の種類の種類
     * @return 削除数
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long delete(final PeriodTypeTypes periodTypeTypes) throws TsstsDomainException {
        long result = 0;
        try {
            result = this.stockPriceTimeSeriesDao.delete(periodTypeTypes);
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
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesMgtModel
     *                                     株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void register(final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel) throws TsstsDomainException {

        if (stockPriceTimeSeriesMgtModel.isDataListEmpty()) {
            return;
        }

        final StockPriceTimeSeriesMgtDto stockPriceTimeSeriesMgtDto = new StockPriceTimeSeriesMgtDtoImpl();
        for (final StockPriceTimeSeriesModel stockPriceTimeSeriesModel : stockPriceTimeSeriesMgtModel.getDataList()) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = new StockPriceTimeSeriesDtoImpl();
            BeanUtils.copyProperties(stockPriceTimeSeriesModel, stockPriceTimeSeriesDto);

            // 株価時系列期間の種類IDを設定する
            stockPriceTimeSeriesDto.setSptsptId(stockPriceTimeSeriesMgtModel.getSptsptId());

            stockPriceTimeSeriesMgtDto.addData(stockPriceTimeSeriesDto);
        }
        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesMgtDto.getDataList()) {
            try {
                this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
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
     *                 株価時系列期間の種類ID
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceTimeSeriesMgtModel findBySptsptId(final long sptsptId) throws TsstsDomainException {

        final StockPriceTimeSeriesMgtModel result = new StockPriceTimeSeriesMgtModelImpl();

        /* 株価時系列期間の種類ID株価時系列管理を検索し、該当する株価時系列ＤＴＯのリストを取得する */
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

        /* 株価時系列管理モデルへ詰め替える */
        final List<StockPriceTimeSeriesModel> stockPriceTimeSeriesModelList = new ArrayList<>();
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesDtoList) {
            final StockPriceTimeSeriesModel stockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
            BeanUtils.copyProperties(stockPriceTimeSeriesDto, stockPriceTimeSeriesModel);
            stockPriceTimeSeriesModelList.add(stockPriceTimeSeriesModel);
        }
        result.addAllData(stockPriceTimeSeriesModelList);

        return result;

    }
}
