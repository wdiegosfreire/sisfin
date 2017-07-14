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
	 * Chama o métoro <code>setAttribute</code> do objeto <code>request</code> passando um objeto <code>List</code> e o
	 * nome com o qual será reconhecido.
	 * 
	 * @param request
	 * <ul><li>Objeto de requisição do usuário</li></ul>
	 * @param list
	 * <ul><li>Coleção que será atribuída ao request</li></ul>
	 * @param name
	 * <ul><li>Nome com a qual a coleção será reconhecida</li></ul>
	 */
	public void setListOnRequest(HttpServletRequest request, List<?> list, String name);

	/**
	 * Tem a finalidade de agrupar o código necessário para o carregamento de comboboxes da página
	 * @param request
	 * <ul><li>Objeto de requisição do usuário</li></ul>
	 * @param bean
	 * <ul><li>Objeto que contém as informações necessárias para possíveis filtros para os dados dos comboboxes.</li></ul>
	 */
	public void loadComboboxData(HttpServletRequest request, TransferObject bean, ConnectionManager conn) throws SessionUserNotFoundException, TimezoneValueException, SQLException;
}