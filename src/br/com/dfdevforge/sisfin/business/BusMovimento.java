package br.com.dfdevforge.sisfin.business;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsDeleteMovimentoByObjetivo;
import br.com.dfdevforge.sisfin.persistence.PrsMovimento;

public class BusMovimento extends BusAbstract
{
	BtpMovimento btpMovimento;

	public void updatePaymentDate(AbstractBean to, ConnectionManager conn) throws NullBeanException, SQLException, TimezoneValueException
	{
		PrsMovimento p = new PrsMovimento(conn);
		p.updateByField(to);
	}

	public List<BtpMovimento> cadastrar(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpMovimento> editar(AbstractBean to, ConnectionManager ct) throws SQLException, ParseException
	{
		return null;
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException
	{
	}

	public void deleteByObjetivo(AbstractBean to, ConnectionManager conn) throws TimezoneValueException, SQLException, NullBeanException, RequiredColumnNotFoundException
	{
		PrsDeleteMovimentoByObjetivo prs = new PrsDeleteMovimentoByObjetivo(conn);

		prs.execute((BtpMovimento) to);
	}

	public List<BtpMovimento> exibir(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpMovimento> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, ParseException
	{
		return null;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException, IOException
	{
		BtpMovimento btpMovimento = (BtpMovimento) to;

		PrsMovimento prsMovimento = new PrsMovimento(conn);
//		prsMovimento.insertDeprecated(btpMovimento);
		prsMovimento.insert(btpMovimento);
	}

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, TimezoneValueException
	{
		BtpMovimento btpMovimento = (BtpMovimento) to;

		PrsMovimento prsMovimento = new PrsMovimento(conn);
		prsMovimento.update(btpMovimento);
	}
}