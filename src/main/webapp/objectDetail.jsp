<%@page import="java.util.Map"%>
<%@page import="dto.SourceDTO"%>
<%@page import="dto.ColumnInfoDTO"%>
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
	<%
		List<ColumnInfoDTO> colInfoList = (List<ColumnInfoDTO>)request.getAttribute("columns");
		List<SourceDTO> sourceList = (List<SourceDTO>)request.getAttribute("sources");
		List<Map<String, String>> tableData = (List<Map<String, String>>)request.getAttribute("tableData");
		String message = (String)request.getAttribute("message");
	%>
	<%
		if (colInfoList != null) {
	%>
		<%
			for (ColumnInfoDTO colInfo : colInfoList) {
		%>
			<%= colInfo.getColumnId() + " | " + colInfo.getColumnName() + " | " + colInfo.getDataType() + " | " + colInfo.getDataLength() + " | " + colInfo.getNullable() + " | " + colInfo.getComments() %> <br />
		<% } %>
	<% } %>
		
	<%
		if (sourceList != null) {
	%>
		<%
			for (SourceDTO source : sourceList) {
		%>
			<%= source.getName() + " | " + source.getType() + " | " + source.getLine() + " | " + source.getText() %><br />
		<% } %>
	<% } %>
	
	<br />
	
	<%
		if (tableData != null && !tableData.isEmpty()) {
	%>
		<h3><%= request.getParameter("name") %> data</h3>
		<table border = "1">
			<tr>
				<%
					Map<String, String> firstRow = tableData.get(0);
				
					for (String columnName : firstRow.keySet()) {
				%>
					<th><%= columnName %></th>
				<% } %>
			</tr>
			<%
				for (Map<String, String> row : tableData) {
			%>
					<tr>
						<%
							for (String value : row.values()) {
						%>
							<td><%= value %></td>
						<% 
							}				
						%>
					</tr>
				<% } %>
		</table>
	<% } %>
	
	<%
		if(message != null) {
	%>
			<p><%= message %></p>
	<%
		}
	%>
</body>
</html>