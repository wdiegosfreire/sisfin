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
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmFormaPagamento;

public class ActFormaPagamento extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		ActionMessages actMsg = new ActionMessages();
		BusFormaPagamento ngc = new BusFormaPagamento();
		
		ConnectionManager conn = null;

		command = ((FrmFormaPagamento) form).getCmd();

		((FrmFormaPagamento) form).getBtpFormaPagamento().setBtpUsuario(this.getUserFromSession(request));

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
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (TimezoneValueException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (RequiredColumnNotFoundException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (NullBeanException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (SessionUserNotFoundException e)
		{
			conn.rollback();
			e.printStackTrace();
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

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException
	{
		return false;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		busFormaPagamento.incluir(frmFormaPagamento.getBtpFormaPagamento(), conn);

		frmFormaPagamento.reset(null, null);

		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();

		List<BtpFormaPagamento> btpFopList = busFormaPagamento.editar(frmFormaPagamento.getBtpFormaPagamento(), conn);

		frmFormaPagamento.setBtpFormaPagamento(btpFopList.get(0));
		setListOnRequest(request, btpFopList, "btpFormaPagamentoList");

		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();

		setListOnRequest(request, busFormaPagamento.consultar(frmFormaPagamento.getBtpFormaPagamento(), conn, frmFormaPagamento.getSqlOrder()), "btpFormaPagamentoList");

		return true;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		busFormaPagamento.alterar(frmFormaPagamento.getBtpFormaPagamento(), conn);

		frmFormaPagamento.reset(null, null);

		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		busFormaPagamento.excluir(frmFormaPagamento.getBtpFormaPagamento(), conn);

		frmFormaPagamento.reset(null, null);

		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		FrmFormaPagamento frmFormaPagamento = (FrmFormaPagamento) actionForm;

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();

		busFormaPagamento.incluir(frmFormaPagamento.getBtpFormaPagamento(), conn);

		return true;
	}
}