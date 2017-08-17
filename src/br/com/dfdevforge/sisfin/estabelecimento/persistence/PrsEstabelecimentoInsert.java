package br.com.dfdevforge.sisfin.estabelecimento.persistence;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.cagece.core.persistence.api.InsertStrategy;
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimentoConfig;

public class PrsEstabelecimentoInsert extends InsertStrategy<BtpEstabelecimento>
{
	public PrsEstabelecimentoInsert(BtpEstabelecimento btpEstabelecimento, ConnectionManager connenction) throws JpaMappingNotFoundException
	{
		this.beanConfig = new BtpEstabelecimentoConfig(btpEstabelecimento);
		this.connection = connenction;
	}
}