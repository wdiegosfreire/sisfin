package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsDeleteMovimentoByObjetivo extends PrsAbstract implements UpdateablePersistence<BtpMovimento>
{
	public PrsDeleteMovimentoByObjetivo(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpMovimento btpMovimento) throws NullBeanException, RequiredColumnNotFoundException, SQLException
	{
		if (btpMovimento == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getBtpObjetivo().getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();

		sql.append("delete from ");
		sql.append("  mov_movimento ");
		sql.append("where ");
		sql.append("  obj_cod_objetivo = " + btpMovimento.getBtpObjetivo().getObjCodObjetivo() + " ");

		
		int updatedRows = this.connectionManager.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}
}