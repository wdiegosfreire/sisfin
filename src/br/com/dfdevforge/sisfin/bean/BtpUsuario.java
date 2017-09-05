package br.com.dfdevforge.sisfin.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.cagece.core.bean.api.AbstractBean;

@Table(name="usu_usuario")
public class BtpUsuario extends AbstractBean
{
	private static final long serialVersionUID = 8682318021712984798L;

	/*
	 *  Atributos da entidade
	 */
	@Id
	@Column(name="usu_cod_usuario")
	private Integer usuCodUsuario;
	public Integer getUsuCodUsuario() {return usuCodUsuario;}
	public void setUsuCodUsuario(Integer usuCodUsuario) {this.usuCodUsuario = usuCodUsuario;}

	@Column(name="usu_txt_nome")
	private String usuTxtNome;
	public String getUsuTxtNome() {return usuTxtNome;}
	public void setUsuTxtNome(String usuTxtNome) {this.usuTxtNome = usuTxtNome;}

	@Column(name="usu_txt_senha")
	private String usuTxtSenha;
	public String getUsuTxtSenha() {return usuTxtSenha;}
	public void setUsuTxtSenha(String usuTxtSenha) {this.usuTxtSenha = usuTxtSenha;}

	@Column(name="usu_txt_email")
	private String usuTxtEmail;
	public String getUsuTxtEmail() {return usuTxtEmail;}
	public void setUsuTxtEmail(String usuTxtEmail) {this.usuTxtEmail = usuTxtEmail;}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuCodUsuario == null) ? 0 : usuCodUsuario.hashCode());

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
		if (!(obj instanceof BtpUsuario))
			return false;

		BtpUsuario other = (BtpUsuario) obj;
		if (usuCodUsuario == null || other.usuCodUsuario == null)
				return false;
		else if (!usuCodUsuario.equals(other.usuCodUsuario))
			return false;

		return true;
	}
}