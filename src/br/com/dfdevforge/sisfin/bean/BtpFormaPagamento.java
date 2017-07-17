package br.com.dfdevforge.sisfin.bean;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpFormaPagamento extends AbstractBean
{
	private static final long serialVersionUID = -7915634792156316474L;

	/*
	 *  Atributos da entidade
	 */
	private Integer fopCodFormaPagamento;
	private String fopNomFormaPagamento;

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpUsuario btpUsuario;

	/*
	 *  Atributos de relacionamento N
	 */

	/*
	 *  Métodos de acesso dos atributos da entidade
	 */
	public Integer getFopCodFormaPagamento() {return fopCodFormaPagamento;}
	public void setFopCodFormaPagamento(Integer fopCodFormaPagamento) {this.fopCodFormaPagamento = fopCodFormaPagamento;}

	public String getFopNomFormaPagamento() {return fopNomFormaPagamento;}
	public void setFopNomFormaPagamento(String fopNomFormaPagamento) {this.fopNomFormaPagamento = fopNomFormaPagamento;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */
	public BtpUsuario getBtpUsuario()
	{
		if (btpUsuario == null)
			btpUsuario = new BtpUsuario();

		return btpUsuario;
	}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento N
	 */
}