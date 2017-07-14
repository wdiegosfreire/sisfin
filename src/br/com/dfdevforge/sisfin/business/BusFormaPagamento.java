package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsFormaPagamento;

public class BusFormaPagamento extends BusAbstract
{
	BtpFormaPagamento btpFormaPagamento;

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsFormaPagamento prs = new PrsFormaPagamento(conn);
		prs.update(to);
	}

	public List<BtpFormaPagamento> cadastrar(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpFormaPagamento> editar(AbstractBean to, ConnectionManager conn) throws SQLException, TimezoneValueException
	{
		return this.consultar(to, conn, 0);
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsFormaPagamento prs = new PrsFormaPagamento(conn);
		prs.delete(to);
	}

	public List<BtpFormaPagamento> exibir(AbstractBean to, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpFormaPagamento> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, TimezoneValueException
	{
		PrsFormaPagamento prs = new PrsFormaPagamento(conn);
		List<BtpFormaPagamento> btpFormaPagamentoList = prs.select(to, sqlOrdem);

		return btpFormaPagamentoList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		PrsFormaPagamento prs = new PrsFormaPagamento(conn);
		prs.insert(to);
	}
}