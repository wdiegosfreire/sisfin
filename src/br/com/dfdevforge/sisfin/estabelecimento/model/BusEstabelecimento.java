package br.com.dfdevforge.sisfin.estabelecimento.model;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;
import br.com.cagece.core.persistence.exception.RequiredFieldNotFoundException;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;

public class BusEstabelecimento
{
	private ConnectionManager connectionManager;

	public BusEstabelecimento(ConnectionManager connectionManager)
	{
		this.connectionManager = connectionManager;
	}

	public void incluir(BtpEstabelecimento btpEstabelecimento) throws SQLException, SessionUserNotFoundException, JpaMappingNotFoundException, Exception
	{
		PrsEstabelecimentoInsert prsEstabelecimento = new PrsEstabelecimentoInsert(btpEstabelecimento, this.connectionManager);
		prsEstabelecimento.execute();

		if (prsEstabelecimento.getAffectedRows() != 1)
			throw new Exception("O número de registros inseridos foi diferente de 1");
	}

	public void alterar(BtpEstabelecimento btpEstabelecimento) throws SQLException, RequiredFieldNotFoundException, SessionUserNotFoundException, JpaMappingNotFoundException, Exception
	{
		PrsEstabelecimentoUpdate prsEstabelecimento = new PrsEstabelecimentoUpdate(btpEstabelecimento, this.connectionManager);
		prsEstabelecimento.execute();
		
		if (prsEstabelecimento.getAffectedRows() != 1)
			throw new Exception("O número de registros inseridos foi diferente de 1");
	}

	public void excluir(BtpEstabelecimento btpEstabelecimento) throws SQLException, RequiredFieldNotFoundException, SessionUserNotFoundException, JpaMappingNotFoundException, Exception
	{
		PrsEstabelecimentoDelete prsEstabelecimento = new PrsEstabelecimentoDelete(btpEstabelecimento, this.connectionManager);
		prsEstabelecimento.execute();
		
		if (prsEstabelecimento.getAffectedRows() != 1)
			throw new Exception("O número de registros inseridos foi diferente de 1");
	}

	/*
	 * Métodos não refatorados
	 */
	public void cadastrar(BtpEstabelecimento btpEstabelecimento)
	{
	}

	public List<BtpEstabelecimento> editar(BtpEstabelecimento btpEstabelecimento) throws SessionUserNotFoundException, SQLException
	{
		return this.consultar(btpEstabelecimento, 0);
	}

//	public void excluir(BtpEstabelecimento btpEstabelecimento) throws SQLException, NullBeanException, RequiredColumnNotFoundException, SessionUserNotFoundException
//	{
//		PrsEstabelecimento prs = new PrsEstabelecimento(connectionManager);
//		prs.delete(btpEstabelecimento);
//	}

	public void exibir(BtpEstabelecimento btpEstabelecimento)
	{
	}

	public List<BtpEstabelecimento> consultar(BtpEstabelecimento btpEstabelecimento, int sqlOrdem) throws SQLException, SessionUserNotFoundException
	{
		PrsEstabelecimento prs = new PrsEstabelecimento(connectionManager);
		List<BtpEstabelecimento> btpEstabelecimentoList = prs.select(btpEstabelecimento, sqlOrdem);

		return btpEstabelecimentoList;
	}
}