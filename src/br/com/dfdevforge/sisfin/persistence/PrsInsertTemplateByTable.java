package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertTemplateByTable extends PrsAbstract implements UpdateablePersistence<BtpTemplate>
{
	public PrsInsertTemplateByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpTemplate to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, IOException
	{
		if (to == null)
			throw new NullBeanException();
		if (!Utils.hasValue(to.getTemTxtNome()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBtpUsuario().getUsuCodUsuario()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into tem_template ( ");
		sql.append("  tem_cod_template, ");
		sql.append("  tem_txt_nome, ");
		sql.append("  usu_cod_usuario ");
		sql.append(")");
		sql.append("values(?, ?, ?) ");

		this.connectionManager.preparedStatementSetSqlScript(sql.toString());

		to.setTemCodTemplate(this.getPrimaryKey("tem_template", "tem_cod_template"));

		this.connectionManager.preparedStatementSetParameter(1, to.getTemCodTemplate());
		this.connectionManager.preparedStatementSetParameter(2, to.getTemTxtNome());
		this.connectionManager.preparedStatementSetParameter(3, to.getBtpUsuario().getUsuCodUsuario());

		int updatedRows = this.connectionManager.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}