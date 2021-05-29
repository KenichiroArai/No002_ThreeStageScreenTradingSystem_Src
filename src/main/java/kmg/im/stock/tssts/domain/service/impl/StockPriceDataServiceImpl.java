package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockPriceDataLogic;
import kmg.im.stock.tssts.domain.model.StockPriceDataMgtModel;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.impl.StockPriceDataMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockPriceDataService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株価データ登録サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceDataServiceImpl implements StockPriceDataService {

    /** 株価データロジック */
    private final StockPriceDataLogic stockPriceDataLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataLogic
     *                            株価データロジック
     */
    public StockPriceDataServiceImpl(final StockPriceDataLogic stockPriceDataLogic) {
        this.stockPriceDataLogic = stockPriceDataLogic;
    }

    /**
     * 株価データ管理マップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価データ管理マップ<株価銘柄コード, 株価データ管理>
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public Map<Long, StockPriceDataMgtModel> getStockPriceDataMgtMap() throws TsstsDomainException {

        final Map<Long, StockPriceDataMgtModel> result = new HashMap<>();

        final List<Path> stockPriceStockStoragePathList = this.stockPriceDataLogic.getStockPriceStockStoragePathList();
        for (final Path path : stockPriceStockStoragePathList) {
            final StockPriceDataMgtModel model = this.getStockPriceDataMgtModel(path);
            result.put(model.getStockBrandCode(), model);
        }

        return result;

    }

    /**
     * 株価データ管理モデルを返す<br>
     * <p>
     * ファイルパスに該当する株価データ管理モデルを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価データ管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public StockPriceDataMgtModel getStockPriceDataMgtModel(final Path filePath) throws TsstsDomainException {

        final StockPriceDataMgtModel result = new StockPriceDataMgtModelImpl();

        final long stockBrandCode = this.stockPriceDataLogic.getStockBrandCode(filePath);
        result.setStockBrandCode(stockBrandCode);

        final List<StockPriceDataModel> stockPriceDataModelList = this.stockPriceDataLogic
            .getStockPriceDataList(filePath);
        result.addAllData(stockPriceDataModelList);

        return result;
    }
}
