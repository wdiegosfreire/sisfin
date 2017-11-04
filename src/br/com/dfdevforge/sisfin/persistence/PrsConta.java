package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.behavior.Persistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsConta extends PrsAbstract implements Persistence
{
	private BtpConta btpConta = null;
	private List<BtpConta> btpContaList = null;

	public PrsConta(ConnectionManager conn) throws TimezoneValueException, SQLException
	{ 
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public List<BtpConta> select(AbstractBean to, int sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		btpConta = (BtpConta) to;

		StringBuilder cond = new StringBuilder();
		if (btpConta != null)
		{
			if (Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
				cond.append(" and con.usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");
			else
				throw new SessionUserNotFoundException();

			if (Utils.hasValue(btpConta.getConCodConta()))
				cond.append(" and con.con_cod_conta = " + btpConta.getConCodConta() + " ");
			if (Utils.hasValue(btpConta.getConTxtDescricao()))
				cond.append(" and con.con_txt_descricao like '%" + btpConta.getConTxtDescricao() + "%' ");
			if (btpConta.getBtpContaPai() != null && Utils.hasValue(btpConta.getBtpContaPai().getConCodConta()))
				cond.append(" and con.con_cod_conta_pai = " + btpConta.getBtpContaPai().getConCodConta() + " ");
		}

		StringBuilder order = new StringBuilder();
		if (sqlOrder == 1)
			order.append("order by con.con_cod_conta ");
		if (sqlOrder == 2)
			order.append("order by con.con_txt_descricao ");
		if (sqlOrder == 3)
			order.append("order by con.con_num_nivel ");

		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("  con.con_cod_conta con_cod_conta, ");
		sql.append("  con.con_txt_descricao con_txt_descricao, ");
		sql.append("  con.con_cod_conta_pai con_cod_conta_pai, ");
		sql.append("  con.con_num_nivel con_num_nivel, ");
		sql.append("  con.con_flg_movimento con_flg_movimento, ");
		sql.append("  con.con_flg_poupanca con_flg_poupanca, ");
		sql.append("  con.con_flg_virtual con_flg_virtual ");
		sql.append("from ");
		sql.append("  con_conta con inner join ");
		sql.append("  usu_usuario usu on con.usu_cod_usuario = usu.usu_cod_usuario ");
		sql.append("where 1 = 1 ");
		sql.append(   cond + " ");
		sql.append(   order + " ");

		this.connectionManager.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();
		BtpConta btpConta = null;
		while (this.connectionManager.getResultSet().next())
		{
			btpConta = new BtpConta();

			btpConta.setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta"));
			btpConta.setConTxtDescricao(this.connectionManager.getResultSet().getString("con_txt_descricao"));
			btpConta.setConNumNivel(this.connectionManager.getResultSet().getString("con_num_nivel"));
			btpConta.setConFlgMovimento(this.connectionManager.getResultSet().getBoolean("con_flg_movimento"));
			btpConta.setConFlgPoupanca(this.connectionManager.getResultSet().getBoolean("con_flg_poupanca"));
			btpConta.setConFlgVirtual(this.connectionManager.getResultSet().getBoolean("con_flg_virtual"));

			if (btpConta.getBtpContaPai() == null)
				btpConta.setBtpContaPai(new BtpConta());

			btpConta.getBtpContaPai().setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta_pai"));

			btpContaList.add(btpConta);
		}

		return btpContaList;
	}

	public List<BtpConta> selectResume(AbstractBean to) throws SQLException
	{
		BtpMovimento btpMovimento = (BtpMovimento) to;
		StringBuilder sql = new StringBuilder();

		// Total de rendimentos bruto agrupado por conta de nível 01 [TRB].
		sql.append("select ");
		sql.append("  cod.con_cod_conta con_cod_conta, ");
		sql.append("  cod.con_num_nivel con_num_nivel, ");
		sql.append("  cod.con_txt_descricao con_txt_descricao, ");
		sql.append("  1 aux_num_ordem, ");
		sql.append("  'Saldo Anterior' aux_txt_tipo, ");
		sql.append("  'ANT' aux_txt_sigla_tipo, ");
		sql.append("  sum(mov.mov_vlr_movimento) mov_vlr_movimento ");
		sql.append("from ");
		sql.append("  mov_movimento mov inner join ");
		sql.append("  con_conta cod on mov.con_cod_conta_destino = cod.con_cod_conta inner join ");
		sql.append("  obj_objetivo obj on mov.obj_cod_objetivo = obj.obj_cod_objetivo ");
		sql.append("where ");
		sql.append("  date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + btpMovimento.getMap().get("auxDatCompetencia") + "' ");
		sql.append("  and substr(cod.con_num_nivel, 1, 2) = '01' ");
		sql.append("  and mov.con_cod_conta_origem = mov.con_cod_conta_destino ");
		sql.append("  and obj.usu_cod_usuario = " + btpMovimento.getBtpObjetivo().getBtpUsuario().getUsuCodUsuario() + " ");
		sql.append("group by ");
		sql.append("  cod.con_cod_conta, cod.con_num_nivel, cod.con_txt_descricao ");

		sql.append("union ");

		// Total de rendimentos do mês agrupado por conta de nível 01 [REN].
		sql.append("select ");
		sql.append("  cod.con_cod_conta con_cod_conta, ");
		sql.append("  cod.con_num_nivel con_num_nivel, ");
		sql.append("  cod.con_txt_descricao con_txt_descricao, ");
		sql.append("  2 aux_num_ordem, ");
		sql.append("  'Rendimentos do Mês' aux_txt_tipo, ");
		sql.append("  'REN' aux_txt_sigla_tipo, ");
		sql.append("  sum(mov.mov_vlr_movimento) mov_vlr_movimento ");
		sql.append("from ");
		sql.append("  mov_movimento mov inner join ");
		sql.append("  con_conta cod on mov.con_cod_conta_destino = cod.con_cod_conta inner join ");
		sql.append("  obj_objetivo obj on mov.obj_cod_objetivo = obj.obj_cod_objetivo ");
		sql.append("where ");
		sql.append("  date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + btpMovimento.getMap().get("auxDatCompetencia") + "' ");
		sql.append("  and substr(cod.con_num_nivel, 1, 2) = '01' ");
		sql.append("  and mov.con_cod_conta_origem <> mov.con_cod_conta_destino ");
		sql.append("  and obj.usu_cod_usuario = " + btpMovimento.getBtpObjetivo().getBtpUsuario().getUsuCodUsuario() + " ");
		sql.append("group by ");
		sql.append("  cod.con_cod_conta, cod.con_num_nivel, cod.con_txt_descricao ");

		sql.append("union ");

		// Total de despesas do mês agrupado por conta de nível 01 [DES].
		sql.append("select ");
		sql.append("  coo.con_cod_conta con_cod_conta, ");
		sql.append("  coo.con_num_nivel con_num_nivel, ");
		sql.append("  coo.con_txt_descricao con_txt_descricao, ");
		sql.append("  3 aux_num_ordem, ");
		sql.append("  'Despesas do Mês' aux_txt_tipo, ");
		sql.append("  'DES' aux_txt_sigla_tipo, ");
		sql.append("  sum(mov.mov_vlr_movimento) mov_vlr_movimento ");
		sql.append("from ");
		sql.append("  mov_movimento mov inner join ");
		sql.append("  con_conta coo on mov.con_cod_conta_origem = coo.con_cod_conta inner join ");
		sql.append("  obj_objetivo obj on mov.obj_cod_objetivo = obj.obj_cod_objetivo ");
		sql.append("where ");
		sql.append("  date_format(mov.mov_dat_pagamento, '%m/%Y') = '" + btpMovimento.getMap().get("auxDatCompetencia") + "' ");
		sql.append("  and substr(coo.con_num_nivel, 1, 2) = '01' ");
		sql.append("  and mov.con_cod_conta_origem <> mov.con_cod_conta_destino ");
		sql.append("  and obj.usu_cod_usuario = " + btpMovimento.getBtpObjetivo().getBtpUsuario().getUsuCodUsuario() + " ");
		sql.append("group by ");
		sql.append("  coo.con_cod_conta, coo.con_num_nivel, coo.con_txt_descricao ");
		sql.append("order by ");
		sql.append("  con_num_nivel, aux_num_ordem ");

		this.connectionManager.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();
		BtpConta btpConta = null;

		int conCodConta = 0;

		while (this.connectionManager.getResultSet().next())
		{
			if (conCodConta != this.connectionManager.getResultSet().getInt("con_cod_conta"))
			{
				btpConta = new BtpConta();

				btpConta.setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta"));
				btpConta.setConNumNivel(this.connectionManager.getResultSet().getString("con_num_nivel"));
				btpConta.setConTxtDescricao(this.connectionManager.getResultSet().getString("con_txt_descricao"));

				btpContaList.add(btpConta);
				conCodConta = this.connectionManager.getResultSet().getInt("con_cod_conta");
			}

			BtpMovimento b = new BtpMovimento();

			b.setMovVlrMovimentado(this.connectionManager.getResultSet().getBigDecimal("mov_vlr_movimento"));
			b.getMap().put("auxTxtTipo", this.connectionManager.getResultSet().getString("aux_txt_tipo"));
			b.getMap().put("auxTxtSiglaTipo", this.connectionManager.getResultSet().getString("aux_txt_sigla_tipo"));

			btpConta.getBtpMovimentoList().add(b);
		}

		return btpContaList;
	}

	public Integer insert(AbstractBean to) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		btpConta = (BtpConta) to;
		btpConta.setConCodConta(this.getPrimaryKey("con_conta", "con_cod_conta"));

		if (btpConta == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpConta.getConCodConta()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConNumNivel()))
			throw new RequiredColumnNotFoundException();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		fields.append("con_cod_conta");
		values.append("" + btpConta.getConCodConta() + "");

		fields.append(", con_txt_descricao");
		values.append(", '" + btpConta.getConTxtDescricao() + "'");

		fields.append(", con_num_nivel");
		values.append(", '" + btpConta.getConNumNivel() + "'");

		fields.append(", usu_cod_usuario");
		values.append(", " + btpConta.getBtpUsuario().getUsuCodUsuario() + "");

		if (btpConta.getConFlgMovimento())
		{
			fields.append(", con_flg_movimento");
			values.append(", '1'");
		}
		else
		{
			fields.append(", con_flg_movimento");
			values.append(", null");
		}

		if (btpConta.getConFlgPoupanca())
		{
			fields.append(", con_flg_poupanca");
			values.append(", '1'");
		}
		else
		{
			fields.append(", con_flg_poupanca");
			values.append(", null");
		}

		if (btpConta.getConFlgVirtual())
		{
			fields.append(", con_flg_virtual");
			values.append(", '1'");
		}
		else
		{
			fields.append(", con_flg_virtual");
			values.append(", null");
		}

		if (Utils.hasValue(btpConta.getBtpContaPai().getConCodConta()))
		{
			fields.append(", con_cod_conta_pai");
			values.append(", " + btpConta.getBtpContaPai().getConCodConta() + "");
		}
		else
		{
			fields.append(", con_cod_conta_pai");
			values.append(", " + btpConta.getConCodConta() + "");
		}

		StringBuilder sql = new StringBuilder();
		sql.append("insert into con_conta(" + fields + ") values(" + values + ")");

		int updatedRows = this.connectionManager.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public BtpConta update(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		btpConta = (BtpConta) to;

		if (btpConta == null || !Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(btpConta.getConCodConta()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConNumNivel()))
			throw new RequiredColumnNotFoundException();

		StringBuilder values = new StringBuilder();

		values.append("con_txt_descricao = '" + btpConta.getConTxtDescricao() + "'");

		values.append(", con_num_nivel = '" + btpConta.getConNumNivel() + "'");

		if (btpConta.getConFlgMovimento())
			values.append(", con_flg_movimento = '1'");
		else
			values.append(", con_flg_movimento = null");

		if (btpConta.getConFlgPoupanca())
			values.append(", con_flg_poupanca = '1'");
		else
			values.append(", con_flg_poupanca = null");

		if (btpConta.getConFlgVirtual())
			values.append(", con_flg_virtual = '1'");
		else
			values.append(", con_flg_virtual = null");

		if (btpConta.getBtpContaPai() != null && Utils.hasValue(btpConta.getBtpContaPai().getConCodConta()))
			values.append(", con_cod_conta_pai = " + btpConta.getBtpContaPai().getConCodConta() + "");
		else
			values.append(", con_cod_conta_pai = null");

		StringBuilder sql = new StringBuilder();
		sql.append("update con_conta set " + values + " ");
		sql.append("where ");
		sql.append("  con_cod_conta = " + btpConta.getConCodConta() + " ");
		sql.append("  and usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");

		this.connectionManager.statementExecuteUpdate(sql.toString());

		return btpConta;
	}

	public BtpConta delete(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		btpConta = (BtpConta) to;

		if (btpConta == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpConta.getConCodConta()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append("  con_conta ");
		sql.append("where ");
		sql.append("  con_cod_conta = " + btpConta.getConCodConta() + " ");
		sql.append("  and usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");

		this.connectionManager.statementExecuteUpdate(sql.toString());

		return btpConta;
	}

	public Integer updateByTable(AbstractBean to) throws Exception
	{
		btpConta = (BtpConta) to;

		if (btpConta == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpConta.getConCodConta()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpConta.getConNumNivel()))
			throw new RequiredColumnNotFoundException();

		StringBuilder values = new StringBuilder();

		values.append("con_txt_descricao = '" + btpConta.getConTxtDescricao() + "'");

		values.append(", con_num_nivel = '" + btpConta.getConNumNivel() + "'");

		if (btpConta.getConFlgMovimento())
			values.append(", con_flg_movimento = '1'");
		else
			values.append(", con_flg_movimento = null");

		if (btpConta.getConFlgPoupanca())
			values.append(", con_flg_poupanca = '1'");
		else
			values.append(", con_flg_poupanca = null");

		if (btpConta.getConFlgVirtual())
			values.append(", con_flg_virtual = '1'");
		else
			values.append(", con_flg_virtual = null");

		if (btpConta.getBtpContaPai() != null && Utils.hasValue(btpConta.getBtpContaPai().getConCodConta()))
			values.append(", con_cod_conta_pai = " + btpConta.getBtpContaPai().getConCodConta() + "");
		else
			values.append(", con_cod_conta_pai = null");

		StringBuilder sql = new StringBuilder();
		sql.append("update con_conta set " + values + " where con_cod_conta = " + btpConta.getConCodConta());

		int updatedRows = this.connectionManager.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public Integer updateByField(AbstractBean to) throws Exception
	{
		return 0;
	}
}