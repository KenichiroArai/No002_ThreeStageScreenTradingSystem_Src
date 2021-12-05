package kmg.im.stock.tssts.application.ui.gui;

import java.io.IOException;
import java.net.URL;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kmg.KmgApplication;

/**
 * 三段階スクリーン・トレーディング・システム制御画面ＧＵＩ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class TsstsControlScreenGui extends Application {

    /** コンテキスト */
    private static ConfigurableApplicationContext context;

    /**
     * エントリポイント<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param args
     *             オプション
     */
    public static void main(final String[] args) {

        TsstsControlScreenGui.context = SpringApplication.run(KmgApplication.class);
        Application.launch(args);
    }

    /**
     * 開始<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     * @param stage
     *              ステージ
     */
    @Override
    public void start(final Stage stage) {

        stage.setTitle("制御画面");
        try {
            final URL url = this.getClass().getResource("TsstsControlScreenGui.fxml");
            final FXMLLoader fxml = new FXMLLoader(url);
            fxml.setControllerFactory(TsstsControlScreenGui.context::getBean);
            final AnchorPane root = fxml.load();
            final Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (final IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 停止<br>
     *
     * @author KenichiroArai
     * @sine 1.0.0
     * @version 1.0.0
     */
    @Override
    public void stop() {
        TsstsControlScreenGui.context.close();
    }

}
