package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertTipoExtratoByTable extends PrsAbstract implements UpdateablePersistence<BtpTipoExtrato>
{
	public PrsInsertTipoExtratoByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpTipoExtrato to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, IOException
	{
		if (!Utils.hasValue(to.getTieCodTipoExtrato()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getTieTxtNome()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into tie_tipo_extrato ( ");
		sql.append("  tie_cod_tipo_extrato, ");
		sql.append("  tie_txt_nome ");
		sql.append(")");
		sql.append("values(?, ?) ");

		this.dbConn.preparedStatementSetSqlScript(sql.toString());

		to.setTieCodTipoExtrato(this.getPrimaryKey("tie_tipo_extrato", "tie_cod_tipo_extrato"));

		this.dbConn.preparedStatementSetParameter(1, to.getTieCodTipoExtrato());
		this.dbConn.preparedStatementSetParameter(2, to.getTieTxtNome());

		int updatedRows = this.dbConn.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}