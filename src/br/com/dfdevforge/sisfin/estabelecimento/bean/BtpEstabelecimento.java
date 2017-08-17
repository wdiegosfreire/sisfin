package br.com.dfdevforge.sisfin.estabelecimento.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

@Table(name="est_estabelecimento")
public class BtpEstabelecimento extends AbstractBean
{
	private static final long serialVersionUID = 648625027775802783L;

	/*
	 *  Atributos da entidade
	 */
	@Id
	@Column(name="est_cod_estabelecimento")
	private Integer estCodEstabelecimento;
	public Integer getEstCodEstabelecimento() {return estCodEstabelecimento;}
	public void setEstCodEstabelecimento(Integer estCodEstabelecimento) {this.estCodEstabelecimento = estCodEstabelecimento;}

	@Column(name="est_nom_estabelecimento")
	private String estNomEstabelecimento;
	public String getEstNomEstabelecimento() {return estNomEstabelecimento;}
	public void setEstNomEstabelecimento(String estNomEstabelecimento) {this.estNomEstabelecimento = estNomEstabelecimento;}

	/*
	 *  Atributos de relacionamento 1
	 */
	@ManyToOne
	@JoinColumn(name="usu_cod_usuario")
	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return this.btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	/*
	 *  Atributos de relacionamento N
	 */
	@OneToMany
	private List<BtpObjetivo> btpObjetivoList;
	public List<BtpObjetivo> getBtpObjetivoList() {return this.btpObjetivoList;}
	public void setBtpObjetivoList(List<BtpObjetivo> btpObjetivoList) {this.btpObjetivoList = btpObjetivoList;}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estCodEstabelecimento == null) ? 0 : estCodEstabelecimento.hashCode());

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BtpEstabelecimento))
			return false;

		BtpEstabelecimento other = (BtpEstabelecimento) obj;
		if (estCodEstabelecimento == null || other.estCodEstabelecimento == null)
			return false;
		else if (!estCodEstabelecimento.equals(other.estCodEstabelecimento))
			return false;

		return true;
	}
}