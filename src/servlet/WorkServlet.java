package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AnnualModel;
import model.EmployeeModel;
import model.MonthlyModel;
import model.WorkTimeModel;
import others.DateMath;
import dao.AnnualDAO;
import dao.MonthlyDAO;
import dao.WorkDAO;

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
		WorkDAO workDao = new WorkDAO();

		AnnualModel annualModel = null;
		MonthlyModel monthlyModel = null;

		ArrayList<WorkTimeModel> workTimeList;

		EmployeeModel myEmp = (EmployeeModel)request.getAttribute("Employee");

		DateMath dateMath = new DateMath();

		String yearBuf = (String)request.getParameter("y_btn");
		String monthBuf = (String)request.getParameter("m_btn");

		int year;
		int month;

		Calendar now = Calendar.getInstance();

		if(yearBuf == null){
			year = now.get(Calendar.YEAR);
		}else{
			year = Integer.parseInt(yearBuf);
		}

		if(monthBuf == null){
			month = now.get(Calendar.MONTH);
		}else{
			month = Integer.parseInt(monthBuf);
		}

		annualModel = annualDao.findAnnualTime(myEmp.getEmployeeNo(),year);

		monthlyModel = monthlyDao.findMonthlyTime(myEmp.getEmployeeNo(),year,month);

		workTimeList = (ArrayList)workDao.d_findByEmployeeNoAndMonth(myEmp.getEmployeeNo(),year,month);

		for(WorkTimeModel wtm :workTimeList){
			long fix = dateMath.fixedTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
			long over = dateMath.overTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
			long leave = wtm.getLeaving().getTime();
			long attend = wtm.getAttendance().getTime();

			int workTime = dateMath.diff(leave, attend);	//勤務時間を算出
			int overTime = dateMath.diff(leave, fix);		//残業時間を算出
			int nightTime = dateMath.diff(leave, over);		//深夜時間を算出
		}

		request.setAttribute("ANNUAL", annualModel);
		request.setAttribute("MOUNTHLY", monthlyModel);
		request.setAttribute("WORKTIME", workTimeList);


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
