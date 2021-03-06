package servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import model.WorkTimeModel;
import others.LoginLogic;
import validation.Validation;
import dao.EmployeeDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		HttpSession session = request.getSession();
		session.removeAttribute("eMsg");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		String employeeNo = request.getParameter("syainNo");
		String employeePw = request.getParameter("password");

		Validation validation = new Validation();

		if (!validation.nullCheck(employeeNo)
				&& !validation.nullCheck(employeePw)) {
			String eMsg = "社員番号又はパスワードに誤りがあります。";
			session.setAttribute("eMsg", eMsg);
			dispatcher = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
		LoginLogic loginlogic = new LoginLogic();
		String passHashCode = loginlogic.passHash(employeePw);

		if (loginlogic.login(employeeNo, passHashCode)) {
				EmployeeModel employee = new EmployeeModel();
				EmployeeDAO employeeDAO = new EmployeeDAO();
				employee = employeeDAO.findEmployee(employeeNo);
				session.setAttribute("Employee", employee);
			// ここから鈴木追加分
			LocalDateTime date = LocalDateTime.now();
			int year = date.getYear();
			int month = date.getMonthValue();
			int day = date.getDayOfMonth() - 1;

			EmployeeModel em = employee;
			WorkDAO wdao = new WorkDAO();
			WorkTimeModel wm = wdao.findWorkTime(em.getEmployeeNo(), year,month, day);

			if (wm.getEmployeeNo() != null) { // 前日のレコードの有り
				if (wm.isWorkTimeFlg() == 1) { // 勤怠フラグを確認、本日のレコードの有無を確認
					wm = wdao.findWorkTime(em.getEmployeeNo(), year, month,day + 1);
					if (wm.getEmployeeNo() == null) {
						session.setAttribute("buttonvalue", "出勤"); // ボタンのバリューを出勤に
					} else {
						if(wm.isWorkTimeFlg()==1){
							session.setAttribute("buttonvalue", "本日打刻済"); // ボタンのバリューを打刻済に
						}else{
							session.setAttribute("work", wm); // 当日を参照する
							session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に
							session.setAttribute("record", "0");
						}
					}
				} else {
					session.setAttribute("work", wm); // 昨日を参照する
					session.setAttribute("record", "1");
					session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に
				}
			} else {	//前日レコード無し
				wm = wdao.findWorkTime(em.getEmployeeNo(), year, month, day + 1);
				session.setAttribute("record", "0");

				if (wm.getEmployeeNo() == null) { // 本日のレコードの有無を確認
					session.setAttribute("buttonvalue", "出勤"); // ボタンのバリューを出勤に
				} else {
					if(wm.isWorkTimeFlg()==1){
						session.setAttribute("buttonvalue", "本日打刻済"); // ボタンのバリューを打刻済に
					}else{
						session.setAttribute("work", wm); // 当日を参照する
						session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に
						session.setAttribute("record", "0");
					}
				}
			}
			//ここまで
			if(employeePw.equals("pass1234")){
				session.removeAttribute("eMsg");
				session.setAttribute("pageTitle", "初期パスワード変更");
				dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);
			}else{
				session.removeAttribute("Msg");
				session.removeAttribute("eMsg");
				session.setAttribute("pageTitle", "メインメニュー");
				dispatcher = request.getRequestDispatcher("index.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			String eMsg = "社員番号又はパスワードに誤りがあります。";
			session.setAttribute("eMsg", eMsg);
			dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}

	}

}
