package br.com.dfdevforge.sisfin.exception;

public class TimezoneValueException extends ParentException
{
	private static final long serialVersionUID = 5849824053176371477L;

	public TimezoneValueException()
	{
		setExceptionName("timezoneValue");
		setExceptionKey("timezoneValue.exception");
	}
}