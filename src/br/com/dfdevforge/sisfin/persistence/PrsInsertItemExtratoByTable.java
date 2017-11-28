package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertItemExtratoByTable extends PrsAbstract implements UpdateablePersistence<BtpItemExtrato>
{
	public PrsInsertItemExtratoByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpItemExtrato to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, SessionUserNotFoundException, IOException
	{
		if (!Utils.hasValue(to.getIteDatMovimento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getIteTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getIteVlrMovimento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getIteTxtTipo()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBtpExtrato().getExtCodExtrato()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into ite_item_extrato ( ");
		sql.append("  ite_cod_item_extrato, ");
		sql.append("  ite_dat_movimento, ");
		sql.append("  ite_txt_descricao, ");
		sql.append("  ite_num_documento, ");
		sql.append("  ite_txt_tipo, ");
		sql.append("  ite_vlr_movimento, ");
		sql.append("  ext_cod_extrato ");
		sql.append(")");
		sql.append("values(?, ?, ?, ?, ?, ?, ?) ");

		this.connectionManager.preparedStatementSetSqlScript(sql.toString());

		to.setIteCodItemExtrato(this.getPrimaryKey("ite_item_extrato", "ite_cod_item_extrato"));

		this.connectionManager.preparedStatementSetParameter(1, to.getIteCodItemExtrato());
		this.connectionManager.preparedStatementSetParameter(2, to.getIteDatMovimento());
		this.connectionManager.preparedStatementSetParameter(3, to.getIteTxtDescricao());
		this.connectionManager.preparedStatementSetParameter(4, to.getIteNumDocumento());
		this.connectionManager.preparedStatementSetParameter(5, to.getIteTxtTipo());
		this.connectionManager.preparedStatementSetParameter(6, to.getIteVlrMovimento());
		this.connectionManager.preparedStatementSetParameter(7, to.getBtpExtrato().getExtCodExtrato());

		int updatedRows = this.connectionManager.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}