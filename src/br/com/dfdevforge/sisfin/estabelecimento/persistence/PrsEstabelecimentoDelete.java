package br.com.dfdevforge.sisfin.estabelecimento.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.cagece.core.persistence.api.DeleteStrategy;
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimentoConfig;

public class PrsEstabelecimentoDelete extends DeleteStrategy<BtpEstabelecimento>
{
	public PrsEstabelecimentoDelete(BtpEstabelecimento btpEstabelecimento, ConnectionManager connenction) throws SQLException, JpaMappingNotFoundException
	{
		this.beanConfig = new BtpEstabelecimentoConfig(btpEstabelecimento);
		this.connection = connenction;
	}
}