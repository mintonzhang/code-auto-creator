package cn.minsin.jfx.tools;

import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author: minton.zhang
 * @since: 2020/4/12 18:08
 */
public class FxFileDialog {


    public static void chooseDirectory(TextField textField) {
        DirectoryChooser fileChooser = new DirectoryChooser();
        fileChooser.setTitle("请选择目录");
        Stage stage = new Stage();
        File file = fileChooser.showDialog(stage);
        if (file != null) {
            File absoluteFile = file.getAbsoluteFile();
            textField.setText(absoluteFile.getAbsolutePath());
        }
    }

    public static void chooseFile(TextField textField, FileChooser.ExtensionFilter[] fileTypes) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择文件");
        fileChooser.getExtensionFilters().addAll(fileTypes);
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            File absoluteFile = file.getAbsoluteFile();
            textField.setText(absoluteFile.getAbsolutePath());
        }
    }
}
