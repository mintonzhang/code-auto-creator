package cn.minsin.jfx.controller;

import cn.minsin.core.tools.StringUtil;
import cn.minsin.jfx.constant.GlobalVariables;
import cn.minsin.jfx.core.CreateDatasource;
import cn.minsin.jfx.core.MutilsAutoGenerator;
import cn.minsin.jfx.enums.SupportDBType;
import cn.minsin.jfx.model.GData;
import cn.minsin.jfx.tools.ConfirmButtonType;
import cn.minsin.jfx.tools.ExtensionUtils;
import cn.minsin.jfx.tools.FxDialogs;
import cn.minsin.jfx.tools.FxFileDialog;
import cn.minsin.jfx.tools.FxRadioButton;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author: minton.zhang
 * @since: 2020/4/11 21:47
 */
public class MainController implements Initializable {

    public ComboBox<String> databaseType;

    public PasswordField password;

    public TextField username;

    public TextField url;


    public TextField globalDir;
    public TextField entityBaseClass;
    public TextField logicDelete;

    public TextField entitySuffix;

    public TextField entityDir;

    public TextField entityPackage;

    public TextArea tablePrefixes;
    public TextArea tables;

    public TextField mapperBaseClass;
    public TextField mapperXmlDir;
    public TextField mapperDir;
    public TextField mapperPackage;
    public TextField mapperSuffix;

    public TextField diverPath;

    private DataSourceConfig dataSourceConfig;

    public ToggleGroup isDate = FxRadioButton.create();

    public ToggleGroup isSkipView = FxRadioButton.create();

    public ToggleGroup isUnderlineToCamel = FxRadioButton.create();

    public ToggleGroup isEnableLombok = FxRadioButton.create();

    public ToggleGroup isOverride = FxRadioButton.create();

    private GData gData;

    public void testConnection(MouseEvent actionEvent) {
        this.checkDatasource(true);
    }

    public void about(ActionEvent actionEvent) throws URISyntaxException, IOException {
        Desktop.getDesktop().browse(new URI("https://github.com/mintonzhang/code-auto-creator"));
    }

