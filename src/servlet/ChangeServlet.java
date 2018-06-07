package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ChangeServlet
 */
public class ChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeServlet() {
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
		if(){
		String employeeNo;
		String divisionNo;
		String authorityNo;
		String pageFlg = "ch";
		String msg = null;

		try{
			employeeNo = request.getParameter("employeeNo");
			divisionNo = request.getParameter("selectDivisionNo");
			authorityNo = request.getParameter("selectAuthorityNo");
		}catch(NullPointerException e){
			msg = "未入力の項目があります。";
		}

		if(msg == null){
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/change.jsp");
			dispatcher.forward(request, response);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/conf.jsp");


	}

}
