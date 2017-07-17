package br.com.dfdevforge.sisfin.estabelecimento.bean;

import br.com.cagece.core.persistence.api.BeanConfig;

public class BtpEstabelecimentoConfig extends BeanConfig<BtpEstabelecimento>
{
	public BtpEstabelecimentoConfig(BtpEstabelecimento btpEstabelecimento)
	{
		this.btpParam = btpEstabelecimento;
		this.setTableName("est_estabelecimento");

		this.setIdentity("est_cod_estabelecimento", this.btpParam.getEstCodEstabelecimento());
		this.setAttribute("est_nom_estabelecimento", this.btpParam.getEstNomEstabelecimento(), Boolean.TRUE);
		this.setAttribute("usu_cod_usuario", this.btpParam.getBtpUsuario().getUsuCodUsuario(), Boolean.TRUE);
	}
}