    public void commit(ActionEvent actionEvent) {
        if (dataSourceConfig == null) {
            this.checkDatasource(false);
        }
        String entityDirValue = entityDir.getText();
        String mapperDirValue = mapperDir.getText();
        String mapperXmlDirValue = mapperXmlDir.getText();
        String globalDirValue = globalDir.getText();
        if (StringUtil.isBlank(globalDirValue)) {
            if (StringUtil.isBlank(entityDirValue)) {
                FxDialogs.showWarning("请选择实体类保存目录");
                entityDir.requestFocus();
                return;
            }
            if (StringUtil.isBlank(mapperDirValue)) {
                FxDialogs.showWarning("请选择Mapper保存目录");
                mapperDir.requestFocus();
                return;
            }
            if (StringUtil.isBlank(mapperXmlDirValue)) {
                FxDialogs.showWarning("请选择MapperXml保存目录");
                mapperXmlDir.requestFocus();
                return;
            }

        }


        //package
        String entityPackageText = entityPackage.getText();
        String mapperPackageText = mapperPackage.getText();


        Boolean isDateValue = FxRadioButton.getUserDataValue(isDate, Boolean.class);
        Boolean isEnableLombokValue = FxRadioButton.getUserDataValue(isEnableLombok, Boolean.class);
        Boolean isSkipViewValue = FxRadioButton.getUserDataValue(isSkipView, Boolean.class);
        Boolean isUnderlineToCamelValue = FxRadioButton.getUserDataValue(isUnderlineToCamel, Boolean.class);
        Boolean isOverrideValue = FxRadioButton.getUserDataValue(isOverride, Boolean.class);


        //父类
        String entityBaseClassText = entityBaseClass.getText();
        String mapperBaseClassText = mapperBaseClass.getText();


        //后缀
        String entitySuffixText = entitySuffix.getText();
        String mapperSuffixText = mapperSuffix.getText();

        //逻辑删除

        String logicDeleteText = logicDelete.getText();

        //表、视图
        String tablesText = tables.getText();

        String[] tableArray = StringUtil.isBlank(tablesText) ? null : tablesText.split("\n");

        //表前缀

        String tablePrefixesText = tablePrefixes.getText();

        String[] tablePrefixesArray = StringUtil.isBlank(tablePrefixesText) ? null : tablePrefixesText.split("\n");

        try {
            gData.setDate(isDateValue)
                    .setSkipView(isSkipViewValue)
                    .setUnderlineToCamel(isUnderlineToCamelValue)
                    .setEnableLombok(isEnableLombokValue)
                    .setOverrideFile(isOverrideValue)
                    .setGlobalDir(globalDirValue)
                    .setEntityDir(entityDirValue)
                    .setMapperDir(mapperDirValue)
                    .setXmlDir(mapperXmlDirValue)
                    .setEntityBaseClass(entityBaseClassText)
                    .setMapperBaseClass(mapperBaseClassText)
                    .setEntityPackage(entityPackageText)
                    .setMapperPackage(mapperPackageText)
                    .setEntitySuffix(entitySuffixText)
                    .setMapperSuffix(mapperSuffixText)
                    .setLogicDeleteFiled(logicDeleteText)
                    .setTables(tableArray)
                    .setTablePrefix(tablePrefixesArray);
            new MutilsAutoGenerator(dataSourceConfig, gData).run();

            //SaveFile
            GlobalVariables.save(JSON.toJSONString(gData));

            if (FxDialogs.showConfirm("生成成功,是否打开对应目录？", ConfirmButtonType.OK)) {
                if (StringUtil.isBlank(globalDirValue)) {
                    this.openDir(entityDirValue);
                    this.openDir(mapperDirValue);
                    this.openDir(mapperXmlDirValue);
                } else {
                    this.openDir(globalDirValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            FxDialogs.showError(e.getMessage());
        }
    }

    public void chooseFile(MouseEvent actionEvent) {
        TextField source = (TextField) actionEvent.getSource();
        String id = source.getId();
        if (id.equals("diverPath")) {
            FxFileDialog.chooseFile(source, ExtensionUtils.jar());
        } else {
            FxFileDialog.chooseDirectory(source);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resetSelectButton();
        //加载文件
        GData read = GlobalVariables.read();
        if (read != null && FxDialogs.showConfirm("检测到您上次使用的配置,是否应用?", ConfirmButtonType.OK)) {
            this.gData = read;
            FxRadioButton.setRadioButtonSelect(isDate, read.isDate);
            FxRadioButton.setRadioButtonSelect(isUnderlineToCamel, read.isUnderlineToCamel);
            FxRadioButton.setRadioButtonSelect(isSkipView, read.isSkipView);
            FxRadioButton.setRadioButtonSelect(isOverride, read.isOverrideFile);
            FxRadioButton.setRadioButtonSelect(isEnableLombok, read.isEnableLombok);
            globalDir.setText(read.globalDir);
            entityDir.setText(read.entityDir);
            mapperDir.setText(read.mapperDir);
            mapperXmlDir.setText(read.xmlDir);
            entityBaseClass.setText(read.entityBaseClass);
            mapperBaseClass.setText(read.mapperBaseClass);
            entityPackage.setText(read.entityPackage);
            mapperPackage.setText(read.mapperPackage);

            entitySuffix.setText(read.entitySuffix);
            mapperSuffix.setText(read.mapperSuffix);
            logicDelete.setText(read.logicDeleteFiled);
            if (read.tables != null) {
                tables.setText(String.join("\n", read.tables));
            }
            if (read.tablePrefix != null) {
                tablePrefixes.setText(String.join("\n", read.tablePrefix));
            }
            databaseType.setValue(read.databaseTypeValue);
            this.url.setText(read.urlText);
            username.setText(read.usernameText);
            password.setText(read.passwordText);
            diverPath.setText(read.diverPathText);
        } else {
            this.reset(null);
        }
    }

    private void checkDatasource(boolean successTips) {
        String ipText = url.getText();
        if (StringUtil.isBlank(ipText)) {
            FxDialogs.showWarning("请输入URL连接语句");
            url.requestFocus();
            return;
        }

        String usernameText = username.getText();
        if (StringUtil.isBlank(usernameText)) {
            FxDialogs.showWarning("请输入用户名");
            username.requestFocus();
            return;
        }

        String passwordText = password.getText();
        if (StringUtil.isBlank(passwordText)) {
            FxDialogs.showWarning("请输入密码");
            password.requestFocus();
            return;
        }
        String databaseTypeValue = databaseType.getValue();
        String diverPathText = diverPath.getText();

        try {
            DataSourceConfig mysql = CreateDatasource.createDataSource(ipText, usernameText, passwordText, databaseTypeValue, diverPathText);
            mysql.getConn();
            if (successTips) {
                FxDialogs.showInformation("连接成功");
            }
            this.dataSourceConfig = mysql;
            this.gData.urlText = ipText;
            this.gData.usernameText = usernameText;
            this.gData.passwordText = passwordText;
            this.gData.databaseTypeValue = databaseTypeValue;
            this.gData.diverPathText = diverPathText;
        } catch (Exception e) {
            FxDialogs.showException(e);
        }
    }

    private void openDir(String path) throws IOException {
        Desktop.getDesktop().open(new File(path));
    }

    private void resetSelectButton() {
        ObservableList<String> strings = FXCollections.observableArrayList(SupportDBType.DB_CACHE_MAP.keySet());
        //添加选择数据
        databaseType.setItems(strings);
        databaseType.setValue(strings.get(0));
    }


    public void reset(MouseEvent mouseEvent) {
        gData = new GData();
        FxRadioButton.setRadioButtonSelect(isDate, true);
        FxRadioButton.setRadioButtonSelect(isUnderlineToCamel, true);
        FxRadioButton.setRadioButtonSelect(isSkipView, true);
        FxRadioButton.setRadioButtonSelect(isOverride, false);
        FxRadioButton.setRadioButtonSelect(isEnableLombok, true);
        globalDir.clear();
        entityDir.clear();
        mapperDir.clear();
        mapperXmlDir.clear();
        entityBaseClass.clear();
        mapperBaseClass.clear();
        entityPackage.setText("cn.minsin.po");
        mapperPackage.setText("cn.minsin.mapper");

        entitySuffix.setText("PO");
        mapperSuffix.setText("Mapper");
        logicDelete.clear();
        tables.clear();
        tablePrefixes.clear();
        this.resetSelectButton();
        username.setText("root");
        password.clear();
        diverPath.clear();
    }
}
