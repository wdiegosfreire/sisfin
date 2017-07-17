package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpRegra;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectRegraParameterized extends PrsAbstract implements SelectablePersistence<BtpRegra>
{
	public PrsSelectRegraParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpRegra> execute(BtpRegra to, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpRegra> btpRegraList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getRegCodRegra()))
				cond.append(" and reg.reg_cod_regra = " + to.getRegCodRegra() + " ");
			if (Utils.hasValue(to.getRegTxtDescricao()))
				cond.append(" and reg.reg_txt_descricao = '" + to.getRegTxtDescricao() + "' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by reg.reg_txt_descricao ";
		else if (sqlOrder == 2)
			order = " order by reg.reg_num_ordem ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  reg.reg_cod_regra reg_cod_regra, ");
		sql.append("  reg.reg_txt_descricao reg_txt_descricao, ");
		sql.append("  reg.reg_flg_valor_associado reg_flg_valor_associado ");
		sql.append("from ");
		sql.append("  reg_regra reg ");
		sql.append( cond);
		sql.append( order);

		this.dbConn.statementExecuteQuery(sql.toString());

		btpRegraList = new ArrayList<BtpRegra>();

		while (this.dbConn.getResultSet().next())
		{
			BtpRegra btp = new BtpRegra();

			btp.setRegCodRegra(this.dbConn.getResultSet().getInt("reg_cod_regra"));
			btp.setRegTxtDescricao(this.dbConn.getResultSet().getString("reg_txt_descricao"));
			btp.setRegFlgValorAssociado(this.dbConn.getResultSet().getBoolean("reg_flg_valor_associado"));

			btpRegraList.add(btp);
		}

		return btpRegraList;
	}
}