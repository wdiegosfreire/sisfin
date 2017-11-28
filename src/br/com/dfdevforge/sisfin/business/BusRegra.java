package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpRegra;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectRegraParameterized;

public class BusRegra extends BusAbstract
{
	BtpRegra btpRegra;

	public List<BtpRegra> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		btpRegra = (BtpRegra) to;

		PrsSelectRegraParameterized prs = new PrsSelectRegraParameterized(conn);
		List<BtpRegra> btpRegraList = prs.execute(btpRegra, sqlOrdem);

		return btpRegraList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) {}
	public void excluir(AbstractBean to, ConnectionManager conn) {}
	public void alterar(AbstractBean to, ConnectionManager conn) {}
	public List<BtpRegra> editar(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpRegra> exibir(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpRegra> cadastrar(AbstractBean to, ConnectionManager conn) {return null;}
}