package br.com.dfdevforge.sisfin.bean;

import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;

public class BtpConta extends AbstractBean
{
	private static final long serialVersionUID = 3956903285810936566L;

	/*
	 *  Atributos da classe
	 */
	private Integer conCodConta;
	private String conTxtDescricao;
	private String conNumNivel;
	private boolean conFlgMovimento;
	private boolean conFlgPoupanca;
	private boolean conFlgVirtual;

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpConta btpContaPai;
	private BtpUsuario btpUsuario;

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpConta> btpContaList;
	private List<BtpMovimento> btpMovimentoList;

	/*
	 *  Métodos de acesso dos atributos da classe
	 */
	public Integer getConCodConta() {return conCodConta;}
	public void setConCodConta(Integer conCodConta) {this.conCodConta = conCodConta;}

	public String getConTxtDescricao() {return conTxtDescricao;}
	public void setConTxtDescricao(String conTxtDescricao) {this.conTxtDescricao = conTxtDescricao;}

	public String getConNumNivel() {return conNumNivel;}
	public void setConNumNivel(String conNumNivel) {this.conNumNivel = conNumNivel;}

	public boolean getConFlgMovimento() {return conFlgMovimento;}
	public void setConFlgMovimento(boolean conFlgMovimento) {this.conFlgMovimento = conFlgMovimento;}

	public boolean getConFlgPoupanca() {return conFlgPoupanca;}
	public void setConFlgPoupanca(boolean conFlgPoupanca) {this.conFlgPoupanca = conFlgPoupanca;}

	public boolean getConFlgVirtual() {return conFlgVirtual;}
	public void setConFlgVirtual(boolean conFlgVirtual) {this.conFlgVirtual = conFlgVirtual;}

	/*
	 *  Métodos de acesso dos atributos de relacionamento 1
	 */
	public BtpConta getBtpContaPai() {return btpContaPai;}
	public void setBtpContaPai(BtpConta btpContaPai) {this.btpContaPai = btpContaPai;}

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
	public List<BtpConta> getBtpContaList()
	{
		if (btpContaList == null)
			btpContaList = new ArrayList<BtpConta>();

		return btpContaList;
	}
	public void setBtpContaList(List<BtpConta> btpContaList) {this.btpContaList = btpContaList;}

	public List<BtpMovimento> getBtpMovimentoList()
	{
		if (btpMovimentoList == null)
			btpMovimentoList = new ArrayList<BtpMovimento>();

		return btpMovimentoList;
	}
	public void setBtpMovimentoList(List<BtpMovimento> btpMovimentoList) {this.btpMovimentoList = btpMovimentoList;}
}