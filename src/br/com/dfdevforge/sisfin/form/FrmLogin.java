package br.com.dfdevforge.sisfin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.util.Utils;

public class FrmLogin extends FrmAbstract
{
	private static final long serialVersionUID = 2433678741492798129L;

	private BtpUsuario btpUsuario;

	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		setBtpUsuario(new BtpUsuario());
	}

	public ActionErrors validate()
	{
		getActionErrors().clear();

		if (!Utils.hasValue(btpUsuario.getUsuTxtEmail()))
			getActionErrors().add("conTxtDescricao", new ActionMessage("erro.attribute.conTxtDescricao.required"));

		return getActionErrors();
	}
}