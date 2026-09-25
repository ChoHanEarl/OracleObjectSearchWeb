package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dto.ColumnInfoDTO;
import dto.SourceDTO;
import util.DBConnection;

public class ObjectDAO {
	

	public static List<ColumnInfoDTO> findTableColumns(
			String tableName){
		List<ColumnInfoDTO> result = new ArrayList<>();
		
		String query = "SELECT C.COLUMN_ID, C.COLUMN_NAME, C.DATA_TYPE, C.DATA_LENGTH, C.NULLABLE, CC.COMMENTS "
				+ "FROM USER_TAB_COLUMNS C "
				+ "LEFT OUTER JOIN USER_COL_COMMENTS CC "
				+ "ON C.TABLE_NAME = CC.TABLE_NAME "
				+ "AND C.COLUMN_NAME = CC.COLUMN_NAME "
				+ "WHERE C.TABLE_NAME = ? "
				+ "ORDER BY C.COLUMN_ID";
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
				){
			pstmt.setString(1, tableName);
			try(ResultSet queryReturn = pstmt.executeQuery()){
				while(queryReturn.next()) {
					int columnId = queryReturn.getInt("COLUMN_ID");
					String columnName = queryReturn.getString("COLUMN_NAME");
					String dataType = queryReturn.getString("DATA_TYPE");
					int dataLength = queryReturn.getInt("DATA_LENGTH");
					String nullable = queryReturn.getString("NULLABLE");
					String comments = queryReturn.getString("COMMENTS");
					
					result.add(new ColumnInfoDTO(columnId, columnName, dataType, dataLength, nullable, comments));
				}
			} 
		} catch (SQLException e) {
			System.out.println("検索失敗: ");
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<SourceDTO> findSource(
									String name, 
									String type
			){
		List<SourceDTO> result = new ArrayList<>();
		
		String query = "SELECT S.NAME, S.TYPE, S.LINE, S.TEXT "
				+ "FROM USER_SOURCE S "
				+ "WHERE S.NAME = ? "
				+ "AND S.TYPE = ? "
				+ "ORDER BY S.LINE";
		
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
			) {
			
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			
			try(ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					String sourceName = rs.getString("NAME");
					String sourceType = rs.getString("TYPE");
					int line = rs.getInt("LINE");
					String text = rs.getString("TEXT");
					
					result.add(new SourceDTO(sourceName, sourceType, line, text));
				}
			}
		} catch(SQLException e) {
			System.out.println("照会失敗：");
			e.printStackTrace();
		}
		return result;
	}
		
	public static List<Map<String, String>> findTableData(String tableName) {
		
		List<Map<String, String>> result = new ArrayList<>();
		if (!isValidTable(tableName)) {
			return result;
		}
		String query = "SELECT * FROM " + tableName;
		try (
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
			){
				try (
					ResultSet rs = pstmt.executeQuery();
				){
					ResultSetMetaData metaData = rs.getMetaData();
					int columnCount = metaData.getColumnCount();
					
					while (rs.next()) {
						Map<String, String> row = new LinkedHashMap<>();
						for(int i = 1; i <= columnCount; i++) {
							String columnName = metaData.getColumnName(i);
							String value = rs.getString(i);
							row.put(columnName, value);
						}
						result.add(row);
					}
				} 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static boolean isValidTable(String tableName) {
		
		String query = "SELECT COUNT(*) "
				+ "FROM USER_OBJECTS "
				+ "WHERE OBJECT_NAME = ? "
				+ "AND OBJECT_TYPE = 'TABLE'";
		boolean result = false;
		
		try(
				Connection conn = DBConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(query);
			){
			pstmt.setString(1, tableName);
			try (
				ResultSet rs = pstmt.executeQuery();
			) {
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count > 0) {
						result = true;
					}
				}
			} 
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}

