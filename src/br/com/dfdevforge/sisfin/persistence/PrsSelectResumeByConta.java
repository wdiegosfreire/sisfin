package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;

public class PrsSelectResumeByConta extends PrsAbstract implements SelectablePersistence<BtpConta>
{
	public PrsSelectResumeByConta(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public List<BtpConta> execute(BtpConta to, Integer sqlOrder) throws SQLException
	{
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			cond.append(" and date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + to.getMap().get("numMes") + "/" + to.getMap().get("numAno") + "' ");
			cond.append(" and con.CON_NUM_NIVEL like '03%' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by pai.con_txt_descricao ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  pai.con_cod_conta con_cod_conta, ");
		sql.append("  pai.con_txt_descricao con_txt_descricao, ");
		sql.append("  pai.con_num_nivel con_num_nivel, ");
		sql.append("  sum(mov.mov_vlr_movimento) mov_vlr_total ");
		sql.append("from ");
		sql.append("  con_conta con inner join ");
		sql.append("  mov_movimento mov on con.con_cod_conta = mov.con_cod_conta_destino inner join ");
		sql.append("  con_conta pai on con.con_cod_conta_pai = pai.con_cod_conta ");
		sql.append( cond);
		sql.append("group by ");
		sql.append("  pai.con_cod_conta ");
		sql.append( order);

		this.connectionManager.statementExecuteQuery(sql.toString());

		List<BtpConta> btpContaList = new ArrayList<BtpConta>();

		while (this.connectionManager.getResultSet().next())
		{
			BtpConta btp = new BtpConta();

			btp.setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta"));
			btp.setConTxtDescricao(this.connectionManager.getResultSet().getString("con_txt_descricao"));
			btp.setConNumNivel(this.connectionManager.getResultSet().getString("con_num_nivel"));
			btp.getMap().put("auxVlrTotal", this.connectionManager.getResultSet().getString("mov_vlr_total"));

			btpContaList.add(btp);
		}

		return btpContaList;
	}
}