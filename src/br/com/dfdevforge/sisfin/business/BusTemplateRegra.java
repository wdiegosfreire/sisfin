package br.com.dfdevforge.sisfin.business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplateRegra;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsInsertTemplateRegraByTable;
import br.com.dfdevforge.sisfin.persistence.PrsSelectTemplateRegraParameterized;

public class BusTemplateRegra extends BusAbstract
{
	BtpTemplateRegra btpTemplateRegra;

	public List<BtpTemplateRegra> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		btpTemplateRegra = (BtpTemplateRegra) to;

		PrsSelectTemplateRegraParameterized prs = new PrsSelectTemplateRegraParameterized(conn);
		List<BtpTemplateRegra> btpTemplateRegraList = prs.execute(btpTemplateRegra, sqlOrdem);

		return btpTemplateRegraList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, IOException
	{
		btpTemplateRegra = (BtpTemplateRegra) to;

		PrsInsertTemplateRegraByTable prsInsertTemplateRegraByTable = new PrsInsertTemplateRegraByTable(conn);
		prsInsertTemplateRegraByTable.execute(btpTemplateRegra);
	}

	public void excluir(AbstractBean to, ConnectionManager conn) {}
	public void alterar(AbstractBean to, ConnectionManager conn) {}
	public List<BtpTemplateRegra> editar(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpTemplateRegra> exibir(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpTemplateRegra> cadastrar(AbstractBean to, ConnectionManager conn) {return null;}
}