package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.behavior.Persistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsObjetivo extends PrsAbstract implements Persistence
{
	private BtpObjetivo btpObjetivo = null;
	private List<BtpObjetivo> btpObjetivoList = null;

	public PrsObjetivo(ConnectionManager dbConn) throws SQLException
	{ 
		this.dbConn = dbConn;
	}

	public List<BtpObjetivo> select(AbstractBean to, int sqlOrder) throws SQLException
	{
		btpObjetivo = (BtpObjetivo) to;

		StringBuilder cond = new StringBuilder();

		if (btpObjetivo != null)
		{
			cond.append(" and usu.usu_cod_usuario = " + btpObjetivo.getBtpUsuario().getUsuCodUsuario() + " ");

			if (Utils.hasValue(btpObjetivo.getObjCodObjetivo()))
				cond.append(" and obj.obj_cod_objetivo = " + btpObjetivo.getObjCodObjetivo() + " ");

			if (btpObjetivo.getMap() != null && Utils.hasValue(btpObjetivo.getMap().get("actualMonth"), "true"))
				cond.append(" and date_format(mov.mov_dat_pagamento, '%m/%Y') = date_format(now(), '%m/%Y') ");

			/*
			 * Definição dos filtros adicionados ao objeto Map
			 */
			if (btpObjetivo.getMap() != null)
			{
				// Filtro por competência
				if (Utils.hasValue(btpObjetivo.getMap().get("numMes")) && Utils.hasValue(btpObjetivo.getMap().get("numAno")))
				{
					String mes = btpObjetivo.getMap().get("numMes");
					String ano = btpObjetivo.getMap().get("numAno");
					cond.append(" and (mov.mov_dat_pagamento is null and date_format(mov.mov_dat_vencimento, '%m/%Y') = '" + mes + "/" + ano + "' or date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + mes + "/" + ano + "') ");
				}
				else if (!Utils.hasValue(btpObjetivo.getMap().get("numMes")) && Utils.hasValue(btpObjetivo.getMap().get("numAno")))
				{
					String ano = btpObjetivo.getMap().get("numAno");
					cond.append(" and (mov.mov_dat_pagamento is null and date_format(mov.mov_dat_vencimento, '%Y') = '" + ano + "' or date_format(mov.mov_dat_pagamento, '%Y') = '" + ano + "') ");
				}

				// Filtro por data de vencimento
				if (Utils.hasValue(btpObjetivo.getMap().get("movDatVencimentoMin")) && Utils.hasValue(btpObjetivo.getMap().get("movDatVencimentoMax")))
					cond.append(" and mov.mov_dat_vencimento between str_to_date('" + btpObjetivo.getMap().get("movDatVencimentoMin") + "', '%d/%m/%Y') and str_to_date('" + btpObjetivo.getMap().get("movDatVencimentoMax") + "', '%d/%m/%Y') ");

				// Filtro por data de pagamento
				if (Utils.hasValue(btpObjetivo.getMap().get("movDatPagamentoMin")) && Utils.hasValue(btpObjetivo.getMap().get("movDatPagamentoMax")))
					cond.append(" and mov.mov_dat_pagamento between str_to_date('" + btpObjetivo.getMap().get("movDatPagamentoMin") + "', '%d/%m/%Y') and str_to_date('" + btpObjetivo.getMap().get("movDatPagamentoMax") + "', '%d/%m/%Y') ");

				// Filtro por descrição do objetivo
				if (Utils.hasValue(btpObjetivo.getMap().get("objTxtDescricao")))
					cond.append(" and upper(obj.obj_txt_descricao) like upper('%" + btpObjetivo.getMap().get("objTxtDescricao") + "%') ");

				// Filtro por estabelecimento
				if (Utils.hasValue(btpObjetivo.getMap().get("estCodEstabelecimento")))
					cond.append(" and est.est_cod_estabelecimento = " + btpObjetivo.getMap().get("estCodEstabelecimento") + " ");

				// Filtro por forma de pagamento
				if (Utils.hasValue(btpObjetivo.getMap().get("fopCodFormaPagamento")))
					cond.append(" and fop.fop_cod_forma_pagamento = " + btpObjetivo.getMap().get("fopCodFormaPagamento") + " ");

				// Filtro por valor
				if (Utils.hasValue(btpObjetivo.getMap().get("movVlrMovimentoMin")) && Utils.hasValue(btpObjetivo.getMap().get("movVlrMovimentoMax")))
					cond.append(" and mov.mov_vlr_movimento between " + btpObjetivo.getMap().get("movVlrMovimentoMin") + " and " + btpObjetivo.getMap().get("movVlrMovimentoMax") + " ");

				// Filtro por conta de origem
				if (Utils.hasValue(btpObjetivo.getMap().get("conCodContaOrigem")))
				{
					cond.append(" and ( ");
					cond.append("   coo.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaOrigem") + " or ");
					cond.append("   coo_pai.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaOrigem") + " or ");
					cond.append("   coo_avo.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaOrigem") + " ");
					cond.append(" ) ");
				}

				// Filtro por conta de destino
				if (Utils.hasValue(btpObjetivo.getMap().get("conCodContaDestino")))
				{
					cond.append(" and ( ");
					cond.append("   cod.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaDestino") + " or ");
					cond.append("   cod_pai.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaDestino") + " or ");
					cond.append("   cod_avo.con_cod_conta = " + btpObjetivo.getMap().get("conCodContaDestino") + " ");
					cond.append(" ) ");
				}
			}
		}

		String order = "";
		if (sqlOrder == 1)
			order = " order by mov.mov_dat_pagamento, mov.mov_dat_vencimento, mov.mov_cod_movimento ";
		else if (sqlOrder == 2)
			order = " order by mov.mov_num_parcela";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  obj.obj_cod_objetivo obj_cod_objetivo, ");
		sql.append("  obj.obj_txt_descricao obj_txt_descricao, ");

		sql.append("  est.est_cod_estabelecimento est_cod_estabelecimento, ");
		sql.append("  est.est_nom_estabelecimento est_nom_estabelecimento, ");

		sql.append("  mov.mov_cod_movimento mov_cod_movimento, ");
		sql.append("  mov.mov_num_parcela mov_num_parcela, ");
		sql.append("  mov.mov_dat_vencimento mov_dat_vencimento, ");
		sql.append("  mov.mov_dat_pagamento mov_dat_pagamento, ");
		sql.append("  mov.mov_vlr_movimento mov_vlr_movimento, ");

		sql.append("  fop.fop_cod_forma_pagamento fop_cod_forma_pagamento, ");
		sql.append("  fop.fop_nom_forma_pagamento fop_nom_forma_pagamento, ");

		sql.append("  coo.con_cod_conta coo_cod_conta, ");
		sql.append("  coo.con_num_nivel coo_num_nivel, ");
		sql.append("  coo.con_txt_descricao coo_txt_descricao, ");
		sql.append("  coo_pai.con_cod_conta coo_cod_conta_pai, ");
		sql.append("  coo_pai.con_num_nivel coo_num_nivel_pai, ");
		sql.append("  coo_pai.con_txt_descricao coo_txt_descricao_pai, ");
		sql.append("  coo_avo.con_cod_conta coo_cod_conta_avo, ");
		sql.append("  coo_avo.con_num_nivel coo_num_nivel_avo, ");
		sql.append("  coo_avo.con_txt_descricao coo_txt_descricao_avo, ");

		sql.append("  cod.con_cod_conta cod_cod_conta, ");
		sql.append("  cod.con_num_nivel cod_num_nivel, ");
		sql.append("  cod.con_txt_descricao cod_txt_descricao, ");
		sql.append("  cod_pai.con_cod_conta cod_cod_conta_pai, ");
		sql.append("  cod_pai.con_num_nivel cod_num_nivel_pai, ");
		sql.append("  cod_pai.con_txt_descricao cod_txt_descricao_pai, ");
		sql.append("  cod_avo.con_cod_conta cod_cod_conta_avo, ");
		sql.append("  cod_avo.con_num_nivel cod_num_nivel_avo, ");
		sql.append("  cod_avo.con_txt_descricao cod_txt_descricao_avo, ");

		sql.append("  (select count(*) from mov_movimento where obj_cod_objetivo = obj.obj_cod_objetivo) aux_qtd_parcela ");
		sql.append("from ");
		sql.append("  obj_objetivo obj inner join ");
		sql.append("  usu_usuario usu on obj.usu_cod_usuario = usu.usu_cod_usuario inner join ");
		sql.append("  mov_movimento mov on obj.obj_cod_objetivo = mov.obj_cod_objetivo inner join ");
		sql.append("  fop_forma_pagamento fop on mov.fop_cod_forma_pagamento = fop.fop_cod_forma_pagamento inner join ");

		sql.append("  con_conta coo on mov.con_cod_conta_origem = coo.con_cod_conta inner join ");
		sql.append("  con_conta coo_pai on coo.con_cod_conta_pai = coo_pai.con_cod_conta inner join ");
		sql.append("  con_conta coo_avo on coo_pai.con_cod_conta_pai = coo_avo.con_cod_conta inner join ");

		sql.append("  con_conta cod on mov.con_cod_conta_destino = cod.con_cod_conta left join ");
		sql.append("  con_conta cod_pai on cod.con_cod_conta_pai = cod_pai.con_cod_conta inner join ");
		sql.append("  con_conta cod_avo on cod_pai.con_cod_conta_pai = cod_avo.con_cod_conta left join ");

		sql.append("  est_estabelecimento est on obj.est_cod_estabelecimento = est.est_cod_estabelecimento ");
		sql.append("where 1 = 1 " + cond + " " + order);

		this.dbConn.statementExecuteQuery(sql.toString());

		btpObjetivoList = new ArrayList<BtpObjetivo>();
		BtpObjetivo btpObjetivo = null;
		while (this.dbConn.getResultSet().next())
		{
			btpObjetivo = new BtpObjetivo();

			btpObjetivo.setObjCodObjetivo(this.dbConn.getResultSet().getInt("obj_cod_objetivo"));
			btpObjetivo.setObjTxtDescricao(this.dbConn.getResultSet().getString("obj_txt_descricao"));

			btpObjetivo.getBtpEstabelecimento().setEstCodEstabelecimento(this.dbConn.getResultSet().getInt("est_cod_estabelecimento"));
			btpObjetivo.getBtpEstabelecimento().setEstNomEstabelecimento(this.dbConn.getResultSet().getString("est_nom_estabelecimento"));

			btpObjetivoList.add(btpObjetivo);

			BtpMovimento mov = new BtpMovimento();

			mov.setMovCodMovimento(this.dbConn.getResultSet().getInt("mov_cod_movimento"));
			mov.setMovNumParcela(this.dbConn.getResultSet().getInt("mov_num_parcela"));
			mov.setMovDatVencimento(this.dbConn.getResultSet().getDate("mov_dat_vencimento"));
			mov.setMovDatPagamento(this.dbConn.getResultSet().getDate("mov_dat_pagamento"));
			mov.setMovVlrMovimentado(this.dbConn.getResultSet().getBigDecimal("mov_vlr_movimento"));

			mov.getBtpFormaPagamento().setFopCodFormaPagamento(this.dbConn.getResultSet().getInt("fop_cod_forma_pagamento"));
			mov.getBtpFormaPagamento().setFopNomFormaPagamento(this.dbConn.getResultSet().getString("fop_nom_forma_pagamento"));

			/*
			 * Recuperando as informações da conta de origem comseu respectivo "pai" e "avô"
			 */
			mov.getBtpContaOrigem().setConCodConta(this.dbConn.getResultSet().getInt("coo_cod_conta"));
			mov.getBtpContaOrigem().setConNumNivel(this.dbConn.getResultSet().getString("coo_num_nivel"));
			mov.getBtpContaOrigem().setConTxtDescricao(this.dbConn.getResultSet().getString("coo_txt_descricao"));
			mov.getBtpContaOrigem().setBtpContaPai(new BtpConta());
			mov.getBtpContaOrigem().getBtpContaPai().setConCodConta(this.dbConn.getResultSet().getInt("coo_cod_conta_pai"));
			mov.getBtpContaOrigem().getBtpContaPai().setConNumNivel(this.dbConn.getResultSet().getString("coo_num_nivel_pai"));
			mov.getBtpContaOrigem().getBtpContaPai().setConTxtDescricao(this.dbConn.getResultSet().getString("coo_txt_descricao_pai"));
			mov.getBtpContaOrigem().getBtpContaPai().setBtpContaPai(new BtpConta());
			mov.getBtpContaOrigem().getBtpContaPai().getBtpContaPai().setConCodConta(this.dbConn.getResultSet().getInt("coo_cod_conta_avo"));
			mov.getBtpContaOrigem().getBtpContaPai().getBtpContaPai().setConNumNivel(this.dbConn.getResultSet().getString("coo_num_nivel_avo"));
			mov.getBtpContaOrigem().getBtpContaPai().getBtpContaPai().setConTxtDescricao(this.dbConn.getResultSet().getString("coo_txt_descricao_avo"));

			/*
			 * Recuperando as informações da conta de destino comseu respectivo "pai" e "avô"
			 */
			mov.getBtpContaDestino().setConCodConta(this.dbConn.getResultSet().getInt("cod_cod_conta"));
			mov.getBtpContaDestino().setConNumNivel(this.dbConn.getResultSet().getString("cod_num_nivel"));
			mov.getBtpContaDestino().setConTxtDescricao(this.dbConn.getResultSet().getString("cod_txt_descricao"));
			mov.getBtpContaDestino().setBtpContaPai(new BtpConta());
			mov.getBtpContaDestino().getBtpContaPai().setConCodConta(this.dbConn.getResultSet().getInt("cod_cod_conta_pai"));
			mov.getBtpContaDestino().getBtpContaPai().setConNumNivel(this.dbConn.getResultSet().getString("cod_num_nivel_pai"));
			mov.getBtpContaDestino().getBtpContaPai().setConTxtDescricao(this.dbConn.getResultSet().getString("cod_txt_descricao_pai"));
			mov.getBtpContaDestino().getBtpContaPai().setBtpContaPai(new BtpConta());
			mov.getBtpContaDestino().getBtpContaPai().getBtpContaPai().setConCodConta(this.dbConn.getResultSet().getInt("cod_cod_conta_avo"));
			mov.getBtpContaDestino().getBtpContaPai().getBtpContaPai().setConNumNivel(this.dbConn.getResultSet().getString("cod_num_nivel_avo"));
			mov.getBtpContaDestino().getBtpContaPai().getBtpContaPai().setConTxtDescricao(this.dbConn.getResultSet().getString("cod_txt_descricao_avo"));

			mov.getMap().put("auxQtdParcela", this.dbConn.getResultSet().getString("aux_qtd_parcela"));

			btpObjetivo.getBtpMovimentoList().add(mov);
		}

		return btpObjetivoList;
	}

	public Integer insert(AbstractBean to) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		btpObjetivo = (BtpObjetivo) to;
		btpObjetivo.setObjCodObjetivo(this.getPrimaryKey("obj_objetivo", "obj_cod_objetivo"));

		if (btpObjetivo == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivo.getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivo.getObjTxtDescricao()))
			throw new RequiredColumnNotFoundException();

		if (btpObjetivo.getBtpUsuario() == null)
			throw new NullBeanException();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		fields.append("obj_cod_objetivo");
		values.append("" + btpObjetivo.getObjCodObjetivo() + "");

		fields.append(", obj_txt_descricao");
		values.append(", '" + btpObjetivo.getObjTxtDescricao() + "'");

		fields.append(", usu_cod_usuario");
		values.append(", " + btpObjetivo.getBtpUsuario().getUsuCodUsuario() + "");

		if (Utils.hasValue(btpObjetivo.getBtpEstabelecimento().getEstCodEstabelecimento()))
		{
			fields.append(", est_cod_estabelecimento");
			values.append(", " + btpObjetivo.getBtpEstabelecimento().getEstCodEstabelecimento() + "");
		}
		else
		{
			fields.append(", est_cod_estabelecimento");
			values.append(", null");
		}

		StringBuilder sql = new StringBuilder();
		sql.append("insert into obj_objetivo(" + fields + ") values(" + values + ")");

		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public BtpObjetivo update(AbstractBean to) {return btpObjetivo;}

	public BtpObjetivo delete(AbstractBean to)
	{
		return null;
	}

	/**
	 * @throws NullBeanException 
	 * @throws RequiredColumnNotFoundException 
	 * @throws SQLException 
	 * @throws SessionUserNotFoundException
	 * 
	 */
	public Integer updateByTable(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		btpObjetivo = (BtpObjetivo) to;

		if (btpObjetivo == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivo.getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivo.getObjTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivo.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		StringBuilder values = new StringBuilder();

		values.append("obj_txt_descricao = '" + btpObjetivo.getObjTxtDescricao() + "'");

		if (Utils.hasValue(btpObjetivo.getBtpEstabelecimento().getEstCodEstabelecimento()))
			values.append(", est_cod_estabelecimento = '" + btpObjetivo.getBtpEstabelecimento().getEstCodEstabelecimento() + "'");
		else
			values.append(", est_cod_estabelecimento = null");

		StringBuilder sql = new StringBuilder();
		sql.append("update obj_objetivo set " + values + " ");
		sql.append("where ");
		sql.append("  obj_cod_objetivo = " + btpObjetivo.getObjCodObjetivo() + " ");
		sql.append("  and usu_cod_usuario = " + btpObjetivo.getBtpUsuario().getUsuCodUsuario() + " ");

		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public Integer updateByField(AbstractBean to) throws Exception
	{
		return 0;
	}
}