package br.com.dfdevforge.sisfin.behavior;

import java.util.List;

import br.com.cagece.core.bean.AbstractBean;

public interface SelectablePersistence<Btp extends AbstractBean>
{
	public List<Btp> execute(Btp to, Integer order) throws Exception;
}