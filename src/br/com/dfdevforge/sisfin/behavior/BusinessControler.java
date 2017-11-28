package br.com.dfdevforge.sisfin.behavior;

import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;

public interface BusinessControler
{
	public List<? extends AbstractBean> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws Exception;
	public List<? extends AbstractBean> cadastrar(AbstractBean to, ConnectionManager conn) throws Exception;
	public List<? extends AbstractBean> editar(AbstractBean to, ConnectionManager conn) throws Exception;
	public List<? extends AbstractBean> exibir(AbstractBean to, ConnectionManager conn) throws Exception;
	public void alterar(AbstractBean to, ConnectionManager conn) throws Exception;
	public void incluir(AbstractBean to, ConnectionManager conn) throws Exception;
	public void excluir(AbstractBean to, ConnectionManager conn) throws Exception;
}