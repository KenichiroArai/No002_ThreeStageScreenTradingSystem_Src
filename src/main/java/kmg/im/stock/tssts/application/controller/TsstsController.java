package kmg.im.stock.tssts.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kmg.im.stock.tssts.domain.service.TsstsService;

/**
 * 三段階スクリーン・トレーディング・システムコントローラ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Controller
public class TsstsController {

    /** 三段階スクリーン・トレーディング・システムサービスインタフェース */
    @Autowired
    private TsstsService tsstsService;

    /**
     * 株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public void registerStockPriceData() {

        this.tsstsService.registerStockPriceData();

    }

}
