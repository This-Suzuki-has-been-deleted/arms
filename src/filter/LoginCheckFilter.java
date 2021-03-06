package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;
import others.LoginLogic;

/**
 * Servlet Filter implementation class LoginCheckFilter
 */
@WebFilter(urlPatterns={"/TimeStampServlet","/ConfServlet","/EmployeeServlet","/RegistrationServlet","/WorkServlet","/ChangeServlet","/InfoChangeServlet"})
public class LoginCheckFilter implements Filter {

    /**
     * Default constructor.
     */
    public LoginCheckFilter() {
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

		LoginLogic loginLogic = new LoginLogic();
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		ServletResponse httpResponse = (ServletResponse)response;
		HttpSession session = httpRequest.getSession();


		EmployeeModel em;
		em = (EmployeeModel)session.getAttribute("Employee");

		if(em != null){
			httpRequest.setCharacterEncoding("UTF-8");
			chain.doFilter(httpRequest, httpResponse);
		}else{
			session.setAttribute("msg", "ログインしてください。");
			RequestDispatcher dr = request.getRequestDispatcher("WEB-INF/jsp/login.jsp");
			dr.forward(httpRequest, httpResponse);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
