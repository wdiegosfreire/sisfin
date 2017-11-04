package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsDeleteObjetivoByPrimaryKey extends PrsAbstract implements UpdateablePersistence<BtpObjetivo>
{
	public PrsDeleteObjetivoByPrimaryKey(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpObjetivo btpObjetivo) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		if (btpObjetivo == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivo.getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();

		sql.append("delete from obj_objetivo where obj_cod_objetivo = " + btpObjetivo.getObjCodObjetivo());

		
		int updatedRows = this.connectionManager.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}
}