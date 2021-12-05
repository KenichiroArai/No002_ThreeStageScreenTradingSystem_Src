package kmg.im.stock.tssts.application.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import kmg.im.stock.tssts.domain.service.TsstsSpDataRegisterService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(TsstsController.class);

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリゾルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 三段階スクリーン・トレーディング・システム株価データ登録サービス */
    private final TsstsSpDataRegisterService tsstsSpDataRegisterService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsLogMessageResolver
     *                                   三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリゾルバ
     * @param tsstsSpDataRegisterService
     *                                   三段階スクリーン・トレーディング・システム株価データ登録サービス
     */
    public TsstsController(final TsstsLogMessageResolver tsstsLogMessageResolver,
        final TsstsSpDataRegisterService tsstsSpDataRegisterService) {
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.tsstsSpDataRegisterService = tsstsSpDataRegisterService;
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
            this.tsstsSpDataRegisterService.registerAllSpData();
        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            final String logMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            TsstsController.LOGGER.error(logMsg, e);
        }

    }

}
