package br.com.dfdevforge.sisfin.estabelecimento.bean;

import br.com.cagece.core.persistence.BeanConfig;

public class BtpEstabelecimentoConfig extends BeanConfig<BtpEstabelecimento>
{
	public BtpEstabelecimentoConfig(BtpEstabelecimento btpEstabelecimento)
	{
		this.btpParam = btpEstabelecimento;
		this.setTableName("EST_ESTABELECIMENTO");

		this.setIdentity("EST_COD_ESTABELECIMENTO", this.btpParam.getEstCodEstabelecimento());
		this.setAttribute("EST_NOM_ESTABELECIMENTO", this.btpParam.getEstNomEstabelecimento(), Boolean.TRUE);
		this.setAttribute("USU_COD_USUARIO", this.btpParam.getBtpUsuario().getUsuCodUsuario(), Boolean.TRUE);
	}
}