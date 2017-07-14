package br.com.dfdevforge.sisfin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class FrmConta extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;
	protected ActionErrors errors = new ActionErrors();

	private BtpConta btpConta = new BtpConta();

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		BtpUsuario btpUsuario = new BtpUsuario();
		btpUsuario = btpConta.getBtpUsuario();

		btpConta = new BtpConta();
		btpConta.setBtpUsuario(btpUsuario);

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		if (btpConta.getConTxtDescricao() == null || btpConta.getConTxtDescricao().equals(""))
			errors.add("conTxtDescricao", new ActionMessage("erro.attribute.conTxtDescricao.required"));

		return errors;
	}

	public BtpConta getBtpConta()
	{
		if (btpConta.getBtpContaPai() == null)
			btpConta.setBtpContaPai(new BtpConta());

		return btpConta;
	}
	public void setBtpConta(BtpConta btpConta)
	{
		this.btpConta = btpConta;
	}
}