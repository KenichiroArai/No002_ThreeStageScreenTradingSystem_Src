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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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

    /** 三段階スクリーン・トレーディング・システムコントローラ */
    @Autowired
    private TsstsController tsstsCtl;

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

    /** 処理時間ラベル */
    @FXML
    private Label lblProcTime;

    /** 処理時間単位ラベル */
    @FXML
    private Label lblProcTimeUnit;

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
        final long startTime = System.nanoTime();
        try {

            /** 株価データを登録する。 */
            this.tsstsCtl.registerStockPriceData();

        } finally {
            final long     endTime = System.nanoTime();
            final String[] time    = ControlScreenController.getTime(startTime, endTime);
            this.lblProcTime.setText(String.valueOf(time[0]));
            this.lblProcTimeUnit.setText(String.valueOf(time[1]));
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
            defaultFilePath = "c:/";
        }
        File defaultFile = new File(defaultFilePath);
        if (defaultFile.isFile()) {
            defaultFile = defaultFile.getParentFile();
        }
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

        // TODO KenichiroArai 2021/05/08 未実装
    }

    // TODO KenichiroArai 2021/05/08 ユーティリティ化する
    /**
     * 開始時間と終了時間の差を返す。 <br>
     * 単位は時間に応じて設定する。
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param startTime
     *                  開始時刻
     * @param endTime
     *                  終了時刻
     * @return 差の時刻と単位
     */
    private static String[] getTime(final long startTime, final long endTime) {

        final String[] result = new String[2];

        double diffTime = endTime - startTime;
        result[0] = String.valueOf(diffTime);
        result[1] = "ナノ秒";

        if (diffTime >= 1000.0) {
            diffTime /= 1000.0;
            result[1] = "マイクロ秒";
        }
        if (diffTime >= 1000.0) {
            diffTime /= 1000.0;
            result[1] = "ミリ秒";
        }
        if (diffTime >= 1000.0) {
            diffTime /= 1000.0;
            result[1] = "秒";
        }
        result[0] = String.valueOf(diffTime);

        return result;
    }
}
