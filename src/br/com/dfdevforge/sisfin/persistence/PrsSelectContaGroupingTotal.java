package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectContaGroupingTotal extends PrsAbstract implements SelectablePersistence<BtpConta>
{
	public PrsSelectContaGroupingTotal(ConnectionManager conn) throws SQLException
	{
		this.dbConn = conn;
	}

	public List<BtpConta> execute(BtpConta btpConta, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpConta> btpContaList = null;
		StringBuilder cond = new StringBuilder();

		if (btpConta != null)
		{
			if (Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
				cond.append(" and con.usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");
			else
				throw new SessionUserNotFoundException();

			cond.append(" and con.con_num_nivel like '03%' ");

			if (btpConta.getMap() != null && Utils.hasValue(btpConta.getMap().get("numMes")) && Utils.hasValue(btpConta.getMap().get("numAno")))
			{
				String mes = btpConta.getMap().get("numMes");
				String ano = btpConta.getMap().get("numAno");
				cond.append(" and (mov.mov_dat_pagamento is null and date_format(mov.mov_dat_vencimento, '%m/%Y') = '" + mes + "/" + ano + "' or date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + mes + "/" + ano + "') ");
			}
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " con_pai.CON_TXT_DESCRICAO ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  con_pai.CON_COD_CONTA CON_COD_CONTA, ");
		sql.append("  con_pai.CON_NUM_NIVEL CON_NUM_NIVEL, ");
		sql.append("  con_pai.CON_TXT_DESCRICAO CON_TXT_DESCRICAO, ");
		sql.append("  con_pai.USU_COD_USUARIO USU_COD_USUARIO, ");
		sql.append("  sum(mov.MOV_VLR_MOVIMENTO) AUX_VLR_TOTAL ");
		sql.append("from ");
		sql.append("  con_conta con inner join ");
		sql.append("  mov_movimento mov on con.CON_COD_CONTA = mov.CON_COD_CONTA_DESTINO inner join ");
		sql.append("  con_conta con_pai on con.CON_COD_CONTA_PAI = con_pai.CON_COD_CONTA ");
		sql.append( cond);
		sql.append("group by ");
		sql.append("  con_pai.CON_TXT_DESCRICAO ");
		sql.append( order);

		
		this.dbConn.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();

		while (this.dbConn.getResultSet().next())
		{
			BtpConta btp = new BtpConta();

			btp.setConCodConta(this.dbConn.getResultSet().getInt("con_cod_conta"));
			btp.setConTxtDescricao(this.dbConn.getResultSet().getString("con_txt_descricao"));
			btp.setConNumNivel(this.dbConn.getResultSet().getString("con_num_nivel"));
			btp.getMap().put("AUX_VLR_TOTAL", this.dbConn.getResultSet().getString("aux_vlr_total"));
			btp.getBtpUsuario().setUsuCodUsuario(this.dbConn.getResultSet().getInt("usu_cod_usuario"));

			btpContaList.add(btp);
		}

		return btpContaList;
	}
}