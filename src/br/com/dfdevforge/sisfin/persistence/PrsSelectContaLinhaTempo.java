package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectContaLinhaTempo extends PrsAbstract implements SelectablePersistence<BtpConta>
{
	public PrsSelectContaLinhaTempo(ConnectionManager conn) throws SQLException
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

			if (Utils.hasValue(btpConta.getConCodConta()))
				cond.append(" and con.con_cod_conta_pai = " + btpConta.getConCodConta() + " ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		StringBuilder fields = new StringBuilder();

		int ano = Integer.parseInt(btpConta.getMap().get("numAno"));
		int mes = Integer.parseInt(btpConta.getMap().get("numMes"));

		String categoria = "";
		List<String> compList = new ArrayList<String>();
		for (int i = 0; i < 6; i++)
		{
			String cMes = "";
			if (mes < 10)
				cMes = "0" + mes;
			else
				cMes = "" + mes;

			String comp = cMes + "/" + ano;
			compList.add(comp);

			categoria += comp + ";";
			fields.append(" ,(select sum(mov_i.MOV_VLR_MOVIMENTO) AUX_VLR_TOTAL from con_conta con_i inner join mov_movimento mov_i ON con_i.CON_COD_CONTA = mov_i.CON_COD_CONTA_DESTINO where con_i.con_cod_conta = con.con_cod_conta and (mov_i.mov_dat_pagamento is null and date_format(mov_i.mov_dat_vencimento, '%m/%Y') = '" + comp + "' or date_format(mov_i.mov_dat_pagamento, '%m/%Y') = '" + comp + "')) as \"" + comp + "\" ");

			if (mes == 1)
			{
				mes = 12;
				ano--;
			}
			else
			{
				mes--;
			}
		}

		fields.replace(0, 2, " ");

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  con.con_cod_conta, ");
		sql.append("  con.con_txt_descricao, ");
		sql.append("  con.usu_cod_usuario, ");
		sql.append(   fields);
		sql.append("from ");
		sql.append("  con_conta con ");
		sql.append( cond);

		
		this.dbConn.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();

		while (this.dbConn.getResultSet().next())
		{
			BtpConta btp = new BtpConta();

			btp.setConCodConta(this.dbConn.getResultSet().getInt("con_cod_conta"));
			btp.setConTxtDescricao(this.dbConn.getResultSet().getString("con_txt_descricao"));

			btp.getBtpUsuario().setUsuCodUsuario(this.dbConn.getResultSet().getInt("usu_cod_usuario"));

			for (int i = 0; i < compList.size(); i++)
				btp.getMap().put(i + "", (this.dbConn.getResultSet().getString(compList.get(i)) != null ? this.dbConn.getResultSet().getString(compList.get(i)) : "0"));

			btp.getMap().put("categoria", categoria);

			btpContaList.add(btp);
		}

		return btpContaList;
	}
}