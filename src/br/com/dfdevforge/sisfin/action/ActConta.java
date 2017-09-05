package br.com.dfdevforge.sisfin.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.ftp.exception.FtpLogoutFailedException;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmConta;

public class ActConta extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, FtpLogoutFailedException
	{
		ActionMessages actMsg = new ActionMessages();
		BusConta ngc = new BusConta();

		ConnectionManager conn = null;

		command = ((FrmConta) form).getCmd();

		((FrmConta) form).getBtpConta().setBtpUsuario(this.getUserFromSession(request));

		try
		{
			conn = new ConnectionManager();

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(request, form, conn, ngc);
			}
			else if (command.equals(EnumComandoNavegacao.actShowEditForm.getNome()))
			{
				actShowEditForm(request, form, conn, ngc);
			}
			else if (command.equals(EnumComandoNavegacao.actShowInclForm.getNome()))
			{
				actShowInclForm(request, form, conn, ngc);
			}
			else if (command.equals(EnumComandoNavegacao.actExecDelete.getNome()))
			{
				actExecDelete(request, form, conn, ngc);
			}
			else if (command.equals(EnumComandoNavegacao.actExecUpdate.getNome()))
			{
				actExecUpdate(request, form, conn, ngc);
			}
			else if (command.equals(EnumComandoNavegacao.actExecInsert.getNome()))
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
		catch (SessionUserNotFoundException e)
		{
			conn.rollback();
			e.printStackTrace();
			actMsg.add(e.getExceptionName(), new ActionMessage(e.getExceptionKey()));
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

		return mapping.findForward(command);
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException
	{
		return false;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;

		BusConta busConta = new BusConta();
		busConta.incluir(frmConta.getBtpConta(), conn);

		frmConta.reset(null, null);
		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;
		BusConta busConta = new BusConta();

		List<BtpConta> btpContaList = busConta.editar(frmConta.getBtpConta(), conn);

		btpContaList.get(0).getBtpUsuario().setUsuCodUsuario(frmConta.getBtpConta().getBtpUsuario().getUsuCodUsuario());
		frmConta.setBtpConta(btpContaList.get(0));
		setListOnRequest(request, btpContaList, "btpContaList");

		BtpConta b = new BtpConta();
		b.setBtpUsuario(frmConta.getBtpConta().getBtpUsuario());
		List<BtpConta> btpContaListCombo = busConta.consultar(b, conn, 3);
		setListOnRequest(request, btpContaListCombo, "btpContaListCombo");

		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;

		BusConta busConta = new BusConta();

		frmConta.getBtpConta().getMap().put("conAuxOnlyRootElements", "true");
		List<BtpConta> btpContaList = busConta.consultar(frmConta.getBtpConta(), conn, 3);
		setListOnRequest(request, btpContaList, "btpContaList");
		setListOnRequest(request, btpContaList, "btpContaListCombo");

		return true;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;

		BusConta busConta = new BusConta();
		busConta.alterar(frmConta.getBtpConta(), conn);

		frmConta.reset(null, null);
		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;

		BusConta busConta = new BusConta();
		busConta.excluir(frmConta.getBtpConta(), conn);

		frmConta.reset(null, null);
		actShowMainPage(request, actionForm, conn, bc);

		return true;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		FrmConta frmConta = (FrmConta) actionForm;

		BusConta busConta = new BusConta();

		busConta.incluir(frmConta.getBtpConta(), conn);

		return true;
	}
}