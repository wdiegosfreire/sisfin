package br.com.dfdevforge.sisfin.dwr;

import java.util.Calendar;

import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.util.Utils;

public abstract class DwrAbstract
{
	public Object getAttribute(String objectName)
	{
		return DWRUtil.getRequest().getAttribute(objectName);
	}

	public void setAttribute(String objectName, Object object)
	{
		DWRUtil.getRequest().setAttribute(objectName, object);
	}

	public void setCompetencia(BtpCompetencia btpCompetencia)
	{
		if (btpCompetencia == null)
			btpCompetencia = new BtpCompetencia();

		if (!Utils.hasValue(btpCompetencia.getComDatMes()) || !Utils.hasValue(btpCompetencia.getComDatAno()))
		{
			String sesNumMes = (String) DWRUtil.getSession().getAttribute("sesNumMes");
			String sesNumAno = (String) DWRUtil.getSession().getAttribute("sesNumAno");

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

				DWRUtil.getSession().setAttribute("sesNumMes", btpCompetencia.getComDatMes());
				DWRUtil.getSession().setAttribute("sesNumAno", btpCompetencia.getComDatAno());
			}
		}
		else
		{
			DWRUtil.getSession().setAttribute("sesNumMes", btpCompetencia.getComDatMes());
			DWRUtil.getSession().setAttribute("sesNumAno", btpCompetencia.getComDatAno());
		}
	}
}