package dto;

public class DbObjectDTO {

	private String objectName;
	private String objectType;
	private String comments;

	public DbObjectDTO(String objectName, String objectType, String comments) {
		this.objectName = objectName;
		this.objectType = objectType;
		this.comments = comments;
	}
	
	public String getObjectName() {
		return objectName;
	}
	public String getObjectType() {
		return objectType;
	}
	public String getComments() {
		return comments;
	}
}
