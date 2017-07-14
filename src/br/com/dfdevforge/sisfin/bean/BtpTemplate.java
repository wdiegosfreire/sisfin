package br.com.dfdevforge.sisfin.bean;

import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;

public class BtpTemplate extends AbstractBean
{
	private static final long serialVersionUID = -7915634792156316474L;

	/*
	 *  Atributos da entidade
	 */
	private Integer temCodTemplate;
	private String temTxtNome;

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpUsuario btpUsuario;

	/*
	 *  Atributos de relacionamento N
	 */
	List<BtpTemplateRegra> btpTemplateRegraList;

	/*
	 *  Métodos de acesso dos atributos da entidade
	 */
	public Integer getTemCodTemplate() {return temCodTemplate;}
	public void setTemCodTemplate(Integer temCodTemplate) {this.temCodTemplate = temCodTemplate;}

	public String getTemTxtNome() {return temTxtNome;}
	public void setTemTxtNome(String temTxtNome) {this.temTxtNome = temTxtNome;}

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
	public List<BtpTemplateRegra> getBtpTemplateRegraList()
	{
		if (btpTemplateRegraList == null)
			btpTemplateRegraList = new ArrayList<BtpTemplateRegra>();

		return btpTemplateRegraList;
	}
	public void setBtpTemplateRegraList(List<BtpTemplateRegra> btpTemplateRegraList) {this.btpTemplateRegraList = btpTemplateRegraList;}
}