package kmg.im.stock.tssts.application.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
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
import kmg.core.domain.PfaMeasModel;
import kmg.im.stock.tssts.domain.service.ImportService;
import kmg.im.stock.tssts.domain.service.SigService;
import kmg.im.stock.tssts.domain.service.SimService;
import kmg.tool.ssts.infrastructure.type.KmgString;

/**
 * 制御画面コントローラ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@Component
public class ControlScreenController {

    /** 株価銘柄格納パス */
    @Value("${stockPriceStockStoragePath}")
    private Path stockPriceStockStoragePath;

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

    /** インポートサービス */
    @Autowired
    private ImportService importService;

    /** シミュレーションサービス */
    @Autowired
    private SimService simService;

    /** シグナルサービス */
    @Autowired
    private SigService sigService;

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
        this.cbSim.getItems().add("すべて");
        this.cbSim.getSelectionModel().select(0);

        // シグナルコンボボックスに項目を追加する
        this.cbSig.getItems().add("すべて");
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
        directoryChooser.setTitle("ディレクトリ選択");
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
        this.txtStorageDirectory.setText(directory.getAbsolutePath());
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
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("存在するディレクトリではありません。存在するディレクトリを指定してください。");
                alert.showAndWait();
                return;
            }

            /* 株価データを登録する */
            this.importService.registerStockPriceDataOfDirectory(importPath);
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
        fileChooser.setTitle("ファイル選択");
        String defaultFilePath = this.txtBrandFile.getText();
        if ((defaultFilePath == null) || defaultFilePath.isEmpty()) {
            // 格納ディレクトリテキストボックスに株価格納パスを設定する
            defaultFilePath = this.stockPriceStockStoragePath.toAbsolutePath().toString();
        }
        final File defaultFile = new File(defaultFilePath);
        fileChooser.setInitialDirectory(defaultFile);
        final File file = fileChooser.showOpenDialog(null);
        this.txtBrandFile.setText(file.getAbsolutePath());
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
            if (Files.isDirectory(importPath)) {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("エラー");
                alert.setHeaderText(null);
                alert.setContentText("存在するファイルではありません。存在するファイルを指定してください。");
                alert.showAndWait();
                return;
            }

            /* 株価データを登録する */
            this.importService.registerStockPriceDataOfDirectory(importPath);
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
            if (KmgString.equals(this.cbSim.getSelectionModel().getSelectedItem(), "すべて")) {
                // すべての場合

                // 全ての銘柄をシミュレーションする
                this.simService.simulate();
            } else {
                // すべて以外の場合

                // 指定した株コードのシミュレーションする
                final long code = Long.parseLong(this.cbSim.getSelectionModel().getSelectedItem());
                this.simService.simulate(code);
            }
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
            if (KmgString.equals(this.cbSim.getSelectionModel().getSelectedItem(), "すべて")) {
                // すべての場合

                // 全ての銘柄をシミュレーションする
                this.sigService.chkSig();
            } else {
                // すべて以外の場合

                // 指定した株コードのシミュレーションする
                final long code = Long.parseLong(this.cbSim.getSelectionModel().getSelectedItem());
                this.sigService.chkSig(code);
            }
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

}
