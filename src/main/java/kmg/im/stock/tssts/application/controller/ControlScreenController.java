package kmg.im.stock.tssts.application.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import kmg.core.domain.model.PfaMeasModel;
import kmg.core.infrastructure.type.KmgString;
import kmg.im.stock.tssts.domain.service.SigChkService;
import kmg.im.stock.tssts.domain.service.SimulationService;
import kmg.im.stock.tssts.domain.service.TsstsSpDataRegisterService;
import kmg.im.stock.tssts.infrastructure.exception.TsstsDomainException;
import kmg.im.stock.tssts.infrastructure.resolver.LogMessageResolver;
import kmg.im.stock.tssts.infrastructure.resolver.MessageResolver;
import kmg.im.stock.tssts.infrastructure.resolver.NameResolver;
import kmg.im.stock.tssts.infrastructure.types.LogMessageTypes;
import kmg.im.stock.tssts.infrastructure.types.MessageTypes;
import kmg.im.stock.tssts.infrastructure.types.NameTypes;

/**
 * 制御画面コントローラ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class ControlScreenController {

    /** ロガー */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControlScreenController.class);

    /** 株価銘柄格納パス */
    @Value("${import.path.stockpricestockstoragepath}")
    private Path stockPriceStockStoragePath;

    /** シミュレーションコンボボックスの項目のすべて */
    @Value("${ctlscn.cbsim.item.all}")
    private String cbSimItemAll;

    /** シグナルコンボボックスの項目のすべて */
    @Value("${ctlscn.cbsig.item.all}")
    private String cbSigItemAll;

    /** 格納ディレクトリテキストボックス */
    @FXML
    private TextField txtStorageDirectory;

    /** 格納ディレクトリディレクトリを開くボタン */
    @FXML
    private Button btnStorageDirectoryOpen;

    /** 格納ディレクトリ読み込むボタン */
    @FXML
    private Button btnStorageDirectoryLoad;

    /** 銘柄ファイルテキストボックス */
    @FXML
    private TextField txtBrandFile;

    /** 銘柄ファイルファイルを開くボタン */
    @FXML
    private Button btnBrandFileOpen;

    /** 銘柄ファイル読み込むボタン */
    @FXML
    private Button btnBrandFileLoad;

    /** シミュレーションコンボボックス */
    @FXML
    private ComboBox<String> cbSim;

    /** シミュレーションするボタン */
    @FXML
    private Button btnSim;

    /** シグナルコンボボックス */
    @FXML
    private ComboBox<String> cbSig;

    /** シグナルを確認するボタン */
    @FXML
    private Button btnSigChk;

    /** 処理時間ラベル */
    @FXML
    private Label lblProcTime;

    /** 処理時間単位ラベル */
    @FXML
    private Label lblProcTimeUnit;

    /** 名称リゾルバ */
    private final NameResolver nameResolver;

    /** メッセージリゾルバ */
    private final MessageResolver messageResolver;

    /** ログメッセージリゾルバ */
    private final LogMessageResolver logMessageResolver;

    /** 三段階スクリーン・トレーディング・システム株価データ登録サービス */
    private final TsstsSpDataRegisterService tsstsSpDataRegisterService;

    /** シミュレーションサービス */
    private final SimulationService simulationService;

    /** シグナル確認サービス */
    private final SigChkService sigChkService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param nameResolver
     *                                   名称リゾルバ
     * @param messageResolver
     *                                   メッセージリゾルバ
     * @param logMessageResolver
     *                                   ログメッセージリゾルバ
     * @param tsstsSpDataRegisterService
     *                                   三段階スクリーン・トレーディング・システム株価データ登録サービス
     * @param simulationService
     *                                   シミュレーションサービス
     * @param sigChkService
     *                                   シグナル確認サービス
     */
    public ControlScreenController(final NameResolver nameResolver, final MessageResolver messageResolver,
        final LogMessageResolver logMessageResolver, final TsstsSpDataRegisterService tsstsSpDataRegisterService,
        final SimulationService simulationService, final SigChkService sigChkService) {
        this.nameResolver = nameResolver;
        this.messageResolver = messageResolver;
        this.logMessageResolver = logMessageResolver;
        this.tsstsSpDataRegisterService = tsstsSpDataRegisterService;
        this.simulationService = simulationService;
        this.sigChkService = sigChkService;
    }

    /**
     * 初期化<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @FXML
    private void initialize() {

        // 格納ディレクトリテキストボックスに株価格納パスを設定する
        this.txtStorageDirectory.setText(this.stockPriceStockStoragePath.toAbsolutePath().toString());

        // 銘柄ファイルテキストボックスに株価格納パスを設定する
        this.txtBrandFile.setText(this.stockPriceStockStoragePath.toAbsolutePath().toString());

        // シミュレーションコンボボックスに項目を追加する
        this.cbSim.getItems().add(this.cbSimItemAll);
        this.cbSim.getSelectionModel().select(0);

        // シグナルコンボボックスに項目を追加する
        this.cbSig.getItems().add(this.cbSigItemAll);
        this.cbSig.getSelectionModel().select(0);

    }

    /**
     * 格納ディレクトリディレクトリを開くボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void openDirectoryOfStorageDirectory(final ActionEvent event) {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(this.nameResolver.getName(NameTypes.KMG_IM_STOCK_TSSTS_NAME10001));
        String defaultDirectoryPath = this.txtStorageDirectory.getText();
        if (KmgString.isEmpty(defaultDirectoryPath)) {
            // 格納ディレクトリテキストボックスに株価格納パスを設定する
            defaultDirectoryPath = this.stockPriceStockStoragePath.toAbsolutePath().toString();
        }
        Path defaultDirectory = Paths.get(defaultDirectoryPath);
        if (!Files.isDirectory(defaultDirectory)) {
            defaultDirectory = defaultDirectory.getParent();
        }
        directoryChooser.setInitialDirectory(defaultDirectory.toFile());
        final File directory = directoryChooser.showDialog(null);
        String directoryStr = KmgString.EMPTY;
        if (directory != null) {
            directoryStr = directory.getAbsolutePath();
        }
        this.txtStorageDirectory.setText(directoryStr);
    }

    /**
     * 格納ディレクトリ読み込むボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void loadDirectoryOfStorageDirectory(final ActionEvent event) {

        this.lblProcTime.setText(KmgString.EMPTY);
        this.lblProcTimeUnit.setText(KmgString.EMPTY);

        final PfaMeasModel pfaMeas = new PfaMeasModel();
        pfaMeas.start();
        try {
            final Path importPath = Paths.get(this.txtStorageDirectory.getText());
            if (!Files.isDirectory(importPath)) {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(this.nameResolver.getName(NameTypes.KMG_IM_STOCK_TSSTS_NAME10002));
                alert.setHeaderText(null);
                alert.setContentText(this.messageResolver.getMessage(MessageTypes.KMG_IM_STOCK_TSSTS_MSG_NO10001));
                alert.showAndWait();
                return;
            }

            /* 株価データを登録する */
            this.tsstsSpDataRegisterService.registerSpDataOfDirectory(importPath);
        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } catch (final Exception e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

    /**
     * 銘柄ファイルファイルを開くボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void openFileOfBrandFile(final ActionEvent event) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(this.nameResolver.getName(NameTypes.KMG_IM_STOCK_TSSTS_NAME10003));
        String defaultFilePath = this.txtBrandFile.getText();
        if ((defaultFilePath == null) || defaultFilePath.isEmpty()) {
            // 格納ディレクトリテキストボックスに株価格納パスを設定する
            defaultFilePath = this.stockPriceStockStoragePath.toAbsolutePath().toString();
        }
        final File defaultFile = new File(defaultFilePath);
        fileChooser.setInitialDirectory(defaultFile);
        final File file = fileChooser.showOpenDialog(null);
        String fileStr = KmgString.EMPTY;
        if (file != null) {
            fileStr = file.getAbsolutePath();
        }
        this.txtBrandFile.setText(fileStr);
    }

    /**
     * 銘柄ファイル読み込むボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void loadFileOfBrandFile(final ActionEvent event) {

        this.lblProcTime.setText(KmgString.EMPTY);
        this.lblProcTimeUnit.setText(KmgString.EMPTY);

        final PfaMeasModel pfaMeas = new PfaMeasModel();
        pfaMeas.start();
        try {
            final Path importPath = Paths.get(this.txtBrandFile.getText());
            if (!Files.exists(importPath)) {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(this.nameResolver.getName(NameTypes.KMG_IM_STOCK_TSSTS_NAME10004));
                alert.setHeaderText(null);
                alert.setContentText(this.messageResolver.getMessage(MessageTypes.KMG_IM_STOCK_TSSTS_MSG_NO10002));
                alert.showAndWait();
                return;
            }
            if (Files.isDirectory(importPath)) {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(this.nameResolver.getName(NameTypes.KMG_IM_STOCK_TSSTS_NAME10005));
                alert.setHeaderText(null);
                alert.setContentText(this.messageResolver.getMessage(MessageTypes.KMG_IM_STOCK_TSSTS_MSG_NO10003));
                alert.showAndWait();
                return;
            }

            /* 株価データを登録する */
            this.tsstsSpDataRegisterService.registerSpDataOfFile(importPath);
        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            // TODO KenichiroArai 2021/08/4 メッセージ
            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } catch (final Exception e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            // TODO KenichiroArai 2021/08/4 メッセージ
            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

    /**
     * シミュレーションするボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void simulate(final ActionEvent event) {

        this.lblProcTime.setText(KmgString.EMPTY);
        this.lblProcTimeUnit.setText(KmgString.EMPTY);

        final PfaMeasModel pfaMeas = new PfaMeasModel();
        pfaMeas.start();
        try {
            /* シミュレーションする */
            // 選択対象がすべてか
            if (KmgString.equals(this.cbSim.getSelectionModel().getSelectedItem(), this.cbSimItemAll)) {
                // すべての場合

                // 全ての銘柄をシミュレーションする
                this.simulationService.simulate();
            } else {
                // すべて以外の場合

                // 指定した株コードのシミュレーションする
                final long code = Long.parseLong(this.cbSim.getSelectionModel().getSelectedItem());
                this.simulationService.simulate(code);
            }
        } catch (final TsstsDomainException e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            // TODO KenichiroArai 2021/08/4 メッセージ
            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } catch (final Exception e) {
            // 三段階スクリーン・トレーディング・システムドメイン例外

            // TODO KenichiroArai 2021/08/4 メッセージ
            final String logMsg = this.logMessageResolver.getMessage(LogMessageTypes.NONE);
            ControlScreenController.LOGGER.error(logMsg, e);
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

    /**
     * シグナルを確認するボタンクリックイベント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void chkSig(final ActionEvent event) {

        this.lblProcTime.setText(KmgString.EMPTY);
        this.lblProcTimeUnit.setText(KmgString.EMPTY);

        final PfaMeasModel pfaMeas = new PfaMeasModel();
        pfaMeas.start();
        try {
            /* シグナルを確認する */
            // 選択対象がすべてか
            if (KmgString.equals(this.cbSim.getSelectionModel().getSelectedItem(), this.cbSigItemAll)) {
                // すべての場合

                // 全ての銘柄をシミュレーションする
                this.sigChkService.chkSig();
            } else {
                // すべて以外の場合

                // 指定した株コードのシミュレーションする
                final long code = Long.parseLong(this.cbSim.getSelectionModel().getSelectedItem());
                this.sigChkService.chkSig(code);
            }
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

}
