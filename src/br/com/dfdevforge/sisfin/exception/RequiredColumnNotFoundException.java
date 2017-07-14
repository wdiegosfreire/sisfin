package br.com.dfdevforge.sisfin.exception;

import br.com.dfdevforge.sisfin.exception.ParentException;

public class RequiredColumnNotFoundException extends ParentException
{
	private static final long serialVersionUID = -8917935802438084828L;

	public RequiredColumnNotFoundException()
	{
		setExceptionName("requiredColumnNotFound");
		setExceptionKey("requiredColumnNotFound.exception");
	}
}