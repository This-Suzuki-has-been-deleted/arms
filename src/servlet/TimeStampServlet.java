package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
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
		AnnualDAO adao = new AnnualDAO();
		DateMath dm = new DateMath();

		if (value.equals("出勤")) {
			java.util.Date date = new java.util.Date();
			long nowTime = date.getTime();
			Timestamp now = new Timestamp(nowTime);
			wt.setAttendance(now);
			wdao.updateWorkTime(wt);
			session.setAttribute("buttonvalue", "退勤");
			session.setAttribute("index_date",date);
		} else {
			java.util.Date date = new java.util.Date();
			long nowTime = date.getTime();
			Timestamp now = new Timestamp(nowTime);

			wt.setLeaving(now);
			wt.setWorkFlg(1);
			wt.setWorkTimeFlg(1);
			wdao.updateWorkTime(wt); // 日次をアップデート
			session.setAttribute("buttonvalue", "出勤");

			// 労働時間の算出
			long attendance = wt.getAttendance().getTime();
			long leaving = wt.getLeaving().getTime();

			MonthlyModel mm = mdao.findMonthlyTime(wt.getEmployeeNo(),wt.getYear(), wt.getMonth());
			AnnualModel am = adao.findAnnualTime(wt.getEmployeeNo(), wt.getYear());

			System.out.println(mm.getEmployeeNo()+" "+am.getEmployeeNo());

			//月次データがなかった場合の処理
			if(mm.getEmployeeNo() ==null){
				mm.setEmployeeNo(wt.getEmployeeNo());
				mm.setYear(wt.getYear());
				mm.setMonth(wt.getMonth());
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date dates = sdf.parse("2000-01-01 00:00:00");

					Timestamp work = new Timestamp(dates.getTime());
					Timestamp over = new Timestamp(dates.getTime());
					Timestamp night = new Timestamp(dates.getTime());
					mm.setM_workTime(work);
					mm.setM_overTime(over);
					mm.setM_nightTime(night);

					mdao.insertMonthlyTime(mm);
				} catch (ParseException | InstantiationException | IllegalAccessException | ClassNotFoundException | NamingException | SQLException e) {
					e.printStackTrace();
				}

			}

			//年次データがなかった場合の処理
			if(am.getEmployeeNo() == null){
				am.setEmployeeNo(wt.getEmployeeNo());
				am.setYear(wt.getYear());
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Date dates = sdf.parse("2000-01-01 00:00:00");

					Timestamp work = new Timestamp(dates.getTime());
					Timestamp over = new Timestamp(dates.getTime());
					Timestamp night = new Timestamp(dates.getTime());
					am.setY_workTime(work);
					am.setY_overTime(over);
					am.setY_nightTime(night);

					adao.insertMonthlyTime(am);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}


			Date workTime = mm.getM_workTime();
			Date overTime = mm.getM_overTime();
			Date nightTime = mm.getM_nightTime();

			// 勤務時間算出
			int diff = dm.diff(leaving, attendance);
			mm.setM_workTime(dm.addMinute(workTime, diff));
			am.setY_workTime(dm.addMinute(workTime, diff));
			// 残業時間算出
			diff = dm.diff(leaving,dm.fixedTime(wt.getYear(), wt.getMonth(), wt.getDay()));
			mm.setM_overTime(dm.addMinute(overTime, diff));
			am.setY_overTime(dm.addMinute(overTime, diff));
			// 深夜時間算出
			diff = dm.diff(leaving,dm.overTime(wt.getYear(), wt.getMonth(), wt.getDay()));
			mm.setM_nightTime(dm.addMinute(nightTime, diff));
			am.setY_nightTime(dm.addMinute(nightTime, diff));

			mdao.updateMonthlyTime(mm);
			adao.updateMonthlyTime(am);
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

}