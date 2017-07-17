package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectTipoExtratoParameterized extends PrsAbstract implements SelectablePersistence<BtpTipoExtrato>
{
	public PrsSelectTipoExtratoParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpTipoExtrato> execute(BtpTipoExtrato to, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpTipoExtrato> btpTipoExtratoList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getTieCodTipoExtrato()))
				cond.append(" and tie.tie_cod_tipo_extrato = " + to.getTieCodTipoExtrato() + " ");
			if (Utils.hasValue(to.getTieTxtNome()))
				cond.append(" and tie.tie_txt_nome like '%" + to.getTieTxtNome() + "%' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by tie.tie_txt_nome ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  tie.tie_cod_tipo_extrato tie_cod_tipo_extrato, ");
		sql.append("  tie.tie_txt_nome tie_txt_nome ");
		sql.append("from ");
		sql.append("  tie_tipo_extrato tie ");
		sql.append( cond);
		sql.append( order);

		
		this.dbConn.statementExecuteQuery(sql.toString());

		btpTipoExtratoList = new ArrayList<BtpTipoExtrato>();

		while (this.dbConn.getResultSet().next())
		{
			BtpTipoExtrato btp = new BtpTipoExtrato();

			btp.setTieCodTipoExtrato(this.dbConn.getResultSet().getInt("tie_cod_tipo_extrato"));
			btp.setTieTxtNome(this.dbConn.getResultSet().getString("tie_txt_nome"));

			btpTipoExtratoList.add(btp);
		}

		return btpTipoExtratoList;
	}
}