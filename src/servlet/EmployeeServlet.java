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
import others.PassChanger;
import dao.DepDAO;
import dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DepDAO d_dao = new DepDAO();

	EmployeeDAO employeeDao = new EmployeeDAO();
	ArrayList<DepModel> deplist = new ArrayList<DepModel>();

	int pageno = 1;
	String selectno = "1";
	int nowpage = 1;

	public EmployeeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		PassChanger passChanger = new PassChanger();
		passChanger.indexOut(request, response);

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");
		emodel.setDepName(employeeDao.findByDepName(emodel.getDepNo()));	//depnoを渡してdepnameをset

		deplist = (ArrayList<DepModel>) d_dao.findDepAll();		//追加

		session.setAttribute("Employee",emodel);
		session.setAttribute("DepList",deplist);
		session.setAttribute("PAGENO",pageno);
		session.setAttribute("SELECTPG",nowpage);
		session.setAttribute("pageTitle","社員検索");

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

			ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();

		//検索結果の情報を削除
		session.removeAttribute("RESULT");

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");

		//社員名が入力されていた場合取得
		String emp_Name = request.getParameter("employee_name");
		//社員名が入力されていなかった場合セッションから社員名情報を取得
		if(emp_Name != null){
			session.setAttribute("SELECTNAME",emp_Name);
		}
		//部署番号判別
		String dep_No = request.getParameter("dep_no");
		if(dep_No.equals("00")) {

		}else {
			//JSPの入力値をセッションに保存
			if(dep_No != null){
				session.setAttribute("SELECTDEP",dep_No);
			}
		}

		//ページ番号の選択された番号を取得
		selectno = request.getParameter("pgno");		//ページ選択value
		//ページ番号が選択されていなかった場合初期値をセット
		if(selectno == null) {
			pageno = 1;
			selectno = "1";
		}
		pageno = Integer.parseInt(selectno);		//検索件数分のページ
		nowpage = Integer.parseInt(selectno);		//現在表示しているページ

		//社員名、部署番号が入力されているかどうか
		if(dep_No.equals(null)  && emp_Name.equals(null)) {	//社員番号、部署番号ともに入力されていなかった場合
			emp_Name = (String) session.getAttribute("SELECTNAME");
			dep_No = (String) session.getAttribute("SELECTDEP");
		}else if(emp_Name.equals(null)) {				//社員名のみが入力されていなかった場合
			emp_Name = (String) session.getAttribute("SELECTNAME");
		}else if(dep_No.equals(null)) {					//部署番号のみが入力されていなかった場合
			dep_No = (String) session.getAttribute("SELECTDEP");
		}else{
		}

		//社員検索呼出し	部署検索OR全社員一覧（00=全部署検索）
		if(dep_No.equals("00")) {
			employeelist = employeeDao.findByAll(emp_Name,pageno);
		}else {
			employeelist = employeeDao.findByNameDep(emodel.getEmployeeNo(),dep_No,emp_Name,pageno); // 検索結果取得 ログイン番号、入力部署、入力社員名、ページ番号
		}
		//ページ数取得
		pageno = employeeDao.CountEmp(dep_No,emp_Name); //ページ数取得

		//セッションに表示内容をセット
		session.setAttribute("RESULT",employeelist);
		session.setAttribute("PAGENO",pageno);
		session.setAttribute("SELECTPG",nowpage);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}

}
