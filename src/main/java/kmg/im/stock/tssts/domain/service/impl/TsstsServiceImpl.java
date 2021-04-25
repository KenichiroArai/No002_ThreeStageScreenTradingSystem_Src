package kmg.im.stock.tssts.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.TsstsLogic;
import kmg.im.stock.tssts.domain.service.TsstsService;

/**
 * 三段階スクリーン・トレーディング・システムサービス<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsServiceImpl implements TsstsService {

    /** 三段階スクリーン・トレーディング・システムロジック */
    @Autowired
    private TsstsLogic tsstsLogic;

    /**
     * 株価データを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void registerStockPriceData() {
        this.tsstsLogic.registerStockPriceData();
    }

}
