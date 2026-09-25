package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.DbObjectDTO;
import util.DBConnection;

public class SearchDAO {
	public static List<DbObjectDTO> search(String searchName, String searchType) {
		
		String query = 
				"SELECT A.OBJECT_NAME, A.OBJECT_TYPE, B.COMMENTS "
				+ "FROM USER_OBJECTS A "
				+ "LEFT OUTER JOIN USER_TAB_COMMENTS B "
				+ "ON A.OBJECT_NAME = B.TABLE_NAME "
				+ "WHERE A.OBJECT_NAME LIKE ? "
				+ "AND (? IS NULL OR A.OBJECT_TYPE = ?) "
				+ "ORDER BY A.OBJECT_NAME";
		
		List<DbObjectDTO> result = new ArrayList<>();
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			try (
					Connection conn = DBConnection.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(query);
				){
					System.out.println("OracleDB 接続成功");
					
					pstmt.setString(1, "%" + searchName + "%");
					pstmt.setString(2, searchType);
					pstmt.setString(3, searchType);
					try(ResultSet queryReturn = pstmt.executeQuery()){
						while(queryReturn.next()) {
							String objectName = queryReturn.getString("OBJECT_NAME");
							String objectType = queryReturn.getString("OBJECT_TYPE");
							String comments = queryReturn.getString("COMMENTS");
							
							DbObjectDTO obj = new DbObjectDTO(objectName, objectType, comments);
							result.add(obj);
						}
					}
				}
			} catch(ClassNotFoundException e) {
				System.out.println("Oracle Driver ローディング失敗");
				e.printStackTrace();
			} catch(SQLException e) {
				System.out.println("検索失敗: ");
				e.printStackTrace();
			}
		
		return result;
	}
	
	public static List<String> findObjectTypes(){
		String query = 
				"SELECT DISTINCT OBJECT_TYPE "
				+ "FROM USER_OBJECTS "
				+ "ORDER BY OBJECT_TYPE";
						
		List<String> result = new ArrayList<>();
		
		try (
			Connection conn = DBConnection.getConnection();	
			PreparedStatement pstmt = conn.prepareStatement(query);
		) {
			try (ResultSet queryResult = pstmt.executeQuery()){
				while(queryResult.next()) {
					String objectType = queryResult.getString("OBJECT_TYPE");
					result.add(objectType);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
