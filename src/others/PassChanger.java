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

	public void indexOut(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//LoginLogic ll = new LoginLogic();
		session = request.getSession();

		myEm = (EmployeeModel) session.getAttribute("Employee");
		if (myEm != null) {
			// if (myEm.getPassword().equals(ll.passHash("pass1234"))) {
			if (myEm.getPassword().equals("bd94dcda26fccb4e68d6a31f9b5aac0b571ae266d822620e901ef7ebe3a11d4f")) {
				session.setAttribute("pageTitle", "初期パスワード変更");
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/WEB-INF/jsp/passChange.jsp");
				dispatcher.forward(request, response);
			}
		} else {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
