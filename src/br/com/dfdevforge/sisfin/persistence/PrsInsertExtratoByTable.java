package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsInsertExtratoByTable extends PrsAbstract implements UpdateablePersistence<BtpExtrato>
{
	public PrsInsertExtratoByTable(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpExtrato to) throws SQLException, NullBeanException, RequiredColumnNotFoundException, SessionUserNotFoundException, IOException
	{
		if (to == null || to.getBtpUsuario() == null || !Utils.hasValue(to.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(to.getExtDatAno()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getExtDatMes()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBtpTipoExtrato().getTieCodTipoExtrato()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(to.getBtpBanco().getBanCodBanco()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into ext_extrato ( ");
		sql.append("  ext_cod_extrato, ");
		sql.append("  ext_dat_ano, ");
		sql.append("  ext_dat_mes, ");
		sql.append("  ext_vlr_saldo_inicial, ");
		sql.append("  ext_vlr_saldo_final, ");
		sql.append("  tie_cod_tipo_extrato, ");
		sql.append("  ban_cod_banco, ");
		sql.append("  usu_cod_usuario ");
		sql.append(")");
		sql.append("values(?, ?, ?, ?, ?, ?, ?, ?) ");

		this.connectionManager.preparedStatementSetSqlScript(sql.toString());

		to.setExtCodExtrato(this.getPrimaryKey("ext_extrato", "ext_cod_extrato"));

		this.connectionManager.preparedStatementSetParameter(1, to.getExtCodExtrato());
		this.connectionManager.preparedStatementSetParameter(2, to.getExtDatAno());
		this.connectionManager.preparedStatementSetParameter(3, to.getExtDatMes());
		this.connectionManager.preparedStatementSetParameter(4, to.getExtVlrSaldoInicial());
		this.connectionManager.preparedStatementSetParameter(5, to.getExtVlrSaldoFinal());
		this.connectionManager.preparedStatementSetParameter(6, to.getBtpTipoExtrato().getTieCodTipoExtrato());
		this.connectionManager.preparedStatementSetParameter(7, to.getBtpBanco().getBanCodBanco());
		this.connectionManager.preparedStatementSetParameter(8, to.getBtpUsuario().getUsuCodUsuario());

		int updatedRows = this.connectionManager.preparedStatementExecuteUpdate();

		return updatedRows;
	}
}