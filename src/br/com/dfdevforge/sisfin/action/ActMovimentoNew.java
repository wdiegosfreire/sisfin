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

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.business.BusEstabelecimento;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusResumo;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmMovimentoNew;

public class ActMovimentoNew extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		ActionMessages messageList = new ActionMessages();
		BusResumo busResumo = new BusResumo();

		FrmMovimentoNew frmMovimentoNew = (FrmMovimentoNew) form;

		ConnectionManager conn = null;

		command = ((FrmMovimentoNew) form).getCmd();

		request.setAttribute("showNewButtom", "true");

		try
		{
			frmMovimentoNew.getBtpCompetencia().setBtpUsuario(this.getUserFromSession(request));

			conn = new ConnectionManager();
			this.loadComboboxData(request, frmMovimentoNew.getBtpCompetencia(), conn);

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(request, frmMovimentoNew, conn, busResumo);
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

	public boolean actShowMainPage(HttpServletRequest request, FrmMovimentoNew frmMovimentoNew, ConnectionManager conn, BusResumo busResumo) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
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

	public void loadComboboxData(HttpServletRequest request, AbstractBean bean, ConnectionManager conn) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
		BtpCompetencia btpCompetencia = (BtpCompetencia) bean;

		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(btpCompetencia.getBtpUsuario());
		btpConta.getMap().put("conAuxOnlyRootElements", "true");

		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(btpCompetencia.getBtpUsuario());

		BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(btpCompetencia.getBtpUsuario());

		BusConta busConta = new BusConta();
		List<BtpConta> btpContaList = busConta.consultar(btpConta, conn, 3);
		setListOnRequest(request, btpContaList, "btpContaListCombo");

		BusEstabelecimento busEstabelecimento = new BusEstabelecimento(conn);
		List<BtpEstabelecimento> btpEstabelecimentoList = busEstabelecimento.consultar(btpEstabelecimento, 2);
		setListOnRequest(request, btpEstabelecimentoList, "btpEstabelecimentoListCombo");

		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		List<BtpFormaPagamento> btpFormaPagamentoList = busFormaPagamento.consultar(btpFormaPagamento, conn, 2);
		setListOnRequest(request, btpFormaPagamentoList, "btpFormaPagamentoListCombo");
	}
}