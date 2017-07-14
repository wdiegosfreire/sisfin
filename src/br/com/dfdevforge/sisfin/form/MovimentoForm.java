package br.com.dfdevforge.sisfin.form;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import br.com.dfdevforge.sisfin.bean.BtpMovimento;

public class MovimentoForm extends FrmAbstract
{
	private static final long serialVersionUID = 8361008974410419105L;

	protected ActionErrors errors = new ActionErrors();

	private BtpMovimento btpMovimento = new BtpMovimento();

	private String movDatMovimento;

	public void reset(ActionMapping mapping, HttpServletRequest request) 
	{
		btpMovimento = new BtpMovimento();

		setSqlOrder(1);
	}

	public ActionErrors validate()
	{
		errors.clear();

		if (btpMovimento.getMovDatRegistro() == null)
			errors.add("movDatRegistro", new ActionMessage("erro.attribute.movDatRegistro.required"));

		return errors;
	}

	public BtpMovimento getMovimentoBean() {return btpMovimento;}
	public void setMovimentoBean(BtpMovimento movimentoBean) {this.btpMovimento = movimentoBean;}

	public String getMovDatMovimento()
	{
		if (btpMovimento.getMovDatRegistro() != null)
		{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			movDatMovimento = df.format(btpMovimento.getMovDatRegistro());
		}
		else
		{
			movDatMovimento = null;
		}

		return movDatMovimento;
	}

	public void setMovDatMovimento(String movDatMovimento)
	{
		if (movDatMovimento != null && !movDatMovimento.trim().equals(""))
		{
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			try
			{
				btpMovimento.setMovDatRegistro(df.parse(movDatMovimento));
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}			
		}
		else
		{
			btpMovimento.setMovDatRegistro(null);
		}
	}
}