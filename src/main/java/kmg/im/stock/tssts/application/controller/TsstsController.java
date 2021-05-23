package kmg.im.stock.tssts.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import kmg.im.stock.tssts.domain.service.StockService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システムコントローラ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Controller
public class TsstsController {

    /** ロガー */
    private static Logger LOGGER = LoggerFactory.getLogger(TsstsController.class);

    /** ログメッセージリゾルバ */
    private final LogMessageResolver logMessageResolver;

    /** 株サービス */
    private final StockService stockService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param logMessageResolver
     *                           ログメッセージリゾルバ
     * @param stockService
     *                           株サービス
     */
    public TsstsController(final LogMessageResolver logMessageResolver, final StockService stockService) {
        this.logMessageResolver = logMessageResolver;
        this.stockService = stockService;
    }

    /**
     * 全株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    public void registerAllStockPriceData() throws TsstsDomainException {

        try {
            this.stockService.registerAllStockPriceData();
        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            TsstsController.LOGGER.error(logMsg, e);
        }

    }

}
