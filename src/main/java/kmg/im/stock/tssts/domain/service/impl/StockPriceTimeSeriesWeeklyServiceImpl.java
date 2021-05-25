package kmg.im.stock.tssts.domain.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesWeeklyService;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;

/**
 * 株価時系列週足サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceTimeSeriesWeeklyServiceImpl implements StockPriceTimeSeriesWeeklyService {

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
    public StockPriceTimeSeriesWeeklyServiceImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                                株価銘柄ID
     * @param stockPriceDataModelList
     *                                株価データのリスト
     */
    @Override
    public void register(final long stockBrandId, final List<StockPriceDataModel> stockPriceDataModelList) {

        if (!ListUtils.isNotEmpty(stockPriceDataModelList)) {
            return;
        }

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
        final List<StockPriceTimeSeriesDto> stockPriceOfWeeklyDtoList = new ArrayList<>(); // 株価週足のリスト
        StockPriceTimeSeriesDto addStockPriceOfWeeklyDto = new StockPriceTimeSeriesDto(); // 追加する週足
        addStockPriceOfWeeklyDto.setStockBrandId(stockBrandId);
        addStockPriceOfWeeklyDto.setNo(0L);
        // 期間の種類IDを設定する
        addStockPriceOfWeeklyDto.setTypeOfPeriodId(TypeOfPeriodTypes.WEEKLY.getValue());
        addStockPriceOfWeeklyDto.setPeriodStartDate(stockPriceDataModelList.get(0).getDate());
        addStockPriceOfWeeklyDto.setOp(stockPriceDataModelList.get(0).getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = stockPriceDataModelList.get(0).getLp();
        BigDecimal hp = stockPriceDataModelList.get(0).getHp();
        long volume = stockPriceDataModelList.get(0).getVolume();
        for (int i = 1; i < stockPriceDataModelList.size(); i++) {

            final StockPriceDataModel stockPriceDataDto = stockPriceDataModelList.get(i);

            // 週が異なるか
            if ((stockPriceDataDto.getDate().getDayOfWeek()
                .compareTo(addStockPriceOfWeeklyDto.getPeriodStartDate().getDayOfWeek()) <= 0)) {
                // 曜日が開始の曜日よりも同じまたは前の場合

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel prestockPriceDataDto = stockPriceDataModelList.get(i - 1);
                addStockPriceOfWeeklyDto.setPeriodEndDate(prestockPriceDataDto.getDate());
                addStockPriceOfWeeklyDto.setCp(prestockPriceDataDto.getCp());
                addStockPriceOfWeeklyDto.setLp(lp);
                addStockPriceOfWeeklyDto.setHp(hp);
                addStockPriceOfWeeklyDto.setLp(lp);
                addStockPriceOfWeeklyDto.setVolume(volume);

                // 株価週足のリストに追加
                stockPriceOfWeeklyDtoList.add(addStockPriceOfWeeklyDto);

                // 現在の情報を追加する株価週足ＤＴＯに設定する
                addStockPriceOfWeeklyDto = new StockPriceTimeSeriesDto();
                addStockPriceOfWeeklyDto.setStockBrandId(stockBrandId);
                addStockPriceOfWeeklyDto.setNo(Integer.valueOf(i).longValue());
                addStockPriceOfWeeklyDto.setPeriodStartDate(stockPriceDataDto.getDate());
                addStockPriceOfWeeklyDto.setOp(stockPriceDataDto.getOp());
                lp = stockPriceDataDto.getLp();
                hp = stockPriceDataDto.getHp();
                volume = stockPriceDataDto.getVolume();

                continue;

            } else if (stockPriceDataDto.getDate()
                .compareTo(addStockPriceOfWeeklyDto.getPeriodStartDate().plusDays(7)) >= 0) {
                // 開始の7日以降の場合

                // TODO KenichiroArai 2021/05/16 曜日の判定と処理が同じなので、まとめる

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel preStockPriceDataModel = stockPriceDataModelList.get(i - 1);
                addStockPriceOfWeeklyDto.setPeriodEndDate(preStockPriceDataModel.getDate());
                addStockPriceOfWeeklyDto.setCp(preStockPriceDataModel.getCp());
                addStockPriceOfWeeklyDto.setLp(lp);
                addStockPriceOfWeeklyDto.setHp(hp);
                addStockPriceOfWeeklyDto.setLp(lp);
                addStockPriceOfWeeklyDto.setVolume(volume);

                // 株価週足のリストに追加
                stockPriceOfWeeklyDtoList.add(addStockPriceOfWeeklyDto);

                // 現在の情報を追加する株価週足ＤＴＯに設定する
                addStockPriceOfWeeklyDto = new StockPriceTimeSeriesDto();
                addStockPriceOfWeeklyDto.setStockBrandId(stockBrandId);
                addStockPriceOfWeeklyDto.setNo(Integer.valueOf(i).longValue());
                addStockPriceOfWeeklyDto.setPeriodStartDate(stockPriceDataDto.getDate());
                addStockPriceOfWeeklyDto.setOp(stockPriceDataDto.getOp());
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
        final StockPriceDataModel endStockPriceDataModel = stockPriceDataModelList
            .get(stockPriceDataModelList.size() - 1);
        addStockPriceOfWeeklyDto.setPeriodEndDate(endStockPriceDataModel.getDate());
        addStockPriceOfWeeklyDto.setCp(endStockPriceDataModel.getCp());
        addStockPriceOfWeeklyDto.setLp(lp);
        addStockPriceOfWeeklyDto.setHp(hp);
        addStockPriceOfWeeklyDto.setLp(lp);
        addStockPriceOfWeeklyDto.setVolume(volume);

        // 株価週足のリストに追加
        stockPriceOfWeeklyDtoList.add(addStockPriceOfWeeklyDto);

        // TODO KenichiroArai 2021/05/16 デバッグ出力
        System.out.println("株価週足：開始");
        stockPriceOfWeeklyDtoList.forEach(dto -> System.out
            .println(String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
        System.out.println("株価週足：終了");

        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceOfWeeklyDtoList) {
            this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
        }
    }
}
