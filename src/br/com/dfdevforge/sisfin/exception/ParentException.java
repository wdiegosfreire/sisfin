package br.com.dfdevforge.sisfin.exception;

public class ParentException extends Exception
{
	private static final long serialVersionUID = 3163934157094540409L;

	private String exceptionName;
	private String exceptionKey;

	public String getExceptionName() {return exceptionName;}
	public void setExceptionName(String exceptionName) {this.exceptionName = exceptionName;}

	public String getExceptionKey() {return exceptionKey;}
	public void setExceptionKey(String exceptionKey) {this.exceptionKey = exceptionKey;}
}