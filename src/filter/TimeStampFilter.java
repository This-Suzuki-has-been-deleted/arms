package filter;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import model.WorkTimeModel;
import dao.WorkDAO;

/**
 * Servlet Filter implementation class TimeStampFilter
 */
//@WebFilter("/LoginServlet")
public class TimeStampFilter implements Filter {

    /**
     * Default constructor.
     */
    public TimeStampFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = httpRequest.getSession();

		EmployeeModel emp= (EmployeeModel)session.getAttribute("Employee");
		if(emp==null){
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			rd.forward(request, response);
		}

		LocalDateTime date = LocalDateTime.now();
		int year = date.getYear();
		int month = date.getMonthValue();
		int day = date.getDayOfMonth() - 1;

		EmployeeModel em = (EmployeeModel) session.getAttribute("Employee");
		WorkDAO wdao = new WorkDAO();
		WorkTimeModel wm = wdao.findWorkTime(em.getEmployeeNo(), year, month,
				day);

		if (wm != null) { // 前日のレコードの有無を確認
			if (wm.isWorkFlg()) { // 勤怠フラグを確認、本日のレコードの有無を確認
				wm = wdao
						.findWorkTime(em.getEmployeeNo(), year, month, day + 1);
				if (wm != null) {
					wm.setEmployeeNo(em.getEmployeeNo());
					wm.setYear(year);
					wm.setMonth(month);
					wm.setDay(day);
					wdao.insertWorkTime(wm); // レコード作成
					session.setAttribute("work", wm); // 当日を参照する
					session.setAttribute("buttonvalue", "出勤"); // ボタンのバリューを出勤に
				} else {
					session.setAttribute("work", wm); // 当日を参照する
					session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に

				}
			} else {
				session.setAttribute("work", wm); // 昨日を参照する
				session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に
			}
		} else {
			wm = wdao.findWorkTime(em.getEmployeeNo(), year, month, day + 1);
			if (wm == null) { // 本日のレコードの有無を確認
				wm.setEmployeeNo(em.getEmployeeNo());
				wm.setYear(year);
				wm.setMonth(month);
				wm.setDay(day);
				wdao.insertWorkTime(wm); // レコード作成
				session.setAttribute("work", wm); // 当日を参照する
				session.setAttribute("buttonvalue", "出勤"); // ボタンのバリューを出勤に
			} else {
				session.setAttribute("work", wm); // 当日を参照する
				session.setAttribute("buttonvalue", "退勤"); // ボタンのバリューを退勤に
			}

		}

		System.out.println("フィルタを通っているかどうか");

		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
