package br.com.dfdevforge.sisfin.dwr;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.ftp.exception.FtpLogoutFailedException;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusMovimentoNew;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.model.BusEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.util.Utils;

public class DwrMovimentoNew extends DwrAbstract
{
	public DwrReturn execute(BtpCompetencia btpCompetencia, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();
		BusMovimentoNew busMovimentoNew = null;
		ConnectionManager dbConn = null;

		if (Utils.hasValue(btpCompetencia.getMap().get("radEscopoConsulta"), "mes"))
			this.setCompetencia(btpCompetencia);

		try
		{
			dbConn = new ConnectionManager();
			busMovimentoNew = new BusMovimentoNew(dbConn);
			btpCompetencia.setBtpUsuario(DWRUtil.getSessionUser());
			dwrReturn.setMsgContainer(new ArrayList<String>());

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(btpCompetencia, dbConn, busMovimentoNew, dwrReturn);
				dwrReturn.getMsgContainer().add("Página principal exibida com sucesso!");
			}
			else if (command.equals(EnumComandoNavegacao.actExecInsert.getNome()))
			{
				actShowMainPage(btpCompetencia, dbConn, busMovimentoNew, dwrReturn);
				dwrReturn.getMsgContainer().add("Página principal exibida com sucesso!");
			}
			else if (command.equals(EnumComandoNavegacao.actShowEditForm.getNome()))
			{
				actShowEditForm(btpCompetencia, dbConn, busMovimentoNew, dwrReturn);
				dwrReturn.getMsgContainer().add("Página principal exibida com sucesso!");
			}

			dbConn.commit();
		}
		catch (NullBeanException e)
		{
			dwrReturn.getMsgContainer().add(e.getMessage());
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (Exception e)
		{
			dwrReturn.getMsgContainer().add("Ocorreu um erro inesperado. Para maiores detalhes entre tem contato com o administrador do sistema.");
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return dwrReturn;
	}

	public boolean actShowMainPage(BtpCompetencia btpCompetencia, ConnectionManager conn, BusMovimentoNew busMovimentoNew, DwrReturn dwrReturn) throws Exception
	{
		dwrReturn.setMapResult(busMovimentoNew.showMainPage(btpCompetencia));

		return false;
	}

	public boolean actShowEditForm(BtpCompetencia btpCompetencia, ConnectionManager conn, BusMovimentoNew busMovimentoNew, DwrReturn dwrReturn) throws Exception
	{
		dwrReturn.setMapResult(busMovimentoNew.showEditForm(btpCompetencia));
		
		return false;
	}

	public boolean actExecInsert(BtpCompetencia btpCompetencia, ConnectionManager conn, BusMovimentoNew busMovimentoNew, DwrReturn dwrReturn) throws Exception
	{
		dwrReturn.setMapResult(busMovimentoNew.showMainPage(btpCompetencia));
		
		return false;
	}

	public DwrReturn loadEstabelecimentoCombo(BtpEstabelecimento btpEstabelecimento) throws SQLException, IOException, FtpLogoutFailedException
	{
		DwrReturn dwrReturn = new DwrReturn();
		ConnectionManager connectionManager = null;

		try
		{
			connectionManager = new ConnectionManager();

			if (btpEstabelecimento == null)
				btpEstabelecimento = new BtpEstabelecimento();

			btpEstabelecimento.setBtpUsuario(DWRUtil.getSessionUser());

			BusEstabelecimento busEstabelecimento = new BusEstabelecimento(connectionManager);
			List<BtpEstabelecimento> btpEstabelecimentoListCombo = busEstabelecimento.consultar(btpEstabelecimento, 2);

			dwrReturn.setBtpResultList(btpEstabelecimentoListCombo);

			connectionManager.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			connectionManager.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(connectionManager);
		}

		return dwrReturn;
	}

	public DwrReturn loadContaCombo(BtpConta btpConta) throws SQLException, IOException, FtpLogoutFailedException
	{
		DwrReturn dwrReturn = new DwrReturn();
		ConnectionManager dbConn = null;
		
		try
		{
			dbConn = new ConnectionManager();
			
			if (btpConta == null)
				btpConta = new BtpConta();
			
			btpConta.setBtpUsuario(DWRUtil.getSessionUser());
			
			BusConta busConta = new BusConta();
			List<BtpConta> btpContaListCombo = busConta.consultar(btpConta, dbConn, 1);
			
			dwrReturn.setBtpResultList(btpContaListCombo);
			
			dbConn.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}
		
		return dwrReturn;
	}

	public DwrReturn loadFormaPagamentoCombo(BtpFormaPagamento btpFormaPagamento) throws SQLException, IOException, FtpLogoutFailedException
	{
		DwrReturn dwrReturn = new DwrReturn();
		ConnectionManager dbConn = null;

		try
		{
			dbConn = new ConnectionManager();

			if (btpFormaPagamento == null)
				btpFormaPagamento = new BtpFormaPagamento();

			btpFormaPagamento.setBtpUsuario(DWRUtil.getSessionUser());

			BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
			List<BtpFormaPagamento> btpFormaPagamentoListCombo = busFormaPagamento.consultar(btpFormaPagamento, dbConn, 2);

			dwrReturn.setBtpResultList(btpFormaPagamentoListCombo);

			dbConn.commit();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return dwrReturn;
	}
}
