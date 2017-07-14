package br.com.dfdevforge.sisfin.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class FrmMovimento extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;
	protected ActionErrors errors = new ActionErrors();

	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	private BtpObjetivo btpObjetivo;
	public BtpObjetivo getBtpObjetivo() {return btpObjetivo;}
	public void setBtpObjetivo(BtpObjetivo btpObjetivo) {this.btpObjetivo = btpObjetivo;}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		btpUsuario = new BtpUsuario();

		btpObjetivo = new BtpObjetivo();
		btpObjetivo.setMap(new HashMap<String, String>());

		this.setCompetencia(btpObjetivo.getMap(), request);

//		Calendar c = Calendar.getInstance();
//
//		int aux = c.get(Calendar.MONTH) + 1;
//		btpObjetivo.getMap().put("numMes", (aux < 10 ? "0" + aux : aux + ""));
//		btpObjetivo.getMap().put("numAno", c.get(Calendar.YEAR) + "");

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		return errors;
	}
}