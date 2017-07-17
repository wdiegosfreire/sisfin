package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplateRegra;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.business.BusTemplateRegra;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;

public class DwrTemplateRegra
{
	public List<BtpTemplateRegra> actExecSelect(BtpTemplateRegra btpTemplateRegra) throws Exception
	{
		ConnectionManager conn = null;
		List<BtpTemplateRegra> btpTemplateRegraList = null;
		BtpUsuario btpUsuario = DWRUtil.getSessionUser();

		try
		{
			conn = new ConnectionManager();

			btpTemplateRegra.getBtpTemplate().setBtpUsuario(btpUsuario);

			BusTemplateRegra bus = new BusTemplateRegra();
			btpTemplateRegraList = bus.consultar(btpTemplateRegra, conn, 1);

			conn.commit();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		catch (SessionUserNotFoundException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}

		return btpTemplateRegraList;
	}
}