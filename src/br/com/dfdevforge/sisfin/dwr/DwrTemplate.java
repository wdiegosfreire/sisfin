package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.business.BusTemplate;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;

public class DwrTemplate
{
	public void actExecInsert(BtpTemplate btpTemplate) throws Exception
	{
		ConnectionManager conn = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			conn = new ConnectionManager();

			btpTemplate.setBtpUsuario(btpUsuario);

			BusTemplate bus = new BusTemplate();
			bus.incluir(btpTemplate, conn);

			conn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (RequiredColumnNotFoundException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (NullBeanException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}
	}
}