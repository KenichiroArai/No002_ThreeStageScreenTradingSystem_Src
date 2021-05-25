package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.PathUtils;
import kmg.im.stock.tssts.data.dao.StockBrandDao;
import kmg.im.stock.tssts.domain.logic.StockBrandLogic;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 株銘柄ロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockBrandLogicImpl implements StockBrandLogic {

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** 株銘柄ＤＡＯ */
    private final StockBrandDao stockBrandDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                           ログメッセージリソルバ
     * @param stockBrandDao
     *                           株銘柄ＤＡＯ
     */
    public StockBrandLogicImpl(final LogMessageResolver logMessageResolver, final StockBrandDao stockBrandDao) {
        this.logMessageResolver = logMessageResolver;
        this.stockBrandDao = stockBrandDao;
    }

    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * システム日付に該当する。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @return 株価銘柄ID
     */
    @Override
    public long getStockBrandId(final long stockBrandCode) {

        /* 株価銘柄IDを取得する */
        // TODO KenichiroArai 2021/05/24 システム日付から取得する
        final long result = this.getStockBrandId(stockBrandCode, LocalDate.now());

        return result;
    }

    /**
     * 株価銘柄IDを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandCode
     *                       株価銘柄コード
     * @param baseDate
     *                       基準日
     * @return 株価銘柄ID
     */
    @Override
    public long getStockBrandId(final long stockBrandCode, final LocalDate baseDate) {

        /* 株価銘柄IDを取得する */
        final long result = this.stockBrandDao.getId(stockBrandCode, baseDate);

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
