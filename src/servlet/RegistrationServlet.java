package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EmployeeModel;
import others.LoginLogic;
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		EmployeeModel employeeModel = new EmployeeModel();
		EmployeeModel myEmp = (EmployeeModel) request.getAttribute("Employee");

		LoginLogic ll = new LoginLogic();
		EmployeeDAO ed = new EmployeeDAO();

		String textCode;
		String textName;
		String selectDivisionNo;
		String selectAuthorityNo;
		String pageFlg = "rs";
		String msg = null;
		boolean flg = false;

		// 部署 division,dep
		// 権限 authority

		try {
			//入力値受け取り
			textCode = request.getParameter("textCode");
			textName = request.getParameter("textName");
			selectDivisionNo = request.getParameter("selectDivisionNo");
			selectAuthorityNo = request.getParameter("selectAuthorityNo");

			// 入力チェック
			/**
			 * 入力チェック
			 */

			if (flg) {
				msg = "・入力形式に誤りがあります。";
				request.setAttribute("msg", msg);
			}

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

		for (EmployeeModel em : employeelist) {
			if (employeeModel.getEmployeeNo().equals(em.getEmployeeNo())) {
				msg = msg + "<br>・社員番号が重複しています。";
			}
		}

		if(msg == null){
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}

		request.setAttribute("employeeModel", employeeModel);
		request.setAttribute("pageFlg", pageFlg);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/conf.jsp");
		dispatcher.forward(request, response);

	}

}
