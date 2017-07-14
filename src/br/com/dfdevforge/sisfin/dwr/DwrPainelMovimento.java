package br.com.dfdevforge.sisfin.dwr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.business.BusPainelMovimento;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;

@Deprecated
public class DwrPainelMovimento
{
	public DwrReturn execute(BtpObjetivo btpObjetivo, String command) throws Exception
	{
		DwrReturn dwrReturn = new DwrReturn();
		BusPainelMovimento busPainelMovimento = new BusPainelMovimento();

		ConnectionManager conn = null;

		try
		{
			conn = new ConnectionManager();
			btpObjetivo.setBtpUsuario(DWRUtil.getSessionUser());
			dwrReturn.setMsgContainer(new ArrayList<String>());

			DWRUtil.getSession().setAttribute("sesNumMes", btpObjetivo.getMap().get("numMes"));
			DWRUtil.getSession().setAttribute("sesNumAno", btpObjetivo.getMap().get("numAno"));

			if (command.equals(Constants.actShowMainPage))
			{
				actShowMainPage(btpObjetivo, conn, busPainelMovimento, dwrReturn);
				dwrReturn.getMsgContainer().add("Página principal exibida com sucesso!");
			}
			else if (command.equals(Constants.actShowEditForm))
			{
				actShowEditForm(btpObjetivo, conn, busPainelMovimento, dwrReturn);
			}
			else if (command.equals(Constants.actShowInclForm))
			{
				actShowInclForm(btpObjetivo, conn, busPainelMovimento);
			}
			else if (command.equals(Constants.actExecDelete))
			{
				actExecDelete(btpObjetivo, conn, busPainelMovimento);
			}
			else if (command.equals(Constants.actExecUpdate))
			{
				actExecUpdate(btpObjetivo, conn, busPainelMovimento);
			}
			else if (command.equals(Constants.actExecInsert))
			{
				actExecInsert(btpObjetivo, conn, busPainelMovimento);
			}
			else if (command.equals(Constants.actExecSearch))
			{
				actExecSearch(btpObjetivo, conn, busPainelMovimento);
			}

			conn.commit();
		}
		catch (NullBeanException e)
		{
			dwrReturn.getMsgContainer().add(e.getMessage());
			e.printStackTrace();
			conn.rollback();
		}
		catch (Exception e)
		{
			dwrReturn.getMsgContainer().add("Ocorreu um erro inesperado. Para maiores detalhes entre tem contato com o administrador do sistema.");
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			ConnectionManager.closeConnection(conn);
		}

		return dwrReturn;
	}

	public boolean actShowMainPage(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc, DwrReturn dwrReturn) throws Exception
	{
		BusPainelMovimento busPainelMovimento = (BusPainelMovimento) bc;

		dwrReturn.setMapResult(busPainelMovimento.showMainPage(btpObjetivo, conn));

		return false;
	}

	public boolean actShowEditForm(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc, DwrReturn dwrReturn) throws SessionUserNotFoundException, SQLException
	{
		BusPainelMovimento busPainelMovimento = (BusPainelMovimento) bc;

		Map<String, AbstractBean> mapBean = new HashMap<String, AbstractBean>();
		mapBean.put("btpObjetivo", btpObjetivo);

		dwrReturn.setMapResult(busPainelMovimento.exibirFormularioCadastroEdicaoObjetivo(mapBean, conn));

		return true;
	}

	public boolean actExecInsert(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc) {return false;}
	public boolean actExecDelete(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc) {return false;}
	public boolean actExecSearch(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc) {return false;}
	public boolean actExecUpdate(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc) {return false;}
	public boolean actShowInclForm(BtpObjetivo btpObjetivo, ConnectionManager conn, BusinessControler bc) {return false;}
}