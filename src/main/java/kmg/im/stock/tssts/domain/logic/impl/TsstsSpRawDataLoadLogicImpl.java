package kmg.im.stock.tssts.domain.logic.impl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import kmg.core.infrastructure.utils.PathUtils;
import kmg.im.stock.core.data.dto.SpRawDataAcqDto;
import kmg.im.stock.core.data.dto.SpRawDataAcqMgtDto;
import kmg.im.stock.core.domain.model.SpRawDataAcqMgtModel;
import kmg.im.stock.core.domain.model.SpRawDataAcqModel;
import kmg.im.stock.core.domain.model.impl.SpRawDataAcqMgtModelImpl;
import kmg.im.stock.core.domain.model.impl.SpRawDataAcqModelImpl;
import kmg.im.stock.tssts.data.dao.SpRawDataDao;
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

    /** 株価生データＤＡＯ */
    private final SpRawDataDao spRawDataDao;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param tsstsLogMessageResolver
     *                                三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param spRawDataDao
     *                                株価生データＤＡＯ
     */
    public TsstsSpRawDataLoadLogicImpl(final TsstsLogMessageResolver tsstsLogMessageResolver,
        final SpRawDataDao spRawDataDao) {
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.spRawDataDao = spRawDataDao;
    }

    /**
     * 株価生データモデル取得管理モデルのマップを返す<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @return 株価生データモデル取得管理モデルのマップ<株価銘柄コード, 株価生データモデル取得管理モデル>
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public Map<Long, SpRawDataAcqMgtModel> getSpDataMgtMap() throws TsstsDomainException {

        final Map<Long, SpRawDataAcqMgtModel> result = new HashMap<>();

        /* 銘柄ごとの株価データのファイルパスを取得する */
        final List<Path> stockPriceStockStoragePathList = this.spRawDataDao.findOfSpsStoragePath();

        /* 株価生データモデル取得管理モデルのマップに追加する */
        for (final Path path : stockPriceStockStoragePathList) {
            final SpRawDataAcqMgtModel model = this.getSpDataMgtModel(path);
            result.put(model.getStockBrandCode(), model);
        }

        return result;

    }

    /**
     * 株価生データモデル取得管理モデルを返す<br>
     * <p>
     * ファイルパスに該当する株価生データモデル取得管理モデルを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価生データモデル取得管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public SpRawDataAcqMgtModel getSpDataMgtModel(final Path filePath) throws TsstsDomainException {

        final SpRawDataAcqMgtModel result = new SpRawDataAcqMgtModelImpl();

        /* ファイルパスから株価銘柄コードを取得する */
        final long stockBrandCode = this.getStockBrandCode(filePath);
        result.setStockBrandCode(stockBrandCode);

        /* ファイルパスから株価生データ取得モデルのリストを取得する */
        final List<SpRawDataAcqModel> stockPriceDataModelList = this.getSpDataList(filePath);
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
     * 株価生データ取得モデルのリストを返す<br>
     * <p>
     * ファイルパスに該当する株価生データ取得モデルのリストを返す。
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @return 株価生データ取得モデルのリスト
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public List<SpRawDataAcqModel> getSpDataList(final Path filePath) throws TsstsDomainException {

        final List<SpRawDataAcqModel> result = new ArrayList<>();

        /* 株価生データ取得のリストを検索する */
        final SpRawDataAcqMgtDto spRawDataAcqMgtDto = this.spRawDataDao.findAll(filePath);

        /* 株価生データ取得リストに追加する */
        for (final SpRawDataAcqDto dto : spRawDataAcqMgtDto.getDataList()) {
            final SpRawDataAcqModel model = new SpRawDataAcqModelImpl();
            BeanUtils.copyProperties(dto, model);
            result.add(model);
        }

        return result;
    }

}
