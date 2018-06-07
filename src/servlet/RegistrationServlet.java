package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EmployeeModel;
import others.LoginLogic;

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
		EmployeeModel employeeModel = new EmployeeModel();
		LoginLogic ll = new LoginLogic();

		String textCode;
		String textName;
		String selectDivisionNo;
		String selectAuthorityNo;
		String pageFlg = "rs";

		boolean flg = false;

		String msg= null;
		try{
			msg = null;

			textCode = request.getParameter("textCode");
			textName = request.getParameter("textName");
			selectDivisionNo = request.getParameter("selectDivisionNo");
			selectAuthorityNo = request.getParameter("selectAuthorityNo");

			if(selectDivisionNo == null ){

			}else{
				selectDivisionNo = "01";
				selectAuthorityNo = "001";
			}
			/**
			 * 入力チェック
			 */
			if(flg){
				msg = "入力に誤りがあります。";
				request.setAttribute("msg",msg);
			}

			employeeModel.setEmployeeNo(textCode);
			employeeModel.setEmployeeName(textName);
			employeeModel.setDepNo(selectDivisionNo);
			employeeModel.setAuthNo(selectAuthorityNo);
			employeeModel.setPassword(ll.passHash("pass1234"));
			employeeModel.setDelFlg(1);
		}catch(NullPointerException e){
			msg = msg + "\n入力されていない項目があります。";
		}





		/**
		 * 重複チェック
		 */

		request.setAttribute("employeeModel",employeeModel);
		request.setAttribute("flg",flg);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/conf.jsp");
		dispatcher.forward(request, response);

	}

}
