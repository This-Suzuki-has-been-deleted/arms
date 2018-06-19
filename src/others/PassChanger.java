package others;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.EmployeeModel;

public class PassChanger {
	HttpSession session;
	EmployeeModel myEm = new EmployeeModel();
	public void indexOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LoginLogic ll = new LoginLogic();
		session = request.getSession();

		myEm = (EmployeeModel)session.getAttribute("Employee");
		if(myEm.getPassword().equals(ll.passHash("pass1234"))){
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/passChange.jsp");
			dispatcher.forward(request, response);
		}
	}
}
