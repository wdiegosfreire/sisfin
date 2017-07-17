package br.com.dfdevforge.sisfin.estabelecimento.bean;

import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class BtpEstabelecimento extends AbstractBean
{
	private static final long serialVersionUID = 648625027775802783L;

	/*
	 *  Atributos da classe
	 */
	private Integer estCodEstabelecimento;
	private String estNomEstabelecimento;

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpUsuario btpUsuario;

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpObjetivo> btpObjetivoList;

	/*
	 * Construtor padrão
	 */
	public BtpEstabelecimento()
	{
		btpObjetivoList = new ArrayList<BtpObjetivo>();
	}

	/*
	 *  Métodos de acesso dos atributos da classe
	 */
	public Integer getEstCodEstabelecimento() {return estCodEstabelecimento;}
	public void setEstCodEstabelecimento(Integer estCodEstabelecimento) {this.estCodEstabelecimento = estCodEstabelecimento;}

	public String getEstNomEstabelecimento() {return estNomEstabelecimento;}
	public void setEstNomEstabelecimento(String estNomEstabelecimento) {this.estNomEstabelecimento = estNomEstabelecimento;}

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
	public List<BtpObjetivo> getBtpObjetivoList()
	{
		if (btpObjetivoList == null)
			btpObjetivoList = new ArrayList<BtpObjetivo>();

		return btpObjetivoList;
	}
	public void setBtpObjetivoList(List<BtpObjetivo> btpObjetivoList) {this.btpObjetivoList = btpObjetivoList;}
}