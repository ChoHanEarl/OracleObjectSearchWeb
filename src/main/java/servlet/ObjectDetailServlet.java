package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ObjectDAO;
import dto.ColumnInfoDTO;
import dto.SourceDTO;

@WebServlet("/objectDetail")
public class ObjectDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String objectName = req.getParameter("name");
		String objectType = req.getParameter("type");
		List<ColumnInfoDTO> columns = null; 
		List<SourceDTO> sources = null;
		List<Map<String, String>> tableData = null;
		String message = null;
		if ("TABLE".equals(objectType)) {
					columns = ObjectDAO.findTableColumns(objectName);
					tableData = ObjectDAO.findTableData(objectName);
		} else if (
				"FUNCTION".equals(objectType)
				|| "PROCEDURE".equals(objectType)
				|| "PACKAGE".equals(objectType)
				|| "PACKAGE BODY".equals(objectType)
		){
			sources = ObjectDAO.findSource(objectName, objectType);
		} else message = "このオブジェクトタイプの詳細表示には対応していません。";
		
		req.setAttribute("columns", columns);
		req.setAttribute("sources", sources);
		req.setAttribute("tableData", tableData);
		req.setAttribute("message", message);
		req.getRequestDispatcher("/objectDetail.jsp").forward(req, resp);
	}
}
