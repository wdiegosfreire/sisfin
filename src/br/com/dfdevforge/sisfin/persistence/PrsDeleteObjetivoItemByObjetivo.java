package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpObjetivoItem;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsDeleteObjetivoItemByObjetivo extends PrsAbstract implements UpdateablePersistence<BtpObjetivoItem>
{
	public PrsDeleteObjetivoItemByObjetivo(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpObjetivoItem btpObjetivoItem) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		if (btpObjetivoItem == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivoItem.getBtpObjetivo().getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();

		sql.append("delete from obi_objetivo_item where obj_cod_objetivo = " + btpObjetivoItem.getBtpObjetivo().getObjCodObjetivo());

		
		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}
}