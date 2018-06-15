package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AuthModel;
import model.DepModel;
import model.EmployeeModel;
import dao.AuthDAO;
import dao.DepDAO;

/**
 * Servlet implementation class InfoChange
 */
@WebServlet("/InfoChangeServlet")
public class InfoChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InfoChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session = request.getSession();
		session.removeAttribute("eMsg");
		EmployeeModel employee = (EmployeeModel) session.getAttribute("Employee");
		String employeeNo = request.getParameter("employeeNo");
		if(employeeNo == null){
			employeeNo = employee.getEmployeeNo();
		}
		List<DepModel> depModel = new ArrayList<DepModel>();
		List<AuthModel> authModel = new ArrayList<AuthModel>();
		DepDAO depDao = new DepDAO();
		AuthDAO authDao = new AuthDAO();
		depModel = depDao.findDepAll();
		authModel = authDao.findAuthAll();
		session.setAttribute("DepModel", depModel);
		session.setAttribute("authModel",authModel);
		EmployeeModel employeeModel = new EmployeeModel();
		employeeModel.setEmployeeName(request.getParameter("employeeName"));
		employeeModel.setDepNo(request.getParameter("selectDivisionNo"));
		employeeModel.setAuthNo(request.getParameter("selectAuthorityNo"));
		session.setAttribute("ChangeEmployee", employeeModel);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/infoChange.jsp");
		dispatcher.forward(request, response);
	}

}
