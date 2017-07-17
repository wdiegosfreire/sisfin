package br.com.dfdevforge.sisfin.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.business.BusEstabelecimento;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.form.FrmEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public class ActEstabelecimento extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionMessages actMsg = new ActionMessages();
		BusEstabelecimento busEstabelecimento = new BusEstabelecimento();
		
		ConnectionManager dbConn = null;

		command = ((FrmEstabelecimento) form).getCmd();

		((FrmEstabelecimento) form).getBtpEstabelecimento().setBtpUsuario(this.getUserFromSession(request));

		try
		{
			dbConn = new ConnectionManager();

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(request, form, dbConn, busEstabelecimento);
			}
			else if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(request, form, dbConn, busEstabelecimento);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(request, form, dbConn, busEstabelecimento);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(request, form, dbConn, busEstabelecimento);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(request, form, dbConn, busEstabelecimento);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(request, form, dbConn, busEstabelecimento);
			}

			dbConn.commit();
		}
		catch (SQLException e)
		{
			dbConn.rollback();
			e.printStackTrace();
			actMsg.add(e.getClass().getSimpleName(), new ActionMessage(e.getClass().getName()));
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
			actMsg.add(e.getClass().getSimpleName(), new ActionMessage(e.getClass().getName()));
		}
		catch (SessionUserNotFoundException e)
		{
			dbConn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (TimezoneValueException e)
		{
			dbConn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (RequiredColumnNotFoundException e)
		{
			dbConn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (NullBeanException e)
		{
			dbConn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		if (!actMsg.isEmpty())
		{
			saveErrors(request, actMsg);
			return mapping.getInputForward();
		}

		return (mapping.findForward(command));
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws SQLException
	{
		return false;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws SQLException, RequiredColumnNotFoundException, NullBeanException, SessionUserNotFoundException, TimezoneValueException, Exception
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;

		busEstabelecimento.incluir(frmEstabelecimento.getBtpEstabelecimento(), conn);

		frmEstabelecimento.reset(null, null);

		actShowMainPage(request, actionForm, conn, busEstabelecimento);

		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;

		List<BtpEstabelecimento> btpEstList = busEstabelecimento.editar(frmEstabelecimento.getBtpEstabelecimento(), conn);

		frmEstabelecimento.setBtpEstabelecimento(btpEstList.get(0));
		setListOnRequest(request, btpEstList, "btpEstabelecimentoList");

		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;

		setListOnRequest(request, busEstabelecimento.consultar(frmEstabelecimento.getBtpEstabelecimento(), conn, frmEstabelecimento.getSqlOrder()), "btpEstabelecimentoList");

		return true;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;

		busEstabelecimento.alterar(frmEstabelecimento.getBtpEstabelecimento(), conn);

		frmEstabelecimento.reset(null, null);

		actShowMainPage(request, actionForm, conn, busEstabelecimento);

		return true;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;
		busEstabelecimento.excluir(frmEstabelecimento.getBtpEstabelecimento(), conn);

		frmEstabelecimento.reset(null, null);

		actShowMainPage(request, actionForm, conn, busEstabelecimento);

		return true;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusEstabelecimento busEstabelecimento) throws SQLException, Exception
	{
		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;
		busEstabelecimento.incluir(frmEstabelecimento.getBtpEstabelecimento(), conn);

		return true;
	}
}