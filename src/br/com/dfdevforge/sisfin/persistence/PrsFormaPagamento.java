package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpFormaPagamento;
import br.com.dfdevforge.sisfin.behavior.Persistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsFormaPagamento extends PrsAbstract implements Persistence
{
	private BtpFormaPagamento btpFormaPagamento = null;
	private List<BtpFormaPagamento> btpFormaPagamentoList = null;

	public PrsFormaPagamento(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpFormaPagamento> select(AbstractBean to, int sqlOrder) throws SQLException
	{
		btpFormaPagamento = (BtpFormaPagamento) to;

		StringBuilder cond = new StringBuilder();
		if (btpFormaPagamento != null)
		{
			cond.append(" and usu.usu_cod_usuario = " + btpFormaPagamento.getBtpUsuario().getUsuCodUsuario() + " ");

			if (Utils.hasValue(btpFormaPagamento.getFopCodFormaPagamento()))
				cond.append(" and fop.fop_cod_forma_pagamento = " + btpFormaPagamento.getFopCodFormaPagamento() + " ");
			if (Utils.hasValue(btpFormaPagamento.getFopNomFormaPagamento()))
				cond.append(" and upper(fop.fop_nom_forma_pagamento) like upper('%" + btpFormaPagamento.getFopNomFormaPagamento() + "%') ");
		}

		StringBuilder order = new StringBuilder();
		if (sqlOrder == 1)
			order.append("order by fop.fop_cod_forma_pagamento ");
		if (sqlOrder == 2)
			order.append("order by fop.fop_nom_forma_pagamento ");

		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("  fop.fop_cod_forma_pagamento fop_cod_forma_pagamento, ");
		sql.append("  fop.fop_nom_forma_pagamento fop_nom_forma_pagamento ");
		sql.append("from ");
		sql.append("  fop_forma_pagamento fop inner join ");
		sql.append("  usu_usuario usu on fop.usu_cod_usuario = usu.usu_cod_usuario ");
		sql.append("where 1 = 1 ");
		sql.append(   cond + " ");
		sql.append(   order + " ");

		this.dbConn.statementExecuteQuery(sql.toString());

		btpFormaPagamentoList = new ArrayList<BtpFormaPagamento>();
		while (this.dbConn.getResultSet().next())
		{
			BtpFormaPagamento b = new BtpFormaPagamento();

			b.setFopCodFormaPagamento(this.dbConn.getResultSet().getInt("fop_cod_forma_pagamento"));
			b.setFopNomFormaPagamento(this.dbConn.getResultSet().getString("fop_nom_forma_pagamento"));

			btpFormaPagamentoList.add(b);
		}

		return btpFormaPagamentoList;
	}

	public Integer insert(AbstractBean to) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		btpFormaPagamento = (BtpFormaPagamento) to;
		btpFormaPagamento.setFopCodFormaPagamento(this.getPrimaryKey("fop_forma_pagamento", "fop_cod_forma_pagamento"));

		if (btpFormaPagamento == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpFormaPagamento.getFopCodFormaPagamento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpFormaPagamento.getFopNomFormaPagamento()))
			throw new RequiredColumnNotFoundException();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		fields.append("fop_cod_forma_pagamento");
		values.append("" + btpFormaPagamento.getFopCodFormaPagamento() + "");

		fields.append(", fop_nom_forma_pagamento");
		values.append(", '" + btpFormaPagamento.getFopNomFormaPagamento() + "'");

		fields.append(", usu_cod_usuario");
		values.append(", " + btpFormaPagamento.getBtpUsuario().getUsuCodUsuario() + "");

		StringBuilder sql = new StringBuilder();
		sql.append("insert into fop_forma_pagamento(" + fields + ") values(" + values + ")");

		
		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public BtpFormaPagamento update(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		btpFormaPagamento = (BtpFormaPagamento) to;

		if (btpFormaPagamento == null || !Utils.hasValue(btpFormaPagamento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(btpFormaPagamento.getFopCodFormaPagamento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpFormaPagamento.getFopNomFormaPagamento()))
			throw new RequiredColumnNotFoundException();

		StringBuilder values = new StringBuilder();

		values.append("fop_nom_forma_pagamento = '" + btpFormaPagamento.getFopNomFormaPagamento() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update fop_forma_pagamento set " + values + " ");
		sql.append("where ");
		sql.append("  fop_cod_forma_pagamento = " + btpFormaPagamento.getFopCodFormaPagamento());
		sql.append("  and usu_cod_usuario = " + btpFormaPagamento.getBtpUsuario().getUsuCodUsuario() + " ");

		this.dbConn.statementExecuteUpdate(sql.toString());

		return btpFormaPagamento;
	}

	public BtpFormaPagamento delete(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		btpFormaPagamento = (BtpFormaPagamento) to;

		if (btpFormaPagamento == null || !Utils.hasValue(btpFormaPagamento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(btpFormaPagamento.getFopCodFormaPagamento()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append("  fop_forma_pagamento ");
		sql.append("where ");
		sql.append("  fop_cod_forma_pagamento = " + btpFormaPagamento.getFopCodFormaPagamento() + " ");
		sql.append("  and usu_cod_usuario = " + btpFormaPagamento.getBtpUsuario().getUsuCodUsuario() + " ");

		this.dbConn.statementExecuteUpdate(sql.toString());

		return btpFormaPagamento;
	}

	public Integer updateByTable(AbstractBean to) throws Exception
	{
		return 0;
	}

	public Integer updateByField(AbstractBean to) throws Exception
	{
		return 0;
	}
}