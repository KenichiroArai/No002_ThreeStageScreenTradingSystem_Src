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
import kmg.im.stock.tssts.No002ThreeStageScreenTradingSystemApplication;

/**
 * 制御画面ＧＵＩ<br>
 *
 * @author KenichiroArai
 * @sine 1.0.0
 * @version 1.0.0
 */
@SpringBootApplication
public class ControlScreenGui extends Application {

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

        ControlScreenGui.context = SpringApplication.run(No002ThreeStageScreenTradingSystemApplication.class);
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
            final URL        url  = this.getClass().getResource("ControlScreenGui.fxml");
            final FXMLLoader fxml = new FXMLLoader(url);
            fxml.setControllerFactory(ControlScreenGui.context::getBean);
            final AnchorPane root  = fxml.load();
            final Scene      scene = new Scene(root);
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
        ControlScreenGui.context.close();
    }

}
