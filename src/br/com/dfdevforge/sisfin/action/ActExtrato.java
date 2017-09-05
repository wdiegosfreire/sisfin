package br.com.dfdevforge.sisfin.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusBanco;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.business.BusExtrato;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusTipoExtrato;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.business.BusEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmExtrato;

public class ActExtrato extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ActionMessages actMsg = new ActionMessages();
		BusExtrato ngc = new BusExtrato();

		ConnectionManager conn = null;

		command = ((FrmExtrato) form).getCmd();

		((FrmExtrato) form).getBtpExtrato().setBtpUsuario(this.getUserFromSession(request));

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
			else if (command.equals(Constants.actExecSearch))
			{
				actExecSearch(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(request, form, conn, ngc);
			}
			else if (command.equals(Constants.actShowImpoForm))
			{
				actShowImpoForm(request, form, conn, ngc);
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
		catch (ClassNotFoundException e)
		{
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

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, TimezoneValueException, SessionUserNotFoundException
	{
		FrmExtrato frmExtrato = (FrmExtrato) actionForm;

		// Carregando os comboboxes da página
		BusBanco busBanco = new BusBanco();
		setListOnRequest(request, busBanco.consultar(null, conn, 1), "btpBancoListCombo");

		BusTipoExtrato busTipoExtrato = new BusTipoExtrato();
		setListOnRequest(request, busTipoExtrato.consultar(null, conn, 1), "btpTipoExtratoListCombo");

		BusExtrato busExtrato = new BusExtrato();
		setListOnRequest(request, busExtrato.consultar(frmExtrato.getBtpExtrato(), conn, 2), "btpExtratoList");

		return false;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SessionUserNotFoundException, TimezoneValueException, SQLException, Exception
	{
		FrmExtrato frmExtrato = (FrmExtrato) actionForm;

		// Carregando os comboboxes da página
		BusBanco busBanco = new BusBanco();
		setListOnRequest(request, busBanco.consultar(null, conn, 1), "btpBancoListCombo");

		BusTipoExtrato busTipoExtrato = new BusTipoExtrato();
		setListOnRequest(request, busTipoExtrato.consultar(null, conn, 1), "btpTipoExtratoListCombo");

		bc.incluir(frmExtrato.getBtpExtrato(), conn);

		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return true;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		FrmExtrato frmExtrato = (FrmExtrato) actionForm;

		// Inicializando os objetos e/ou variáveis
		frmExtrato.getBtpExtrato().setBtpBanco(new BtpBanco());
		frmExtrato.getBtpExtrato().setBtpTipoExtrato(new BtpTipoExtrato());

		// Carregando os comboboxes da página
		BusBanco busBanco = new BusBanco();
		setListOnRequest(request, busBanco.consultar(null, conn, 1), "btpBancoListCombo");

		BusTipoExtrato busTipoExtrato = new BusTipoExtrato();
		setListOnRequest(request, busTipoExtrato.consultar(null, conn, 1), "btpTipoExtratoListCombo");

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

	public boolean actShowImpoForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws TimezoneValueException, SessionUserNotFoundException, SQLException
	{
		FrmExtrato frmExtrato = (FrmExtrato) actionForm;

		// Carregando o combobox de estabelecimentos
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(frmExtrato.getBtpExtrato().getBtpUsuario());
		BusEstabelecimento busEstabelecimento = new BusEstabelecimento(conn);
		setListOnRequest(request, busEstabelecimento.consultar(btpEstabelecimento, 2), "btpEstabelecimentoListCombo");

		// Carregando o combobox de formas de pagamento
		BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(frmExtrato.getBtpExtrato().getBtpUsuario());
		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		setListOnRequest(request, busFormaPagamento.consultar(btpFormaPagamento, conn, 1), "btpFormaPagamentoListCombo");

		// Carregando o combobox de contas
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(frmExtrato.getBtpExtrato().getBtpUsuario());
//		btpConta.getMap().put("auxFlgNivel", "1");
		btpConta.getMap().put("conAuxOnlyRootElements", "true");
		BusConta busConta = new BusConta();
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 3), "btpContaListCombo");

		BusExtrato busExtrato = new BusExtrato();
		setListOnRequest(request, busExtrato.consultar(frmExtrato.getBtpExtrato(), conn, 1), "btpExtratoList");

		return true;
	}
}