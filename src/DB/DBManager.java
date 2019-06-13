package DB;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * �������ݿ�����
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
	
	// ׼����������Դ C3P0
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
			System.out.println("�������ӳؼ���ʧ��!");
		}
	}
	// ���ͨ���˷��� ���connection ���Ӷ���
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

}
