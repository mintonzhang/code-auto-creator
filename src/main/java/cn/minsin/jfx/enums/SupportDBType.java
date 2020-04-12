package cn.minsin.jfx.enums;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: minton.zhang
 * @since: 2020/4/6 19:05
 */
public enum SupportDBType {
    Mysql8("com.mysql.cj.jdbc.Driver", DbType.MYSQL),
    Mysql5("com.mysql.jdbc.Driver", DbType.MYSQL),
    ORACLE("oracle.jdbc.driver.OracleDriver", DbType.ORACLE),
    ORACLE_12C("oracle.jdbc.driver.OracleDriver", DbType.ORACLE_12C),
    DB2("com.ibm.db2.jcc.DB2Driver", DbType.DB2),
    MARIADB("org.mariadb.jdbc.Driver", DbType.MARIADB),
    H2("org.h2.Driver", DbType.H2),
    HSQL("org.hsqldb.jdbcDriver", DbType.HSQL),
    POSTGRE_SQL("org.postgresql.Driver", DbType.POSTGRE_SQL),
    SQLITE("org.sqlite.JDBC", DbType.SQLITE),
    DM("dm.jdbc.driver.DmDriver", DbType.DM),
    SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", DbType.SQL_SERVER),
    SQL_SERVER2005("com.microsoft.sqlserver.jdbc.SQLServerDriver", DbType.SQL_SERVER);

    @Getter
    private final String diverClassName;

    @Getter
    private final DbType dbType;

    public static final Map<String, SupportDBType> DB_CACHE_MAP;

    static {
        SupportDBType[] values = values();
        DB_CACHE_MAP = new ConcurrentHashMap<>(values.length);

        for (SupportDBType value : values) {
            DB_CACHE_MAP.put(value.dbType.getDesc(), value);
        }
    }

    SupportDBType(String clazzFullName, DbType dbType) {
        this.diverClassName = clazzFullName;
        this.dbType = dbType;
    }

    public static SupportDBType findTypeByName(String name) {
        return DB_CACHE_MAP.get(name);
    }


}
