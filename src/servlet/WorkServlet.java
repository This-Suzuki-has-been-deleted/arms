package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AnnualModel;
import model.EmployeeModel;
import model.MonthlyModel;
import model.WorkTimeDateModel;
import model.WorkTimeModel;
import others.DateMath;
import dao.AnnualDAO;
import dao.MonthlyDAO;
import dao.WorkDAO;

/**
 * Servlet implementation class WorkServlet
 */
@WebServlet("/WorkServlet")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// ANNUAL
	// MONTHLY

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.removeAttribute("eMsg");

		//ログイン中のユーザーの情報をセッションから得る
		EmployeeModel myEmp = (EmployeeModel)session.getAttribute("Employee");

//		//ログインチェック
//		if(myEmp == null){
//			RequestDispatcher dispatcher = request
//					.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
//			dispatcher.forward(request, response);
//		}

		//宣言
		AnnualDAO annualDao = new AnnualDAO();
		MonthlyDAO monthlyDao = new MonthlyDAO();
		WorkDAO workDao = new WorkDAO();

		AnnualModel annualModel = null;
		MonthlyModel monthlyModel = null;

		ArrayList<WorkTimeModel> workTimeList;

		DateMath dateMath = new DateMath();

		//年月をセッションから取得
		String yearBuf = request.getParameter("y_btn");
		String monthBuf = request.getParameter("m_btn");

		Calendar now = Calendar.getInstance();
		Calendar first = Calendar.getInstance();

		String[] week = {"日曜日","月曜日","火曜日","水曜日","木曜日","金曜日","土曜日"};

		int year;
		int month;
		String eMsg = null;

		first.set( Calendar.DATE,1);
		int firstWeek = (first.get( Calendar.DAY_OF_WEEK )-1);
		int maxDayCnt = now.getActualMaximum(Calendar.DAY_OF_MONTH);

		//セッションに曜日の情報が入っている配列をセット
		session.setAttribute("week",week);
		//セッションに月初めの曜日情報を1～7の数値でセット
		session.setAttribute("firstWeek",firstWeek);
		//セッションに月末の日にちをセット
		session.setAttribute("maxDayCnt",maxDayCnt);


		//yearとmonthの初期値を設定
		if(yearBuf == null){
			year = now.get(Calendar.YEAR);//セッションに年が入っていなかった場合は今日の年を取得
			session.setAttribute("now_year", year);
		}else if(Integer.parseInt(yearBuf) == 1){
			year = (int) session.getAttribute("now_year")+1;	//セッションに年が入っていた場合はその年を取得
		}else{
			year = (int) session.getAttribute("now_year")-1;
		}


		if(monthBuf == null){
			month = now.get(Calendar.MONTH)+1;	//セッションに月が入っていなかった場合は今日の月を取得
			session.setAttribute("now_month", month);
		}else if(Integer.parseInt(monthBuf) == 1){
			month =(int) session.getAttribute("now_month")+1;	//セッションに月が入っていた場合はその月を取得
		}else{
			month =(int) session.getAttribute("now_month")-1;
		}


		int now_year = (int) session.getAttribute("now_year");
		int now_month = (int) session.getAttribute("now_month");
		session.setAttribute("now_year", year);
		session.setAttribute("now_month", month);
		int i=0;

		annualModel = annualDao.findAnnualTime(myEmp.getEmployeeNo(),year);
		//データベースからモデルに追加

		if(annualModel.getEmployeeNo() == null){
			eMsg = year+"年の勤務表はありません。";
			session.removeAttribute("ANNUAL");
		}else{
			session.setAttribute("ANNUAL", annualModel);
		}

		monthlyModel = monthlyDao.findMonthlyTime(myEmp.getEmployeeNo(),year,month);
		if(monthlyModel.getEmployeeNo() == null){
			if(eMsg == null){
				eMsg = month+"月の勤務表はありません。";
			}

			session.removeAttribute("MOUNTHLY");
		}else{
			session.setAttribute("MOUNTHLY", monthlyModel);
		}
		if(eMsg== null){
			workTimeList = (ArrayList<WorkTimeModel>)workDao.d_findByEmployeeNoAndMonth(myEmp.getEmployeeNo(),year,month);

			//モデルに必要な情報を他メソッドを使い追加
			for(WorkTimeModel wtm :workTimeList){
				long fix = dateMath.fixedTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
				long over = dateMath.overTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
				long leave = wtm.getLeaving().getTime();
			long attend = wtm.getAttendance().getTime();

			int workTime = dateMath.diff(leave, attend);	//勤務時間を算出
			int overTime = dateMath.diff(leave,fix);		//残業時間を算出
			int nightTime = dateMath.diff(leave, over);		//深夜時間を算出

			wtm.setWorkTimeH((int)workTime / 60);
			wtm.setWorkTimeM((int)workTime % 60);
			wtm.setOverTimeH((int)overTime / 60);
			wtm.setOverTimeM((int)overTime % 60);
			wtm.setNightTimeH((int)nightTime / 60);
			wtm.setNightTimeM((int)nightTime % 60);
		}
		//年間と月間の勤務時間を変換
		long epic = 946652400000L;
		WorkTimeDateModel workTimeDateModel = new WorkTimeDateModel();
		workTimeDateModel.setM_workTime(new Date(monthlyModel.getM_workTime().getTime()-epic).getTime()/60000);
		workTimeDateModel.setM_overTime(new Date(monthlyModel.getM_overTime().getTime()-epic).getTime()/60000);
		workTimeDateModel.setM_nightTime(new Date(monthlyModel.getM_nightTime().getTime()-epic).getTime()/60000);


		workTimeDateModel.setY_workTime(new Date(annualModel.getY_workTime().getTime()-epic).getTime()/60000);
		workTimeDateModel.setY_overTime(new Date(annualModel.getY_overTime().getTime()-epic).getTime()/60000);
		workTimeDateModel.setY_nightTime(new Date(annualModel.getY_nightTime().getTime()-epic).getTime()/60000);

		//セッションに表示内容をセット
		session.setAttribute("ANNUAL", annualModel);
		session.setAttribute("MOUNTHLY", monthlyModel);
		session.setAttribute("WorkTimeDate", workTimeDateModel);
		session.setAttribute("Worktime", workTimeList);
	}else{
		session.setAttribute("eMsg", eMsg);
		session.removeAttribute("WorkTimeDate");
		session.removeAttribute("Worktime");
	}
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/workTimeList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		// 入力内容を取得
		WorkTimeModel workTimeModel = new WorkTimeModel();
		workTimeModel.setEmployeeNo(request.getParameter("wtm.employeeNo"));
		workTimeModel
				.setYear(Integer.parseInt(request.getParameter("wtm.year")));
		workTimeModel.setMonth(Integer.parseInt(request
				.getParameter("wtm.month")));
		workTimeModel.setDay(Integer.parseInt(request.getParameter("wtm.day")));
		WorkDAO workDao = new WorkDAO();
		workTimeModel = workDao.findWorkTime(workTimeModel.getEmployeeNo(),
				workTimeModel.getYear(), workTimeModel.getMonth(),
				workTimeModel.getDay());
		// 入力内容をセッションにセット
		session.setAttribute("workTimeModel", workTimeModel);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/workTimeChange.jsp");
		dispatcher.forward(request, response);
	}

}
