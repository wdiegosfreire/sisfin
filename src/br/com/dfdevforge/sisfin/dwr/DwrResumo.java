package br.com.dfdevforge.sisfin.dwr;

import java.util.ArrayList;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.business.BusResumo;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.constants.EnumComandoNavegacao;
import br.com.dfdevforge.sisfin.exception.NullBeanException;

public class DwrResumo extends DwrAbstract
{
	public DwrReturn execute(BtpCompetencia btpCompetencia, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();
		BusResumo busResumo = new BusResumo();

		ConnectionManager dbConn = null;

		this.setCompetencia(btpCompetencia);

		try
		{
			dbConn = new ConnectionManager();
			btpCompetencia.setBtpUsuario(DWRUtil.getSessionUser());
			dwrReturn.setMsgContainer(new ArrayList<String>());

			if (command.equals(EnumComandoNavegacao.actShowMainPage.getNome()))
			{
				actShowMainPage(btpCompetencia, dbConn, busResumo, dwrReturn);
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

	public boolean actShowMainPage(BtpCompetencia btpCompetencia, ConnectionManager conn, BusResumo busResumo, DwrReturn dwrReturn) throws Exception
	{
		dwrReturn.setMapResult(busResumo.showMainPage(btpCompetencia, conn));

		return false;
	}
}