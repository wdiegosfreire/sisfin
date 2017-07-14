package br.com.dfdevforge.sisfin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class FrmExtrato extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;
	protected ActionErrors errors = new ActionErrors();

	private BtpExtrato btpExtrato = new BtpExtrato();
	public BtpExtrato getBtpExtrato() {return btpExtrato;}
	public void setBtpExtrato(BtpExtrato btpExtrato) {this.btpExtrato = btpExtrato;}

	private FormFile formFile = null;
	public FormFile getFormFile() {return formFile;}
	public void setFormFile(FormFile formFile) {this.formFile = formFile;}

	private BtpObjetivo[] btpObjetivoArray;
	public BtpObjetivo[] getBtpObjetivoArray() {return btpObjetivoArray;}
	public void setBtpObjetivoArray(BtpObjetivo[] btpObjetivoArray) {this.btpObjetivoArray = btpObjetivoArray;}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		BtpUsuario btpUsuario = new BtpUsuario();
		btpUsuario = btpExtrato.getBtpUsuario();

		btpExtrato = new BtpExtrato();
		btpExtrato.setBtpUsuario(btpUsuario);

		if (btpExtrato.getBtpBanco() == null)
			btpExtrato.setBtpBanco(new BtpBanco());

		if (btpExtrato.getBtpTipoExtrato() == null)
			btpExtrato.setBtpTipoExtrato(new BtpTipoExtrato());

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		return errors;
	}
}