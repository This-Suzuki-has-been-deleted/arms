package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.EmployeeModel;
import dao.EmployeeDAO;

/**
 * Servlet implementation class EmployeeServlet
 */
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeModel emodel = new  EmployeeModel();
	EmployeeDAO edao = new  EmployeeDAO();
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

		String dep_no = request.getParameter("dep_no");
		String employee_name = request.getParameter("employee_no");

		cnt = edao.CountEmp(emodel.getAuthNo(),emodel.getEmployeeNo());

		edao.findByDepNo(emodel.getEmployeeNo(),dep_no,emodel.getAuthNo());


		edao.findByNameDep(dep_no,employee_name);

		if(emodel.getAuthNo() == "003") {
			if(dep_no == emodel.getDepNo()) {
				edao.findByEmployeeName(employee_name,emodel.getEmployeeNo());
			}

		}
	}

}
