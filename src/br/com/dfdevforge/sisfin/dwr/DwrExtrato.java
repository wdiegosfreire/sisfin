package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.business.BusItemExtrato;
import br.com.dfdevforge.sisfin.business.BusObjetivo;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public class DwrExtrato
{
	public DwrReturn execute(BtpExtrato btpExtrato, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();

		ConnectionManager dbConn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbConn);

		try
		{
			dbConn = new ConnectionManager();
			btpExtrato.setBtpUsuario(DWRUtil.getSessionUser());
			dwrReturn.setMsgContainer(new ArrayList<String>());

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(btpExtrato, dbConn, busObjetivo, dwrReturn);
			}
			else if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(btpExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(btpExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(btpExtrato, busObjetivo);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(btpExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(btpExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecSearch))
			{
				actExecSearch(btpExtrato, dbConn, busObjetivo);
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

	public boolean actExecInsert(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc) throws Exception
	{
		BusObjetivo busObjetivo = new BusObjetivo(conn);
		BusItemExtrato busItemExtrato = new BusItemExtrato();

		for (BtpObjetivo btpObjetivo : btpExtrato.getBtpObjetivoList())
		{
			// Insere um registro de movimento correspondente ao item do extrato importado
			btpObjetivo.setBtpUsuario(btpExtrato.getBtpUsuario());

			Date now = new Date();
			for (BtpMovimento btpMovimento : btpObjetivo.getBtpMovimentoList())
				btpMovimento.setMovDatRegistro(now);

			busObjetivo.incluir(btpObjetivo);

			// TODO Marca o item do extrato para exportado, de modo que este não possa ser exportado mais de uma vez
			BtpItemExtrato btpItemExtrato = new BtpItemExtrato();
			btpItemExtrato.setIteCodItemExtrato(Integer.parseInt(btpObjetivo.getMap().get("iteCodItemExtrato")));
			btpItemExtrato.setIteFlgExportado(Boolean.TRUE);

			busItemExtrato.marcarExportado(btpItemExtrato, conn);
		}

		return false;
	}

	public void actExecDelete(BtpExtrato btpExtrato, BusObjetivo busObjetivo) throws NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, SQLException
	{
		busObjetivo.excluir(btpExtrato);
	}

	public boolean actExecSearch(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc) throws Exception
	{
		
		return false;
	}

	public boolean actExecUpdate(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc) throws Exception
	{
		return false;
	}

	public boolean actShowInclForm(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc) throws Exception
	{
		return false;
	}

	public boolean actShowEditForm(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc) throws Exception
	{
		return false;
	}

	public boolean actShowMainPage(BtpExtrato btpExtrato, ConnectionManager conn, BusObjetivo bc, DwrReturn dwrReturn) throws Exception
	{
		return false;
	}
}