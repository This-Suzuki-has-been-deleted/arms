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
import others.SpaceKill;
import validation.Validation;
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

		session.removeAttribute("eMsg");

		// 部署のプルダウンメニューのために一覧を持ってくる
		ArrayList<DepModel> depList = (ArrayList<DepModel>) dd.findDepAll();

		// セッションにセット
		session.setAttribute("depList", depList);
		session.setAttribute("pageTitle", "社員登録");

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

		// 宣言
		EmployeeModel employeeModel = new EmployeeModel();
		EmployeeModel employee = null;
		// EmployeeModel myEmp = (EmployeeModel)
		// request.getAttribute("Employee");

		LoginLogic ll = new LoginLogic();
		EmployeeDAO ed = new EmployeeDAO();

		Validation validation = new Validation();

		SpaceKill spaceKill = new SpaceKill();

		String textCode; // 入力内容を受け取る変数
		String textName; // 入力内容を受け取る変数
		String selectDivisionNo;
		String selectAuthorityNo;
		String pageFlg;
		String msg;

		pageFlg = null;
		msg = null;

		HttpSession session = request.getSession();

		// // ログインチェック
		// if (myEmp == null) {
		// RequestDispatcher dispatcher = request
		// .getRequestDispatcher("/WEB-INF/jsp/login.jsp");
		// dispatcher.forward(request, response);
		// }

		// 部署 division,dep
		// 権限 authority

		// 入力値受け取り

		textCode = request.getParameter("textCode");
		textName = request.getParameter("textName");
		selectDivisionNo = request.getParameter("selectDivisionNo");
		selectAuthorityNo = request.getParameter("selectAuthorityNo");

		textCode = spaceKill.stringSpaceKill(textCode);
		textName = spaceKill.stringSpaceKill(textName);

		// モデルに入力された内容と初期値をセット
		employeeModel.setEmployeeNo(textCode);
		employeeModel.setEmployeeName(textName);
		employeeModel.setDepNo(selectDivisionNo);
		employeeModel.setAuthNo(selectAuthorityNo);
		employeeModel.setPassword(ll.passHash("pass1234"));
		employeeModel.setDelFlg(1);

		// エラーチェック
		if (validation.nullCheck(textCode)) { // 社員番号は入力されているかチェック
			if (validation.employeeCodeValidation(textCode)) { // 社員番号が入力されていてかつ入力形式が正しいかチェック
				employee = ed.findEmployee(textCode);
				if (employee.getEmployeeNo() != null) { // 社員番号が既に存在しているかチェック
					msg = "・社員番号が重複しています。";
					if (!(validation.nullCheck(textName))) { // 社員名が入力されているかチェック
						msg = msg + "・未入力項目があります。";
					}
				} else { // 社員番号が存在していなかった場合
					if (!(validation.nullCheck(textName))) { // 社員名が入力されているかチェック
						msg = "・未入力項目があります。";
					}
				}
			} else { // 社員番号の入力形式が正しくなかった場合
				msg = "・入力形式に誤りがあります。";
				if (!(validation.nullCheck(textName))) { // 社員名が入力されているかチェック
					msg = msg + "\n・未入力項目があります。";
				}
			}
		} else { // 社員番号が入力されていなかった場合
			msg = "・未入力項目があります。";
		}

		// 入力内容に不正なものがあった場合エラーメッセージをセッションにセットして登録画面へ遷移
		if (msg != null) {
			session.setAttribute("eMsg", msg);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsp/employeeRegistration.jsp");
			dispatcher.forward(request, response);
		}

		employeeModel.setDepName(ed.findByDepName(employeeModel.getDepNo()));
		employeeModel.setAuthName(ed.findByAuthName(employeeModel.getAuthNo()));

		session.setAttribute("employeeModel", employeeModel);

		pageFlg = "RegistrationServlet";
		session.setAttribute("pageFlg", pageFlg);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/conf.jsp");
		dispatcher.forward(request, response);
	}
}
