package br.com.dfdevforge.sisfin.behavior;

import java.util.List;

import br.com.cagece.core.bean.AbstractBean;

public interface Persistence
{
	public List<? extends AbstractBean> select(AbstractBean to, int sqlOrdem) throws Exception;
	public Integer insert(AbstractBean to) throws Exception;
	public AbstractBean delete(AbstractBean to) throws Exception;
	public Integer updateByTable(AbstractBean to) throws Exception;
	public Integer updateByField(AbstractBean to) throws Exception;

	public Integer getPrimaryKey(String tableName, String fieldName) throws Exception;

	/*
	 * Deprecated methods
	 */
	@Deprecated
	public AbstractBean update(AbstractBean to) throws Exception;
}