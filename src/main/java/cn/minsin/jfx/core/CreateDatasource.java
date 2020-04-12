package cn.minsin.jfx.core;

import cn.minsin.jfx.enums.SupportDBType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;

import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

/**
 * @author: minton.zhang
 * @since: 2020/4/11 22:06
 */
public class CreateDatasource {

    public static DataSourceConfig createDataSource(String url, String username, String password, String type, String jarPath) {
        SupportDBType typeByName = SupportDBType.findTypeByName(type);
        DataSourceConfig dataSourceConfig = new MyDataSourceConfig(jarPath);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setDbType(typeByName.getDbType());
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setDriverName(typeByName.getDiverClassName());
        return dataSourceConfig;
    }

    public static class MyDataSourceConfig extends DataSourceConfig {

        private final String jarPath;

        public MyDataSourceConfig(String jarPath) {
            this.jarPath = jarPath;
        }

        @Override
        public Connection getConn() {
            try {
                URL jarUrl = new URL(String.format("file:%s", jarPath));
                URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
                Driver driver = (Driver) classLoader.loadClass(super.getDriverName()).newInstance();
                // 注册驱动
                DriverManager.registerDriver(new DynamicDriver(driver));
                return DriverManager.getConnection(super.getUrl(), super.getUsername(), super.getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
