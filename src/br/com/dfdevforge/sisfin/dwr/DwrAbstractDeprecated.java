package br.com.dfdevforge.sisfin.dwr;

import br.com.dfdevforge.sisfin.behavior.DwrControler;

@Deprecated
public abstract class DwrAbstractDeprecated implements DwrControler
{
	public Object getAttribute(String objectName)
	{
		return DWRUtil.getRequest().getAttribute(objectName);
	}

	public void setAttribute(String objectName, Object object)
	{
		DWRUtil.getRequest().setAttribute(objectName, object);
	}
}