package br.com.dfdevforge.sisfin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class FrmFormaPagamento extends FrmAbstract
{
	private static final long serialVersionUID = 664636639064166552L;
	protected ActionErrors errors = new ActionErrors();

	private BtpFormaPagamento btpFormaPagamento = new BtpFormaPagamento();

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		BtpUsuario btpUsuario = new BtpUsuario();
		btpUsuario = btpFormaPagamento.getBtpUsuario();

		btpFormaPagamento = new BtpFormaPagamento();
		btpFormaPagamento.setBtpUsuario(btpUsuario);

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		if (btpFormaPagamento.getFopNomFormaPagamento() == null || btpFormaPagamento.getFopNomFormaPagamento().equals(""))
			errors.add("fopNomFormaPagamento", new ActionMessage("erro.attribute.fopNomFormaPagamento.required"));

		return errors;
	}

	public BtpFormaPagamento getBtpFormaPagamento() {return btpFormaPagamento;}
	public void setBtpFormaPagamento(BtpFormaPagamento btpFormaPagamento) {this.btpFormaPagamento = btpFormaPagamento;}
}