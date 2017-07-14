package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplateRegra;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertTemplateRegraByTable extends PrsAbstract implements UpdateablePersistence<BtpTemplateRegra>
{
	public PrsInsertTemplateRegraByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpTemplateRegra to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, IOException
	{
		if (to == null)
			throw new NullBeanException();
		if (!Utils.hasValue(to.getBtpTemplate().getTemCodTemplate()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBtpRegra().getRegCodRegra()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into ter_template_regra ( ");
		sql.append("  ter_cod_valor_associado, ");
		sql.append("  tem_cod_template, ");
		sql.append("  reg_cod_regra ");
		sql.append(")");
		sql.append("values(?, ?, ?) ");

		this.dbConn.preparedStatementSetSqlScript(sql.toString());

		this.dbConn.preparedStatementSetParameter(1, to.getTerCodValorAssociado());
		this.dbConn.preparedStatementSetParameter(2, to.getBtpTemplate().getTemCodTemplate());
		this.dbConn.preparedStatementSetParameter(3, to.getBtpRegra().getRegCodRegra());

		int updatedRows = this.dbConn.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}