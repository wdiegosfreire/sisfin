package br.com.dfdevforge.sisfin.exception;

public class NullBeanException extends ParentException
{
	private static final long serialVersionUID = 2654865013349716751L;

	public NullBeanException()
	{
		setExceptionName("nullBean");
		setExceptionKey("nullBean.exception");
	}

	@Override
	public String getMessage()
	{
		return "O objeto que representa a entidade está nulo.";
	}
}