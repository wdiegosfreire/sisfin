package br.com.dfdevforge.sisfin.estabelecimento.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.form.FrmAbstract;

public class FrmEstabelecimento extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;
	protected ActionErrors errors = new ActionErrors();

	private BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		BtpUsuario btpUsuario = new BtpUsuario();
		btpUsuario = btpEstabelecimento.getBtpUsuario();

		btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(btpUsuario);

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		if (btpEstabelecimento.getEstNomEstabelecimento() == null || btpEstabelecimento.getEstNomEstabelecimento().equals(""))
			errors.add("estNomEstabelecimento", new ActionMessage("erro.attribute.estNomEstabelecimento.required"));

		return errors;
	}

	public BtpEstabelecimento getBtpEstabelecimento() {return btpEstabelecimento;}
	public void setBtpEstabelecimento(BtpEstabelecimento btpEstabelecimento) {this.btpEstabelecimento = btpEstabelecimento;}
}