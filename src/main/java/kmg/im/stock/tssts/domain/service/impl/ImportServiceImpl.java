package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.ImportLogic;
import kmg.im.stock.tssts.domain.service.ImportService;

/**
 * インポートサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class ImportServiceImpl implements ImportService {

    /** インポートロジック */
    @Autowired
    private ImportLogic importLogicLogic;

    /**
     * 全株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void registerAllStockPriceData() {
        this.importLogicLogic.registerAllStockPriceData();
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
        this.importLogicLogic.registerStockPriceDataOfDirectory(directoryPath);
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
        this.importLogicLogic.registerStockPriceDataOfFile(filePath);
    }

}
