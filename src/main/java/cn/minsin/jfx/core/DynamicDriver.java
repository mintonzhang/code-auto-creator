package cn.minsin.jfx.core;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author: minton.zhang
 * @since: 2020/4/12 21:40
 */
public class DynamicDriver implements Driver {

    private final Driver dynamicDriver;

    public DynamicDriver(Driver d) {
        this.dynamicDriver = d;
    }

	@Override
	public boolean acceptsURL(String u) throws SQLException {
		return this.dynamicDriver.acceptsURL(u);
	}

	@Override
	public Connection connect(String u, Properties p) throws SQLException {
		return this.dynamicDriver.connect(u, p);
	}

	@Override
	public int getMajorVersion() {
		return this.dynamicDriver.getMajorVersion();
	}

	@Override
	public int getMinorVersion() {
		return this.dynamicDriver.getMinorVersion();
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String u, Properties p) throws SQLException {
		return this.dynamicDriver.getPropertyInfo(u, p);
	}

	@Override
	public boolean jdbcCompliant() {
		return this.dynamicDriver.jdbcCompliant();
	}

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.dynamicDriver.getParentLogger();
    }


}
