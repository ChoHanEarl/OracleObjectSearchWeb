package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SearchDAO;
import dto.DbObjectDTO;

@WebServlet("/objects")
public class ObjectSearchServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String searchName = req.getParameter("name");
		String searchType = req.getParameter("type");
		if(searchType != null && searchType.isEmpty()) {
			searchType = null;
		}
		List<DbObjectDTO> objectList = SearchDAO.search(searchName, searchType);
		req.setAttribute("objectList", objectList);
		List<String> objectTypes = SearchDAO.findObjectTypes();
		req.setAttribute("objectTypes", objectTypes);
		req.getRequestDispatcher("/objectList.jsp").forward(req, resp);
	}
}
