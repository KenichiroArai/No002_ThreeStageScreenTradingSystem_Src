package kmg.im.stock.tssts.domain.model.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceTimeSeriesMonthlyMgtModel;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodTypes;
import kmg.spring.infrastructure.constants.BeanDefaultScopeConstants;

/**
 * 株価時系列月足管理モデルインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
@Scope(BeanDefaultScopeConstants.PROTOTYPE)
public class StockPriceTimeSeriesMonthlyMgtModelImpl implements StockPriceTimeSeriesMonthlyMgtModel {

    /** 株価銘柄ID */
    private long stockBrandId;

    /** 株価データのリスト */
    private List<StockPriceDataModel> stockPriceDataModelList;

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
    public StockPriceTimeSeriesMonthlyMgtModelImpl(final StockPriceTimeSeriesDao stockPriceTimeSeriesDao) {
        this.stockPriceTimeSeriesDao = stockPriceTimeSeriesDao;
    }

    /**
     * 初期化する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandId
     *                                株価銘柄ID
     * @param stockPriceDataModelList
     *                                株価データのリスト
     */
    @SuppressWarnings("hiding")
    @Override
    public void initialize(final long stockBrandId, final List<StockPriceDataModel> stockPriceDataModelList) {
        this.stockBrandId = stockBrandId;
        this.stockPriceDataModelList = stockPriceDataModelList;
    }

    /**
     * 株価銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄ID
     */
    @Override
    public long getStockBrandId() {
        final long result = this.stockBrandId;
        return result;
    }

    /**
     * 株価データのリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データのリスト
     */
    @Override
    public List<StockPriceDataModel> getStockPriceDataModelList() {
        final List<StockPriceDataModel> result = this.stockPriceDataModelList;
        return result;
    }

    /**
     * 登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void register() {

        if (!ListUtils.isNotEmpty(this.stockPriceDataModelList)) {
            return;
        }

        // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
        final List<StockPriceTimeSeriesDto> stockPriceOfMonthlyDtoList = new ArrayList<>(); // 株価月足のリスト
        StockPriceTimeSeriesDto addStockPriceTimeSeriesDto = new StockPriceTimeSeriesDto(); // 追加する株価月足ＤＴＯ
        addStockPriceTimeSeriesDto.setStockBrandId(this.stockBrandId);
        addStockPriceTimeSeriesDto.setNo(0L);
        // 期間の種類IDを設定する
        addStockPriceTimeSeriesDto.setTypeOfPeriodId(TypeOfPeriodTypes.MONTHLY.getValue());
        addStockPriceTimeSeriesDto.setPeriodStartDate(this.stockPriceDataModelList.get(0).getDate());
        addStockPriceTimeSeriesDto.setOp(this.stockPriceDataModelList.get(0).getOp()); // 始値は最初のデータを設定する
        BigDecimal lp = this.stockPriceDataModelList.get(0).getLp();
        BigDecimal hp = this.stockPriceDataModelList.get(0).getHp();
        long volume = this.stockPriceDataModelList.get(0).getVolume();
        for (int i = 1; i < this.stockPriceDataModelList.size(); i++) {

            final StockPriceDataModel stockPriceDataModel = this.stockPriceDataModelList.get(i);

            // 月が異なるか
            if (stockPriceDataModel.getDate().getMonthValue() != addStockPriceTimeSeriesDto.getPeriodStartDate()
                .getMonthValue()) {
                // 月が開始の月と異なる場合

                // ひとつ前の情報を終値に設定する
                final StockPriceDataModel preStockPriceDataModel = this.stockPriceDataModelList.get(i - 1);
                addStockPriceTimeSeriesDto.setPeriodEndDate(preStockPriceDataModel.getDate());
                addStockPriceTimeSeriesDto.setCp(preStockPriceDataModel.getCp());
                addStockPriceTimeSeriesDto.setLp(lp);
                addStockPriceTimeSeriesDto.setHp(hp);
                addStockPriceTimeSeriesDto.setLp(lp);
                addStockPriceTimeSeriesDto.setVolume(volume);

                // 株価月足のリストに追加
                stockPriceOfMonthlyDtoList.add(addStockPriceTimeSeriesDto);

                // 現在の情報を追加する株価月足ＤＴＯに設定する
                addStockPriceTimeSeriesDto = new StockPriceTimeSeriesDto();
                addStockPriceTimeSeriesDto.setStockBrandId(this.stockBrandId);
                addStockPriceTimeSeriesDto.setNo(Integer.valueOf(i).longValue());
                addStockPriceTimeSeriesDto.setPeriodStartDate(stockPriceDataModel.getDate());
                addStockPriceTimeSeriesDto.setOp(stockPriceDataModel.getOp());
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
        final StockPriceDataModel endStockPriceDataModel = this.stockPriceDataModelList
            .get(this.stockPriceDataModelList.size() - 1);
        addStockPriceTimeSeriesDto.setPeriodEndDate(endStockPriceDataModel.getDate());
        addStockPriceTimeSeriesDto.setCp(endStockPriceDataModel.getCp());
        addStockPriceTimeSeriesDto.setLp(lp);
        addStockPriceTimeSeriesDto.setHp(hp);
        addStockPriceTimeSeriesDto.setLp(lp);
        addStockPriceTimeSeriesDto.setVolume(volume);

        // 株価月足のリストに追加
        stockPriceOfMonthlyDtoList.add(addStockPriceTimeSeriesDto);

        // TODO KenichiroArai 2021/05/16 デバッグ出力
        System.out.println("株価月足：開始");
        stockPriceOfMonthlyDtoList.forEach(dto -> System.out
            .println(String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
        System.out.println("株価週足：終了");

        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceMonthlyDto : stockPriceOfMonthlyDtoList) {
            this.stockPriceTimeSeriesDao.insert(stockPriceMonthlyDto);
        }

    }

}
