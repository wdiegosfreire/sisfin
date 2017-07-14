package br.com.dfdevforge.sisfin.bean;

import java.util.List;

import br.com.cagece.core.bean.AbstractBean;


public class BtpTipoExtrato extends AbstractBean
{
	private static final long serialVersionUID = -8933489170480104496L;

	/*
	 *  Atributos da classe
	 */
	private Integer tieCodTipoExtrato;
	public Integer getTieCodTipoExtrato() {return tieCodTipoExtrato;}
	public void setTieCodTipoExtrato(Integer tieCodTipoExtrato) {this.tieCodTipoExtrato = tieCodTipoExtrato;}

	private String tieTxtNome;
	public String getTieTxtNome() {return tieTxtNome;}
	public void setTieTxtNome(String tieTxtNome) {this.tieTxtNome = tieTxtNome;}

	/*
	 *  Atributos de relacionamento 1
	 */

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpExtrato> btpExtratoList;
	public List<BtpExtrato> getBtpExtratoList() {return btpExtratoList;}
	public void setBtpExtratoList(List<BtpExtrato> btpExtratoList) {this.btpExtratoList = btpExtratoList;}

	/*
	 * Métodos sobrescritos
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tieCodTipoExtrato == null) ? 0 : tieCodTipoExtrato.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof BtpTipoExtrato))
			return false;

		BtpTipoExtrato other = (BtpTipoExtrato) obj;
		if (tieCodTipoExtrato == null || other.tieCodTipoExtrato == null)
		{
				return false;
		}
		else if (!tieCodTipoExtrato.equals(other.tieCodTipoExtrato))
		{
			return false;
		}

		return true;
	}
}