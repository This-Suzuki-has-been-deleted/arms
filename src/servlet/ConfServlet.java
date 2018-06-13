package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import dao.EmployeeDAO;

/**
 * Servlet implementation class ConfServlet
 */
@WebServlet("/ConfServlet")
public class ConfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfServlet() {
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
		String pageFlg = (String) session.getAttribute("pageFlg");
		EmployeeModel empModel;
		EmployeeDAO employeeDao;
		if(pageFlg.equals("RegistrationServlet")){
			 empModel = (EmployeeModel) session.getAttribute("EmployeeModel");
			employeeDao = new EmployeeDAO();
			employeeDao.InsertEmployee(empModel);

		}else if(pageFlg.equals("ChangeServlet")){
			empModel = (EmployeeModel) session.getAttribute("EmployeeModel");
			employeeDao = new EmployeeDAO();
			employeeDao.updateEmployee(empModel);
		}
		String Msg = null;
		session.setAttribute(Msg, "完了です！");
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);

	}

}
