package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.DepModel;
import model.EmployeeModel;
import dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeDAO employeeDao = new EmployeeDAO();
	ArrayList<DepModel> deplist = new ArrayList<DepModel>();

	public EmployeeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");
		emodel.setDepName(employeeDao.findByDepName(emodel.getDepNo()));	//depnoを渡してdepnameをset

		deplist = employeeDao.findAllByDepNo(emodel.getDepNo());

		//deplist = employeeDao.findByDepNo(emodel);		//ログイン中ユーザの所属部署以外をリストに挿入

		session.setAttribute("Employee",emodel);
		session.setAttribute("DepList",deplist);
		session.setAttribute("PAGENO",0);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		 session.removeAttribute("RESULT");
		 session.removeAttribute("PAGENO");

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");

		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

		String emp_Name = request.getParameter("employee_name");
		String dep_No = request.getParameter("dep_no");
		String page_no = request.getParameter("pgno");		//ページ選択value
		int pageno = 1;

		employeelist = employeeDao.findByNameDep(emodel.getEmployeeNo(),dep_No,emp_Name,pageno); // 検索結果取得 ログイン番号、入力部署、入力社員名、ページ番号
		pageno = employeeDao.CountEmp(emodel.getEmployeeNo(), dep_No, emp_Name); // ページ数取得

		session.setAttribute("RESULT",employeelist);
		session.setAttribute("PAGENO",pageno);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);

	}

}
