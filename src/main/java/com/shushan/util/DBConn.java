package com.shushan.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConn {
	 // JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/webhook?serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASS = "123456";

    // 连接池一些配置
    private static final int INIT_POOL_SIZE = 10;
    private static final int MAX_IDLE_TIME = 30;
    private static final int MAX_POOL_SIZE = 100;
    private static final int MIN_POOL_SIZE = 10;

    private static ComboPooledDataSource dataSource;

    // 初始化连接池
    static {
        dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(JDBC_DRIVER);
            dataSource.setJdbcUrl(DB_URL);
            dataSource.setUser(USER);
            dataSource.setPassword(PASS);
            dataSource.setInitialPoolSize(INIT_POOL_SIZE);
            dataSource.setMaxIdleTime(MAX_IDLE_TIME);
            dataSource.setMaxPoolSize(MAX_POOL_SIZE);
            dataSource.setMinPoolSize(MIN_POOL_SIZE);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

	public static Connection getConnection() {
		Connection cn = null;
		try {
			cn = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cn;
	}

	public static void close(Connection conn, PreparedStatement psmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (psmt != null) {
			try {
				psmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
