package br.com.dfdevforge.sisfin.estabelecimento.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.cagece.core.persistence.InsertStrategy;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimentoConfig;

public class PrsEstabelecimentoInsert extends InsertStrategy<BtpEstabelecimento>
{
	public PrsEstabelecimentoInsert(BtpEstabelecimento btpEstabelecimento, ConnectionManager connenction) throws SQLException
	{
		this.beanConfig = new BtpEstabelecimentoConfig(btpEstabelecimento);
		this.connection = connenction;
	}
}