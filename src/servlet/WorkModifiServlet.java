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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//
		HttpSession session = request.getSession();
		DateMath dateMath = new DateMath();
		WorkTimeModel workTimeModel = (WorkTimeModel) session.getAttribute("workTimeModel");
		Timestamp attendanceTime = null;
		Timestamp leavingTime = null;
		Date dates = new Date();
		Date dates2 = new Date();
		request.setCharacterEncoding("utf-8");
		String attendance = request.getParameter("attendance");
		String leaving = request.getParameter("leaving");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 体裁を整える
			attendance = dateMath.replaceDate(attendance);
			leaving = dateMath.replaceDate(leaving);

			// String→Dateの変換
			dates = sdf.parse(attendance);
			dates2 = sdf.parse(leaving);

			// Date→TimeStampの変換
			attendanceTime = new Timestamp(dates.getTime());
			leavingTime = new Timestamp(dates2.getTime());
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		System.out.println(attendanceTime);

		workTimeModel.setAttendance(attendanceTime);
		workTimeModel.setLeaving(leavingTime);

		WorkDAO workDao = new WorkDAO();
		WorkTimeModel workTime = new WorkTimeModel();
		workTime = workDao.findWorkTime(workTimeModel.getEmployeeNo(),
				workTimeModel.getYear(), workTimeModel.getMonth(),
				workTimeModel.getDay());

		workDao.updateWorkTime(workTimeModel);

		AnnualDAO annualDao = new AnnualDAO();
		AnnualModel annual = new AnnualModel();
		annual = annualDao.findAnnualTime(workTimeModel.getEmployeeNo(),
				workTimeModel.getYear());

		MonthlyDAO monthlyDao = new MonthlyDAO();
		MonthlyModel monthly = new MonthlyModel();
		monthly = monthlyDao.findMonthlyTime(workTimeModel.getEmployeeNo(),
				workTimeModel.getYear(), workTimeModel.getMonth());

		// 入力内容加工
		long fix = dateMath.fixedTime(workTime.getYear(), workTime.getMonth(),workTime.getDay());
		long over = dateMath.overTime(workTime.getYear(), workTime.getMonth(),workTime.getDay());
		long leave = workTime.getLeaving().getTime();
		long attend = workTime.getAttendance().getTime();
		long nextLeave =leavingTime.getTime();
		long nextAttend =attendanceTime.getTime();

		int workTimes =dateMath.diff(nextLeave, nextAttend) - dateMath.diff(leave, attend); // 勤務時間を算出
		int overTimes =dateMath.diff(nextLeave, fix) - dateMath.diff(leave, fix); // 残業時間を算出
		int nightTimes =dateMath.diff(nextLeave, over) - dateMath.diff(leave, over); // 深夜時間を算出

		System.out.println(workTimes +"  "+overTimes+"  "+nightTimes);
		System.out.println(annual.getY_workTime()+"  "+annual.getY_overTime()+"  "+annual.getY_nightTime());
		System.out.println(monthly.getM_workTime()+"  "+monthly.getM_overTime()+"  "+monthly.getM_nightTime());
		// 年
		if (workTimes != 0) {
			annual.setY_workTime(dateMath.addMinute(annual.getY_workTime(),workTimes));
			monthly.setM_workTime(dateMath.addMinute(monthly.getM_workTime(),workTimes));
		}
		if (overTimes != 0) {
			annual.setY_overTime(dateMath.addMinute(annual.getY_overTime(),overTimes));
			monthly.setM_overTime(dateMath.addMinute(monthly.getM_overTime(),overTimes));
		}
		if (nightTimes != 0) {
			annual.setY_nightTime(dateMath.addMinute(annual.getY_nightTime(),nightTimes));
			monthly.setM_nightTime(dateMath.addMinute(monthly.getM_nightTime(),nightTimes));
		}

		System.out.println(annual.getY_workTime()+"  "+annual.getY_overTime()+"  "+annual.getY_nightTime());
		System.out.println(monthly.getM_workTime()+"  "+monthly.getM_overTime()+"  "+monthly.getM_nightTime());

		annualDao.updateAnnualTime(annual);
		monthlyDao.updateMonthlyTime(monthly);

		session.setAttribute("Msg", "変更完了です。");
		response.sendRedirect("WorkServlet");

	}

}
