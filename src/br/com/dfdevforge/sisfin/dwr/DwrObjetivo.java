package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpRegra;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.behavior.TransferObject;
import br.com.dfdevforge.sisfin.business.BusConta;
import br.com.dfdevforge.sisfin.business.BusEstabelecimento;
import br.com.dfdevforge.sisfin.business.BusFormaPagamento;
import br.com.dfdevforge.sisfin.business.BusObjetivo;
import br.com.dfdevforge.sisfin.business.BusRegra;
import br.com.dfdevforge.sisfin.business.BusTemplate;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.persistence.PrsEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectContaGroupingTotal;
import br.com.dfdevforge.sisfin.persistence.PrsSelectResumeByConta;
import br.com.dfdevforge.sisfin.util.Utils;

public class DwrObjetivo extends DwrAbstract
{
	public DwrReturn execute(BtpObjetivo btpObjetivo, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();
		BusObjetivo busObjetivo = null;
		ConnectionManager dbConn = null;

		try
		{
			dbConn = new ConnectionManager();
			busObjetivo = new BusObjetivo(dbConn);
			btpObjetivo.setBtpUsuario(DWRUtil.getSessionUser());
			dwrReturn.setMsgContainer(new ArrayList<String>());

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(btpObjetivo, dbConn, busObjetivo, dwrReturn);
			}
			else if (command.equals(EnumComandoNavegacao.actShowEditForm.getNome()))
			{
				actShowEditForm(btpObjetivo, busObjetivo);
			}
			else if (command.equals(EnumComandoNavegacao.actShowInclForm.getNome()))
			{
				actShowInclForm(btpObjetivo, busObjetivo);
			}
			else if (command.equals(EnumComandoNavegacao.actExecDelete.getNome()))
			{
				actExecDelete(btpObjetivo, dbConn, busObjetivo);
				dwrReturn.getMsgContainer().add("Exclusão realizada com sucesso!");
			}
			else if (command.equals(EnumComandoNavegacao.actExecUpdate.getNome()))
			{
				actExecUpdate(btpObjetivo, busObjetivo);
			}
			else if (command.equals(EnumComandoNavegacao.actExecInsert.getNome()))
			{
				actExecInsert(btpObjetivo, busObjetivo);
			}
			else if (command.equals(EnumComandoNavegacao.actExecSearch.getNome()))
			{
				actExecSearch(btpObjetivo, dbConn, busObjetivo);
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

		if (dwrReturn.getMsgContainer().isEmpty())
			dwrReturn.getMsgContainer().add("Operação realizada com sucesso!");

		return dwrReturn;
	}

	public List<BtpEstabelecimento> getBtpEstabelecimentoList() throws Exception
	{
		ConnectionManager dbConn = null;
		List<BtpEstabelecimento> btpEstabelecimentoList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
			btpEstabelecimento.setBtpUsuario(btpUsuario);

			BusEstabelecimento b = new BusEstabelecimento();
			b.consultar(btpEstabelecimento, dbConn, 1);

			PrsEstabelecimento prsEstabelecimento = new PrsEstabelecimento(dbConn);
			btpEstabelecimentoList = prsEstabelecimento.select(null, 1);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpEstabelecimentoList;
	}

	public List<BtpConta> getBtpContaList() throws Exception
	{
		ConnectionManager dbConn = null;
		List<BtpConta> btpContaList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			BusConta busConta = new BusConta();
			BtpConta btpConta = new BtpConta();
			btpConta.setBtpUsuario(btpUsuario);

			btpConta.getMap().put("auxFlgNivel", "1");
			btpContaList = busConta.consultar(btpConta, dbConn, 3);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpContaList;
	}

	public List<BtpFormaPagamento> getBtpFormaPagamentoList() throws Exception
	{
		ConnectionManager dbConn = null;
		List<BtpFormaPagamento> btpFormaPagamentoList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			BusFormaPagamento busFormaPagamento = new BusFormaPagamento();
			BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();
			btpFormaPagamento.setBtpUsuario(btpUsuario);

			btpFormaPagamentoList = busFormaPagamento.consultar(btpFormaPagamento, dbConn, 2);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpFormaPagamentoList;
	}

	public List<BtpTemplate> getBtpTemplateList() throws Exception
	{
		ConnectionManager dbConn = null;
		List<BtpTemplate> btpTemplateList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			BtpTemplate btpTemplate = new BtpTemplate();
			btpTemplate.setBtpUsuario(btpUsuario);

			BusTemplate busTemplate = new BusTemplate();

			btpTemplateList = busTemplate.consultar(btpTemplate, dbConn, 1);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (SessionUserNotFoundException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpTemplateList;
	}

	public List<BtpRegra> getBtpRegraList() throws Exception
	{
		ConnectionManager dbConn = null;
		List<BtpRegra> btpRegraList = null;

		try
		{
			dbConn = new ConnectionManager();

			BtpRegra btpRegra = new BtpRegra();

			BusRegra busRegra = new BusRegra();

			btpRegraList = busRegra.consultar(btpRegra, dbConn, 2);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (SessionUserNotFoundException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpRegraList;
	}

	@Deprecated
	public void actExecInsertDeprecated(BtpObjetivo btpObjetivo) throws Exception
	{
		ConnectionManager dbConn = null;
		BusObjetivo bus = new BusObjetivo(dbConn);
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			btpObjetivo.setBtpUsuario(btpUsuario);

			Date now = new Date();
			for (BtpMovimento btpMovimento : btpObjetivo.getBtpMovimentoList())
				btpMovimento.setMovDatRegistro(now);

			bus.incluir(btpObjetivo);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (RequiredColumnNotFoundException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (NullBeanException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}
	}

	@Deprecated
	public void actExecUpdate(BtpObjetivo btpObjetivo) throws Exception
	{
		ConnectionManager dbConn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbConn);
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			btpObjetivo.setBtpUsuario(btpUsuario);

			Date now = new Date();
			for (BtpMovimento btpMovimento : btpObjetivo.getBtpMovimentoList())
				btpMovimento.setMovDatRegistro(now);

			busObjetivo.alterar(btpObjetivo);

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (SessionUserNotFoundException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (RequiredColumnNotFoundException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (NullBeanException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}
	}

	@Deprecated
	public List<BtpObjetivo> actExecSelect(BtpObjetivo btpObjetivo) throws Exception
	{
		ConnectionManager dbConn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbConn);
		List<BtpObjetivo> btpObjetivoList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			if (!Utils.hasValue(btpObjetivo.getMap().get("sqlOrder")))
				btpObjetivo.getMap().put("sqlOrder", "1");

			btpObjetivo.setBtpUsuario(btpUsuario);
			btpObjetivoList = busObjetivo.consultar(btpObjetivo, Integer.parseInt(btpObjetivo.getMap().get("sqlOrder")));

			BtpConta btpConta = new BtpConta();
			btpConta.setMap(btpObjetivo.getMap());

			PrsSelectResumeByConta prs = new PrsSelectResumeByConta(dbConn);
			setAttribute("btpTotalConta", prs.execute(btpConta, 1));

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (TimezoneValueException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpObjetivoList;
	}

	@Deprecated
	public void actExecDelete(BtpObjetivo btpObjetivo) throws Exception
	{
		ConnectionManager dbconn = null;
		BusObjetivo busObjetipo = new BusObjetivo(dbconn);
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbconn = new ConnectionManager();

			btpObjetivo.setBtpUsuario(btpUsuario);

			busObjetipo.excluir(btpObjetivo);

			dbconn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbconn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (RequiredColumnNotFoundException e)
		{
			e.printStackTrace();
			dbconn.rollback();
		}
		catch (NullBeanException e)
		{
			e.printStackTrace();
			dbconn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbconn);
		}
	}

	public List<BtpConta> actExecSelectResume(BtpMovimento btpMovimento) throws Exception
	{
		List<BtpConta> btpContaList = null;
		BusConta bus = new BusConta();
		ConnectionManager dbConn = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbConn = new ConnectionManager();

			if (!Utils.hasValue(btpMovimento.getMap().get("sqlOrder")))
				btpMovimento.getMap().put("sqlOrder", "1");

			btpMovimento.getBtpObjetivo().setBtpUsuario(btpUsuario);
			btpContaList = bus.busExecSelectResume(btpMovimento, dbConn, Integer.parseInt(btpMovimento.getMap().get("sqlOrder")));

			dbConn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbConn.rollback();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			ConnectionManager.closeConnection(dbConn);
		}

		return btpContaList;
	}

	public List<BtpObjetivo> showEditForm(BtpObjetivo btpObjetivo) throws Exception
	{
		ConnectionManager dbconn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbconn);
		List<BtpObjetivo> btpObjetivoList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			dbconn = new ConnectionManager();

			btpObjetivo.setBtpUsuario(btpUsuario);
			btpObjetivoList = busObjetivo.editar(btpObjetivo);

			dbconn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			dbconn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(dbconn);
		}

		return btpObjetivoList;
	}

	
	public Map<String, List<? extends TransferObject>> loadAuxiliaryList() throws Exception
	{
		Map<String, List<? extends TransferObject>> map = new HashMap<String, List<? extends TransferObject>>();

//		map.put("btpTemplateList", this.getBtpTemplateList());

		return map;
	}

	public void actExecInsert(BtpObjetivo btpObjetivo, BusObjetivo busObjetivo) throws Exception
	{
		busObjetivo.incluir(btpObjetivo);
	}

	public void actExecDelete(BtpObjetivo btpObjetivo, ConnectionManager dbConn, BusObjetivo busObjetivo) throws NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, SQLException
	{
		busObjetivo.excluir(btpObjetivo);
	}

	public boolean actExecSearch(BtpObjetivo btpObjetivo, ConnectionManager dbConn, BusObjetivo bc) throws Exception
	{
		return false;
	}

	public boolean actExecUpdate(BtpObjetivo btpObjetivo, BusObjetivo busObjetivo) throws Exception
	{
		Date now = new Date();

		for (BtpMovimento btpMovimento : btpObjetivo.getBtpMovimentoList())
			btpMovimento.setMovDatRegistro(now);

		busObjetivo.alterar(btpObjetivo);

		return false;
	}

	public boolean actShowInclForm(BtpObjetivo btpObjetivo, BusObjetivo busObjetivo) throws Exception
	{
		return false;
	}

	public boolean actShowEditForm(BtpObjetivo btpObjetivo, BusObjetivo busObjetivo) throws Exception
	{
		return false;
	}

	public boolean actShowMainPage(BtpObjetivo btpObjetivo, ConnectionManager dbConn, BusObjetivo bc, DwrReturn dwrReturn) throws Exception
	{
		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(btpObjetivo.getBtpUsuario());
		btpConta.setMap(btpObjetivo.getMap());

		PrsSelectContaGroupingTotal prs = new PrsSelectContaGroupingTotal(dbConn);
		List<BtpConta> btpContaList = prs.execute(btpConta, 0);

		dwrReturn.setBtpResultList(btpContaList);

		return false;
	}
}