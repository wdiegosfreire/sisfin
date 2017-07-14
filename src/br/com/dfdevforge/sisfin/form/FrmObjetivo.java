package br.com.dfdevforge.sisfin.form;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;

public class FrmObjetivo extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;
	protected ActionErrors errors = new ActionErrors();

	private BtpObjetivo btpObjetivo = new BtpObjetivo();
	private BtpTemplate btpTemplate = new BtpTemplate();

	/*
	 * Atributos auxiliares
	 */
	private String movDatRegistro;
	public String getMovDatRegistro() {return movDatRegistro;}
	public void setMovDatRegistro(String movDatRegistro) {this.movDatRegistro = movDatRegistro;}

	private String movDatVencimento;
	public String getMovDatVencimento() {return movDatVencimento;}
	public void setMovDatVencimento(String movDatVencimento) {this.movDatVencimento = movDatVencimento;}

	private String movDatPagamento;
	public String getMovDatPagamento() {return movDatPagamento;}
	public void setMovDatPagamento(String movDatPagamento) {this.movDatPagamento = movDatPagamento;}

	private Integer movNumParcela;
	public Integer getMovNumParcela() {return movNumParcela;}
	public void setMovNumParcela(Integer movNumParcela) {this.movNumParcela = movNumParcela;}

	private Integer conCodContaOrigem;
	public Integer getConCodContaOrigem() {return conCodContaOrigem;}
	public void setConCodContaOrigem(Integer conCodContaOrigem) {this.conCodContaOrigem = conCodContaOrigem;}

	private Integer conCodContaDestino;
	public Integer getConCodContaDestino() {return conCodContaDestino;}
	public void setConCodContaDestino(Integer conCodContaDestino) {this.conCodContaDestino = conCodContaDestino;}

	private BigDecimal movVlrMovimento;
	public BigDecimal getMovVlrMovimento() {return movVlrMovimento;}
	public void setMovVlrMovimento(BigDecimal movVlrMovimento) {this.movVlrMovimento = movVlrMovimento;}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		btpObjetivo = new BtpObjetivo();

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		if (btpObjetivo.getObjTxtDescricao() == null || btpObjetivo.getObjTxtDescricao().equals(""))
			errors.add("objTxtDescricao", new ActionMessage("erro.attribute.objTxtDescricao.required"));

		return errors;
	}

	public BtpObjetivo getBtpObjetivo() {return btpObjetivo;}
	public void setBtpObjetivo(BtpObjetivo btpObjetivo) {this.btpObjetivo = btpObjetivo;}

	public BtpTemplate getBtpTemplate() {return btpTemplate;}
	public void setBtpTemplate(BtpTemplate btpTemplate) {this.btpTemplate = btpTemplate;}
}