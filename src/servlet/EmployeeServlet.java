package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeModel emodel = new  EmployeeModel();
	EmployeeDAO edao = new  EmployeeDAO();
	ArrayList<EmployeeModel> employeelist = new ArrayList<EmployeeModel>();
	int cnt = 0;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
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
		HttpSession session = request.getSession();

		int pageno = 1;
		String employee_no = emodel.getEmployeeNo();
		String dep_no = request.getParameter("dep_no");
		String employee_name = request.getParameter("employee_no");

		cnt = edao.CountEmp(emodel.getAuthNo(),emodel.getEmployeeNo());
		employeelist = edao.findByNameDep(employee_no,dep_no,employee_name,pageno,cnt);

		session.setAttribute("Emp",employeelist);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/employeeSearch");
		dispatcher.forward(request, response);

	}

}
