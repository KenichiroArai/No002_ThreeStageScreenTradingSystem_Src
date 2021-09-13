package kmg.im.stock.tssts.domain.logic.impl;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.exception.KmgDomainException;
import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.core.infrastructure.types.StockPriceCalcValueTypeTypes;
import kmg.im.stock.tssts.data.dao.SimDao;
import kmg.im.stock.tssts.data.dto.SimDto;
import kmg.im.stock.tssts.domain.logic.SimLogic;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.domain.model.StockPriceCalcValueModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockBrandModelImpl;
import kmg.im.stock.tssts.domain.model.impl.StockPriceCalcValueModelImpl;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesModelImpl;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * シミュレーションロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class SimLogicImpl implements SimLogic {

    /** シミュレーションＤＡＯ */
    private final SimDao simDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param simDao
     *               シミュレーションＤＡＯ
     */
    public SimLogicImpl(final SimDao simDao) {
        this.simDao = simDao;
    }

    /**
     * 株価コードに該当する株価時系列管理モデルを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockCode
     *                  株価コード
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockBrandModel getStockPriceTimeSeriesMgtModel(final long stockCode) throws TsstsDomainException {
        final StockBrandModel result = new StockBrandModelImpl();

        /* シミュレーションを行うデータを取得する */
        List<SimDto> simDtoList = null;
        try {
            simDtoList = this.simDao.find(stockCode);
        } catch (final KmgDomainException e) {
            // TODO KenichiroArai 2021/06/11 例外処理
            final String errMsg = "";
            final LogMessageTypes logMsgTypes = LogMessageTypes.NONE;
            final Object[] logMsgArg = {};
            throw new TsstsDomainException(errMsg, logMsgTypes, logMsgArg, e);
        }

        /* 株価銘柄を設定する */
        if (ListUtils.isNotEmpty(simDtoList)) {
            final SimDto simDto = simDtoList.get(0);

            result.setStockBrandId(simDto.getStockBrandId()); // 株銘柄ID
            result.setStockBrandCode(simDto.getStockBrandCode()); // 株価銘柄コード
        }

        /* 株価時系列情報を設定する */
        final SortedMap<Long, StockPriceTimeSeriesModel> sptsMap = new TreeMap<>(); // 株価時系列モデルのマップ
        for (final SimDto simDto : simDtoList) {

            // 株価時系列モデルの取得
            StockPriceTimeSeriesModel sptsModel = sptsMap.get(simDto.getNo());
            if (sptsModel == null) {
                // 空の場合

                // 株価時系列情報を設定する
                sptsModel = new StockPriceTimeSeriesModelImpl();
                sptsModel.setId(simDto.getSptsId()); // 株価時系列ID
                sptsModel.setName(simDto.getSptsName()); // 株価時系列名称
                sptsModel.setNo(simDto.getNo()); // 番号
                sptsModel.setPeriodStartDate(simDto.getPeriodStartDate()); // 期間開始日
                sptsModel.setPeriodEndDate(simDto.getPeriodEndDate()); // 期間終了日
                sptsModel.setOp(simDto.getOp()); // 始値
                sptsModel.setHp(simDto.getHp()); // 高値
                sptsModel.setLp(simDto.getLp()); // 安値
                sptsModel.setCp(simDto.getCp()); // 終値
                sptsModel.setVolume(simDto.getVolume()); // 出来高

                // 株価時系列モデルのマップに追加する
                sptsMap.put(simDto.getNo(), sptsModel);

                // 株価時系列管理モデルに追加する
                // TODO KenichiroArai 2021/09/07 株銘柄へのモデル変更対応の一時的エラー回避株銘柄
//                final PeriodTypeTypes periodTypeTypes = PeriodTypeTypes.getEnum(simDto.getPeriodTypeId());
//                result.addData(periodTypeTypes, sptsModel);
            }

            // 株価計算値を設定する
            // 株価計算値の種類の種類を取得する
            final StockPriceCalcValueTypeTypes stockPriceCalcValueTypeTypes = StockPriceCalcValueTypeTypes
                .getEnum(simDto.getSpcvtId());
            // 株価計算値モデルを取得する
            StockPriceCalcValueModel spcvModel = sptsModel.getStockPriceCalcValueModel(stockPriceCalcValueTypeTypes);
            if (spcvModel == null) {
                // データがない場合

                // 新規に作成
                spcvModel = new StockPriceCalcValueModelImpl();
                sptsModel.addSpcvModel(stockPriceCalcValueTypeTypes, spcvModel);
            }
            // 株価計算値モデルを設定する
            spcvModel.setSpcvtId(stockPriceCalcValueTypeTypes); // 株価計算値の種類ID
            spcvModel.setName(simDto.getSpcvtName()); // 株価計算値の種類名称
            spcvModel.setCalcValue(simDto.getCalcValue()); // 計算値
        }

        return result;
    }

    /**
     * 第１のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnFirstScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO KenichiroArai 2021/08/24 未実装
        return false;
    }

    /**
     * 第２のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnSecondScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO KenichiroArai 2021/08/24 未実装
        return false;
    }

    /**
     * 第３のスクリーンに掛ける<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeries
     *                             株価時系列
     * @return true：スクリーンに引っかる、false：スクリーンに引っかからない
     */
    @Override
    public boolean hangOnThirdScreen(final StockPriceTimeSeriesModel stockPriceTimeSeries) {
        // TODO KenichiroArai 2021/08/24 未実装
        return false;
    }

}
