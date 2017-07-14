package br.com.dfdevforge.sisfin.dwr;

import java.lang.reflect.Method;

import org.directwebremoting.AjaxFilter;
import org.directwebremoting.AjaxFilterChain;

public class DwrFilter implements AjaxFilter
{
	private String teste;
	public String getTeste() {return teste;}
	public void setTeste(String teste) {this.teste = teste;}

	@Override
	public Object doFilter(Object obj, Method method, Object[] param, AjaxFilterChain chain) throws Exception
	{
		return chain.doFilter(obj, method, param);
	}
}