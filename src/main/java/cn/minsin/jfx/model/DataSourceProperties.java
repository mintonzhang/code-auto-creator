package cn.minsin.jfx.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/5/2
 */
@Getter
@Setter
public class DataSourceProperties {
    private String driverJarFile;

    private String driverClass;

    private String url;

    private String username;

    private String password;


}
