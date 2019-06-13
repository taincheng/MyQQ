package DB;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 用于数据库连接
 * 
 * @author Old_man
 *
 */
public class DBManager {
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "123456";
	public static final String URL = "jdbc:mysql://127.0.0.1:3306/myqqdate";
	public static DataSource dataSource = null;
	
	// 准备加载数据源 C3P0
	static {
		try {
			ComboPooledDataSource pool = new ComboPooledDataSource();
			pool.setDriverClass(DRIVER_NAME);
			pool.setUser(USERNAME);
			pool.setPassword(PASSWORD);
			pool.setJdbcUrl(URL);
			pool.setMaxPoolSize(30);
			pool.setMinPoolSize(5);
			dataSource = pool;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("数据连接池加载失败!");
		}
	}
	// 大家通过此方法 获得connection 连接对象
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

}
