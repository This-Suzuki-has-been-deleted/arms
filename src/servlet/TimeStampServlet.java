package servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.MonthlyModel;
import model.WorkTimeModel;
import others.DateMath;
import dao.MonthlyDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class TimeStampServlet
 */
@WebServlet("/TimeStampServlet")
public class TimeStampServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TimeStampServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String value = (String) session.getAttribute("buttonvalue");
		WorkTimeModel wt = (WorkTimeModel) session.getAttribute("work");
		WorkDAO wdao = new WorkDAO();
		MonthlyDAO mdao = new MonthlyDAO();
		DateMath dm = new DateMath();

		if (value.equals("出勤")) {
			LocalDateTime date = LocalDateTime.now();
			String temp = date.toString();
			Date now = Date.valueOf(temp);
			wt.setAttendance(now);
			wdao.updateWorkTime(wt);
		} else {
			LocalDateTime date = LocalDateTime.now();
			String temp = date.toString();
			Date now = Date.valueOf(temp);

			wt.setLeaving(now);
			wt.setWorkFlg(true);
			wdao.updateWorkTime(wt); // 日次をアップデート

			// 労働時間の算出
			long attendance = wt.getAttendance().getTime();
			long leaving = wt.getLeaving().getTime();

			MonthlyModel mm = mdao.findMonthlyTime(wt.getEmployeeNo(),
					wt.getYear(), wt.getMonth());
			Date workTime = mm.getM_workTime();
			Date overTime = mm.getM_overTime();
			Date nightTime = mm.getM_nightTime();

			// 勤務時間算出
			int diff = dm.diff(leaving, attendance);
			mm.setM_workTime(dm.addMinute(workTime, diff));
			// 残業時間算出
			diff = dm.diff(leaving,
					dm.fixedTime(wt.getYear(), wt.getMonth(), wt.getDay()));
			mm.setM_overTime(dm.addMinute(overTime, diff));
			// 深夜時間算出
			diff = dm.diff(leaving,
					dm.overTime(wt.getYear(), wt.getMonth(), wt.getDay()));
			mm.setM_nightTime(dm.addMinute(nightTime, diff));
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

}