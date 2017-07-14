package br.com.dfdevforge.sisfin.bean;

import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;

public class BtpObjetivo extends AbstractBean
{
	private static final long serialVersionUID = 8682318021712984798L;

	/*
	 *  Atributos da entidade
	 */
	private Integer objCodObjetivo;
	public Integer getObjCodObjetivo() {return objCodObjetivo;}
	public void setObjCodObjetivo(Integer objCodObjetivo) {this.objCodObjetivo = objCodObjetivo;}

	private String objTxtDescricao;
	public String getObjTxtDescricao() {return objTxtDescricao;}
	public void setObjTxtDescricao(String objTxtDescricao) {this.objTxtDescricao = objTxtDescricao;}

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpEstabelecimento btpEstabelecimento;
	public BtpEstabelecimento getBtpEstabelecimento()
	{
		if (btpEstabelecimento == null)
			btpEstabelecimento = new BtpEstabelecimento();

		return btpEstabelecimento;
	}
	public void setBtpEstabelecimento(BtpEstabelecimento btpEstabelecimento) {this.btpEstabelecimento = btpEstabelecimento;}

	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario()
	{
		if (btpUsuario == null)
			btpUsuario = new BtpUsuario();

		return btpUsuario;
	}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpMovimento> btpMovimentoList;
	public List<BtpMovimento> getBtpMovimentoList()
	{
		if (btpMovimentoList == null)
			btpMovimentoList = new ArrayList<BtpMovimento>();

		return btpMovimentoList;
	}
	public void setBtpMovimentoList(List<BtpMovimento> btpMovimentoList) {this.btpMovimentoList = btpMovimentoList;}

	private List<BtpObjetivoItem> btpObjetivoItemList;
	public List<BtpObjetivoItem> getBtpObjetivoItemList()
	{
		if (btpObjetivoItemList == null)
			btpObjetivoItemList = new ArrayList<BtpObjetivoItem>();

		return btpObjetivoItemList;
	}
	public void setBtpObjetivoItemList(List<BtpObjetivoItem> btpObjetivoItemList) {this.btpObjetivoItemList = btpObjetivoItemList;}
}