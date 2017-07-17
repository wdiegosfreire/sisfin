package br.com.dfdevforge.sisfin.behavior;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.dwr.DwrReturn;

public interface DwrControler<Bean extends TransferObject>
{
	public DwrReturn execute(Bean transferObject, String command) throws Exception;

	public boolean actExecInsert(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecDelete(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecSearch(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actExecUpdate(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowInclForm(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowEditForm(Bean transferObject, ConnectionManager conn, BusinessControler bc) throws Exception;
	public boolean actShowMainPage(Bean transferObject, ConnectionManager conn, BusinessControler bc, DwrReturn dwrReturn) throws Exception;
}