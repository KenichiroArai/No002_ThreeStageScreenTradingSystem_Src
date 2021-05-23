package kmg.im.stock.tssts.domain.model.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kmg.im.stock.tssts.data.dao.StockPriceDataDao;
import kmg.im.stock.tssts.data.dto.StockPriceDataDto;
import kmg.im.stock.tssts.domain.model.StockPriceDataModel;
import kmg.im.stock.tssts.domain.model.StockPriceMgtModel;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.spring.infrastructure.constants.BeanDefaultScopeConstants;

/**
 * 株価データ管理モデル<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
@Scope(BeanDefaultScopeConstants.PROTOTYPE)
public class StockPriceMgtModelImpl implements StockPriceMgtModel {

    /** 株価データＤＡＯ */
    private final StockPriceDataDao stockPriceDataDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stockPriceDataDao
     *                          株価データＤＡＯ
     */
    public StockPriceMgtModelImpl(final StockPriceDataDao stockPriceDataDao) {
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
        final List<StockPriceDataDto> stockPriceDataDtoList = this.stockPriceDataDao
            .findAllStockPriceDataList(filePath);

        for (final StockPriceDataDto dto : stockPriceDataDtoList) {
            final StockPriceDataModel model = new StockPriceDataModel();
            BeanUtils.copyProperties(dto, model);
            result.add(model);
        }

        return result;
    }
}
