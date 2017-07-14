package br.com.dfdevforge.sisfin.dwr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import br.com.dfdevforge.sisfin.bean.BtpUsuario;

public class DWRUtil
{
	public static HttpServletRequest getRequest()
	{
		WebContext wctx = WebContextFactory.get();

		return wctx.getHttpServletRequest(); 
	}
	
	public static HttpServletResponse getResponse()
	{
		WebContext wctx = WebContextFactory.get();
						
		return wctx.getHttpServletResponse(); 
	}

	public static HttpSession getSession()
	{
		return getRequest().getSession();
	}

	public static BtpUsuario getSessionUser()
	{
		return (BtpUsuario)getSession().getAttribute("btpUsuario");
	}	
}