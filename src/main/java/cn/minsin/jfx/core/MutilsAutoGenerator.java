package cn.minsin.jfx.core;

import cn.minsin.core.tools.StringUtil;
import cn.minsin.jfx.model.GData;
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

import java.util.Map;

/**
 * @author: minton.zhang
 * @since: 2019/11/21 17:49
 */
@Getter
@Setter
@Accessors(chain = true)
public class MutilsAutoGenerator extends AutoGenerator {

	public MutilsAutoGenerator(DataSourceConfig dataSourceConfig, GData gData) {
		super.setDataSource(dataSourceConfig);
		this.gData = gData;
	}

	private GData gData;


	/**
	 * 1 实体类 2 mapper 3 xml
	 *
	 * @param type
	 * @return
	 */
	protected String getSaveFile(int type) {
		boolean g = StringUtil.isBlank(gData.globalDir);
		switch (type) {
			case 1:
				return g ? gData.entityDir : gData.globalDir.concat("/entity");
			case 2:
				return g ? gData.mapperDir : gData.globalDir.concat("/mapper");
			case 3:
				return g ? gData.xmlDir : gData.globalDir.concat("/mapper/xml");
		}
		return null;
	}


	public void run() {
		TemplateConfig templateConfig = new TemplateConfig();
		super.setTemplate(templateConfig);
		super.setTemplateEngine(new MyTemplateEngine());
		GlobalConfig gc = new GlobalConfig();
		gc.setFileOverride(gData.isOverrideFile);
		//输出路径
		gc.setAuthor("created by `code-auto-creator`");
		gc.setDateType(gData.isDate ? DateType.ONLY_DATE : DateType.TIME_PACK);
		gc.setOpen(false);
		gc.setSwagger2(false);
		gc.setBaseColumnList(true);
		gc.setEnableCache(false);
		gc.setEntityName("%s".concat(gData.entitySuffix));
		gc.setMapperName("%s".concat(gData.mapperSuffix));
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
		strategy.setLogicDeleteFieldName(gData.logicDeleteFiled);
		strategy.setEntityColumnConstant(true);
		strategy.setEntityLombokModel(gData.isEnableLombok);
		//跳过视图
		strategy.setSkipView(gData.isSkipView);
		if (gData.tables != null && gData.tables.length > 0) {
			strategy.setInclude(gData.tables);
		}
		if (gData.tablePrefix != null && gData.tablePrefix.length > 0) {
			strategy.setTablePrefix(gData.tablePrefix);
		}
		strategy.setEntityBooleanColumnRemoveIsPrefix(true);
		if (StringUtil.isNoneBlank(gData.entityBaseClass)) {
			strategy.setSuperEntityClass(gData.entityBaseClass);
		}
		if (StringUtil.isNoneBlank(gData.entityBaseClass)) {
			strategy.setSuperMapperClass(gData.mapperBaseClass);
		}

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
		// 自定义配置 cfg.XXXX
		return new InjectionConfig() {
			@Override
			public void initMap() {
				//更改输出文件目录
				ConfigBuilder config = super.getConfig();
				Map<String, String> packageInfo = config.getPackageInfo();
				packageInfo.put(ConstVal.MAPPER, MutilsAutoGenerator.this.gData.mapperPackage);
				packageInfo.put(ConstVal.ENTITY, MutilsAutoGenerator.this.gData.entityPackage);
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
