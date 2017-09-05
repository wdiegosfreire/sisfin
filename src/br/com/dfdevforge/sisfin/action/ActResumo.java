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
import br.com.dfdevforge.sisfin.business.BusResumo;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmResumo;

public class ActResumo extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, FtpLogoutFailedException
	{
		ActionMessages messageList = new ActionMessages();
		BusResumo busResumo = new BusResumo();

		FrmResumo frmResumo = (FrmResumo) form;

		ConnectionManager conn = null;

		command = ((FrmResumo) form).getCmd();

		try
		{
			frmResumo.getBtpCompetencia().setBtpUsuario(this.getUserFromSession(request));

			conn = new ConnectionManager();

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(request, frmResumo, conn, busResumo);
			}

			conn.commit();
		}
		catch (Exception e)
		{
			conn.rollback();
			e.printStackTrace();
			messageList.add(e.getClass().getSimpleName(), new ActionMessage(e.getClass().getName()));
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}

		if (!messageList.isEmpty())
			saveErrors(request, messageList);

		return mapping.findForward(command);
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException
	{
		return false;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, SessionUserNotFoundException, TimezoneValueException
	{
		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, FrmResumo frmResumo, ConnectionManager conn, BusResumo busResumo) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(frmResumo.getBtpCompetencia().getBtpUsuario());
		btpConta.getMap().put("conAuxOnlyRootElements", "true");

		BusConta busConta = new BusConta();
		List<BtpConta> btpContaList = busConta.consultar(btpConta, conn, 3);
		setListOnRequest(request, btpContaList, "btpContaListCombo");

		return true;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return true;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return true;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		return true;
	}
}