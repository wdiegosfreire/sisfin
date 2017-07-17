package br.com.dfdevforge.sisfin.bean;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpUsuario extends AbstractBean
{
	private static final long serialVersionUID = 8682318021712984798L;

	/*
	 *  Atributos da entidade
	 */
	private Integer usuCodUsuario;
	private String usuTxtNome;
	private String usuTxtSenha;
	private String usuTxtEmail;

	/*
	 *  Atributos de relacionamento 1
	 */

	/*
	 *  Atributos de relacionamento N
	 */

	/*
	 *  Métodos de acesso dos atributos da entidade
	 */
	public Integer getUsuCodUsuario() {return usuCodUsuario;}
	public void setUsuCodUsuario(Integer usuCodUsuario) {this.usuCodUsuario = usuCodUsuario;}

	public String getUsuTxtNome() {return usuTxtNome;}
	public void setUsuTxtNome(String usuTxtNome) {this.usuTxtNome = usuTxtNome;}

	public String getUsuTxtSenha() {return usuTxtSenha;}
	public void setUsuTxtSenha(String usuTxtSenha) {this.usuTxtSenha = usuTxtSenha;}

	public String getUsuTxtEmail() {return usuTxtEmail;}
	public void setUsuTxtEmail(String usuTxtEmail) {this.usuTxtEmail = usuTxtEmail;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */

	/*
	 *  Métodos de acesso dos atributos de relacionamento N
	 */

	/*
	 * Métodos sobrescritos
	 */
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuCodUsuario == null) ? 0 : usuCodUsuario.hashCode());

		return result;
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof BtpUsuario))
			return false;

		BtpUsuario other = (BtpUsuario) obj;

		if (usuCodUsuario == null)
		{
			if (other.usuCodUsuario != null)
				return false;
		}
		else if (usuCodUsuario == 0 || !usuCodUsuario.equals(other.usuCodUsuario))
		{
			return false;
		}

		return true;
	}

}