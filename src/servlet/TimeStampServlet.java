package servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Calendar;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import model.MonthlyModel;
import model.WorkTimeModel;
import dao.MonthlyDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class TimeStampServlet
 */
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		LocalDateTime date = LocalDateTime.now();
		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth()-1;

		EmployeeModel em = (EmployeeModel)session.getAttribute("employee");
		WorkDAO wdao = new WorkDAO();
		try {
			WorkTimeModel wm=wdao.findWorkTime(em.getEmployeeNo(), year, month, day);

			if(wm != null){														//前日のレコードの有無を確認
				if(wm.isWorkFlg()){												//勤怠フラグを確認、本日のレコードの有無を確認
					wm=wdao.findWorkTime(em.getEmployeeNo(), year, month, day+1);
					if(wm!=null){
						wm.setEmployeeNo(em.getEmployeeNo());
						wm.setYear(year);
						wm.setMonth(month);
						wm.setDay(day);
						wdao.insertWorkTime(wm);								//レコード作成
						session.setAttribute("work",wm);						//当日を参照する
						session.setAttribute("buttonvalue", "出勤");			//ボタンのバリューを出勤に
					}else{
						session.setAttribute("work",wm);							//当日を参照する
						session.setAttribute("buttonvalue", "退勤");				//ボタンのバリューを退勤に
					}
				}else{
					session.setAttribute("work",wm);							//昨日を参照する
					session.setAttribute("buttonvalue", "退勤");				//ボタンのバリューを退勤に
				}
			}else{
				wm=wdao.findWorkTime(em.getEmployeeNo(), year, month, day+1);
				if(wm==null){													//本日のレコードの有無を確認
					wm.setEmployeeNo(em.getEmployeeNo());
					wm.setYear(year);
					wm.setMonth(month);
					wm.setDay(day);
					wdao.insertWorkTime(wm);									//レコード作成
					session.setAttribute("work",wm);							//当日を参照する
					session.setAttribute("buttonvalue", "出勤");				//ボタンのバリューを出勤に
				}else{
					session.setAttribute("work",wm);							//当日を参照する
					session.setAttribute("buttonvalue", "退勤");				//ボタンのバリューを退勤に
				}

			}

		} catch (InstantiationException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String value = (String)session.getAttribute("buttonvalue");
		WorkTimeModel wt = (WorkTimeModel)session.getAttribute("work");
		WorkDAO wdao = new WorkDAO();
		MonthlyDAO mdao = new MonthlyDAO();

		if(value.equals("出勤")){
			LocalDateTime date = LocalDateTime.now();
			String temp = date.toString();
			Date now = Date.valueOf(temp);
			wt.setAttendance(now);
			try {
				wdao.updateWorkTime(wt);
			} catch (InstantiationException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			LocalDateTime date = LocalDateTime.now();
			String temp = date.toString();
			Date now = Date.valueOf(temp);
			Calendar cal = Calendar.getInstance();

			wt.setLeaving(now);
			wt.setWorkFlg(true);
			try {
				wdao.updateWorkTime(wt);							//日次をアップデート

				//労働時間の算出
				long attendance = wt.getAttendance().getTime();
				long leaving = wt.getLeaving().getTime();

				long diff = (leaving - attendance) / (1000 * 60);	//差分を分で取る
				MonthlyModel mm = mdao.findMonthlyTime(wt.getEmployeeNo(), wt.getYear(), wt.getMonth());
				Date workTime = mm.getM_workTime();
				Date overTime = mm.getM_overTime();
				Date nightTime = mm.getM_nightTime();

			} catch (InstantiationException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
	}

}
