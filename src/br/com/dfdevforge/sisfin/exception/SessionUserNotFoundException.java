package br.com.dfdevforge.sisfin.exception;

public class SessionUserNotFoundException extends ParentException
{
	private static final long serialVersionUID = -8785868512811723823L;

	public SessionUserNotFoundException()
	{
		setExceptionName("sessionUserNotFound");
		setExceptionKey("sessionUserNotFound.exception");
	}
}