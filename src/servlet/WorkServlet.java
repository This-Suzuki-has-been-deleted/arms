package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnnualModel;
import model.EmployeeModel;
import dao.AnnualDAO;
import dao.MonthlyDAO;

/**
 * Servlet implementation class WorkServlet
 */
public class WorkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //ANNUAL
    //MONTHLY


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AnnualDAO annualDao = new AnnualDAO();
		MonthlyDAO monthlyDao = new MonthlyDAO();
		EmployeeModel myEmp = (EmployeeModel)request.getAttribute("Employee");
		ArrayList<AnnualModel> annualList = (ArrayList<AnnualModel>) annualDao.m_findByEmployeeNo(myEmp.getEmployeeNo());





		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/workTimeList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
