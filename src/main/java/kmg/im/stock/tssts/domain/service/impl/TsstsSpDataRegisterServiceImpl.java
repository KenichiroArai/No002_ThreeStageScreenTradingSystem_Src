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

import kmg.im.stock.core.domain.model.ImStkSimpleSptsMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpDataRegMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpDataRegModel;
import kmg.im.stock.core.domain.model.ImStkSpRawDataAcqMgtModel;
import kmg.im.stock.core.domain.model.ImStkSpRawDataAcqModel;
import kmg.im.stock.core.domain.model.ImStkSpcvInitMgtModel;
import kmg.im.stock.core.domain.model.impl.ImStkSpDataRegMgtModelImpl;
import kmg.im.stock.core.domain.model.impl.ImStkSpDataRegModelImpl;
import kmg.im.stock.core.domain.model.impl.ImStkSpcvInitMgtModelImpl;
import kmg.im.stock.core.domain.service.ImStkStockBrandService;
import kmg.im.stock.core.domain.service.ImStkStockPriceTimeSeriesService;
import kmg.im.stock.core.infrastructure.exception.ImStkDomainException;
import kmg.im.stock.core.infrastructure.types.ImStkPeriodTypeTypes;
import kmg.im.stock.tssts.domain.logic.TsstsSpRawDataLoadLogic;
import kmg.im.stock.tssts.domain.service.TsstsSpDataRegisterService;
import kmg.im.stock.tssts.domain.service.TsstsSptsDailyRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsMonthlyRegService;
import kmg.im.stock.tssts.domain.service.TsstsSptsWeeklyRegService;
import kmg.im.stock.tssts.domain.service.TsstsStockPriceCalcValueService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.TsstsLogMessageResolver;
import kmg.im.stock.tssts.infrastructure.types.TsstsLogMessageTypes;

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

    /** 三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ */
    private final TsstsLogMessageResolver tsstsLogMessageResolver;

    /** 三段階スクリーン・トレーディング・システム株価生データ読み込みロジック */
    private final TsstsSpRawDataLoadLogic tsstsSpRawDataLoadLogic;

    /** 投資株式株銘柄サービス */
    private final ImStkStockBrandService imStkStockBrandService;

    /** 投資株式株価時系列サービス */
    private final ImStkStockPriceTimeSeriesService imStkStockPriceTimeSeriesService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param context
     *                                         アプリケーションコンテキスト
     * @param tsstsLogMessageResolver
     *                                         三段階スクリーン・トレーディング・システムログメッセージリゾルバログメッセージリソルバ
     * @param tsstsSpRawDataLoadLogic
     *                                         三段階スクリーン・トレーディング・システム株価生データ読み込みロジック
     * @param imStkStockBrandService
     *                                         投資株式株銘柄サービス
     * @param imStkStockPriceTimeSeriesService
     *                                         投資株式株価時系列サービス
     */
    public TsstsSpDataRegisterServiceImpl(final ApplicationContext context,
        final TsstsLogMessageResolver tsstsLogMessageResolver, final TsstsSpRawDataLoadLogic tsstsSpRawDataLoadLogic,
        final ImStkStockBrandService imStkStockBrandService,
        final ImStkStockPriceTimeSeriesService imStkStockPriceTimeSeriesService) {
        this.context = context;
        this.tsstsLogMessageResolver = tsstsLogMessageResolver;
        this.tsstsSpRawDataLoadLogic = tsstsSpRawDataLoadLogic;
        this.imStkStockBrandService = imStkStockBrandService;
        this.imStkStockPriceTimeSeriesService = imStkStockPriceTimeSeriesService;
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

        /* 投資株式株価生データ取得管理モデルを全て取得する */
        final Map<Long, ImStkSpRawDataAcqMgtModel> acqMgtMap = this.tsstsSpRawDataLoadLogic.getImStkSpDataMgtMap();

        /* 投資株式株価データ登録管理モデルに詰め替える */
        final ImStkSpDataRegMgtModel regMgt = new ImStkSpDataRegMgtModelImpl();
        for (final long key : acqMgtMap.keySet()) {
            final ImStkSpRawDataAcqMgtModel acqMgt = acqMgtMap.get(key);

            regMgt.setStockBrandCode(acqMgt.getStockBrandCode());
            for (final ImStkSpRawDataAcqModel acq : acqMgt.getDataList()) {
                final ImStkSpDataRegModel reg = new ImStkSpDataRegModelImpl();
                BeanUtils.copyProperties(acq, reg);
                regMgt.addData(reg);
            }
        }

        /* 投資株式株価データ登録管理モデルを登録する */
        this.registerImStkSpDataMgtModel(regMgt);
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
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
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

        /* 投資株式株価生データ取得管理モデルを取得する */
        final ImStkSpRawDataAcqMgtModel acqMgt = this.tsstsSpRawDataLoadLogic.getImStkSpRawDataAcqMgtModel(filePath);

        /* 投資株式株価データ登録管理モデルに詰め替える */
        final ImStkSpDataRegMgtModel regMgt = new ImStkSpDataRegMgtModelImpl();
        regMgt.setStockBrandCode(acqMgt.getStockBrandCode());
        for (final ImStkSpRawDataAcqModel acq : acqMgt.getDataList()) {
            final ImStkSpDataRegModel reg = new ImStkSpDataRegModelImpl();
            BeanUtils.copyProperties(acq, reg);
            regMgt.addData(reg);
        }

        /* 投資株式株価データ登録管理モデルを登録する */
        this.registerImStkSpDataMgtModel(regMgt);

    }

    /**
     * 投資株式株価データ登録管理モデルを登録する<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param imStkSpDataRegMgtModel
     *                               投資株式株価データ登録管理モデル
     * @throws TsstsDomainException
     *                              三段階スクリーン・トレーディング・システムドメイン例外
     */
    private void registerImStkSpDataMgtModel(final ImStkSpDataRegMgtModel imStkSpDataRegMgtModel)
        throws TsstsDomainException {

        /* 株価時系列日足 */
        /* 株銘柄ＩＤの取得 */
        final long dailyStockBrandId = this.imStkStockBrandService
            .getStockBrandId(imStkSpDataRegMgtModel.getStockBrandCode());
        // 株価時系列日足登録サービスの生成
        final TsstsSptsDailyRegService tsstsSptsDailyRegService = this.context
            .getBean(TsstsSptsDailyRegServiceImpl.class);
        // 株価時系列日足登録サービスの初期化
        tsstsSptsDailyRegService.initialize(dailyStockBrandId, imStkSpDataRegMgtModel);
        // 株価時系列日足登録サービスの削除する
        tsstsSptsDailyRegService.delete();
        // 株価時系列日足登録サービスの登録する
        tsstsSptsDailyRegService.register();
        // 登録データを取得する
        ImStkSimpleSptsMgtModel imStkSimpleSptsMgtDailyModel = null;
        try {
            imStkSimpleSptsMgtDailyModel = this.imStkStockPriceTimeSeriesService
                .findSimpleBySbIdAndPti(dailyStockBrandId, ImStkPeriodTypeTypes.DAILY);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 株価計算値を登録する
        final ImStkSpcvInitMgtModel imStkSpcvInitMgtDailyModel = new ImStkSpcvInitMgtModelImpl();
        imStkSpcvInitMgtDailyModel.from(imStkSimpleSptsMgtDailyModel);
        final TsstsStockPriceCalcValueService calcVlueDailyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueDailyService.initialize(imStkSpcvInitMgtDailyModel);
        calcVlueDailyService.register();

        /* 株価時系列週足 */
        /* 株銘柄ＩＤの取得 */
        final long weeklyStockBrandId = this.imStkStockBrandService
            .getStockBrandId(imStkSpDataRegMgtModel.getStockBrandCode());
        // 株価時系列週足登録サービスの生成
        final TsstsSptsWeeklyRegService tsstsSptsWeeklyRegService = this.context
            .getBean(TsstsSptsWeeklyRegServiceImpl.class);
        // 株価時系列週足登録サービの初期化
        tsstsSptsWeeklyRegService.initialize(weeklyStockBrandId, imStkSpDataRegMgtModel);
        // 株価時系列週足登録サービの削除する
        tsstsSptsWeeklyRegService.delete();
        // 株価時系列週足登録サービの登録する
        tsstsSptsWeeklyRegService.register();
        // 登録データを取得する
        ImStkSimpleSptsMgtModel imStkSimpleSptsMgtWeeklyModel = null;
        try {
            imStkSimpleSptsMgtWeeklyModel = this.imStkStockPriceTimeSeriesService
                .findSimpleBySbIdAndPti(dailyStockBrandId, ImStkPeriodTypeTypes.WEEKLY);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 株価計算値を登録する
        final ImStkSpcvInitMgtModel tsstsSpcvInitMgtWeeklyModel = new ImStkSpcvInitMgtModelImpl();
        tsstsSpcvInitMgtWeeklyModel.from(imStkSimpleSptsMgtWeeklyModel);
        final TsstsStockPriceCalcValueService calcVlueWeeklyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueWeeklyService.initialize(tsstsSpcvInitMgtWeeklyModel);
        calcVlueWeeklyService.register();

        /* 株価時系列月足 */
        /* 株銘柄ＩＤの取得 */
        final long monthlyStockBrandId = this.imStkStockBrandService
            .getStockBrandId(imStkSpDataRegMgtModel.getStockBrandCode());
        // 株価時系列月足登録サービスの生成
        final TsstsSptsMonthlyRegService tsstsSptsMonthlyRegService = this.context
            .getBean(TsstsSptsMonthlyRegServiceImpl.class);
        // 株価時系列月足登録サービスの初期化
        tsstsSptsMonthlyRegService.initialize(monthlyStockBrandId, imStkSpDataRegMgtModel);
        // 株価時系列月足登録サービスの削除する
        tsstsSptsMonthlyRegService.delete();
        // 株価時系列月足登録サービスの登録する
        tsstsSptsMonthlyRegService.register();
        // 登録データを取得する
        ImStkSimpleSptsMgtModel imStkSimpleSptsMgtMonthlyModel = null;
        try {
            imStkSimpleSptsMgtMonthlyModel = this.imStkStockPriceTimeSeriesService
                .findSimpleBySbIdAndPti(dailyStockBrandId, ImStkPeriodTypeTypes.MONTHLY);
        } catch (final ImStkDomainException e) {
            // TODO KenichiroArai 2021/12/11 例外処理
            final String errMsg = this.tsstsLogMessageResolver.getMessage(TsstsLogMessageTypes.NONE);
            throw new TsstsDomainException(errMsg, TsstsLogMessageTypes.NONE, e);
        }
        // 株価計算値を登録する
        final ImStkSpcvInitMgtModel tsstsSpcvInitMgtMonthlyModel = new ImStkSpcvInitMgtModelImpl();
        tsstsSpcvInitMgtMonthlyModel.from(imStkSimpleSptsMgtMonthlyModel);
        final TsstsStockPriceCalcValueService calcVlueMonthlyService = this.context
            .getBean(TsstsStockPriceCalcValueService.class);
        calcVlueMonthlyService.initialize(tsstsSpcvInitMgtMonthlyModel);
        calcVlueMonthlyService.register();

    }

}
