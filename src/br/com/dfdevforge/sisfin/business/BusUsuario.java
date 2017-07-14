package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.exception.LoginFailException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectUsuarioParameterized;

public class BusUsuario extends BusAbstract
{
	BtpUsuario btpUsuario;
	PrsSelectUsuarioParameterized prs;

	public List<BtpUsuario> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, TimezoneValueException
	{
		btpUsuario = (BtpUsuario) to;
		prs = new PrsSelectUsuarioParameterized(conn);

		List<BtpUsuario> btpUsuarioList = prs.execute(btpUsuario, 0);

		return btpUsuarioList;
	}

	public List<BtpUsuario> authenticate(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws LoginFailException, SQLException, TimezoneValueException
	{
		List<BtpUsuario> btpUsuarioList = consultar(to, conn, 0);

		return btpUsuarioList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) {}
	public void alterar(AbstractBean to, ConnectionManager conn) {}
	public void excluir(AbstractBean to, ConnectionManager conn) {}
	public List<BtpUsuario> editar(AbstractBean to, ConnectionManager ct) {return null;}
	public List<BtpUsuario> exibir(AbstractBean to, ConnectionManager ct) {return null;}
	public List<BtpUsuario> cadastrar(AbstractBean to, ConnectionManager ct) {return null;}
}