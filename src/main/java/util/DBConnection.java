package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	
	public static Connection getConnection() throws SQLException {
		Properties properties = new Properties();
		try (
				InputStream input = DBConnection.class.getClassLoader()
				.getResourceAsStream("db.properties");				
		) {
			if (input == null) {
				throw new RuntimeException("db.propertiesが見つかりません。");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("db.properties読み込み失敗", e);
		}
		String url = properties.getProperty("db.url");
		String user = properties.getProperty("db.user");
		String password = properties.getProperty("db.password");
		
		return DriverManager.getConnection(url, user, password);
	}
}
