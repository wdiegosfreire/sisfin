package br.com.dfdevforge.sisfin.estabelecimento.persistence;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.cagece.core.persistence.api.DeleteStrategy;
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimentoConfig;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsEstabelecimentoDelete extends DeleteStrategy<BtpEstabelecimento>
{
	public PrsEstabelecimentoDelete(BtpEstabelecimento btpEstabelecimento, ConnectionManager connenction) throws JpaMappingNotFoundException, SessionUserNotFoundException
	{
		if (btpEstabelecimento == null || btpEstabelecimento.getBtpUsuario() == null || !Utils.hasValue(btpEstabelecimento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		this.beanConfig = new BtpEstabelecimentoConfig(btpEstabelecimento);
		this.connection = connenction;
	}
}