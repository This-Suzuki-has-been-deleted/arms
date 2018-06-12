package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EmployeeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");
		EmployeeDAO employeeDao = new EmployeeDAO();
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

		String emp_Name = request.getParameter("employee_name");
		String dep_No = request.getParameter("dep_no");
		int pageno = 1;

		employeelist = employeeDao.findByNameDep(emodel.getEmployeeNo(),
				emodel.getDepNo(), emp_Name, pageno); // 検索結果取得
		pageno = employeeDao.CountEmp(emodel.getEmployeeNo(), dep_No, emp_Name); // ページ数取得

		session.setAttribute("RESULT", employeelist);
		session.setAttribute("PAGENO", pageno);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/employeeSearch");
		dispatcher.forward(request, response);

	}

}
