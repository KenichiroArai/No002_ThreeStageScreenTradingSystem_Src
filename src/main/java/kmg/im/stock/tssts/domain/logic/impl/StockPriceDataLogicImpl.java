package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.PathUtils;
import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.data.dto.StockPriceDataMgtDto;
import kmg.im.stock.tssts.domain.logic.StockPriceDataLogic;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 株価データサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockPriceDataLogicImpl implements StockPriceDataLogic {

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** 株価データＤＡＯ */
    private final StockPriceDataDao stockPriceDataDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                           ログメッセージリソルバ
     * @param stockPriceDataDao
     *                           株価データＤＡＯ
     */
    public StockPriceDataLogicImpl(final LogMessageResolver logMessageResolver,
        final StockPriceDataDao stockPriceDataDao) {
        this.logMessageResolver = logMessageResolver;
        this.stockPriceDataDao = stockPriceDataDao;
    }

    /**
     * 株価銘柄格納パスリストを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価銘柄格納パスリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<Path> getStockPriceStockStoragePathList() throws TsstsDomainException {

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> result = this.stockPriceDataDao.findOfStockPriceStockStoragePath();

        return result;
    }

    /**
     * 株価データリストを返す<br>
     * <p>
     * ファイルパスに該当する株価データリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価データリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<StockPriceDataModel> getStockPriceDataList(final Path filePath) throws TsstsDomainException {

        final List<StockPriceDataModel> result = new ArrayList<>();

        /* 株価データのリストを検索する */
        final StockPriceDataMgtDto stockPriceDataMgtDto = this.stockPriceDataDao.findAll(filePath);

        for (final StockPriceDataDto dto : stockPriceDataMgtDto.getDataList()) {
            final StockPriceDataModel model = new StockPriceDataModelImpl();
            BeanUtils.copyProperties(dto, model);
            result.add(model);
        }

        return result;
    }

    /**
     * 株価銘柄コードを返す<br>
     * <p>
     * ファイルパスに該当する株価銘柄コードを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価銘柄ID
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public long getStockBrandCode(final Path filePath) throws TsstsDomainException {

        long result = 0L;

        try {
            final String fileName = PathUtils.getFileNameOnly(filePath);
            result = Integer.parseInt(fileName);
        } catch (final NumberFormatException e) {

            // TODO KenichiroArai 2021/05/21 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
        }

        return result;
    }

}
