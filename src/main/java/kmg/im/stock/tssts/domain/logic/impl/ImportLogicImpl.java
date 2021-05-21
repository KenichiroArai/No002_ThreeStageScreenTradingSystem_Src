package kmg.im.stock.tssts.domain.logic.impl;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockBrandDao;
import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.domain.logic.ImportLogic;
import kmg.im.stock.tssts.infrastructure.types.TypeOfPeriodypes;

/**
 * インポートロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class ImportLogicImpl implements ImportLogic {

    /** 株価銘柄格納パス */
    @Value("${import.path.stockpricestockstoragepath}")
    private Path stockPriceStockStoragePath;

    /** 株価データＤＡＯ */
    @Autowired
    private StockPriceDataDao stockPriceDataDao;

    /** 株銘柄ＤＡＯ */
    @Autowired
    private StockBrandDao stockBrandDao;

    /** 株価時系列ＤＡＯ */
    @Autowired
    private StockPriceTimeSeriesDao stockPriceTimeSeriesDao;

    /**
     * 全株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void registerAllStockPriceData() {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataDao
            .findOfStockPriceStockStoragePath(this.stockPriceStockStoragePath);

        /* 銘柄ごとの株価データを株価時系列に挿入する */
        for (final Path stockPricePath : stockPriceStockStoragePathList) {
            this.registerStockPriceDataOfFile(stockPricePath);
        }

    }

    /**
     * ディレクトリにある株価データを登録する<br>
     * <p>
     * ディレクトリパスにある株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param directoryPath
     *                      ディレクトリパス
     */
    @Override
    public void registerStockPriceDataOfDirectory(final Path directoryPath) {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataDao
            .findOfStockPriceStockStoragePath(this.stockPriceStockStoragePath);

        /* 銘柄ごとの株価データを株価時系列に挿入する */
        for (final Path stockPricePath : stockPriceStockStoragePathList) {
            this.registerStockPriceDataOfFile(stockPricePath);
        }
    }

    /**
     * ファイルの株価データを登録する<br>
     * <p>
     * ファイルパスに該当する株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     */
    @Override
    public void registerStockPriceDataOfFile(final Path filePath) {

        /* コードを取得する */
        long code = 0L;
        try {
            // TODO KenichiroArai 2021/05/07 ユーティリティ化する
            final String fullFileName = filePath.getFileName().toString();
            final String fileName = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
            code = Integer.parseInt(fileName);
        } catch (final NumberFormatException e) {
            // TODO KenichiroArai 2021/05/07 例外処理

            e.printStackTrace();
            return;
        }

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandDao.getId(code, LocalDate.now());

        /* 株価データのリストを検索する */
        final List<StockPriceDataDto> stockPriceDataDtoList = this.stockPriceDataDao
            .findAllStockPriceDataDtoList(filePath);

        /* 日足の株価時系列を登録する */
        final List<StockPriceTimeSeriesDto> stockPriceTimeSeriesOfDialyList = new ArrayList<>();
        for (final StockPriceDataDto stockPriceDataDto : stockPriceDataDtoList) {

            // TODO KenichiroArai 2021/05/20 BeanUtils.copyPropertiesをユーティリティ化する
            final StockPriceTimeSeriesDto stockPriceTimeSeriesOfDialy = new StockPriceTimeSeriesDto();
            BeanUtils.copyProperties(stockPriceDataDto, stockPriceTimeSeriesOfDialy);

            // 株価銘柄IDを設定する
            stockPriceTimeSeriesOfDialy.setStockBrandId(stockBrandId);
            // 期間の種類IDを設定する
            stockPriceTimeSeriesOfDialy.setTypeOfPeriodId(TypeOfPeriodypes.DAILY.getValue());
            // 期間開始日を設定する
            stockPriceTimeSeriesOfDialy.setPeriodStartDate(stockPriceDataDto.getDate());
            // 期間終了日を設定する
            stockPriceTimeSeriesOfDialy.setPeriodEndDate(stockPriceDataDto.getDate());

            stockPriceTimeSeriesOfDialyList.add(stockPriceTimeSeriesOfDialy);
        }
        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesOfDialyList) {
            this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
        }

        /* 週足の株価時系列を登録する */
        // TODO KenichiroArai 2021/05/16 外部クラス化またはメソッド化する
        if (ListUtils.isNotEmpty(stockPriceDataDtoList)) {
            // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
            final List<StockPriceTimeSeriesDto> stockPriceOfWeeklyDtoList = new ArrayList<>(); // 株価週足のリスト
            StockPriceTimeSeriesDto addStockPriceOfWeeklyDto = new StockPriceTimeSeriesDto(); // 追加する週足
            addStockPriceOfWeeklyDto.setStockBrandId(stockBrandId);
            addStockPriceOfWeeklyDto.setNo(0L);
            // 期間の種類IDを設定する
            addStockPriceOfWeeklyDto.setTypeOfPeriodId(TypeOfPeriodypes.WEEKLY.getValue());
            addStockPriceOfWeeklyDto.setPeriodStartDate(stockPriceDataDtoList.get(0).getDate());
            addStockPriceOfWeeklyDto.setOp(stockPriceDataDtoList.get(0).getOp()); // 始値は最初のデータを設定する
            BigDecimal lp = stockPriceDataDtoList.get(0).getLp();
            BigDecimal hp = stockPriceDataDtoList.get(0).getHp();
            long volume = stockPriceDataDtoList.get(0).getVolume();
            for (int i = 1; i < stockPriceDataDtoList.size(); i++) {

                final StockPriceDataDto stockPriceDataDto = stockPriceDataDtoList.get(i);

                // 週が異なるか
                if ((stockPriceDataDto.getDate().getDayOfWeek()
                    .compareTo(addStockPriceOfWeeklyDto.getPeriodStartDate().getDayOfWeek()) <= 0)) {
                    // 曜日が開始の曜日よりも同じまたは前の場合

                    // ひとつ前の情報を終値に設定する
                    final StockPriceDataDto prestockPriceDataDto = stockPriceDataDtoList.get(i - 1);
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
                    final StockPriceDataDto preStockPriceDataDto = stockPriceDataDtoList.get(i - 1);
                    addStockPriceOfWeeklyDto.setPeriodEndDate(preStockPriceDataDto.getDate());
                    addStockPriceOfWeeklyDto.setCp(preStockPriceDataDto.getCp());
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
            final StockPriceDataDto endStockPriceDataDto = stockPriceDataDtoList.get(stockPriceDataDtoList.size() - 1);
            addStockPriceOfWeeklyDto.setPeriodEndDate(endStockPriceDataDto.getDate());
            addStockPriceOfWeeklyDto.setCp(endStockPriceDataDto.getCp());
            addStockPriceOfWeeklyDto.setLp(lp);
            addStockPriceOfWeeklyDto.setHp(hp);
            addStockPriceOfWeeklyDto.setLp(lp);
            addStockPriceOfWeeklyDto.setVolume(volume);

            // 株価週足のリストに追加
            stockPriceOfWeeklyDtoList.add(addStockPriceOfWeeklyDto);

            // TODO KenichiroArai 2021/05/16 デバッグ出力
            System.out.println("株価週足：開始");
            stockPriceOfWeeklyDtoList.forEach(dto -> System.out.println(
                String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                    dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
            System.out.println("株価週足：終了");

            // TODO KenichiroArai 2021/05/16 実装中
            for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceOfWeeklyDtoList) {
                this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
            }
        }

        /* 株価月足を作成する */
        // TODO KenichiroArai 2021/05/16 外部クラス化またはメソッド化する
        if (ListUtils.isNotEmpty(stockPriceDataDtoList)) {

            // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
            final List<StockPriceTimeSeriesDto> stockPriceOfMonthlyDtoList = new ArrayList<>(); // 株価月足のリスト
            StockPriceTimeSeriesDto addStockPriceTimeSeriesDto = new StockPriceTimeSeriesDto(); // 追加する株価月足ＤＴＯ
            addStockPriceTimeSeriesDto.setStockBrandId(stockBrandId);
            addStockPriceTimeSeriesDto.setNo(0L);
            // 期間の種類IDを設定する
            addStockPriceTimeSeriesDto.setTypeOfPeriodId(TypeOfPeriodypes.MONTHLY.getValue());
            addStockPriceTimeSeriesDto.setPeriodStartDate(stockPriceDataDtoList.get(0).getDate());
            addStockPriceTimeSeriesDto.setOp(stockPriceDataDtoList.get(0).getOp()); // 始値は最初のデータを設定する
            BigDecimal lp = stockPriceDataDtoList.get(0).getLp();
            BigDecimal hp = stockPriceDataDtoList.get(0).getHp();
            long volume = stockPriceDataDtoList.get(0).getVolume();
            for (int i = 1; i < stockPriceDataDtoList.size(); i++) {

                final StockPriceDataDto stockPriceDataDto = stockPriceDataDtoList.get(i);

                // 月が異なるか
                if (stockPriceDataDto.getDate().getMonthValue() != addStockPriceTimeSeriesDto.getPeriodStartDate()
                    .getMonthValue()) {
                    // 月が開始の月と異なる場合

                    // ひとつ前の情報を終値に設定する
                    final StockPriceDataDto preStockPriceDataDto = stockPriceDataDtoList.get(i - 1);
                    addStockPriceTimeSeriesDto.setPeriodEndDate(preStockPriceDataDto.getDate());
                    addStockPriceTimeSeriesDto.setCp(preStockPriceDataDto.getCp());
                    addStockPriceTimeSeriesDto.setLp(lp);
                    addStockPriceTimeSeriesDto.setHp(hp);
                    addStockPriceTimeSeriesDto.setLp(lp);
                    addStockPriceTimeSeriesDto.setVolume(volume);

                    // 株価月足のリストに追加
                    stockPriceOfMonthlyDtoList.add(addStockPriceTimeSeriesDto);

                    // 現在の情報を追加する株価月足ＤＴＯに設定する
                    addStockPriceTimeSeriesDto = new StockPriceTimeSeriesDto();
                    addStockPriceTimeSeriesDto.setStockBrandId(stockBrandId);
                    addStockPriceTimeSeriesDto.setNo(Integer.valueOf(i).longValue());
                    addStockPriceTimeSeriesDto.setPeriodStartDate(stockPriceDataDto.getDate());
                    addStockPriceTimeSeriesDto.setOp(stockPriceDataDto.getOp());
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
            final StockPriceDataDto endStockPriceDataDto = stockPriceDataDtoList.get(stockPriceDataDtoList.size() - 1);
            addStockPriceTimeSeriesDto.setPeriodEndDate(endStockPriceDataDto.getDate());
            addStockPriceTimeSeriesDto.setCp(endStockPriceDataDto.getCp());
            addStockPriceTimeSeriesDto.setLp(lp);
            addStockPriceTimeSeriesDto.setHp(hp);
            addStockPriceTimeSeriesDto.setLp(lp);
            addStockPriceTimeSeriesDto.setVolume(volume);

            // 株価月足のリストに追加
            stockPriceOfMonthlyDtoList.add(addStockPriceTimeSeriesDto);

            // TODO KenichiroArai 2021/05/16 デバッグ出力
            System.out.println("株価月足：開始");
            stockPriceOfMonthlyDtoList.forEach(dto -> System.out.println(
                String.format("期間開始日：%s, 期間終了日：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getPeriodStartDate(),
                    dto.getPeriodEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
            System.out.println("株価週足：終了");

            // TODO KenichiroArai 2021/05/16 実装中
            for (final StockPriceTimeSeriesDto stockPriceMonthlyDto : stockPriceOfMonthlyDtoList) {
                this.stockPriceTimeSeriesDao.insert(stockPriceMonthlyDto);
            }
        }

    }
}
