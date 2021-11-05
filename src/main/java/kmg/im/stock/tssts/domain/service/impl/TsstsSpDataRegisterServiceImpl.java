package kmg.im.stock.tssts.domain.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import kmg.im.stock.tssts.domain.logic.TsstsSpRawDataLoadLogic;
import kmg.im.stock.tssts.domain.model.SimpleSptsMgtModel;
import kmg.im.stock.tssts.domain.model.SpDataRegMgtModel;
import kmg.im.stock.tssts.domain.model.SpDataRegModel;
import kmg.im.stock.tssts.domain.model.SpRawDataAcqMgtModel;
import kmg.im.stock.tssts.domain.model.SpRawDataAcqModel;
import kmg.im.stock.tssts.domain.model.TsstsSpcvInitMgtModel;
import kmg.im.stock.tssts.domain.model.impl.SpDataRegMgtModelImpl;
import kmg.im.stock.tssts.domain.model.impl.SpDataRegModelImpl;
import kmg.im.stock.tssts.domain.model.impl.TsstsSpcvInitMgtModelImpl;
import kmg.im.stock.tssts.domain.service.StockPriceTimeSeriesService;
import kmg.im.stock.tssts.domain.service.TsstsSpDataRegisterService;
import kmg.im.stock.tssts.domain.service.TsstsSptsDailyRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsMonthlyRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsWeeklyRegService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;
import kmg.im.stock.tssts.infrastructure.types.PeriodTypeTypes;

