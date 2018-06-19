package servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnnualModel;
import model.MonthlyModel;
import model.WorkTimeModel;
import others.DateMath;
import validation.Validation;
import dao.AnnualDAO;
import dao.MonthlyDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class WorkModifiServlet
 */
@WebServlet("/WorkModifiServlet")
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
		DateMath dateMath = new DateMath();
		WorkTimeModel workTimeModel = (WorkTimeModel) session.getAttribute("workTimeModel");
		Timestamp attendanceTime = null;
		Timestamp leavingTime = null;
		Date dates =  new Date();
		Date dates2 =  new Date();
		request.setCharacterEncoding("utf-8");
		String attendance = request.getParameter("attendance");
		String leaving = request.getParameter("leaving");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//体裁を整える
			attendance= dateMath.replaceDate(attendance);
			leaving = dateMath.replaceDate(leaving);

			//String→Dateの変換
			dates = sdf.parse(attendance);
			dates2 = sdf.parse(leaving);

			//Date→TimeStampの変換
			attendanceTime = new Timestamp(dates.getTime());
			leavingTime = new Timestamp(dates2.getTime());
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println(attendanceTime);
		
		workTimeModel.setAttendance(attendanceTime);
		workTimeModel.setLeaving(leavingTime);
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

		//入力内容加工
		long fix = dateMath.fixedTime(workTime .getYear(), workTime .getMonth(),workTime .getDay());
		long over = dateMath.overTime(workTime .getYear(),workTime .getMonth(),workTime .getDay());
		long leave = workTime .getLeaving().getTime();
		long attend = workTime .getAttendance().getTime();

		int workTimes = dateMath.diff(leave, attend);	//勤務時間を算出
		int overTimes = dateMath.diff(leave, fix);		//残業時間を算出
		int nightTimes = dateMath.diff(leave, over);		//深夜時間を算出
		//減算処理
		//年
		annual.setY_workTime(dateMath.addMinute(annual.getY_workTime(), -workTimes));
		annual.setY_overTime(dateMath.addMinute(annual.getY_overTime(), -overTimes));
		annual.setY_nightTime(dateMath.addMinute(annual.getY_nightTime(), -nightTimes));
		//月
		monthly.setM_workTime(dateMath.addMinute(annual.getY_workTime(), -workTimes));
		monthly.setM_overTime(dateMath.addMinute(annual.getY_overTime(), -overTimes));
		monthly.setM_nightTime(dateMath.addMinute(annual.getY_nightTime(), -nightTimes));

		//入力内容加工
		fix = dateMath.fixedTime(workTimeModel.getYear(), workTimeModel.getMonth(),workTimeModel.getDay());
		over = dateMath.overTime(workTimeModel.getYear(),workTimeModel.getMonth(),workTimeModel.getDay());
		leave = workTimeModel.getLeaving().getTime();
		attend = workTimeModel.getAttendance().getTime();


		workTimes = dateMath.diff(leave, attend);	//勤務時間を算出
		overTimes = dateMath.diff(leave, fix);		//残業時間を算出
		nightTimes = dateMath.diff(leave, over);		//深夜時間を算出

		//加算処理
				//年
				annual.setY_workTime(dateMath.addMinute(annual.getY_workTime(), workTimes));
				annual.setY_overTime(dateMath.addMinute(annual.getY_overTime(), overTimes));
				annual.setY_nightTime(dateMath.addMinute(annual.getY_nightTime(), nightTimes));
				//
				monthly.setM_workTime(dateMath.addMinute(annual.getY_workTime(), workTimes));
				monthly.setM_overTime(dateMath.addMinute(annual.getY_overTime(), overTimes));
				monthly.setM_nightTime(dateMath.addMinute(annual.getY_nightTime(),nightTimes));

				annualDao.updateMonthlyTime(annual);
				monthlyDao.updateMonthlyTime(monthly);

		session.setAttribute("Msg", "変更完了です。");
		response.sendRedirect("WorkServlet");

	}

}
