package kmg.tool.application.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import kmg.core.domain.model.PfaMeasModel;
import kmg.core.infrastructure.type.KmgString;
import kmg.tool.domain.service.InsertSqlCreationService;
import kmg.tool.domain.service.InsertSqlCreationServiceImpl;

/**
 * 挿入ＳＱＬ作成画面画面コントローラ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
public class InsertSqlCreationController {

    /** 入力ファイルテキストボックス */
    @FXML
    private TextField txtInputFile;

    /** 入力ファイル読み込みボタン */
    @FXML
    private Button btnInputFileOpen;

    /** 出力ディレクトリテキストボックス */
    @FXML
    private TextField txtOutputDirectory;

    /** 出力ディレクトリ読み込みボタン */
    @FXML
    private Button btnOutputDirectoryOpen;

    /** 実行ボタン */
    @FXML
    private Button btnRun;

    /** 処理時間ラベル */
    @FXML
    private Label lblProcTime;

    /** 処理時間単位ラベル */
    @FXML
    private Label lblProcTimeUnit;

    /** 挿入ＳＱＬ作成サービス */
    private final InsertSqlCreationService insertSqlCreationService;

    /**
     * コンストラクタ<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    public InsertSqlCreationController() {
        this.insertSqlCreationService = new InsertSqlCreationServiceImpl();
    }

    /**
     * 入力ファイル読み込みボタンクリックイベント
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcInputFileOpenClicked(final ActionEvent event) {

        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ファイル選択");
        String defaultFilePath = this.txtInputFile.getText();
        if ((defaultFilePath == null) || defaultFilePath.isEmpty()) {
            defaultFilePath = "c:/";
        }
        File defaultFile = new File(defaultFilePath);
        if (defaultFile.isFile()) {
            defaultFile = defaultFile.getParentFile();
        }
        fileChooser.setInitialDirectory(defaultFile);
        final File file = fileChooser.showOpenDialog(null);
        this.txtInputFile.setText(file.getAbsolutePath());
    }

    /**
     * 出力ディレクトリ読み込みボタンクリックイベント
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcOutputDirectoryOpenClicked(final ActionEvent event) {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("ディレクトリ選択");
        String defaultFilePath = this.txtOutputDirectory.getText();
        if ((defaultFilePath == null) || defaultFilePath.isEmpty()) {
            defaultFilePath = "c:/";
        }
        File defaultFile = new File(defaultFilePath);
        if (defaultFile.isFile()) {
            defaultFile = defaultFile.getParentFile();
        }
        directoryChooser.setInitialDirectory(defaultFile);
        final File file = directoryChooser.showDialog(null);
        this.txtOutputDirectory.setText(file.getAbsolutePath());
    }

    /**
     * 実行ボタンクリックイベント
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param event
     *              アクションイベント
     */
    @FXML
    private void onCalcRunClicked(final ActionEvent event) {

        // TODO KenichiroArai 2021/07/08 デバッグ用
        if (KmgString.isEmpty(this.txtInputFile.getText())) {
            this.txtInputFile.setText(
                "D:\\投資システム\\No002_三段階スクリーン・トレーディング・システム\\ドキュメント\\AF010_環境\\BB010_DB\\CB030_データ\\DA010_管理\\DA010001_初期データ.xlsm");
        }
        if (KmgString.isEmpty(this.txtOutputDirectory.getText())) {
            this.txtOutputDirectory.setText(
                "D:\\投資システム\\No002_三段階スクリーン・トレーディング・システム\\ドキュメント\\AF010_環境\\BB010_DB\\CB030_データ\\DA010_管理\\output");
        }

        final PfaMeasModel pfaMeas = new PfaMeasModel();
        pfaMeas.start();
        try {
            // メイン処理
            final Path inputPath = Paths.get(this.txtInputFile.getText());
            final Path outputPath = Paths.get(this.txtOutputDirectory.getText());
            this.mainProc(inputPath, outputPath);
        } finally {
            pfaMeas.end();
            this.lblProcTime.setText(String.valueOf(pfaMeas.getElapsedTime()));
            this.lblProcTimeUnit.setText(pfaMeas.getTimeUnit().getUnitName());
        }
    }

    /**
     * メイン処理
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param inputPath
     *                   入力パス
     * @param outputPath
     *                   出力パス
     */
    protected void mainProc(final Path inputPath, final Path outputPath) {

        this.insertSqlCreationService.outputInsertSql(inputPath, outputPath);

    }
}
