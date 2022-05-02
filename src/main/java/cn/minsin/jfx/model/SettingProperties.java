package cn.minsin.jfx.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/5/2
 */
@Getter
@Setter

public class SettingProperties {
    private String mapperSuffix;

    private String entitySuffix;

    private boolean useJdk8Time;

    private boolean useLombok;
    private boolean isSkipView;

    private boolean isUnderlineToCamel;

    private String logicDeleteFiled;

    private DataSourceProperties dataSource;
}
