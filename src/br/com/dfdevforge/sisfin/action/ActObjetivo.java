package br.com.dfdevforge.sisfin.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.business.BusEstabelecimento;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusObjetivo;
import br.com.dfdevforge.sisfin.business.BusTemplate;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.form.FrmObjetivo;

public class ActObjetivo extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws SQLException
	{
		ActionMessages actMsg = new ActionMessages();
		
		ConnectionManager dbConn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbConn);

		command = ((FrmObjetivo) form).getCmd();

		((FrmObjetivo) form).getBtpObjetivo().setBtpUsuario(this.getUserFromSession(request));

		try
		{
			dbConn = new ConnectionManager();

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(request, form, dbConn, busObjetivo);
			}
			if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(request, form, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(request, form, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(request, form, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(request, form, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(request, form, dbConn, busObjetivo);
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
			dbConn.rollback();
			e.printStackTrace();
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

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
		FrmObjetivo frmObjetivo = (FrmObjetivo) actionForm;

		// Carregando o combobox de estabelecimentos
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusEstabelecimento busEstabelecimento = new BusEstabelecimento();
		this.setListOnRequest(request, busEstabelecimento.consultar(btpEstabelecimento, conn, 2), "btpEstabelecimentoListCombo");

		// Carregando o combobox de templates
		BtpTemplate btpTemplate = new BtpTemplate();
		btpTemplate.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusTemplate busTemplate = new BusTemplate();
		this.setListOnRequest(request, busTemplate.consultar(btpTemplate, conn, 2), "btpTemplateListCombo");

		// Carregando o combobox de contas
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
//		btpConta.getMap().put("auxFlgNivel", "1");
		btpConta.getMap().put("conAuxOnlyRootElements", "true");
		BusConta busConta = new BusConta();
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 3), "btpContaListCombo");

		// Carregando o combobox de formas de pagamento
		BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		this.setListOnRequest(request, busFormaPagamento.consultar(btpFormaPagamento, conn, 2), "btpFormaPagamentoListCombo");

		SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
		String[] aux = sdf.format(new Date()).split("/");

		frmObjetivo.getBtpObjetivo().getMap().put("numMes", aux[0]);
		frmObjetivo.getBtpObjetivo().getMap().put("numAno", aux[1]);

		return true;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
		FrmObjetivo frmObjetivo = (FrmObjetivo) actionForm;

		// Carregando o combobox de estabelecimentos
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusEstabelecimento busEstabelecimento = new BusEstabelecimento();
		this.setListOnRequest(request, busEstabelecimento.consultar(btpEstabelecimento, conn, 2), "btpEstabelecimentoListCombo");

		// Carregando o combobox de formas de pagamento
		BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		this.setListOnRequest(request, busFormaPagamento.consultar(btpFormaPagamento, conn, 2), "btpFormaPagamentoListCombo");

		// Preparando as consultas de consta de origem e destino
		BusConta busConta = new BusConta();
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		btpConta.getMap().put("conAuxOnlyRootElements", "true");

		// Setar o btpConta para recuperar apenas as contas de origem
		btpConta.getMap().put("conAuxOnlyOrigin", "true");
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 1), "btpContaOrigemListCombo");

		// Setar o btpConta para recuperar apenas as contas de destino
		btpConta.getMap().remove("conAuxOnlyOrigin");
		btpConta.getMap().put("conAuxOnlyDestiny", "true");
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 1), "btpContaDestinoListCombo");

		return true;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
		FrmObjetivo frmObjetivo = (FrmObjetivo) actionForm;

		// Carregando o combobox de estabelecimentos
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusEstabelecimento busEstabelecimento = new BusEstabelecimento();
		this.setListOnRequest(request, busEstabelecimento.consultar(btpEstabelecimento, conn, 2), "btpEstabelecimentoListCombo");

		// Carregando o combobox de formas de pagamento
		BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
		this.setListOnRequest(request, busFormaPagamento.consultar(btpFormaPagamento, conn, 2), "btpFormaPagamentoListCombo");

		// Preparando as consultas de consta de origem e destino
		BusConta busConta = new BusConta();
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(frmObjetivo.getBtpObjetivo().getBtpUsuario());
		btpConta.getMap().put("conAuxOnlyRootElements", "true");

		// Setar o btpConta para recuperar apenas as contas de origem
		btpConta.getMap().put("conAuxOnlyOrigin", "true");
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 1), "btpContaOrigemListCombo");

		// Setar o btpConta para recuperar apenas as contas de destino
		btpConta.getMap().remove("conAuxOnlyOrigin");
		btpConta.getMap().put("conAuxOnlyDestiny", "true");
		this.setListOnRequest(request, busConta.consultar(btpConta, conn, 1), "btpContaDestinoListCombo");

		return true;
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusObjetivo bc) {return false;}
}