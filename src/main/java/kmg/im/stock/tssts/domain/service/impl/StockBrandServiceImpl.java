package kmg.im.stock.tssts.domain.service.impl;

import java.nio.file.Path;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.StockBrandLogic;
import kmg.im.stock.tssts.domain.service.StockBrandService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;

/**
 * 株銘柄サービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class StockBrandServiceImpl implements StockBrandService {

    /** 株銘柄ロジック */
    private final StockBrandLogic stockBrandLogic;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockBrandLogic
     *                        株銘柄ロジック
     */
    public StockBrandServiceImpl(final StockBrandLogic stockBrandLogic) {
        this.stockBrandLogic = stockBrandLogic;
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
        final long result = this.stockBrandLogic.getStockBrandId(stockBrandCode);
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
        final long result = this.stockBrandLogic.getStockBrandId(stockBrandCode, baseDate);
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

        final long result = this.stockBrandLogic.getStockBrandCode(filePath);
        return result;
    }

}
