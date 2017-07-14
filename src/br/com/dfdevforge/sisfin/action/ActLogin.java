package br.com.dfdevforge.sisfin.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusUsuario;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.LoginFailException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmLogin;

public class ActLogin extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		ActionMessages actMsg = new ActionMessages();
		BusUsuario bus = new BusUsuario();

		ConnectionManager conn = null;

		command = ((FrmLogin) form).getCmd();

		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
		request.getSession().setAttribute("userCurrentLocale", new Locale("pt", "BR"));

		try
		{
			conn = new ConnectionManager();

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(request, form, conn, bus);
			}
			else if (command.equals(Constants.actExecSearch))
			{
				actExecSearch(request, form, conn, bus);
			}
			else if (command.equals(Constants.actExecLogoff))
			{
				actExecLogoff(request, form, conn, bus);
			}
			else
			{
				actMsg.add("label.attribute.renCodRendimento", new ActionMessage(""));

			}

			conn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
			actMsg.add(e.getClass().getSimpleName(), new ActionMessage(e.getClass().getName()));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SessionUserNotFoundException e)
		{
			e.printStackTrace();
			conn.rollback();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (TimezoneValueException e)
		{
			e.printStackTrace();
			conn.rollback();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (LoginFailException e)
		{
			e.printStackTrace();
			conn.rollback();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}

		if (!actMsg.isEmpty())
		{
			saveErrors(request, actMsg);
			return mapping.getInputForward();
		}

		return (mapping.findForward(command));
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws LoginFailException, SQLException, TimezoneValueException
	{
		FrmLogin frmLogin = (FrmLogin) actionForm;
		BusUsuario busUsuario = new BusUsuario();

		List<BtpUsuario> btpUsuarioList = busUsuario.authenticate(frmLogin.getBtpUsuario(), conn, 0);

		if (btpUsuarioList.size() != 1)
		{
			request.getSession().removeAttribute("btpUsuario");
			throw new LoginFailException();
		}

		request.getSession().setAttribute("btpUsuario", btpUsuarioList.get(0));

		return true;
	}

	public boolean actExecLogoff(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws LoginFailException, SQLException
	{
		request.getSession().setAttribute("btpUsuario", null);

		return true;
	}
}