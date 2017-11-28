package br.com.dfdevforge.sisfin.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.ftp.exception.FtpLogoutFailedException;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmMovimento;

public class ActMovimento extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, FtpLogoutFailedException
	{
		ActionMessages actMsg = new ActionMessages();
		BusFormaPagamento ngc = new BusFormaPagamento();
		
		ConnectionManager conn = null;

		command = ((FrmMovimento) form).getCmd();

		((FrmMovimento) form).setBtpUsuario(this.getUserFromSession(request));

		try
		{
			conn = new ConnectionManager();

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(request, form, conn, ngc);
			}

			conn.commit();
		}
		catch (SQLException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getClass().getSimpleName(), new ActionMessage(e.getClass().getName()));
		}
		catch (TimezoneValueException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
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

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, TimezoneValueException
	{
		return true;
	}

	@Override public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
	@Override public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
	@Override public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
	@Override public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
	@Override public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
	@Override public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) {return false;}
}