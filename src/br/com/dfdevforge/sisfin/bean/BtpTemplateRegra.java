package br.com.dfdevforge.sisfin.bean;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpTemplateRegra extends AbstractBean
{
	private static final long serialVersionUID = -7915634792156316474L;

	/*
	 *  Atributos da entidade
	 */
	private Integer terCodTemplateRegra;
	private Integer terCodValorAssociado;

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpTemplate btpTemplate;
	private BtpRegra btpRegra;

	/*
	 *  Atributos de relacionamento N
	 */

	/*
	 *  Métodos de acesso dos atributos da entidade
	 */
	public Integer getTerCodTemplateRegra() {return terCodTemplateRegra;}
	public void setTerCodTemplateRegra(Integer terCodTemplateRegra) {this.terCodTemplateRegra = terCodTemplateRegra;}

	public Integer getTerCodValorAssociado() {return terCodValorAssociado;}
	public void setTerCodValorAssociado(Integer terCodValorAssociado) {this.terCodValorAssociado = terCodValorAssociado;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */
	public BtpTemplate getBtpTemplate()
	{
		if (btpTemplate == null)
			btpTemplate = new BtpTemplate();

		return btpTemplate;
	}
	public void setBtpTemplate(BtpTemplate btpTemplate) {this.btpTemplate = btpTemplate;}

	public BtpRegra getBtpRegra()
	{
		if (btpRegra == null)
			btpRegra = new BtpRegra();

		return btpRegra;
	}
	public void setBtpRegra(BtpRegra btpRegra) {this.btpRegra = btpRegra;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento N
	 */
}