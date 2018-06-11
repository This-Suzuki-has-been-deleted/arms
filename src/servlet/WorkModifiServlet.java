package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnnualModel;
import model.MonthlyModel;
import model.WorkTimeModel;
import validation.Validation;
import dao.AnnualDAO;
import dao.MonthlyDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class WorkModifiServlet
 */
public class WorkModifiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkModifiServlet() {
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
		WorkTimeModel workTimeModel = (WorkTimeModel) session.getAttribute("WorkTimeModel");
		Validation validation = new Validation();
	/**
	 * if(validation.(workTimeModel)){
	 * String eMsg = "入力誤り有";
	 * session.setAttribute("eMsg",eMsg);
	 * RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/workTimeChange.jsp");
	 * dispatcher.forword(request,response);
	 * }
	 */
		WorkDAO workDao = new WorkDAO();
		WorkTimeModel workTime = new WorkTimeModel();
		workTime = workDao.findWorkTime(workTimeModel.getEmployeeNo(), workTimeModel.getYear(), workTimeModel.getMonth(), workTimeModel.getDay());

		workDao.updateWorkTime(workTimeModel);

		AnnualDAO annualDao = new AnnualDAO();
		AnnualModel annual = new AnnualModel();
		annual = annualDao.findAnnualTime(workTimeModel.getEmployeeNo(), workTimeModel.getYear());

		MonthlyDAO monthlyDao = new MonthlyDAO();
		MonthlyModel monthly = new MonthlyModel();
		monthly = monthlyDao.findMonthlyTime(workTimeModel.getEmployeeNo(), workTimeModel.getYear(),workTimeModel.getMonth());

		
	}

}
