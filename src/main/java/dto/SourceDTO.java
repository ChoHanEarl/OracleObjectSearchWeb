package dto;

public class SourceDTO {
	private String name;
	private String type;
	private int line;
	private String text;
	
	public SourceDTO(
			String name,
			String type,
			int line,
			String text
			) {
		this.name = name;
		this.type = type;
		this.line = line;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getLine() {
		return line;
	}
	public String getText() {
		return text;
	}
}
