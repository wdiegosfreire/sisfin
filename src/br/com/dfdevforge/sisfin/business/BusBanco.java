package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectBancoParameterized;

public class BusBanco extends BusAbstract
{
	BtpEstabelecimento btpEstabelecimento;

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
	}

	public List<BtpEstabelecimento> cadastrar(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpEstabelecimento> editar(AbstractBean to, ConnectionManager conn) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return null;
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
	}

	public List<BtpEstabelecimento> exibir(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpBanco> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		BtpBanco btpBanco = (BtpBanco) to;

		PrsSelectBancoParameterized prs = new PrsSelectBancoParameterized(conn);
		List<BtpBanco> btpBancoList = prs.execute(btpBanco, sqlOrdem);

		return btpBancoList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
	}
}