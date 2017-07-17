package br.com.dfdevforge.sisfin.bean;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpRegra extends AbstractBean
{
	private static final long serialVersionUID = 5307564601098154799L;

	/*
	 *  Atributos da entidade
	 */
	private Integer regCodRegra;
	private String regTxtDescricao;
	private Integer regNumOrdem;
	private String regTxtObjetoHtml;
	private String regTxtEventoHtml;
	private String regTxtNomeFuncao;
	private String regTxtCodigoFuncao;
	private boolean regFlgValorAssociado;
	

	/*
	 *  Atributos de relacionamento 1
	 */

	/*
	 *  Atributos de relacionamento N
	 */

	/*
	 *  Métodos de acesso dos atributos da entidade
	 */
	public Integer getRegCodRegra() {return regCodRegra;}
	public void setRegCodRegra(Integer regCodRegra) {this.regCodRegra = regCodRegra;}

	public String getRegTxtDescricao() {return regTxtDescricao;}
	public void setRegTxtDescricao(String regTxtDescricao) {this.regTxtDescricao = regTxtDescricao;}

	public Integer getRegNumOrdem() {return regNumOrdem;}
	public void setRegNumOrdem(Integer regNumOrdem) {this.regNumOrdem = regNumOrdem;}

	public String getRegTxtObjetoHtml() {return regTxtObjetoHtml;}
	public void setRegTxtObjetoHtml(String regTxtObjetoHtml) {this.regTxtObjetoHtml = regTxtObjetoHtml;}

	public String getRegTxtEventoHtml() {return regTxtEventoHtml;}
	public void setRegTxtEventoHtml(String regTxtEventoHtml) {this.regTxtEventoHtml = regTxtEventoHtml;}

	public String getRegTxtNomeFuncao() {return regTxtNomeFuncao;}
	public void setRegTxtNomeFuncao(String regTxtNomeFuncao) {this.regTxtNomeFuncao = regTxtNomeFuncao;}

	public String getRegTxtCodigoFuncao() {return regTxtCodigoFuncao;}
	public void setRegTxtCodigoFuncao(String regTxtCodigoFuncao) {this.regTxtCodigoFuncao = regTxtCodigoFuncao;}

	public boolean isRegFlgValorAssociado() {return regFlgValorAssociado;}
	public void setRegFlgValorAssociado(boolean regFlgValorAssociado) {this.regFlgValorAssociado = regFlgValorAssociado;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */

	/*
	 *  Métodos de acesso dos atributos de relacionamento N
	 */
}