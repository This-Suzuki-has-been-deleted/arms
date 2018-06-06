package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EmployeeModel;

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

		String textCode;
		String textName;
		String selectDivisionNo;
		String selectAuthorityNo;

		textCode = request.getParameter("textCode");
		textName = request.getParameter("textName");
		if(true){
			selectDivisionNo = request.getParameter("selectDivisionNo");
			selectAuthorityNo = request.getParameter("selectAuthorityNo");
		}

		/**
		 * 入力チェック
		 */

		employeeModel.setEmployeeNo(textCode);
		employeeModel.setEmployeeName(textName);
		employeeModel.setDepNo(Integer.parseInt(selectDivisionNo));
		employeeModel.setAuthNo(Integer.parseInt(selectAuthorityNo));



	}

}
