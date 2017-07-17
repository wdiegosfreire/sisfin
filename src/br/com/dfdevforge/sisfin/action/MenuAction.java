package br.com.dfdevforge.sisfin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.behavior.BusinessControler;
import br.com.dfdevforge.sisfin.constants.Constants;

public class MenuAction extends ActAbstract
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		Constants.errors.clear();
		boolean ret = false;
		String cmd = "";

		cmd = request.getParameter("cmd");
		try
		{
			if (cmd.equals(Constants.actShowMainPage))
			{
				ret = actShowMainPage(request, null, null, null);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (!ret || Constants.errors.size() > 0)
		{
			saveErrors(request, Constants.errors);
			return (new ActionForward(mapping.getInput()));
		}

		return (mapping.findForward(cmd));
	}

	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception
	{
		return false;
	}

	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc)
	{
		return true;
	}
}