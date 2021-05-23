package kmg.im.stock.tssts.domain.model.impl;

import java.nio.file.Path;
import java.time.LocalDate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.data.dao.StockBrandDao;
import kmg.im.stock.tssts.domain.model.StockBrandModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;
import kmg.spring.infrastructure.constants.BeanDefaultScopeConstants;

/**
 * 株銘柄モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
@Scope(BeanDefaultScopeConstants.PROTOTYPE)
public class StockBrandModelImpl implements StockBrandModel {

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
    public StockBrandModelImpl(final LogMessageResolver logMessageResolver, final StockBrandDao stockBrandDao) {
        this.logMessageResolver = logMessageResolver;
        this.stockBrandDao = stockBrandDao;
    }

    // TODO KenichiroArai 2021/05/23 別に移す
    /**
     * 株価銘柄IDを返す<br>
     * <p>
     * ファイルパスに該当する株価銘柄IDを返す。
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
    public long getStockBrandId(final Path filePath) throws TsstsDomainException {

        long result = 0L;

        /* コードを取得する */
        long code = 0L;
        try {
            // TODO KenichiroArai 2021/05/07 ユーティリティ化する
            final String fullFileName = filePath.getFileName().toString();
            final String fileName = fullFileName.substring(0, fullFileName.lastIndexOf('.'));
            code = Integer.parseInt(fileName);
        } catch (final NumberFormatException e) {

            // TODO KenichiroArai 2021/05/21 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
        }

        /* 株価銘柄IDを取得する */
        result = this.stockBrandDao.getId(code, LocalDate.now());

        return result;
    }

}
