package br.com.dfdevforge.sisfin.behavior;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public interface ActionControler
{
	public boolean actExecInsert(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecDelete(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecSearch(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecUpdate(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowInclForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowEditForm(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowMainPage(HttpServletRequest request, ActionForm actionForm, ConnectionManager conn, BusinessControler bc) throws Exception;

	/**
	 * Chama o m�toro <code>setAttribute</code> do objeto <code>request</code> passando um objeto <code>List</code> e o
	 * nome com o qual ser� reconhecido.
	 * 
	 * @param request
	 * <ul><li>Objeto de requisi��o do usu�rio</li></ul>
	 * @param list
	 * <ul><li>Cole��o que ser� atribu�da ao request</li></ul>
	 * @param name
	 * <ul><li>Nome com a qual a cole��o ser� reconhecida</li></ul>
	 */
	public void setListOnRequest(HttpServletRequest request, List<?> list, String name);

	/**
	 * Tem a finalidade de agrupar o c�digo necess�rio para o carregamento de comboboxes da p�gina
	 * @param request
	 * <ul><li>Objeto de requisi��o do usu�rio</li></ul>
	 * @param bean
	 * <ul><li>Objeto que cont�m as informa��es necess�rias para poss�veis filtros para os dados dos comboboxes.</li></ul>
	 */
	public void loadComboboxData(HttpServletRequest request, TransferObject bean, ConnectionManager conn) throws SessionUserNotFoundException, TimezoneValueException, SQLException;
}