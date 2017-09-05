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
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;
import br.com.cagece.core.persistence.exception.RequiredFieldNotFoundException;
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
	public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionMessages actMsg = new ActionMessages();
		BusEstabelecimento busEstabelecimento = null;
		ConnectionManager connectionManager = null;

		command = ((FrmEstabelecimento) actionForm).getCmd();

		FrmEstabelecimento frmEstabelecimento = (FrmEstabelecimento) actionForm;

		frmEstabelecimento.getBtpEstabelecimento().setBtpUsuario(this.getUserFromSession(request));

		try
		{
			connectionManager = new ConnectionManager();
			busEstabelecimento = new BusEstabelecimento(connectionManager);

			if (command.equals(Constants.actShowMainPage))
				actShowMainPage(request, frmEstabelecimento, busEstabelecimento);
			else if (command.equals(Constants.actShowEditForm))
				actShowEditForm(request, frmEstabelecimento, busEstabelecimento);
			else if (command.equals(Constants.actShowInclForm))
				actShowInclForm(request, frmEstabelecimento, busEstabelecimento);
			else if (command.equals(Constants.actExecDelete))
				actExecDelete(request, frmEstabelecimento, busEstabelecimento);
			else if (command.equals(Constants.actExecUpdate))
				actExecUpdate(request, frmEstabelecimento, busEstabelecimento);
			else if (command.equals(Constants.actExecInsert))
				actExecInsert(request, frmEstabelecimento, busEstabelecimento);

			connectionManager.commit();
		}
		catch (SQLException e)
		{
			connectionManager.rollback();
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
			connectionManager.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (TimezoneValueException e)
		{
			connectionManager.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (RequiredColumnNotFoundException e)
		{
			connectionManager.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		catch (NullBeanException e)
		{
			connectionManager.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
		}
		finally
		{
			ConnectionManager.closeConnection(connectionManager);
		}

		if (!actMsg.isEmpty())
		{
			saveErrors(request, actMsg);
			return mapping.getInputForward();
		}

		return (mapping.findForward(command));
	}

	public void actExecSearch(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SQLException
	{
	}

	public void actExecInsert(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SessionUserNotFoundException, SQLException, JpaMappingNotFoundException, Exception
	{
		busEstabelecimento.incluir(frmEstabelecimento.getBtpEstabelecimento());
		frmEstabelecimento.reset(null, null);
		actShowMainPage(request, frmEstabelecimento, busEstabelecimento);
	}

	public void actShowEditForm(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		List<BtpEstabelecimento> btpEstList = busEstabelecimento.editar(frmEstabelecimento.getBtpEstabelecimento());
		frmEstabelecimento.setBtpEstabelecimento(btpEstList.get(0));
		setListOnRequest(request, btpEstList, "btpEstabelecimentoList");
	}

	public void actShowMainPage(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		setListOnRequest(request, busEstabelecimento.consultar(frmEstabelecimento.getBtpEstabelecimento(), frmEstabelecimento.getSqlOrder()), "btpEstabelecimentoList");
	}

	public void actExecUpdate(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SessionUserNotFoundException, SQLException, RequiredFieldNotFoundException, JpaMappingNotFoundException, Exception
	{
		busEstabelecimento.alterar(frmEstabelecimento.getBtpEstabelecimento());
		frmEstabelecimento.reset(null, null);
		actShowMainPage(request, frmEstabelecimento, busEstabelecimento);
	}

	public void actExecDelete(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SessionUserNotFoundException, SQLException, RequiredFieldNotFoundException, JpaMappingNotFoundException, Exception
	{
		busEstabelecimento.excluir(frmEstabelecimento.getBtpEstabelecimento());
		frmEstabelecimento.reset(null, null);
		actShowMainPage(request, frmEstabelecimento, busEstabelecimento);
	}

	public void actShowInclForm(HttpServletRequest request, FrmEstabelecimento frmEstabelecimento, BusEstabelecimento busEstabelecimento) throws SQLException, Exception
	{
		busEstabelecimento.incluir(frmEstabelecimento.getBtpEstabelecimento());
	}
}