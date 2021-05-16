package kmg.im.stock.tssts.domain.logic.impl;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.ListUtils;
import kmg.im.stock.tssts.data.dao.StockBrandDao;
import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dao.StockPriceMonthlyDao;
import kmg.im.stock.tssts.data.dao.StockPriceTimeSeriesDao;
import kmg.im.stock.tssts.data.dao.StockPriceWeeklyDao;
import kmg.im.stock.tssts.data.dto.StockPriceMonthlyDto;
import kmg.im.stock.tssts.data.dto.StockPriceTimeSeriesDto;
import kmg.im.stock.tssts.data.dto.StockPriceWeeklyDto;
import kmg.im.stock.tssts.domain.logic.ImportLogic;

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

    /** 株価週足ＤＡＯ */
    @Autowired
    private StockPriceWeeklyDao stockPriceWeeklyDao;

    /** 株価月足ＤＡＯ */
    @Autowired
    private StockPriceMonthlyDao stockPriceMonthlyDao;

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
            final String fileName     = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
            code = Integer.parseInt(fileName);
        } catch (final NumberFormatException e) {
            // TODO KenichiroArai 2021/05/07 例外処理

            e.printStackTrace();
            return;
        }

        /* 株価銘柄IDを取得する */
        final long stockBrandId = this.stockBrandDao.getId(code, LocalDate.now());

        /* 株価時系列を設定する */
        // 株価時系列を取得する
        final List<StockPriceTimeSeriesDto> stockPriceTimeSeriesDtoList = this.stockPriceDataDao
            .findAllStockPriceTimeSeriesDtoList(filePath);
        // 株価銘柄IDを設定する
        stockPriceTimeSeriesDtoList.forEach(dto -> dto.setStockBrandId(stockBrandId));

        /* 株価時列を登録する */
        // TODO KenichiroArai 2021/05/16 実装中
        for (final StockPriceTimeSeriesDto stockPriceTimeSeriesDto : stockPriceTimeSeriesDtoList) {
            this.stockPriceTimeSeriesDao.insert(stockPriceTimeSeriesDto);
        }

        /* 株価週足を作成する */
        // TODO KenichiroArai 2021/05/16 外部クラス化またはメソッド化する
        if (ListUtils.isNotEmpty(stockPriceTimeSeriesDtoList)) {
            // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
            final List<StockPriceWeeklyDto> stockPriceWeeklyDtoList = new ArrayList<>();         // 株価週足のリスト
            StockPriceWeeklyDto             addStockPriceWeeklyDto  = new StockPriceWeeklyDto(); // 追加する株価週足ＤＴＯ
            addStockPriceWeeklyDto.setStockBrandId(stockBrandId);
            addStockPriceWeeklyDto.setNo(0L);
            addStockPriceWeeklyDto.setWeeklyStartDate(stockPriceTimeSeriesDtoList.get(0).getDate());
            addStockPriceWeeklyDto.setOp(stockPriceTimeSeriesDtoList.get(0).getOp()); // 始値は最初のデータを設定する
            BigDecimal lp     = stockPriceTimeSeriesDtoList.get(0).getLp();
            BigDecimal hp     = stockPriceTimeSeriesDtoList.get(0).getHp();
            long       volume = stockPriceTimeSeriesDtoList.get(0).getVolume();
            for (int i = 1; i < stockPriceTimeSeriesDtoList.size(); i++) {

                final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList.get(i);

                // 週が異なるか
                if ((stockPriceTimeSeriesDto.getDate().getDayOfWeek()
                    .compareTo(addStockPriceWeeklyDto.getWeeklyStartDate().getDayOfWeek()) <= 0)) {
                    // 曜日が開始の曜日よりも同じまたは前の場合

                    // ひとつ前の情報を終値に設定する
                    final StockPriceTimeSeriesDto preStockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList.get(i - 1);
                    addStockPriceWeeklyDto.setWeeklyEndDate(preStockPriceTimeSeriesDto.getDate());
                    addStockPriceWeeklyDto.setCp(preStockPriceTimeSeriesDto.getCp());
                    addStockPriceWeeklyDto.setLp(lp);
                    addStockPriceWeeklyDto.setHp(hp);
                    addStockPriceWeeklyDto.setLp(lp);
                    addStockPriceWeeklyDto.setVolume(volume);

                    // 株価週足のリストに追加
                    stockPriceWeeklyDtoList.add(addStockPriceWeeklyDto);

                    // 現在の情報を追加する株価週足ＤＴＯに設定する
                    addStockPriceWeeklyDto = new StockPriceWeeklyDto();
                    addStockPriceWeeklyDto.setStockBrandId(stockBrandId);
                    addStockPriceWeeklyDto.setNo(Integer.valueOf(i).longValue());
                    addStockPriceWeeklyDto.setWeeklyStartDate(stockPriceTimeSeriesDto.getDate());
                    addStockPriceWeeklyDto.setOp(stockPriceTimeSeriesDto.getOp());
                    lp = stockPriceTimeSeriesDto.getLp();
                    hp = stockPriceTimeSeriesDto.getHp();
                    volume = stockPriceTimeSeriesDto.getVolume();

                    continue;

                } else if (stockPriceTimeSeriesDto.getDate()
                    .compareTo(addStockPriceWeeklyDto.getWeeklyStartDate().plusDays(7)) >= 0) {
                    // 開始の7日以降の場合

                    // TODO KenichiroArai 2021/05/16 曜日の判定と処理が同じなので、まとめる

                    // ひとつ前の情報を終値に設定する
                    final StockPriceTimeSeriesDto preStockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList.get(i - 1);
                    addStockPriceWeeklyDto.setWeeklyEndDate(preStockPriceTimeSeriesDto.getDate());
                    addStockPriceWeeklyDto.setCp(preStockPriceTimeSeriesDto.getCp());
                    addStockPriceWeeklyDto.setLp(lp);
                    addStockPriceWeeklyDto.setHp(hp);
                    addStockPriceWeeklyDto.setLp(lp);
                    addStockPriceWeeklyDto.setVolume(volume);

                    // 株価週足のリストに追加
                    stockPriceWeeklyDtoList.add(addStockPriceWeeklyDto);

                    // 現在の情報を追加する株価週足ＤＴＯに設定する
                    addStockPriceWeeklyDto = new StockPriceWeeklyDto();
                    addStockPriceWeeklyDto.setStockBrandId(stockBrandId);
                    addStockPriceWeeklyDto.setNo(Integer.valueOf(i).longValue());
                    addStockPriceWeeklyDto.setWeeklyStartDate(stockPriceTimeSeriesDto.getDate());
                    addStockPriceWeeklyDto.setOp(stockPriceTimeSeriesDto.getOp());
                    lp = stockPriceTimeSeriesDto.getLp();
                    hp = stockPriceTimeSeriesDto.getHp();
                    volume = stockPriceTimeSeriesDto.getVolume();

                    continue;

                }

                lp = lp.min(stockPriceTimeSeriesDto.getLp());
                hp = hp.max(stockPriceTimeSeriesDto.getHp());
                volume += stockPriceTimeSeriesDto.getVolume();
            }

            // ひとつ前の情報を終値に設定する
            final StockPriceTimeSeriesDto endStockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList
                .get(stockPriceTimeSeriesDtoList.size() - 1);
            addStockPriceWeeklyDto.setWeeklyEndDate(endStockPriceTimeSeriesDto.getDate());
            addStockPriceWeeklyDto.setCp(endStockPriceTimeSeriesDto.getCp());
            addStockPriceWeeklyDto.setLp(lp);
            addStockPriceWeeklyDto.setHp(hp);
            addStockPriceWeeklyDto.setLp(lp);
            addStockPriceWeeklyDto.setVolume(volume);

            // 株価週足のリストに追加
            stockPriceWeeklyDtoList.add(addStockPriceWeeklyDto);

            // TODO KenichiroArai 2021/05/16 デバッグ出力
            System.out.println("株価週足：開始");
            stockPriceWeeklyDtoList.forEach(dto -> System.out.println(
                String.format("週開始日付：%s, 週終了日付：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getWeeklyStartDate(),
                    dto.getWeeklyEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
            System.out.println("株価週足：終了");

            // TODO KenichiroArai 2021/05/16 実装中
            for (final StockPriceWeeklyDto stockPriceWeeklyDto : stockPriceWeeklyDtoList) {
                this.stockPriceWeeklyDao.insert(stockPriceWeeklyDto);
            }
        }

        /* 株価月足を作成する */
        // TODO KenichiroArai 2021/05/16 外部クラス化またはメソッド化する
        if (ListUtils.isNotEmpty(stockPriceTimeSeriesDtoList)) {

            // TODO KenichiroArai 2021/05/16 SQLとの作成とどちらが早いか試す
            final List<StockPriceMonthlyDto> stockPriceMonthlyDtoList = new ArrayList<>();          // 株価月足のリスト
            StockPriceMonthlyDto             addStockPriceMonthlyDto  = new StockPriceMonthlyDto(); // 追加する株価月足ＤＴＯ
            addStockPriceMonthlyDto.setStockBrandId(stockBrandId);
            addStockPriceMonthlyDto.setNo(0L);
            addStockPriceMonthlyDto.setMonthlyStartDate(stockPriceTimeSeriesDtoList.get(0).getDate());
            addStockPriceMonthlyDto.setOp(stockPriceTimeSeriesDtoList.get(0).getOp()); // 始値は最初のデータを設定する
            BigDecimal lp     = stockPriceTimeSeriesDtoList.get(0).getLp();
            BigDecimal hp     = stockPriceTimeSeriesDtoList.get(0).getHp();
            long       volume = stockPriceTimeSeriesDtoList.get(0).getVolume();
            for (int i = 1; i < stockPriceTimeSeriesDtoList.size(); i++) {

                final StockPriceTimeSeriesDto stockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList.get(i);

                // 月が異なるか
                if (stockPriceTimeSeriesDto.getDate().getMonthValue() != addStockPriceMonthlyDto.getMonthlyStartDate()
                    .getMonthValue()) {
                    // 月が開始の月と異なる場合

                    // ひとつ前の情報を終値に設定する
                    final StockPriceTimeSeriesDto preStockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList.get(i - 1);
                    addStockPriceMonthlyDto.setMonthlyEndDate(preStockPriceTimeSeriesDto.getDate());
                    addStockPriceMonthlyDto.setCp(preStockPriceTimeSeriesDto.getCp());
                    addStockPriceMonthlyDto.setLp(lp);
                    addStockPriceMonthlyDto.setHp(hp);
                    addStockPriceMonthlyDto.setLp(lp);
                    addStockPriceMonthlyDto.setVolume(volume);

                    // 株価月足のリストに追加
                    stockPriceMonthlyDtoList.add(addStockPriceMonthlyDto);

                    // 現在の情報を追加する株価月足ＤＴＯに設定する
                    addStockPriceMonthlyDto = new StockPriceMonthlyDto();
                    addStockPriceMonthlyDto.setStockBrandId(stockBrandId);
                    addStockPriceMonthlyDto.setNo(Integer.valueOf(i).longValue());
                    addStockPriceMonthlyDto.setMonthlyStartDate(stockPriceTimeSeriesDto.getDate());
                    addStockPriceMonthlyDto.setOp(stockPriceTimeSeriesDto.getOp());
                    lp = stockPriceTimeSeriesDto.getLp();
                    hp = stockPriceTimeSeriesDto.getHp();
                    volume = stockPriceTimeSeriesDto.getVolume();

                    continue;

                }

                lp = lp.min(stockPriceTimeSeriesDto.getLp());
                hp = hp.max(stockPriceTimeSeriesDto.getHp());
                volume += stockPriceTimeSeriesDto.getVolume();
            }

            // ひとつ前の情報を終値に設定する
            final StockPriceTimeSeriesDto endStockPriceTimeSeriesDto = stockPriceTimeSeriesDtoList
                .get(stockPriceTimeSeriesDtoList.size() - 1);
            addStockPriceMonthlyDto.setMonthlyEndDate(endStockPriceTimeSeriesDto.getDate());
            addStockPriceMonthlyDto.setCp(endStockPriceTimeSeriesDto.getCp());
            addStockPriceMonthlyDto.setLp(lp);
            addStockPriceMonthlyDto.setHp(hp);
            addStockPriceMonthlyDto.setLp(lp);
            addStockPriceMonthlyDto.setVolume(volume);

            // 株価月足のリストに追加
            stockPriceMonthlyDtoList.add(addStockPriceMonthlyDto);

            // TODO KenichiroArai 2021/05/16 デバッグ出力
            System.out.println("株価月足：開始");
            stockPriceMonthlyDtoList.forEach(dto -> System.out.println(
                String.format("月開始日付：%s, 月終了日付：%s, 始値：%f, 安値：%f, 高値：%f, 終値：%f, 出来高：%d", dto.getMonthlyStartDate(),
                    dto.getMonthlyEndDate(), dto.getOp(), dto.getLp(), dto.getHp(), dto.getCp(), dto.getVolume())));
            System.out.println("株価週足：終了");

            // TODO KenichiroArai 2021/05/16 実装中
            for (final StockPriceMonthlyDto stockPriceMonthlyDto : stockPriceMonthlyDtoList) {
                this.stockPriceMonthlyDao.insert(stockPriceMonthlyDto);
            }
        }

    }
}
