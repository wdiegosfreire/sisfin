package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivoItem;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsDeleteObjetivoItemByObjetivo;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivoItem;

public class BusObjetivoItem extends BusAbstract
{
	BtpMovimento btpObjetivoItem;

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

	public void deleteByObjetivo(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, TimezoneValueException
	{
		PrsDeleteObjetivoItemByObjetivo prs = new PrsDeleteObjetivoItemByObjetivo(conn);

		prs.execute((BtpObjetivoItem) to);
	}

	public List<BtpMovimento> exibir(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpMovimento> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, ParseException
	{
		return null;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException
	{
		BtpObjetivoItem btpObjetivoItem = (BtpObjetivoItem) to;

		PrsObjetivoItem prsObjetivoItem = new PrsObjetivoItem(conn);
		prsObjetivoItem.insert(btpObjetivoItem);
	}

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, TimezoneValueException
	{
		BtpObjetivoItem btpObjetivoItem = (BtpObjetivoItem) to;

		PrsObjetivoItem prsObjetivoItem = new PrsObjetivoItem(conn);
		prsObjetivoItem.update(btpObjetivoItem);
	}
}