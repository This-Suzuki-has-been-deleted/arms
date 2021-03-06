package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AuthModel;
import model.DepModel;
import model.EmployeeModel;
import others.LoginLogic;
import others.PassChanger;
import validation.Validation;
import dao.AuthDAO;
import dao.DepDAO;
import dao.EmployeeDAO;

/**
 * Servlet implementation class ChangeServlet
 */
@WebServlet("/ChangeServlet")
public class ChangeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;

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
		session = request.getSession();
		//メッセージ削除
		session.removeAttribute("eMsg");

		EmployeeModel employee = (EmployeeModel) session.getAttribute("Employee");
		String employeeNo = request.getParameter("employeeNo");
		//もし入ってなかった場合ログインしている人の番号を入力
		if (employeeNo == null){
			employeeNo = employee.getEmployeeNo();
		}
		List<DepModel> depModel = new ArrayList<DepModel>();
		List<AuthModel> authModel = new ArrayList<AuthModel>();
		DepDAO depDao = new DepDAO();
		AuthDAO authDao = new AuthDAO();
		//初期パスワード検査
		PassChanger passChanger = new PassChanger();
		passChanger.indexOut(request, response);
		//部署と権限をすべて持ってくる
		depModel = depDao.findDepAll();
		authModel = authDao.findAuthAll();
		//sessionに上記の２つをセット
		session.setAttribute("DepModel", depModel);
		session.setAttribute("AuthModel",authModel);

		//権限が一般、もしくは社員番号が同じだった時
		if(employee.getAuthNo().equals("01") || employee.getEmployeeNo() == employeeNo ){
			session.setAttribute("pageTitle", "社員情報変更");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
			dispatcher.forward(request, response);
		}else{
//			EmployeeModel employeeModel = new EmployeeModel();
			session.setAttribute("pageTitle", "社員情報変更");
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/infoChange.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		session = request.getSession();
		EmployeeModel employee = (EmployeeModel) session.getAttribute("Employee");
		String employeeNo = request.getParameter("employeeNo");
		if(employeeNo == null){
			employeeNo = employee.getEmployeeNo();
		}
		//権限が一般、もしくは社員番号が同じだった時
		if(employee.getAuthNo().equals("001") || employee.getEmployeeNo() == employeeNo ){
			String password = request.getParameter("pass");
			String nextPassword = request.getParameter("nextPass");
			String Msg = null;
			String eMsg = null;
			LoginLogic loginlogic = new LoginLogic();
			String passHashCode = loginlogic.passHash(password);
			EmployeeDAO employeeDao = new EmployeeDAO();
			EmployeeModel emp = employeeDao.findEmployee(employeeNo);
			Validation validation = new Validation();

			//password とnextPasswordがnullであるかどうか
			if (! validation.nullCheck(password) || ! validation.nullCheck(nextPassword)){
				eMsg = "パスワードが未入力です。";
				session.setAttribute("eMsg",eMsg);
				session.setAttribute("pageTitle", "社員情報変更");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);

				//入力された現パスワードが一致しているか
			}else if(emp.getPassword() == passHashCode){
				eMsg = "パスワードが間違っています。";
				session.setAttribute("eMsg",eMsg);
				session.setAttribute("pageTitle", "社員情報変更");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);
			}else{
			Msg = "パスワード変更しました。";
			session.setAttribute("Msg",Msg);
			String nextPassHashCode = loginlogic.passHash(nextPassword);
			emp.setPassword(nextPassHashCode);
			employeeDao.updateEmppass(emp);
			employee = employeeDao.findEmployee(employee.getEmployeeNo());
			session.setAttribute("Employee", employee);

			session.setAttribute("pageTitle", "メインメニュー");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
//			dispatcher.forward(request, response);
			response.sendRedirect("index.jsp");
			}

		}else{
			EmployeeModel employeeUser = (EmployeeModel) session.getAttribute("ChangeEmployee");
			DepDAO depDao = new DepDAO();
			AuthDAO authDao = new AuthDAO();
			String employeeName = null;
			String divisionNo = null;
			String authorityNo = null;
			String pageFlg = "ChangeServlet";
			String msg = null;
			try{
				employeeName = request.getParameter("employeeName");
				divisionNo = request.getParameter("selectDivisionNo");
				authorityNo = request.getParameter("selectAuthorityNo");
			}catch(NullPointerException e){
				msg = "未入力の項目があります。";
			}
			employeeUser.setEmployeeNo(employeeNo);
			employeeUser.setEmployeeName(employeeName);
			employeeUser.setAuthNo(authorityNo);
			employeeUser.setDepNo(divisionNo);
			employeeUser.setAuthName(authDao.findByAuthName(employeeUser.getAuthNo()));
			employeeUser.setDepName(depDao.findByDepName(employeeUser.getDepNo()));

			Validation validation = new Validation();

			// エラーチェック
				if (!(validation.nullCheck(employeeUser.getEmployeeName()))) { // 社員名が入力されているかチェック
					msg = msg + "・未入力項目があります。";
				}

			session.setAttribute("pageFlg", pageFlg);
			session.setAttribute("employeeModel", employeeUser);
			session.setAttribute("Employee", employee);

			//msgがnullでない ＝ エラーがなかった状態
			if(msg == null){
				session.setAttribute("pageTitle", "社員情報確認");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/conf.jsp");
				dispatcher.forward(request, response);
			}else{
				session.setAttribute("eMsg", msg);
				session.setAttribute("pageTitle", "社員情報変更");
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/infoChange.jsp");
				dispatcher.forward(request, response);
			}
		}
	}
}
