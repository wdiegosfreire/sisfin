package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectContaParameterized extends PrsAbstract implements SelectablePersistence<BtpConta>
{
	public PrsSelectContaParameterized(ConnectionManager conn) throws SQLException
	{
		this.connectionManager = conn;
	}

	public List<BtpConta> execute(BtpConta btpConta, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpConta> btpContaList = null;
		StringBuilder whereClause = new StringBuilder();

		// Verificando as credenciais do usuário autenticado
		if (btpConta == null || btpConta.getBtpUsuario() == null || !Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();
		else
			whereClause.append(" and con.usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");

		/*
		 * Montando a cláusula where
		 */
		if (Utils.hasValue(btpConta.getMap().get("conAuxOnlyRootElements"), "true"))
		{
			whereClause.append(" and con.con_cod_conta = con.con_cod_conta_pai ");

			if (Utils.hasValue(btpConta.getMap().get("conAuxOnlyOrigin"), "true"))
				whereClause.append(" and (con.con_num_nivel like '01%' or con.con_num_nivel like '02%') ");
			if (Utils.hasValue(btpConta.getMap().get("conAuxOnlyDestiny"), "true"))
				whereClause.append(" and (con.con_num_nivel like '01%' or con.con_num_nivel like '03%') ");
		}
		else if (Utils.hasValue(btpConta.getMap().get("conAuxOnlyChildElements"), "true"))
		{
			whereClause.append(" and con.con_cod_conta_pai = " + btpConta.getConCodConta() + " ");
			whereClause.append(" and con.con_cod_conta_pai <> con.con_cod_conta ");
		}
		else
		{
			if (Utils.hasValue(btpConta.getConCodConta()))
				whereClause.append(" and con.con_cod_conta = " + btpConta.getConCodConta() + " ");
			if (Utils.hasValue(btpConta.getConTxtDescricao()))
				whereClause.append(" and con.con_txt_descricao like '%" + btpConta.getConTxtDescricao() + "%' ");
		}

		if (whereClause.length() > 4)
			whereClause.replace(0, 4, " where ");

		String orderClause = "";
		if (sqlOrder == 1)
			orderClause = " order by con.con_num_nivel ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  con.con_cod_conta con_cod_conta, ");
		sql.append("  con.con_txt_descricao con_txt_descricao, ");
		sql.append("  con.con_num_nivel con_num_nivel, ");
		sql.append("  con.con_flg_poupanca con_flg_poupanca, ");
		sql.append("  con.con_flg_movimento con_flg_movimento, ");
		sql.append("  con.con_flg_virtual con_flg_virtual, ");
		sql.append("  con.usu_cod_usuario usu_cod_usuario ");
		sql.append("from ");
		sql.append("  con_conta con ");
		sql.append( whereClause);
		sql.append( orderClause);

		
		this.connectionManager.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();

		while (this.connectionManager.getResultSet().next())
		{
			BtpConta btp = new BtpConta();

			btp.setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta"));
			btp.setConTxtDescricao(this.connectionManager.getResultSet().getString("con_txt_descricao"));
			btp.setConNumNivel(this.connectionManager.getResultSet().getString("con_num_nivel"));
			btp.setConFlgMovimento(this.connectionManager.getResultSet().getBoolean("con_flg_movimento"));
			btp.setConFlgPoupanca(this.connectionManager.getResultSet().getBoolean("con_flg_poupanca"));
			btp.setConFlgVirtual(this.connectionManager.getResultSet().getBoolean("con_flg_virtual"));
			btp.getBtpUsuario().setUsuCodUsuario(this.connectionManager.getResultSet().getInt("usu_cod_usuario"));

			btpContaList.add(btp);
		}

		/*
		 * Executando o método recursivamente para obter os filhos 
		 */
		for (BtpConta btpConAux : btpContaList)
		{
			btpConAux.getMap().put("conAuxOnlyChildElements", "true");
			btpConAux.setBtpContaList(this.execute(btpConAux, 0));
		}

		return btpContaList;
	}
}