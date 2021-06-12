package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceTimeSeriesLogic;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesMgtModelImpl;
import kmg.im.stock.tssts.domain.model.impl.StockPriceTimeSeriesModelImpl;
import kmg.im.stock.tssts.domain.service.AbstractStockPriceTimeSeriesService;
import kmg.im.stock.tssts.domain.service.SptsptService;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesWeeklyService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 株価時系列週足サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesWeeklyServiceImpl extends AbstractStockPriceTimeSeriesService
    implements StockPriceTimeSeriesWeeklyService {

    /** 株価時系列ロジック */
    private final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic;

    /** 株銘柄サービス */
    private final StockBrandService stockBrandService;

    /** 株価時系列期間の種類サービス */
    private final SptsptService sptsptService;

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
     * @param sptsptService
     *                                  株価時系列期間の種類サービス
     */
    public StockPriceTimeSeriesWeeklyServiceImpl(final StockPriceTimeSeriesLogic stockPriceTimeSeriesLogic,
        final StockBrandService stockBrandService, final SptsptService sptsptService) {
        super(stockPriceTimeSeriesLogic);
        this.stockPriceTimeSeriesLogic = stockPriceTimeSeriesLogic;
        this.stockBrandService = stockBrandService;
        this.sptsptService = sptsptService;
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
        final long result = this.stockPriceTimeSeriesLogic.delete(PeriodTypeTypes.WEEKLY);
        return result;
    }

    /**
     * 株価時系列管理モデルにして返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataMgtModel
     *                               株価データ管理モデル
     * @return 株価時系列管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceTimeSeriesMgtModel toStockPriceTimeSeriesMgtModel(
        final StockPriceDataMgtModel stockPriceDataMgtModel) throws TsstsDomainException {

        final StockPriceTimeSeriesMgtModel result = new StockPriceTimeSeriesMgtModelImpl();

        if (stockPriceDataMgtModel.isDataListEmpty()) {
            return result;
        }

        stockPriceDataMgtModel.setStockBrandCode(stockPriceDataMgtModel.getStockBrandCode());

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandService.getStockBrandId(stockPriceDataMgtModel.getStockBrandCode());
        // 株価銘柄IDを設定する
        result.setStockBrandId(stockBrandId);
        // 期間の種類の種類を設定する
        result.setPeriodTypeTypes(PeriodTypeTypes.WEEKLY);
        // 株価銘柄IDを設定する
        final long sptsptId = this.sptsptService.getSptsptId(result.getStockBrandId(), result.getPeriodTypeTypes());
        result.setSptsptId(sptsptId);

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
        StockPriceTimeSeriesModel addStockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl(); // 追加する週足
        addStockPriceTimeSeriesModel.setNo(0L);
        // 期間の種類IDを設定する
        addStockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataMgtModel.getDataList().get(0).getDate());
        addStockPriceTimeSeriesModel.setOp(stockPriceDataMgtModel.getDataList().get(0).getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = stockPriceDataMgtModel.getDataList().get(0).getLp();
        BigDecimal hp = stockPriceDataMgtModel.getDataList().get(0).getHp();
        long volume = stockPriceDataMgtModel.getDataList().get(0).getVolume();
        for (int i = 1; i < stockPriceDataMgtModel.getDataList().size(); i++) {

            final StockPriceDataModel stockPriceDataDto = stockPriceDataMgtModel.getDataList().get(i);

            // 週が異なるか
            if ((stockPriceDataDto.getDate().getDayOfWeek()
                .compareTo(addStockPriceTimeSeriesModel.getPeriodStartDate().getDayOfWeek()) <= 0)) {
                // 曜日が開始の曜日よりも同じまたは前の場合

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel prestockPriceDataDto = stockPriceDataMgtModel.getDataList().get(i - 1);
                addStockPriceTimeSeriesModel.setPeriodEndDate(prestockPriceDataDto.getDate());
                addStockPriceTimeSeriesModel.setCp(prestockPriceDataDto.getCp());
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setHp(hp);
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setVolume(volume);

                // 株価週足のリストに追加
                result.addData(addStockPriceTimeSeriesModel);

                // 現在の情報を追加する株価週足ＤＴＯに設定する
                addStockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
                addStockPriceTimeSeriesModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addStockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataDto.getDate());
                addStockPriceTimeSeriesModel.setOp(stockPriceDataDto.getOp());
                lp = stockPriceDataDto.getLp();
                hp = stockPriceDataDto.getHp();
                volume = stockPriceDataDto.getVolume();

                continue;

            } else if (stockPriceDataDto.getDate()
                .compareTo(addStockPriceTimeSeriesModel.getPeriodStartDate().plusDays(7)) >= 0) {
                // 開始の7日以降の場合

                // TODO KenichiroArai 2021/05/16 曜日の判定と処理が同じなので、まとめる

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel preStockPriceDataModel = stockPriceDataMgtModel.getDataList().get(i - 1);
                addStockPriceTimeSeriesModel.setPeriodEndDate(preStockPriceDataModel.getDate());
                addStockPriceTimeSeriesModel.setCp(preStockPriceDataModel.getCp());
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setHp(hp);
                addStockPriceTimeSeriesModel.setLp(lp);
                addStockPriceTimeSeriesModel.setVolume(volume);

                // 株価週足のリストに追加
                result.addData(addStockPriceTimeSeriesModel);

                // 現在の情報を追加する株価週足ＤＴＯに設定する
                addStockPriceTimeSeriesModel = new StockPriceTimeSeriesModelImpl();
                addStockPriceTimeSeriesModel.setNo(Integer.valueOf(i).longValue());
                // 期間の種類IDを設定する
                addStockPriceTimeSeriesModel.setPeriodStartDate(stockPriceDataDto.getDate());
                addStockPriceTimeSeriesModel.setOp(stockPriceDataDto.getOp());
                lp = stockPriceDataDto.getLp();
                hp = stockPriceDataDto.getHp();
                volume = stockPriceDataDto.getVolume();

                continue;

            }

            lp = lp.min(stockPriceDataDto.getLp());
            hp = hp.max(stockPriceDataDto.getHp());
            volume += stockPriceDataDto.getVolume();
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

        // 株価週足のリストに追加
        result.addData(addStockPriceTimeSeriesModel);

        // TODO KenichiroArai 2021/05/16 デバッグ出力
        System.out.println("株価週足：開始");
        result.getDataList()
            .forEach(dto -> System.out.println(
                String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                    dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
        System.out.println("株価週足：終了");

        return result;
    }
}
