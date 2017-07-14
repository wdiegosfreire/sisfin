package br.com.dfdevforge.sisfin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionServlet;

public class SessionControl extends ActionServlet
{
	private static final long serialVersionUID = 2074470954672120871L;

	protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		boolean acesso = true;

		String acao = splitAcao(request.getRequestURI());
		String comando = request.getParameter("cmd");
		System.out.println(acao + ".do?cmd=" + comando);

		if (!acao.equals("login"))
		{
			if (!hasSessionUser(request))
			{
				System.out.println("Session user not found!");
			}
		}

		if (acesso)
			super.process(request, response);
	}

	private String splitAcao(String acao)
	{
		String[] aux = acao.split("/");
		acao = aux[aux.length - 1];

		aux = acao.split("\\.do");
		acao = aux[0];

		return acao;
	}

	private boolean hasSessionUser(HttpServletRequest request)
	{
		HttpSession sess = request.getSession();
		if (sess.getAttribute("btpUsuario") == null)
			return false;

		return true;
	}
}