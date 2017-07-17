package br.com.dfdevforge.sisfin.action;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.behavior.ActionControler;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.behavior.TransferObject;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public abstract class ActAbstract extends Action implements ActionControler
{
	protected String command;

	public Integer getIdUsuarioFromSession(HttpServletRequest request)
	{
		HttpSession sess = request.getSession();
		BtpUsuario btpUsuario = (BtpUsuario)sess.getAttribute("BtpUsuario");

		Integer usuCodUsuario = null;
		if (btpUsuario != null && btpUsuario.getUsuCodUsuario() > 0)
			usuCodUsuario = btpUsuario.getUsuCodUsuario();

		return usuCodUsuario;
	}

	public BtpUsuario getUserFromSession(HttpServletRequest request)
	{
		HttpSession sess = request.getSession();
		BtpUsuario btpUsuario = (BtpUsuario)sess.getAttribute("btpUsuario");

		return btpUsuario;
	}

	public void setListOnRequest(HttpServletRequest request, List<?> list, String name)
	{
		request.setAttribute(name, list);
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
		return true;
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	@Override
	public void loadComboboxData(HttpServletRequest request, TransferObject bean, ConnectionManager conn) throws SessionUserNotFoundException, TimezoneValueException, SQLException
	{
	}
}