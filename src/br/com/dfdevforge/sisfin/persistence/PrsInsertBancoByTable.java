package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertBancoByTable extends PrsAbstract implements UpdateablePersistence<BtpBanco>
{
	public PrsInsertBancoByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpBanco to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, IOException
	{
		if (!Utils.hasValue(to.getBanCodBanco()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBanTxtNome()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into ban_banco ( ");
		sql.append("  ban_cod_banco, ");
		sql.append("  ban_txt_nome ");
		sql.append(")");
		sql.append("values(?, ?) ");

		this.dbConn.preparedStatementSetSqlScript(sql.toString());

		to.setBanCodBanco(this.getPrimaryKey("ban_banco", "ban_cod_banco"));

		this.dbConn.preparedStatementSetParameter(1, to.getBanCodBanco());
		this.dbConn.preparedStatementSetParameter(2, to.getBanTxtNome());

		int updatedRows = this.dbConn.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}