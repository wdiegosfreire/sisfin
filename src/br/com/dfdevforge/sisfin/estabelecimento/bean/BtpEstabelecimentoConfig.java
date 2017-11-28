package br.com.dfdevforge.sisfin.estabelecimento.bean;

import br.com.cagece.core.persistence.api.BeanConfig;
import br.com.cagece.core.persistence.exception.JpaMappingNotFoundException;

public class BtpEstabelecimentoConfig extends BeanConfig<BtpEstabelecimento>
{
	public BtpEstabelecimentoConfig(BtpEstabelecimento btpEstabelecimento) throws JpaMappingNotFoundException
	{
		this.configByAnnotation(btpEstabelecimento);
	}
}