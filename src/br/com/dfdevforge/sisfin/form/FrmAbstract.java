package br.com.dfdevforge.sisfin.form;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.util.Utils;

public abstract class FrmAbstract extends ActionForm
{
	private static final long serialVersionUID = 6566873102747181689L;

	private int sqlOrder;
	private String cmd;
	private ActionErrors actionErrors = new ActionErrors();

	public int getSqlOrder() {return sqlOrder;}
	public void setSqlOrder(int sqlOrder) {this.sqlOrder = sqlOrder;}

	public String getCmd() {return cmd;}
	public void setCmd(String cmd) {this.cmd = cmd;}

	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	public ActionErrors getActionErrors() {return actionErrors;}
	public void setActionErrors(ActionErrors actionErrors) {this.actionErrors = actionErrors;}

	public void setCompetencia(BtpCompetencia btpCompetencia, HttpServletRequest request)
	{
		if (btpCompetencia == null)
			btpCompetencia = new BtpCompetencia();
		
		if (!Utils.hasValue(btpCompetencia.getComDatMes()) || !Utils.hasValue(btpCompetencia.getComDatAno()))
		{
			String sesNumMes = (String) request.getSession().getAttribute("sesNumMes");
			String sesNumAno = (String) request.getSession().getAttribute("sesNumAno");
			
			if (Utils.hasValue(sesNumAno) && Utils.hasValue(sesNumMes))
			{
				btpCompetencia.setComDatMes(sesNumMes);
				btpCompetencia.setComDatAno(sesNumAno);
			}
			else
			{
				Calendar c = Calendar.getInstance();
				
				int aux = c.get(Calendar.MONTH) + 1;
				
				btpCompetencia.setComDatMes((aux < 10 ? "0" + aux : aux + ""));
				btpCompetencia.setComDatAno(c.get(Calendar.YEAR) + "");
								
				request.getSession().setAttribute("sesNumMes", btpCompetencia.getComDatMes());
				request.getSession().setAttribute("sesNumAno", btpCompetencia.getComDatAno());
			}
		}
	}

	@Deprecated
	public void setCompetencia(Map<String, String> map, HttpServletRequest request)
	{
		if (map == null)
			map = new HashMap<String, String>();

		if (!Utils.hasValue(map.get("numMes")) || !Utils.hasValue(map.get("numAno")))
		{
			String sesNumMes = (String) request.getSession().getAttribute("sesNumMes");
			String sesNumAno = (String) request.getSession().getAttribute("sesNumAno");

			if (Utils.hasValue(sesNumAno) && Utils.hasValue(sesNumMes))
			{
				map.put("numMes", sesNumMes);
				map.put("numAno", sesNumAno);
			}
			else
			{
				Calendar c = Calendar.getInstance();
				
				int aux = c.get(Calendar.MONTH) + 1;
				map.put("numMes", (aux < 10 ? "0" + aux : aux + ""));
				map.put("numAno", c.get(Calendar.YEAR) + "");

				request.getSession().setAttribute("sesNumMes", map.get("numMes"));
				request.getSession().setAttribute("sesNumAno", map.get("numAno"));
			}
		}
	}
}