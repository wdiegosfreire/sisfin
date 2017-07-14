package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;
import java.util.ArrayList;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.business.BusItemExtrato;
import br.com.dfdevforge.sisfin.business.BusObjetivo;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public class DwrItemExtrato
{
	public DwrReturn execute(BtpItemExtrato btpItemExtrato, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();

		ConnectionManager dbConn = null;
		BusObjetivo busObjetivo = new BusObjetivo(dbConn);

		try
		{
			dbConn = new ConnectionManager();
			dwrReturn.setMsgContainer(new ArrayList<String>());

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(btpItemExtrato, dbConn, busObjetivo, dwrReturn);
			}
			else if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(btpItemExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(btpItemExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(btpItemExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(btpItemExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(btpItemExtrato, dbConn, busObjetivo);
			}
			else if (command.equals(Constants.actExecSearch))
			{
				actExecSearch(btpItemExtrato, dbConn, busObjetivo);
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

	public boolean actExecUpdate(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) throws NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, SQLException
	{
		BusItemExtrato b = new BusItemExtrato();
		b.alterarPorCampo(btpItemExtrato, conn);

		return false;
	}

	public boolean actExecDelete(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) throws NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, SQLException
	{
		BusItemExtrato busItemExtrato = new BusItemExtrato();

		busItemExtrato.excluir(btpItemExtrato, conn);

		return false;
	}

	public boolean actExecSearch(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actExecInsert(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actShowInclForm(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actShowEditForm(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc) {return false;}
	public boolean actShowMainPage(BtpItemExtrato btpItemExtrato, ConnectionManager conn, BusObjetivo bc, DwrReturn dwrReturn) {return false;}
}