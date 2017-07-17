package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.persistence.PrsEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.persistence.PrsEstabelecimentoInsert;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public class BusEstabelecimento
{
	BtpEstabelecimento btpEstabelecimento;

	public void alterar(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsEstabelecimento prs = new PrsEstabelecimento(conn);
		prs.update(btpEstabelecimento);
	}

	public List<BtpEstabelecimento> cadastrar(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpEstabelecimento> editar(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		return this.consultar(btpEstabelecimento, conn, 0);
	}

	public void excluir(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsEstabelecimento prs = new PrsEstabelecimento(conn);
		prs.delete(btpEstabelecimento);
	}

	public List<BtpEstabelecimento> exibir(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn)
	{
		return null;
	}

	public List<BtpEstabelecimento> consultar(BtpEstabelecimento btpEstabelecimento, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		PrsEstabelecimento prs = new PrsEstabelecimento(conn);
		List<BtpEstabelecimento> btpEstabelecimentoList = prs.select(btpEstabelecimento, sqlOrdem);

		return btpEstabelecimentoList;
	}

	public void incluir(BtpEstabelecimento btpEstabelecimento, ConnectionManager connection) throws SQLException, Exception
	{
		PrsEstabelecimentoInsert prsEstabelecimento = new PrsEstabelecimentoInsert(btpEstabelecimento, connection);
		prsEstabelecimento.execute();

		if (prsEstabelecimento.getAffectedRows() != 1)
			throw new Exception("O número de registros inseridos foi diferente de 1");
	}
}