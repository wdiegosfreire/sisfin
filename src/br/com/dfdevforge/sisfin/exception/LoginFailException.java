package br.com.dfdevforge.sisfin.exception;

import br.com.dfdevforge.sisfin.exception.ParentException;

public class LoginFailException extends ParentException
{
	private static final long serialVersionUID = 6876033876548147724L;

	public LoginFailException()
	{
		setExceptionName("loginFail");
		setExceptionKey("loginFail.exception");
	}
}