package br.com.dfdevforge.sisfin.business;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsInsertItemExtratoByTable;
import br.com.dfdevforge.sisfin.persistence.PrsUpdateItemExtratoByField;

public class BusItemExtrato extends BusAbstract
{
	BtpItemExtrato btpItemExtrato;
	PrsInsertItemExtratoByTable prs;

	public List<BtpItemExtrato> cadastrar(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpItemExtrato> editar(AbstractBean to, ConnectionManager ct) throws SQLException, ParseException
	{
		return null;
	}

	public void excluir(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException
	{
	}

	public List<BtpItemExtrato> exibir(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpItemExtrato> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, ParseException
	{
		return null;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException, SessionUserNotFoundException, IOException
	{
		btpItemExtrato = (BtpItemExtrato) to;

		prs = new PrsInsertItemExtratoByTable(conn);
		prs.execute(btpItemExtrato);
	}

	public void alterar(AbstractBean to, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, TimezoneValueException
	{
		
	}

	public void alterarPorCampo(AbstractBean to, ConnectionManager conn) throws TimezoneValueException, SQLException, NullBeanException, RequiredColumnNotFoundException
	{
		btpItemExtrato = (BtpItemExtrato) to;

		PrsUpdateItemExtratoByField p = new PrsUpdateItemExtratoByField(conn);
		p.execute(btpItemExtrato);
	}

	public void marcarExportado(BtpItemExtrato btpItemExtrato, ConnectionManager conn) throws TimezoneValueException, SQLException, NullBeanException, RequiredColumnNotFoundException
	{
		PrsUpdateItemExtratoByField prsUpdateIteExtratoByField = new PrsUpdateItemExtratoByField(conn);
		prsUpdateIteExtratoByField.execute(btpItemExtrato);
	}
}