package br.com.dfdevforge.sisfin.bean;

import java.math.BigDecimal;
import java.util.Date;

import br.com.cagece.core.bean.AbstractBean;

public class BtpMovimento extends AbstractBean
{
	private static final long serialVersionUID = -2523271287245433689L;

	/*
	 * Declaração dos construtores da classe
	 */
	public BtpMovimento()
	{
		btpObjetivo = new BtpObjetivo();
		btpFormaPagamento = new BtpFormaPagamento();
		btpContaOrigem = new BtpConta();
		btpContaDestino = new BtpConta();
	}

	/*
	 * Atributos da entidade
	 */
	private Integer movCodMovimento;
	public Integer getMovCodMovimento() {return movCodMovimento;}
	public void setMovCodMovimento(Integer movCodMovimento) {this.movCodMovimento = movCodMovimento;}

	private Integer movNumParcela;
	public Integer getMovNumParcela() {return movNumParcela;}
	public void setMovNumParcela(Integer movNumParcela) {this.movNumParcela = movNumParcela;}

	private Date movDatRegistro;
	public Date getMovDatRegistro() {return movDatRegistro;}
	public void setMovDatRegistro(Date movDatRegistro) {this.movDatRegistro = movDatRegistro;}

	private Date movDatVencimento;
	public Date getMovDatVencimento() {return movDatVencimento;}
	public void setMovDatVencimento(Date movDatVencimento) {this.movDatVencimento = movDatVencimento;}

	private Date movDatPagamento;
	public Date getMovDatPagamento() {return movDatPagamento;}
	public void setMovDatPagamento(Date movDatPagamento) {this.movDatPagamento = movDatPagamento;}

	private BigDecimal movVlrMovimentado;
	public BigDecimal getMovVlrMovimentado() {return movVlrMovimentado;}
	public void setMovVlrMovimentado(BigDecimal movVlrMovimentado) {this.movVlrMovimentado = movVlrMovimentado;}

	/*
	 * Atributos de relacionamento 1
	 */
	private BtpObjetivo btpObjetivo;
	public BtpObjetivo getBtpObjetivo() {return btpObjetivo;}
	public void setBtpObjetivo(BtpObjetivo btpObjetivo) {this.btpObjetivo = btpObjetivo;}

	private BtpFormaPagamento btpFormaPagamento;
	public BtpFormaPagamento getBtpFormaPagamento() {return btpFormaPagamento;}
	public void setBtpFormaPagamento(BtpFormaPagamento btpFormaPagamento) {this.btpFormaPagamento = btpFormaPagamento;}

	private BtpConta btpContaOrigem;
	public BtpConta getBtpContaOrigem() {return btpContaOrigem;}
	public void setBtpContaOrigem(BtpConta btpContaOrigem) {this.btpContaOrigem = btpContaOrigem;}

	private BtpConta btpContaDestino;
	public BtpConta getBtpContaDestino() {return btpContaDestino;}
	public void setBtpContaDestino(BtpConta btpContaDestino) {this.btpContaDestino = btpContaDestino;}

	/*
	 * Atributos de relacionamento N
	 */
}