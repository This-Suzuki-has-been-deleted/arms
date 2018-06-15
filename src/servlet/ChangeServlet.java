package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AuthModel;
import model.DepModel;
import model.EmployeeModel;
import others.LoginLogic;
import validation.Validation;
import dao.AuthDAO;
import dao.DepDAO;
import dao.EmployeeDAO;

/**
 * Servlet implementation class ChangeServlet
 */
@WebServlet("/ChangeServlet")
public class ChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		session.removeAttribute("eMsg");
		EmployeeModel employee = (EmployeeModel) session.getAttribute("Employee");
		String employeeNo = request.getParameter("employeeNo");
		if(employeeNo == null){
			employeeNo = employee.getEmployeeNo();
		}
		List<DepModel> depModel = new ArrayList<DepModel>();
		List<AuthModel> authModel = new ArrayList<AuthModel>();
		DepDAO depDao = new DepDAO();
		AuthDAO authDao = new AuthDAO();
		depModel = depDao.findDepAll();
		authModel = authDao.findAuthAll();
		session.setAttribute("DepModel", depModel);
		session.setAttribute("authModel",authModel);
		if(employee.getAuthNo().equals("01") || employee.getEmployeeNo() == employeeNo ){
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
			dispatcher.forward(request, response);
		}else{
			EmployeeModel employeeModel = new EmployeeModel();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/infoChange.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		EmployeeModel employee = (EmployeeModel) session.getAttribute("Employee");
		String employeeNo = request.getParameter("employeeNo");
		if(employeeNo == null){
			employeeNo = employee.getEmployeeNo();
		}
		if(employee.getAuthNo().equals("01") || employee.getEmployeeNo() == employeeNo ){
			String password = request.getParameter("pass");
			String nextPassword = request.getParameter("nextPass");
			String Msg = null;
			String eMsg = null;
			LoginLogic loginlogic = new LoginLogic();
			String passHashCode = loginlogic.passHash(password);
			EmployeeDAO employeeDao = new EmployeeDAO();
			EmployeeModel emp = employeeDao.findEmployee(employeeNo);
			Validation validation = new Validation();
			if(validation.nullCheck(password) || validation.nullCheck(nextPassword)){
				eMsg = "パスワードが未入力です。";
				session.setAttribute("eMsg",eMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);
			}else if(emp.getPassword() == passHashCode){
				eMsg = "パスワードが重複しています。";
				session.setAttribute("eMsg",eMsg);
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);
			}else{
			Msg = "パスワード変更しました。";
			session.setAttribute("Msg",Msg);
			String nextPassHashCode = loginlogic.passHash(nextPassword);
			emp.setPassword(nextPassHashCode);
			employeeDao.updateEmppass(emp);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
			}
		}else{
			String employeeName = null;
			String divisionNo = null;
			String authorityNo = null;
			String pageFlg = "ChangeServlet";
			String msg = null;
			try{
				employeeName = request.getParameter("employeeName");
				divisionNo = request.getParameter("selectDivisionNo");
				authorityNo = request.getParameter("selectAuthorityNo");
			}catch(NullPointerException e){
				msg = "未入力の項目があります。";
			}

			/**
			 * バリデーションチェック挿入予定地
			 */

			employee.setEmployeeNo(employeeNo);
			employee.setEmployeeName(employeeName);
			employee.setAuthNo(authorityNo);
			employee.setDepNo(divisionNo);

			session.setAttribute("pageFlg", pageFlg);
			session.setAttribute("employeeModel", employee);

			if(msg == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/conf.jsp");
				dispatcher.forward(request, response);
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/infoChange.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
