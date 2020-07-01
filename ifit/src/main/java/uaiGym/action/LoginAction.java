package uaiGym.action;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uaiGym.service.AuthService;

public class LoginAction implements Action {

	private String doGet(HttpServletRequest request) {

		AuthService authenticator = new AuthService(request.getSession());

		if (authenticator.isValid()) {
			return "menu";
		}
		request.setAttribute("mensagem", authenticator.getMessages());
		return "index";
	}

	private String doPost(HttpServletRequest request) {

		AuthService authenticator = new AuthService(request.getSession());

		String usuario = request.getParameter("usuario");
		String senha = request.getParameter("senha");

		boolean isValid = false;
		try {
			isValid = authenticator.login(usuario, senha);
		} catch (NoSuchAlgorithmException | ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

		request.setAttribute("mensagem", authenticator.getMessages());

		return (isValid) ? "menu" : "index";
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String returns;
		switch (request.getMethod()) {
		case "GET":
			returns = doGet(request);
			break;
		case "POST":
			returns = doPost(request);
			break;
		default:
			returns = "index";
		}
		return returns;
	}

	@Override
	public String toString() {
		return this.getClass().getName();
	}
}
