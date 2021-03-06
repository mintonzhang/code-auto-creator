package cn.minsin.jfx;

import cn.minsin.jfx.constant.GlobalVariables;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author: minton.zhang
 * @since: 2020/4/4 17:42
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("home/home.fxml")));
        primaryStage.setTitle("mybatis-plus代码生成工具");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.getIcons().add(new Image("template/icon.png"));
        primaryStage.show();
        GlobalVariables.width = primaryStage.getWidth();
        GlobalVariables.height = primaryStage.getHeight();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
