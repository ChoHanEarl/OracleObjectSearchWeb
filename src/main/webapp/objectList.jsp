<%@page import="dto.ColumnInfoDTO"%>
<%@page import="dto.DbObjectDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="objects" method="get">
		<input type="text" name="name">
		<button type="submit">検索</button>
		<%
			String selectedType = request.getParameter("type");
		%>
		<%
			List<String> objectTypes =
			(List<String>)request.getAttribute("objectTypes");
		%>
		<select name="type">
			<option value="" <%= (selectedType == null || selectedType.isEmpty()) ? "selected" : "" %>>
				ALL
			</option>
			<%
				for (String type : objectTypes) {
			%>
				<option value="<%= type %>"
					<%= type.equals(selectedType) ? "selected" : "" %>>
					<%= type %>
				</option>
			<% 
				} 
			%>
		</select>
	</form>
	<%
		List<DbObjectDTO> objList = 
			(List<DbObjectDTO>)request.getAttribute("objectList");
	%>
	<%
		for (DbObjectDTO obj : objList) {
	%>
		<a href="objectDetail?name=<%= obj.getObjectName() %>&type=<%= obj.getObjectType()%>">
			<%= obj.getObjectName() %>
		</a>
		
		| <%= obj.getObjectType() %>
		| <%= obj.getComments() %>
		<br />
	<%
		}
	%>
</body>
</html>