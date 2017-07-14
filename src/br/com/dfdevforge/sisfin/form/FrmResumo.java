package br.com.dfdevforge.sisfin.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class FrmResumo extends FrmAbstract
{
	private static final long serialVersionUID = -8613451731692033907L;

	private BtpCompetencia btpCompetencia;
	public BtpCompetencia getBtpCompetencia() {return btpCompetencia;}
	public void setBtpCompetencia(BtpCompetencia btpCompetencia) {this.btpCompetencia = btpCompetencia;}

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		this.setBtpUsuario(new BtpUsuario());

		this.btpCompetencia = new BtpCompetencia();
		this.setCompetencia(this.btpCompetencia, request);
	}
}