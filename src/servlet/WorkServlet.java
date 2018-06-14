package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    //ANNUAL
    //MONTHLY


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

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

		first.set( Calendar.DATE,1);
		int firstWeek = (first.get( Calendar.DAY_OF_WEEK )-1);
		int maxDayCnt = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(week[firstWeek-1]);

		//セッションに曜日の情報が入っている配列をセット
		session.setAttribute("week",week);
		//セッションに月初めの曜日情報を1～7の数値でセット
		session.setAttribute("firstWeek",firstWeek);
		//セッションに月末の日にちをセット
		session.setAttribute("maxDayCnt",maxDayCnt);

		//yearとmonthの初期値を設定
		if(yearBuf == null){
			year = now.get(Calendar.YEAR);		//セッションに年が入っていなかった場合は今日の年を取得
		}else{
			year = Integer.parseInt(yearBuf);	//セッションに年が入っていた場合はその年を取得
		}
		if(monthBuf == null){
			month = now.get(Calendar.MONTH)+1;	//セッションに月が入っていなかった場合は今日の月を取得
		}else{
			month = Integer.parseInt(monthBuf);	//セッションに月が入っていた場合はその月を取得
		}

		//データベースからモデルに追加
		annualModel = annualDao.findAnnualTime(myEmp.getEmployeeNo(),year);
		monthlyModel = monthlyDao.findMonthlyTime(myEmp.getEmployeeNo(),year,month);
		workTimeList = (ArrayList<WorkTimeModel>)workDao.d_findByEmployeeNoAndMonth(myEmp.getEmployeeNo(),year,month);

		//モデルに必要な情報を他メソッドを使い追加
		for(WorkTimeModel wtm :workTimeList){
			long fix = dateMath.fixedTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
			long over = dateMath.overTime(wtm.getYear(), wtm.getMonth(), wtm.getDay());
			long leave = wtm.getLeaving().getTime();
			long attend = wtm.getAttendance().getTime();

			int workTime = dateMath.diff(leave, attend);	//勤務時間を算出
			int overTime = dateMath.diff(leave, fix);		//残業時間を算出
			int nightTime = dateMath.diff(leave, over);		//深夜時間を算出

			wtm.setWorkTimeH((int)workTime / 60);
			wtm.setWorkTimeM((int)workTime % 60);
			wtm.setOverTimeH((int)overTime / 60);
			wtm.setOverTimeM((int)overTime % 60);
			wtm.setNightTimeH((int)nightTime / 60);
			wtm.setNightTimeM((int)nightTime % 60);
		}

		//セッションに表示内容をセット
		session.setAttribute("ANNUAL", annualModel);
		session.setAttribute("MOUNTHLY", monthlyModel);
		session.setAttribute("Worktime", workTimeList);


		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/workTimeList.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();

		//入力内容を取得
		WorkTimeModel workTimeModel = (WorkTimeModel)request.getAttribute("wtm");

		//入力内容をセッションにセット
		session.setAttribute("workTimeModel", workTimeModel);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("/WEB-INF/jsp/workTimeChange.jsp");
		dispatcher.forward(request, response);
	}

}
