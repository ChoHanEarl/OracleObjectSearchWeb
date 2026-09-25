package dto;

public class ColumnInfoDTO {
	private int columnId;
	private String columnName;
	private String dataType;
	private int dataLength;
	private String nullable;
	private String comments;
	
	public ColumnInfoDTO(
				int columnId,
				String columnName,
				String dataType,
				int dataLength,
				String nullable,
				String comments
	) {
		this.columnId = columnId;
		this.columnName = columnName;
		this.dataType = dataType;
		this.dataLength = dataLength;
		this.nullable = nullable;
		this.comments = comments;
	}
	
	public int getColumnId() {
		return columnId;
	}
	
	public String getColumnName() {
		return columnName;
	}
	
	public String getDataType() {
		return dataType;
	}
	
	public int getDataLength() {
		return dataLength;
	}
	
	public String getNullable() {
		return nullable;
	}
	
	public String getComments() {
		return comments;
	}
}