/**
 * 三段階スクリーン・トレーディング・システム株価データ登録サービスインタフェース<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Service
public class TsstsSpDataRegisterServiceImpl implements TsstsSpDataRegisterService {

    /** アプリケーションコンテキスト */
    private final ApplicationContext context;

    /** ログメッセージリソルバ */
    private final LogMessageResolver logMessageResolver;

    /** 三段階スクリーン・トレーディング・システム株価生データ読み込みロジック */
    private final TsstsSpRawDataLoadLogic tsstsSpRawDataLoadLogic;

    /** 株価時系列サービス */
    private final StockPriceTimeSeriesService stockPriceTimeSeriesService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                                    アプリケーションコンテキスト
     * @param logMessageResolver
     *                                    ログメッセージリソルバ
     * @param tsstsSpRawDataLoadLogic
     *                                    三段階スクリーン・トレーディング・システム株価生データ読み込みロジック
     * @param stockPriceTimeSeriesService
     *                                    株価時系列サービス
     */
    public TsstsSpDataRegisterServiceImpl(final ApplicationContext context, final LogMessageResolver logMessageResolver,
        final TsstsSpRawDataLoadLogic tsstsSpRawDataLoadLogic,
        final StockPriceTimeSeriesService stockPriceTimeSeriesService) {
        this.context = context;
        this.logMessageResolver = logMessageResolver;
        this.tsstsSpRawDataLoadLogic = tsstsSpRawDataLoadLogic;
        this.stockPriceTimeSeriesService = stockPriceTimeSeriesService;
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
    @Override
    public void registerAllSpData() throws TsstsDomainException {

        /* 株価生データ取得管理モデルを全て取得する */
        final Map<Long, SpRawDataAcqMgtModel> acqMgtMap = this.tsstsSpRawDataLoadLogic.getSpDataMgtMap();

        /* 株価データ登録管理モデルに詰め替える */
        final SpDataRegMgtModel regMgt = new SpDataRegMgtModelImpl();
        for (final long key : acqMgtMap.keySet()) {
            final SpRawDataAcqMgtModel acqMgt = acqMgtMap.get(key);

            regMgt.setStockBrandCode(acqMgt.getStockBrandCode());
            for (final SpRawDataAcqModel acq : acqMgt.getDataList()) {
                final SpDataRegModel reg = new SpDataRegModelImpl();
                BeanUtils.copyProperties(acq, reg);
                regMgt.addData(reg);
            }
        }

        /* 株価データ管理モデルを登録する */
        this.registerSpDataMgtModel(regMgt);
    }

    /**
     * ディレクトリにある株価データを登録する<br>
     * <p>
     * ディレクトリパスにある株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param directoryPath
     *                      ディレクトリパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void registerSpDataOfDirectory(final Path directoryPath) throws TsstsDomainException {

        try {
            final Stream<Path> streamPath = Files.walk(directoryPath).filter(path -> !Files.isDirectory(path));
            final Iterator<Path> itPath = streamPath.iterator();
            while (itPath.hasNext()) {
                final Path path = itPath.next();
                this.registerSpDataOfFile(path);
            }
        } catch (final IOException e) {
            // TODO KenichiroArai 2021/05/29 例外処理
            final String errMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, LogMessageTypes.NONE, e);
        }
    }

    /**
     * ファイルの株価データを登録する<br>
     * <p>
     * ファイルパスに該当する株価データを登録する
     * </p>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param filePath
     *                 ファイルパス
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    @Override
    public void registerSpDataOfFile(final Path filePath) throws TsstsDomainException {

        /* 株価生データ取得管理モデルを取得する */
        final SpRawDataAcqMgtModel acqMgt = this.tsstsSpRawDataLoadLogic.getSpDataMgtModel(filePath);

        /* 株価データ登録管理モデルに詰め替える */
        final SpDataRegMgtModel regMgt = new SpDataRegMgtModelImpl();
        regMgt.setStockBrandCode(acqMgt.getStockBrandCode());
        for (final SpRawDataAcqModel acq : acqMgt.getDataList()) {
            final SpDataRegModel reg = new SpDataRegModelImpl();
            BeanUtils.copyProperties(acq, reg);
            regMgt.addData(reg);
        }

        /* 株価データ登録管理モデルを登録する */
        this.registerSpDataMgtModel(regMgt);

    }

    /**
     * 株価データ登録管理モデルを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param spDataRegMgtModel
     *                          株価データ登録管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    private void registerSpDataMgtModel(final SpDataRegMgtModel spDataRegMgtModel) throws TsstsDomainException {

        /* 株価時系列日足 */
        final TsstsSptsDailyRegService tsstsSptsDailyRegService = this.context
            .getBean(TsstsSptsDailyRegServiceImpl.class);
        // 初期化
        tsstsSptsDailyRegService.initialize(spDataRegMgtModel);
        // 削除する
        tsstsSptsDailyRegService.delete();
        // 登録する
        tsstsSptsDailyRegService.register();
        // 登録データを取得する
        final SimpleSptsMgtModel simpleSptsMgtDailyModel = this.stockPriceTimeSeriesService
            .findSimpleBySbcAndPti(spDataRegMgtModel.getStockBrandCode(), PeriodTypeTypes.DAILY);
        // 株価計算値を登録する
        final TsstsSpcvInitMgtModel tsstsSpcvInitMgtDailyModel = new TsstsSpcvInitMgtModelImpl();
        tsstsSpcvInitMgtDailyModel.from(simpleSptsMgtDailyModel);
        final TsstsStockPriceCalcValueService calcVlueDailyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueDailyService.initialize(tsstsSpcvInitMgtDailyModel);
        calcVlueDailyService.register();

        /* 株価時系列週足 */
        final TsstsSptsWeeklyRegService tsstsSptsWeeklyRegService = this.context
            .getBean(TsstsSptsWeeklyRegServiceImpl.class);
        // 初期化
        tsstsSptsWeeklyRegService.initialize(spDataRegMgtModel);
        // 削除する
        tsstsSptsWeeklyRegService.delete();
        // 登録する
        tsstsSptsWeeklyRegService.register();
        // 登録データを取得する
        final SimpleSptsMgtModel simpleSptsMgtWeeklyModel = this.stockPriceTimeSeriesService
            .findSimpleBySbcAndPti(spDataRegMgtModel.getStockBrandCode(), PeriodTypeTypes.DAILY);
        // 株価計算値を登録する
        final TsstsSpcvInitMgtModel tsstsSpcvInitMgtWeeklyModel = new TsstsSpcvInitMgtModelImpl();
        tsstsSpcvInitMgtWeeklyModel.from(simpleSptsMgtWeeklyModel);
        final TsstsStockPriceCalcValueService calcVlueWeeklyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueWeeklyService.initialize(tsstsSpcvInitMgtWeeklyModel);
        calcVlueWeeklyService.register();

        /* 株価時系列月足 */
        final TsstsSptsMonthlyRegService tsstsSptsMonthlyRegService = this.context
            .getBean(TsstsSptsMonthlyRegServiceImpl.class);
        // 初期化
        tsstsSptsMonthlyRegService.initialize(spDataRegMgtModel);
        // 削除する
        tsstsSptsMonthlyRegService.delete();
        // 登録する
        tsstsSptsMonthlyRegService.register();
        // 登録データを取得する
        final SimpleSptsMgtModel simpleSptsMgtMonthlyModel = this.stockPriceTimeSeriesService
            .findSimpleBySbcAndPti(spDataRegMgtModel.getStockBrandCode(), PeriodTypeTypes.DAILY);
        // 株価計算値を登録する
        final TsstsSpcvInitMgtModel tsstsSpcvInitMgtMonthlyModel = new TsstsSpcvInitMgtModelImpl();
        tsstsSpcvInitMgtMonthlyModel.from(simpleSptsMgtMonthlyModel);
        final TsstsStockPriceCalcValueService calcVlueMonthlyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueMonthlyService.initialize(tsstsSpcvInitMgtMonthlyModel);
        calcVlueMonthlyService.register();

    }

}
