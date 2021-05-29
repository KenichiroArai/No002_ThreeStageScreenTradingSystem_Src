package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.logic.impl.StockPriceTimeSeriesModelImpl;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesMonthlyService;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列月足サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesMonthlyServiceImpl implements StockPriceTimeSeriesMonthlyService {

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /** 株銘柄サービス */
    private final StockBrandService stockBrandService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceTimeSeriesLogic
     *                                  株価時系列ロジック
     * @param stockBrandService
     *                                  株銘柄サービス
     */
    public StockPriceTimeSeriesMonthlyServiceImpl(final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic,
        final StockBrandService stockBrandService) {
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockBrandService = stockBrandService;
    }

    /**
     * 削除する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void delete() {
        this.stockPriceTimeSeriesLogic.delete(TypeOfPeriodTypes.MONTHLY);
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     */
    @Override
    public void register(final StockPriceDataMgtModel stockPriceDataMgtModel) {

        if (stockPriceDataMgtModel.isDataListEmpty()) {
            return;
        }

        final StockPriceTimeSeriesMgtModel stockPriceTimeSeriesMgtModel = new StockPriceTimeSeriesMgtModelImpl();
        stockPriceDataMgtModel.setStockBrandCode(stockPriceDataMgtModel.getStockBrandCode());

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandService.getStockBrandId(stockPriceDataMgtModel.getStockBrandCode());
        // 株価銘柄IDを設定する
        stockPriceTimeSeriesMgtModel.setStockBrandId(stockBrandId);

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
        StockPriceTimeSeriesModel addStockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl(); // 追加する株価時系列モデル
        addStockPriceTimeSeriesModel.setNo(0L);
        // 期間の種類IDを設定する
        addStockPriceTimeSeriesModel.setTypeOfPeriodId(TypeOfPeriodTypes.MONTHLY.getValue());
        addStockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataMgtModel.getDataList().get(0).getDate());
        addStockPriceTimeSeriesModel.setOp(stockPriceDataMgtModel.getDataList().get(0).getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = stockPriceDataMgtModel.getDataList().get(0).getLp();
        BigDecimal hp = stockPriceDataMgtModel.getDataList().get(0).getHp();
        long volume = stockPriceDataMgtModel.getDataList().get(0).getVolume();
        for (int i = 1; i < stockPriceDataMgtModel.getDataList().size(); i++) {

            final StockPriceDataModel stockPriceDataModel = stockPriceDataMgtModel.getDataList().get(i);

            // 月が異なるか
            if (stockPriceDataModel.getDate().getMonthValue() != addStockPriceTimeSeriesModel.getPeriodStartDate()
                .getMonthValue()) {
                // 月が開始の月と異なる場合

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel preStockPriceDataModel = stockPriceDataMgtModel.getDataList().get(i - 1);
                addStockPriceTimeSeriesModel.setPeriodEndDate(preStockPriceDataModel.getDate());
                addStockPriceTimeSeriesModel.setCp(preStockPriceDataModel.getCp());
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setHp(hp);
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setVolume(volume);

                // 株価月足のリストに追加
                stockPriceTimeSeriesMgtModel.addData(addStockPriceTimeSeriesModel);

                // 現在の情報を追加する株価時系列管理モデルに設定する
                addStockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
                addStockPriceTimeSeriesModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addStockPriceTimeSeriesModel.setTypeOfPeriodId(TypeOfPeriodTypes.MONTHLY.getValue());
                addStockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataModel.getDate());
                addStockPriceTimeSeriesModel.setOp(stockPriceDataModel.getOp());
                lp = stockPriceDataModel.getLp();
                hp = stockPriceDataModel.getHp();
                volume = stockPriceDataModel.getVolume();

                continue;

            }

            lp = lp.min(stockPriceDataModel.getLp());
            hp = hp.max(stockPriceDataModel.getHp());
            volume += stockPriceDataModel.getVolume();
        }

        // ひとつ前の情報を終値に設定する
        final StockPriceDataModel endStockPriceDataModel = stockPriceDataMgtModel.getDataList()
            .get(stockPriceDataMgtModel.getDataList().size() - 1);
        addStockPriceTimeSeriesModel.setPeriodEndDate(endStockPriceDataModel.getDate());
        addStockPriceTimeSeriesModel.setCp(endStockPriceDataModel.getCp());
        addStockPriceTimeSeriesModel.setLp(lp);
        addStockPriceTimeSeriesModel.setHp(hp);
        addStockPriceTimeSeriesModel.setLp(lp);
        addStockPriceTimeSeriesModel.setVolume(volume);

        // 株価月足のリストに追加
        stockPriceTimeSeriesMgtModel.addData(addStockPriceTimeSeriesModel);

        // TODO KenichiroArai 2021/05/16 デバッグ出力
        System.out.println("株価月足：開始");
        stockPriceTimeSeriesMgtModel.getDataList()
            .forEach(dto -> System.out.println(
                String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                    dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
        System.out.println("株価週足：終了");

        this.stockPriceTimeSeriesLogic.register(stockPriceTimeSeriesMgtModel);

    }
}
