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

	int pageno = 1;
	String selectno = "1";


	public EmployeeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");
		emodel.setDepName(employeeDao.findByDepName(emodel.getDepNo()));	//depnoを渡してdepnameをset

		deplist = employeeDao.findAllByDepNo(emodel.getDepNo());

		session.setAttribute("Employee",emodel);
		session.setAttribute("DepList",deplist);
		session.setAttribute("PAGENO",pageno);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		 session.removeAttribute("RESULT");

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");

		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

		String emp_Name = request.getParameter("employee_name");
		if(emp_Name != null){
			session.setAttribute("SELECTNAME",emp_Name);
		}

		String dep_No = request.getParameter("dep_no");
		if(dep_No != null){
			session.setAttribute("SELECTDEP",dep_No);
		}
		selectno = request.getParameter("pgno");		//ページ選択value

		if(selectno == null) {
			pageno = 1;
			selectno = "1";
		}
		pageno = Integer.parseInt(selectno);

		if(dep_No == null  && emp_Name == null) {
			emp_Name = (String) session.getAttribute("SELECTNAME");
			dep_No = (String) session.getAttribute("SELECTDEP");
		}else if(emp_Name == null) {
			emp_Name = (String) session.getAttribute("SELECTNAME");
		}else if(dep_No == null) {
			dep_No = (String) session.getAttribute("SELECTDEP");
		}

		employeelist = employeeDao.findByNameDep(emodel.getEmployeeNo(),dep_No,emp_Name,pageno); // 検索結果取得 ログイン番号、入力部署、入力社員名、ページ番号
		pageno = employeeDao.CountEmp(emodel.getEmployeeNo(),dep_No,emp_Name); //ページ数取得

		session.setAttribute("RESULT",employeelist);
		session.setAttribute("PAGENO",pageno);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);

	}

}
