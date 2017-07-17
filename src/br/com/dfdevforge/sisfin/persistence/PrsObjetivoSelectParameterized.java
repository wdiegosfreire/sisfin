package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpObjetivoItem;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsObjetivoSelectParameterized extends PrsAbstract implements SelectablePersistence<BtpObjetivo>
{
	public PrsObjetivoSelectParameterized(ConnectionManager dbConn) throws SQLException
	{
		this.dbConn = dbConn;
	}

	public List<BtpObjetivo> execute(BtpObjetivo to, Integer sqlOrder) throws SQLException
	{
		List<BtpObjetivo> btpObjetivoList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			cond.append(" and usu.usu_cod_usuario = " + to.getBtpUsuario().getUsuCodUsuario() + " ");

			if (Utils.hasValue(to.getObjCodObjetivo()))
				cond.append(" and obj.obj_cod_objetivo = " + to.getObjCodObjetivo() + " ");
		}
		cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by mov.mov_dat_pagamento desc, mov.mov_dat_vencimento desc, mov.mov_cod_movimento desc ";
		else if (sqlOrder == 2)
			order = " order by mov.mov_num_parcela";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  obj.obj_cod_objetivo obj_cod_objetivo, ");
		sql.append("  obj.obj_txt_descricao obj_txt_descricao, ");

		sql.append("  est.est_cod_estabelecimento est_cod_estabelecimento, ");
		sql.append("  est.est_nom_estabelecimento est_nom_estabelecimento, ");

		sql.append("  obi.obi_cod_objetivo_item obi_cod_objetivo_item, ");
		sql.append("  obi.obi_num_item obi_num_item, ");
		sql.append("  obi.obi_txt_descricao obi_txt_descricao, ");
		sql.append("  obi.obi_num_quantidade obi_num_quantidade, ");
		sql.append("  obi.obi_vlr_unidade obi_vlr_unidade, ");

		sql.append("  mov.mov_cod_movimento mov_cod_movimento, ");
		sql.append("  mov.mov_num_parcela mov_num_parcela, ");
		sql.append("  mov.mov_vlr_movimento mov_vlr_movimento, ");
		sql.append("  mov.mov_dat_vencimento mov_dat_vencimento, ");
		sql.append("  mov.mov_dat_pagamento mov_dat_pagamento, ");

		sql.append("  fop.fop_cod_forma_pagamento fop_cod_forma_pagamento, ");
		sql.append("  fop.fop_nom_forma_pagamento fop_nom_forma_pagamento, ");

		sql.append("  coo.con_cod_conta coo_cod_conta, ");
		sql.append("  coo.con_txt_descricao coo_txt_descricao, ");

		sql.append("  cod.con_cod_conta cod_cod_conta, ");
		sql.append("  cod.con_txt_descricao cod_txt_descricao ");
		sql.append("from ");
		sql.append("  obj_objetivo obj inner join ");
		sql.append("  usu_usuario usu on obj.usu_cod_usuario = usu.usu_cod_usuario left join ");
		sql.append("  est_estabelecimento est on obj.est_cod_estabelecimento = est.est_cod_estabelecimento inner join ");
		sql.append("  obi_objetivo_item obi on obj.obj_cod_objetivo = obi.obj_cod_objetivo inner join ");
		sql.append("  mov_movimento mov on obj.obj_cod_objetivo = mov.obj_cod_objetivo inner join ");
		sql.append("  fop_forma_pagamento fop on mov.fop_cod_forma_pagamento = fop.fop_cod_forma_pagamento inner join ");
		sql.append("  con_conta coo on mov.con_cod_conta_origem = coo.con_cod_conta inner join ");
		sql.append("  con_conta cod on mov.con_cod_conta_destino = cod.con_cod_conta ");
		sql.append( cond);
		sql.append( order);

		this.dbConn.statementExecuteQuery(sql.toString());

		btpObjetivoList = new ArrayList<BtpObjetivo>();

		BtpObjetivo btpObjetivo = null;
		int objCodObjetivo = 0;
		String objCodList = "";

		BtpObjetivoItem btpObjetivoItem = null;
		int obiCodObjetivoItem = 0;
		String obiCodList = "";

		BtpMovimento btpMovimento = null;
		int movCodMovimento = 0;
		String movCodList = "";

		while (this.dbConn.getResultSet().next())
		{
			if (objCodObjetivo != this.dbConn.getResultSet().getInt("obj_cod_objetivo") && !objCodList.contains(this.dbConn.getResultSet().getString("obj_cod_objetivo")))
			{
				btpObjetivo = new BtpObjetivo();

				btpObjetivo.setObjCodObjetivo(this.dbConn.getResultSet().getInt("obj_cod_objetivo"));
				btpObjetivo.setObjTxtDescricao(this.dbConn.getResultSet().getString("obj_txt_descricao"));

				btpObjetivo.getBtpEstabelecimento().setEstCodEstabelecimento(this.dbConn.getResultSet().getInt("est_cod_estabelecimento"));
				btpObjetivo.getBtpEstabelecimento().setEstNomEstabelecimento(this.dbConn.getResultSet().getString("est_nom_estabelecimento"));

				btpObjetivoList.add(btpObjetivo);

				objCodObjetivo = this.dbConn.getResultSet().getInt("obj_cod_objetivo");
				objCodList += this.dbConn.getResultSet().getString("obj_cod_objetivo");
			}

			if (obiCodObjetivoItem != this.dbConn.getResultSet().getInt("obi_cod_objetivo_item") && !obiCodList.contains(this.dbConn.getResultSet().getString("obi_cod_objetivo_item")))
			{
				btpObjetivoItem = new BtpObjetivoItem();

				btpObjetivoItem.setObiCodObjetivoItem(this.dbConn.getResultSet().getInt("obi_cod_objetivo_item"));
				btpObjetivoItem.setObiNumItem(this.dbConn.getResultSet().getInt("obi_num_item"));
				btpObjetivoItem.setObiTxtDescricao(this.dbConn.getResultSet().getString("obi_txt_descricao"));
				btpObjetivoItem.setObiNumQuantidade(this.dbConn.getResultSet().getBigDecimal("obi_num_quantidade"));
				btpObjetivoItem.setObiVlrUnidade(this.dbConn.getResultSet().getBigDecimal("obi_vlr_unidade"));

				btpObjetivo.getBtpObjetivoItemList().add(btpObjetivoItem);

				obiCodObjetivoItem = this.dbConn.getResultSet().getInt("obi_cod_objetivo_item");
				obiCodList += this.dbConn.getResultSet().getString("obi_cod_objetivo_item") + ";";

				System.out.println(obiCodList);
			}

			if (movCodMovimento != this.dbConn.getResultSet().getInt("mov_cod_movimento") && !movCodList.contains(this.dbConn.getResultSet().getString("mov_cod_movimento")))
			{
				btpMovimento = new BtpMovimento();

				btpMovimento.setMovCodMovimento(this.dbConn.getResultSet().getInt("mov_cod_movimento"));
				btpMovimento.setMovNumParcela(this.dbConn.getResultSet().getInt("mov_num_parcela"));
				btpMovimento.setMovVlrMovimentado(this.dbConn.getResultSet().getBigDecimal("mov_vlr_movimento"));
				btpMovimento.setMovDatVencimento(this.dbConn.getResultSet().getDate("mov_dat_vencimento"));
				btpMovimento.setMovDatPagamento(this.dbConn.getResultSet().getDate("mov_dat_pagamento"));

				btpMovimento.getBtpFormaPagamento().setFopCodFormaPagamento(this.dbConn.getResultSet().getInt("fop_cod_forma_pagamento"));
				btpMovimento.getBtpFormaPagamento().setFopNomFormaPagamento(this.dbConn.getResultSet().getString("fop_nom_forma_pagamento"));

				btpMovimento.getBtpContaOrigem().setConCodConta(this.dbConn.getResultSet().getInt("coo_cod_conta"));
				btpMovimento.getBtpContaOrigem().setConTxtDescricao(this.dbConn.getResultSet().getString("coo_txt_descricao"));

				btpMovimento.getBtpContaDestino().setConCodConta(this.dbConn.getResultSet().getInt("cod_cod_conta"));
				btpMovimento.getBtpContaDestino().setConTxtDescricao(this.dbConn.getResultSet().getString("cod_txt_descricao"));

				btpObjetivo.getBtpMovimentoList().add(btpMovimento);

				movCodMovimento = this.dbConn.getResultSet().getInt("mov_cod_movimento");
				movCodList += this.dbConn.getResultSet().getString("mov_cod_movimento");
			}
		}

		return btpObjetivoList;
	}
}