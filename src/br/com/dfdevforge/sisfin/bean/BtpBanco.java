package br.com.dfdevforge.sisfin.bean;

import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpBanco extends AbstractBean
{
	private static final long serialVersionUID = 5062761202361192479L;

	/*
	 *  Atributos da entidade
	 */
	private Integer banCodBanco;
	public Integer getBanCodBanco() {return banCodBanco;}
	public void setBanCodBanco(Integer banCodBanco) {this.banCodBanco = banCodBanco;}

	private String banTxtNome;
	public String getBanTxtNome() {return banTxtNome;}
	public void setBanTxtNome(String banTxtNome) {this.banTxtNome = banTxtNome;}

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
		result = prime * result + ((banCodBanco == null) ? 0 : banCodBanco.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof BtpBanco))
			return false;

		BtpBanco other = (BtpBanco) obj;
		if (banCodBanco == null || other.banCodBanco == null)
		{
			return false;
		}
		else if (!banCodBanco.equals(other.banCodBanco))
		{
			return false;
		}

		return true;
	}
}