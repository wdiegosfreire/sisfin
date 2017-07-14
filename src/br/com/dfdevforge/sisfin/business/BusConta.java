package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsConta;
import br.com.dfdevforge.sisfin.persistence.PrsSelectContaParameterized;

public class BusConta extends BusAbstract
{
	BtpConta btpConta;

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsConta prs = new PrsConta(conn);
		prs.update(to);
	}

	public List<BtpConta> cadastrar(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpConta> editar(AbstractBean to, ConnectionManager ct) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return this.consultar(to, ct, 0);
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsConta prs = new PrsConta(conn);
		prs.delete(to);
	}

	public List<AbstractBean> exibir(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpConta> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		BtpConta btpConta = (BtpConta) to;
		PrsSelectContaParameterized prs = new PrsSelectContaParameterized(conn);
		List<BtpConta> btpContaList = prs.execute(btpConta, sqlOrdem);

		return btpContaList;
	}

	public List<BtpConta> busExecSelectResume(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, TimezoneValueException
	{
		PrsConta prs = new PrsConta(conn);
		List<BtpConta> btpContaList = prs.selectResume(to);

		return btpContaList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		PrsConta prs = new PrsConta(conn);
		prs.insert(to);
	}
}