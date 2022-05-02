package cn.minsin.cg.core;

import cn.minsin.cg.model.ConfigProperties;
import cn.minsin.cg.model.DataSourceProperties;
import cn.minsin.cg.model.OutputProperties;
import cn.minsin.cg.model.SettingProperties;
import cn.minsin.core.tools.IOUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * @author minton.zhang
 * @since 2019/11/21 17:49
 */
@Getter
@Setter
@Accessors(chain = true)
public class MutilsAutoGenerator extends AutoGenerator {

    private static ConfigProperties configProperties;
    private static String outputFolderPath;

    static {
        try {
            ClassLoader classLoader = CommonClassLoader.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("config/config.json");
            byte[] bytes = IOUtil.copyInputStream(resourceAsStream);
            configProperties = JSON.parseObject(new String(bytes), ConfigProperties.class);

            URL resource = classLoader.getResource("");
            String path1 = resource.getPath();
            outputFolderPath = path1.replace("target/classes/", "src/main/resources/output");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutilsAutoGenerator() {
        super.setDataSource(this.createDatasourceConfig());
    }

    private DataSourceConfig createDatasourceConfig() {
        DataSourceProperties dataSource = configProperties.getSetting().getDataSource();
        return CreateDatasource.createDataSource(
                dataSource.getUrl(),
                dataSource.getUsername()
                , dataSource.getPassword(),
                dataSource.getDriverClass(),
                dataSource.getDriverJarFile()
        );
    }


    /**
     * 1 实体类 2 mapper 3 xml
     */
    protected String getSaveFile(int type) {
        switch (type) {
            case 1:
                return outputFolderPath + "/entity";
            case 2:
                return outputFolderPath + "/mapper";
            case 3:
                return outputFolderPath + "/mapper/xml";
        }
        return null;
    }


    public void run() {
        SettingProperties setting = configProperties.getSetting();
        OutputProperties output = configProperties.getOutput();

        TemplateConfig templateConfig = new TemplateConfig();
        super.setTemplate(templateConfig);
        super.setTemplateEngine(new MyTemplateEngine());
        GlobalConfig gc = new GlobalConfig();
        gc.setFileOverride(true);
        //输出路径
        gc.setAuthor("created by `minsin's code auto generator`");
        gc.setDateType(setting.isUseJdk8Time() ? DateType.TIME_PACK : DateType.ONLY_DATE);
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setBaseColumnList(true);
        gc.setEnableCache(false);
        gc.setEntityName("%s".concat(setting.getEntitySuffix()));
        gc.setMapperName("%s".concat(setting.getMapperSuffix()));
        gc.setBaseColumnList(false);
        gc.setBaseResultMap(false);
        super.setGlobalConfig(gc);
//        super.getPackageInfo().setMapper();

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setLogicDeleteFieldName(setting.getLogicDeleteFiled());
        strategy.setEntityColumnConstant(true);
        strategy.setEntityLombokModel(setting.isUseLombok());
        //跳过视图
        strategy.setSkipView(setting.isSkipView());
        String[] tables = output.getTables();

        if (tables != null && tables.length > 0) {
            strategy.setInclude(tables);
        }
        //if (gData.tablePrefix != null && gData.tablePrefix.length > 0) {
        //    strategy.setTablePrefix(gData.tablePrefix);
        //}
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        super.setStrategy(strategy);
        InjectionConfig injectionConfig = this.getInjectionConfig();
        if (injectionConfig != null) {
            super.setCfg(injectionConfig);
        }
        this.execute();
        //saveFile
    }


    @Override
    public AutoGenerator setConfig(ConfigBuilder config) {


        return super.setConfig(config);
    }


    protected InjectionConfig getInjectionConfig() {
        OutputProperties output = configProperties.getOutput();

        // 自定义配置 cfg.XXXX
        return new InjectionConfig() {
            @Override
            public void initMap() {
                //更改输出文件目录
                ConfigBuilder config = super.getConfig();
                Map<String, String> packageInfo = config.getPackageInfo();
                packageInfo.put(ConstVal.MAPPER, output.getMapperPackageName());
                packageInfo.put(ConstVal.ENTITY, output.getEntityPackageName());
                Map<String, String> pathInfo = config.getPathInfo();
                //controller和service、serviceImpl不要
                pathInfo.put(ConstVal.SERVICE_IMPL_PATH, null);
                pathInfo.put(ConstVal.SERVICE_PATH, null);
                pathInfo.put(ConstVal.CONTROLLER_PATH, null);
                //mapper输出到mybaits模块
                pathInfo.put(ConstVal.MAPPER_PATH, MutilsAutoGenerator.this.getSaveFile(2));
                pathInfo.put(ConstVal.ENTITY_PATH, MutilsAutoGenerator.this.getSaveFile(1));
                //xml输出到mybaits的resource下
                pathInfo.put(ConstVal.XML_PATH, MutilsAutoGenerator.this.getSaveFile(3));
            }
        };
    }
}
