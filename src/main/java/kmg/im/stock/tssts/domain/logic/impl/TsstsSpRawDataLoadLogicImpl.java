package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.PathUtils;
import kmg.im.stock.core.data.dao.ImStkSpRawDataDao;
import kmg.im.stock.core.data.dto.ImStkSpRawDataAcqDto;
import kmg.im.stock.core.data.dto.ImStkSpRawDataAcqMgtDto;
import kmg.im.stock.core.domain.model.ImStkSpRawDataAcqMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpRawDataAcqModel;
import kmg.im.stock.core.domain.model.impl.ImStkSpRawDataAcqMgtModelImpl;
import kmg.im.stock.core.domain.model.impl.ImStkSpRawDataAcqModelImpl;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.tssts.domain.logic.TsstsSpRawDataLoadLogic;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価生データ読み込みロジック<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSpRawDataLoadLogicImpl implements TsstsSpRawDataLoadLogic {

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 投資株式株価生データＤＡＯ */
    private final ImStkSpRawDataDao imStkSpRawDataDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsLogMessageResolver
     *                                三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param imStkSpRawDataDao
     *                                投資株式株価生データＤＡＯ
     */
    public TsstsSpRawDataLoadLogicImpl(final TsstsLogMessageResolver tsstsLogMessageResolver,
        final ImStkSpRawDataDao imStkSpRawDataDao) {
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.imStkSpRawDataDao = imStkSpRawDataDao;
    }

    /**
     * 投資株式株価生データ取得管理モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 投資株式株価生データ取得管理モデルのマップ<株価銘柄コード, 投資株式株価生データ取得管理モデル>
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public Map<Long, ImStkSpRawDataAcqMgtModel> getImStkSpDataMgtMap() throws TsstsDomainException {

        final Map<Long, ImStkSpRawDataAcqMgtModel> result = new HashMap<>();

        /* 銘柄ごとの株価データのファイルパスを取得する */
        List<Path> stockPriceStockStoragePathList;
        try {
            stockPriceStockStoragePathList = this.imStkSpRawDataDao.findOfSpsStoragePath();
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/08 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 投資株式株価生データ取得管理モデルのマップに追加する */
        for (final Path path : stockPriceStockStoragePathList) {
            final ImStkSpRawDataAcqMgtModel model = this.getImStkSpRawDataAcqMgtModel(path);
            result.put(model.getStockBrandCode(), model);
        }

        return result;

    }

    /**
     * 投資株式株価生データ取得管理モデルを返す<br>
     * <p>
     * ファイルパスに該当する 投資株式株価生データ取得管理モデルを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 投資株式株価生データ取得管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public ImStkSpRawDataAcqMgtModel getImStkSpRawDataAcqMgtModel(final Path filePath) throws TsstsDomainException {

        final ImStkSpRawDataAcqMgtModel result = new ImStkSpRawDataAcqMgtModelImpl();

        /* ファイルパスから株価銘柄コードを取得する */
        final long stockBrandCode = this.getStockBrandCode(filePath);
        result.setStockBrandCode(stockBrandCode);

        /* ファイルパスから投資株式株価生データ取得モデルのリストを取得する */
        final List<ImStkSpRawDataAcqModel> stockPriceDataModelList = this.getImStkSpDataList(filePath);
        result.addAllData(stockPriceDataModelList);

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
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        return result;
    }

    /**
     * 投資株式株価生データ取得モデルのリストを返す<br>
     * <p>
     * ファイルパスに該当する投資株式株価生データ取得モデルのリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 投資株式株価生データ取得モデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<ImStkSpRawDataAcqModel> getImStkSpDataList(final Path filePath) throws TsstsDomainException {

        final List<ImStkSpRawDataAcqModel> result = new ArrayList<>();

        /* 株価生データ取得のリストを検索する */
        ImStkSpRawDataAcqMgtDto imStkSpRawDataAcqMgtDto = null;
        try {
            imStkSpRawDataAcqMgtDto = this.imStkSpRawDataDao.findAll(filePath);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/08 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }

        /* 株価生データ取得リストに追加する */
        for (final ImStkSpRawDataAcqDto dto : imStkSpRawDataAcqMgtDto.getDataList()) {
            final ImStkSpRawDataAcqModel model = new ImStkSpRawDataAcqModelImpl();
            BeanUtils.copyProperties(dto, model);
            result.add(model);
        }

        return result;
    }

}
