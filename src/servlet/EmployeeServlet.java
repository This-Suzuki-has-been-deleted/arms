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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//初期パスワードの変更を促す
		PassChanger passChanger = new PassChanger();
		passChanger.indexOut(request, response);


		session.removeAttribute("PAGENO");

		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");
		emodel.setDepName(d_dao.findByDepName(emodel.getDepNo())); // depnoを渡してdepnameをset

		//地震の所属部署以外をdeplistにいれる
		deplist = (ArrayList<DepModel>) d_dao.findDepAll(); // 追加

		// セッションに表示内容をセット
		session.setAttribute("Employee", emodel);
		session.setAttribute("DepList", deplist);
		session.setAttribute("PAGENO", pageno);
		session.setAttribute("SELECTPG", nowpage);
		session.setAttribute("pageTitle", "社員検索");


		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		//社員の検索結果をいれる
		ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
		EmployeeModel emodel = (EmployeeModel) session.getAttribute("Employee");

		// 検索結果の情報を削除
		session.removeAttribute("RESULT");

		// 社員名が入力されていた場合取得
		String emp_Name = request.getParameter("employee_name");


		// 社員名が入力されていなかった場合、セッションから社員名情報を取得
		if (emp_Name != null) {
			session.setAttribute("SELECTNAME", emp_Name);
		} else {
			emp_Name = (String) session.getAttribute("SELECTNAME");
		}

		// 部署番号判別
		String dep_No = request.getParameter("dep_no");

		//部署名がNullだった場合、セッションから部署番号を取得
		if(dep_No != null){
			session.setAttribute("SELECTDEP", dep_No);

			//全部署検索だった場合
			if (dep_No.equals("00")) {
			}
		} else {
			dep_No = (String) session.getAttribute("SELECTDEP");
		}


		// ページ番号の選択された番号を取得
		selectno = request.getParameter("pgno"); // ページ選択value

		// ページ番号が選択されていなかった場合初期値をセット
		if (selectno == null) {
			pageno = 1;
			selectno = "1";
		}

		pageno = Integer.parseInt(selectno); // 検索件数分のページ
		nowpage = Integer.parseInt(selectno); // 現在表示しているページ


		// 社員検索呼出し 部署検索OR全社員一覧（00=全部署検索）
		if (dep_No.equals("00")) {
			employeelist = employeeDao.findByAll(emp_Name, pageno);
		} else {
			// 検索結果取得 ログイン番号、入力部署、入力社員名、ページ番号
			employeelist = employeeDao.findByNameDep(emodel.getEmployeeNo(),dep_No, emp_Name, pageno);
		}

		// ページ数取得
		pageno = employeeDao.CountEmp(dep_No, emp_Name); // ページ数取得


		// セッションに表示内容をセット
		session.setAttribute("RESULT", employeelist);
		session.setAttribute("PAGENO", pageno);
		session.setAttribute("SELECTPG", nowpage);


		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeSearch.jsp");
		dispatcher.forward(request, response);
	}

}
