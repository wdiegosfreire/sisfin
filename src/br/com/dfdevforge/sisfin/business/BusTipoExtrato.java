package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectTipoExtratoParameterized;

public class BusTipoExtrato extends BusAbstract
{
	BtpTipoExtrato btpTipoExtrato;

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
	}

	public List<BtpTipoExtrato> cadastrar(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpTipoExtrato> editar(AbstractBean to, ConnectionManager conn) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return this.consultar(to, conn, 0);
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
	}

	public List<BtpTipoExtrato> exibir(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpTipoExtrato> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		btpTipoExtrato = (BtpTipoExtrato) to;

		PrsSelectTipoExtratoParameterized prs = new PrsSelectTipoExtratoParameterized(conn);
		List<BtpTipoExtrato> btpTipoExtratoList = prs.execute(btpTipoExtrato, sqlOrdem);

		return btpTipoExtratoList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
	}
}