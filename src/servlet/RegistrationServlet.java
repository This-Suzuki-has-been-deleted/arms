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
import others.LoginLogic;
import dao.DepDAO;
import dao.EmployeeDAO;

/**
 * Servlet implementation class RegistrationServlet
 */

@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrationServlet() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DepDAO dd = new DepDAO();

		HttpSession session = request.getSession();

//		// ログイン中のユーザーの情報をセッションから得る
//		EmployeeModel myEmp = (EmployeeModel) request.getAttribute("Employee");
//
//		// ログインチェック
//		if (myEmp == null) {
//			RequestDispatcher dispatcher = request
//					.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//			dispatcher.forward(request, response);
//		}

		// 部署のプルダウンメニューのために一覧を持ってくる
		ArrayList<DepModel> depList = (ArrayList<DepModel>) dd.findDepAll();

		// セッションにセット
		session.setAttribute("depList", depList);
		if(depList == null){
			System.out.println("depはnullだよ");
		}else{
			System.out.println("depはあるみたい");
		}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/employeeRegistration.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//宣言
		EmployeeModel employeeModel = new EmployeeModel();
//		EmployeeModel myEmp = (EmployeeModel) request.getAttribute("Employee");

		LoginLogic ll = new LoginLogic();
		EmployeeDAO ed = new EmployeeDAO();

		String textCode;	//入力内容を受け取る変数
		String textName;	//入力内容を受け取る変数
		String selectDivisionNo;
		String selectAuthorityNo;
		String pageFlg = "RegistrationServlet";
		String msg = null;
		boolean flg = false;

		HttpSession session = request.getSession();

//		// ログインチェック
//		if (myEmp == null) {
//			RequestDispatcher dispatcher = request
//					.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//			dispatcher.forward(request, response);
//		}

		// 部署 division,dep
		// 権限 authority

		try {
			// 入力値受け取り
			textCode = request.getParameter("textCode");
			textName = request.getParameter("textName");
			selectDivisionNo = request.getParameter("selectDivisionNo");
			selectAuthorityNo = request.getParameter("selectAuthorityNo");

			// 入力チェック
			/**
			 * 入力チェック
			 */

			//入力チェック
			if (flg) {
				msg = "・入力形式に誤りがあります。";
			}

			//モデルに入力された内容と初期値をセット
			employeeModel.setEmployeeNo(textCode);
			employeeModel.setEmployeeName(textName);
			employeeModel.setDepNo(selectDivisionNo);
			employeeModel.setAuthNo(selectAuthorityNo);
			employeeModel.setPassword(ll.passHash("pass1234"));
			employeeModel.setDelFlg(1);
		} catch (NullPointerException e) {
			msg = msg + "<br>・未入力項目があります。";
		}

		// 重複チェック
		ArrayList<EmployeeModel> employeelist = ed.findAll();

		//社員番号の重複チェック
		for (EmployeeModel em : employeelist) {
			if (employeeModel.getEmployeeNo().equals(em.getEmployeeNo())) {
				msg = msg + "<br>・社員番号が重複しています。";
			}
		}

		//入力内容に不正なものがなかったか
		if (msg != null) {
			session.setAttribute("msg", msg);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}

		session.setAttribute("employeeModel", employeeModel);
		session.setAttribute("pageFlg", pageFlg);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/conf.jsp");
		dispatcher.forward(request, response);
	}
}
