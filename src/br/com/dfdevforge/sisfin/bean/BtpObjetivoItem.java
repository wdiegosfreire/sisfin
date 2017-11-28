package br.com.dfdevforge.sisfin.bean;

import java.math.BigDecimal;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpObjetivoItem extends AbstractBean
{
	private static final long serialVersionUID = -2988865855375440035L;

	/*
	 *  Atributos da entidade
	 */
	private Integer obiCodObjetivoItem;
	public Integer getObiCodObjetivoItem() {return obiCodObjetivoItem;}
	public void setObiCodObjetivoItem(Integer obiCodObjetivoItem) {this.obiCodObjetivoItem = obiCodObjetivoItem;}

	private Integer obiNumItem;
	public Integer getObiNumItem() {return obiNumItem;}
	public void setObiNumItem(Integer obiNumItem) {this.obiNumItem = obiNumItem;}

	private String obiTxtDescricao;
	public String getObiTxtDescricao() {return obiTxtDescricao;}
	public void setObiTxtDescricao(String obiTxtDescricao) {this.obiTxtDescricao = obiTxtDescricao;}

	private BigDecimal obiNumQuantidade;
	public BigDecimal getObiNumQuantidade() {return obiNumQuantidade;}
	public void setObiNumQuantidade(BigDecimal obiNumQuantidade) {this.obiNumQuantidade = obiNumQuantidade;}

	private BigDecimal obiVlrUnidade;
	public BigDecimal getObiVlrUnidade() {return obiVlrUnidade;}
	public void setObiVlrUnidade(BigDecimal obiVlrUnidade) {this.obiVlrUnidade = obiVlrUnidade;}

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpObjetivo btpObjetivo;

	/*
	 *  Atributos de relacionamento N
	 */

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */
	public BtpObjetivo getBtpObjetivo()
	{
		if (btpObjetivo == null)
			btpObjetivo = new BtpObjetivo();

		return btpObjetivo;
	}
	public void setBtpObjetivo(BtpObjetivo btpObjetivo) {this.btpObjetivo = btpObjetivo;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento N
	 */
}