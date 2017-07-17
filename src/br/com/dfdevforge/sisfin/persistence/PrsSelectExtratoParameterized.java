package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectExtratoParameterized extends PrsAbstract implements SelectablePersistence<BtpExtrato>
{
	public PrsSelectExtratoParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpExtrato> execute(BtpExtrato to, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getExtCodExtrato()))
				cond.append(" and ext.ext_cod_extrato = " + to.getExtCodExtrato() + " ");
			if (Utils.hasValue(to.getExtDatMes()))
				cond.append(" and ext.ext_dat_mes = " + to.getExtDatMes() + " ");
			if (Utils.hasValue(to.getExtDatAno()))
				cond.append(" and ext.ext_dat_ano = " + to.getExtDatAno() + " ");
			if (to.getBtpBanco() != null && Utils.hasValue(to.getBtpBanco().getBanCodBanco()))
				cond.append(" and ban.ban_cod_banco = " + to.getBtpBanco().getBanCodBanco() + " ");
			if (to.getBtpTipoExtrato() != null && Utils.hasValue(to.getBtpTipoExtrato().getTieCodTipoExtrato()))
				cond.append(" and tie.tie_cod_tipo_extrato = " + to.getBtpTipoExtrato().getTieCodTipoExtrato() + " ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by ban.ban_txt_nome, ext.ext_dat_ano, ext.ext_dat_mes, tie.tie_txt_nome ";
		if (sqlOrder == 2)
			order = " order by ban.ban_txt_nome, tie.tie_txt_nome, ext.ext_dat_ano, ext.ext_dat_mes ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  ext.ext_cod_extrato ext_cod_extrato, ");
		sql.append("  ext.ext_dat_ano ext_dat_ano, ");
		sql.append("  ext.ext_dat_mes ext_dat_mes, ");
		sql.append("  ext.ext_vlr_saldo_inicial ext_vlr_saldo_inicial, ");
		sql.append("  ext.ext_vlr_saldo_final ext_vlr_saldo_final, ");
		sql.append("  (select count(*) from ite_item_extrato ite_s where ite_s.ite_flg_exportado is null and ite_s.ext_cod_extrato = ext.ext_cod_extrato) ext_qtd_pendente, ");

		sql.append("  ban.ban_cod_banco, ");
		sql.append("  ban.ban_txt_nome, ");

		sql.append("  tie.tie_cod_tipo_extrato tie_cod_tipo_extrato, ");
		sql.append("  tie.tie_txt_nome tie_txt_nome, ");

		sql.append("  ite.ite_cod_item_extrato ite_cod_item_extrato, ");
		sql.append("  ite.ite_dat_movimento ite_dat_movimento, ");
		sql.append("  ite.ite_num_documento ite_num_documento, ");
		sql.append("  ite.ite_txt_descricao ite_txt_descricao, ");
		sql.append("  ite.ite_txt_tipo ite_txt_tipo, ");
		sql.append("  ite.ite_vlr_movimento ite_vlr_movimento, ");
		sql.append("  ite.ite_flg_exportado ite_flg_exportado, ");
		sql.append("  (select count(*) from mov_movimento m where date_format(m.mov_dat_pagamento, '%c/%Y') = concat(ext.ext_dat_mes, '/', ext.ext_dat_ano) and m.mov_vlr_movimento = ite.ite_vlr_movimento) aux_num_occurrence_valor, ");
		sql.append("  (select count(*) from mov_movimento m where m.mov_dat_pagamento = ite.ite_dat_movimento and m.mov_vlr_movimento = ite.ite_vlr_movimento) aux_num_occurrence_data_valor ");
		sql.append("from ");
		sql.append("  ext_extrato ext inner join ");
		sql.append("  ban_banco ban on ext.ban_cod_banco = ban.ban_cod_banco inner join ");
		sql.append("  tie_tipo_extrato tie on ext.tie_cod_tipo_extrato = tie.tie_cod_tipo_extrato inner join ");
		sql.append("  ite_item_extrato ite on ext.ext_cod_extrato = ite.ext_cod_extrato ");
		sql.append( cond);
		sql.append( order);

		
		this.dbConn.statementExecuteQuery(sql.toString());

		List<BtpExtrato> btpExtratoList = new ArrayList<BtpExtrato>();

		BtpExtrato btpExtrato = null;

		while (this.dbConn.getResultSet().next())
		{
			btpExtrato = new BtpExtrato();
			btpExtrato.setExtCodExtrato(this.dbConn.getResultSet().getInt("ext_cod_extrato"));

			if (!btpExtratoList.contains(btpExtrato))
			{
				btpExtrato.setExtDatMes(this.dbConn.getResultSet().getInt("ext_dat_mes"));
				btpExtrato.setExtDatAno(this.dbConn.getResultSet().getInt("ext_dat_ano"));
				btpExtrato.setExtVlrSaldoInicial(this.dbConn.getResultSet().getBigDecimal("ext_vlr_saldo_inicial"));
				btpExtrato.setExtVlrSaldoFinal(this.dbConn.getResultSet().getBigDecimal("ext_vlr_saldo_final"));
				btpExtrato.getMap().put("extQtdPendente", this.dbConn.getResultSet().getString("ext_qtd_pendente"));

				btpExtrato.setBtpBanco(new BtpBanco());
				btpExtrato.getBtpBanco().setBanCodBanco(this.dbConn.getResultSet().getInt("ban_cod_banco"));
				btpExtrato.getBtpBanco().setBanTxtNome(this.dbConn.getResultSet().getString("ban_txt_nome"));

				btpExtrato.setBtpTipoExtrato(new BtpTipoExtrato());
				btpExtrato.getBtpTipoExtrato().setTieCodTipoExtrato(this.dbConn.getResultSet().getInt("tie_cod_tipo_extrato"));
				btpExtrato.getBtpTipoExtrato().setTieTxtNome(this.dbConn.getResultSet().getString("tie_txt_nome"));

				btpExtratoList.add(btpExtrato);
			}

			BtpItemExtrato btpItemExtrato = new BtpItemExtrato();
			btpItemExtrato.setIteCodItemExtrato(this.dbConn.getResultSet().getInt("ite_cod_item_extrato"));
			btpItemExtrato.setIteDatMovimento(this.dbConn.getResultSet().getDate("ite_dat_movimento"));
			btpItemExtrato.setIteNumDocumento(this.dbConn.getResultSet().getString("ite_num_documento"));
			btpItemExtrato.setIteTxtDescricao(this.dbConn.getResultSet().getString("ite_txt_descricao"));
			btpItemExtrato.setIteTxtTipo(this.dbConn.getResultSet().getString("ite_txt_tipo"));
			btpItemExtrato.setIteVlrMovimento(this.dbConn.getResultSet().getBigDecimal("ite_vlr_movimento"));
			btpItemExtrato.setIteFlgExportado(this.dbConn.getResultSet().getBoolean("ite_flg_exportado"));

			btpItemExtrato.getMap().put("auxNumOccurrenceValor", this.dbConn.getResultSet().getString("aux_num_occurrence_valor"));
			btpItemExtrato.getMap().put("auxNumOccurrenceDataValor", this.dbConn.getResultSet().getString("aux_num_occurrence_data_valor"));

			if (btpExtratoList.get(btpExtratoList.indexOf(btpExtrato)).getBtpItemExtratoList() == null)
				btpExtratoList.get(btpExtratoList.indexOf(btpExtrato)).setBtpItemExtratoList(new ArrayList<BtpItemExtrato>());

			btpExtratoList.get(btpExtratoList.indexOf(btpExtrato)).getBtpItemExtratoList().add(btpItemExtrato);
		}

		return btpExtratoList;
	}
}