package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.business.BusMovimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;

public class DwrMovimento
{
	public void actExecConfirmPaymentDate(BtpMovimento btpMovimento) throws Exception
	{
		BusMovimento busMovimento = new BusMovimento();
		ConnectionManager dbConn = null;

		try
		{
			dbConn = new ConnectionManager();

			busMovimento.updatePaymentDate(btpMovimento, dbConn);

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
}