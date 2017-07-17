package br.com.dfdevforge.sisfin.behavior;

import br.com.cagece.core.bean.api.AbstractBean;

public interface UpdateablePersistence<Btp extends AbstractBean>
{
	public int execute(Btp to) throws Exception;
}