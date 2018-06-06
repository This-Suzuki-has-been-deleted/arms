package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import others.LoginLogic;
import validation.Validation;
import dao.EmployeeDAO;

/**
 * Servlet implementation class LoginServlet
 */
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
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher;
		String employeeNo = request.getParameter("");
		String employeePw = request.getParameter("");

		Validation validation = new Validation();

		//if(){}else{};
		LoginLogic loginlogic = new LoginLogic();
		String passHashCode = loginlogic.passHash(employeePw);


		if( loginlogic.login(employeeNo, passHashCode) ){

			EmployeeModel employee = new EmployeeModel();
			EmployeeDAO employeeDAO = new EmployeeDAO();
			employee = employeeDAO.findEmployee(employeeNo);
			session.setAttribute("Employee", employee);
			dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);

		}else{
			String eMsg = "社員番号又はパスワードに誤りがあります。";
			session.setAttribute("eMsg", eMsg);
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}


	}

}
