package br.com.dfdevforge.sisfin.persistence;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.behavior.Persistence;
import br.com.dfdevforge.sisfin.constants.Constants;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsMovimento extends PrsAbstract implements Persistence
{
	private BtpMovimento btpMovimento = null;
	private List<BtpMovimento> btpMovimentoList = null;

	public PrsMovimento(ConnectionManager conn) throws TimezoneValueException, SQLException
	{ 
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpMovimento> select(AbstractBean to, int sqlOrder) throws SQLException
	{
		return btpMovimentoList;
	}

	public Integer insert(AbstractBean to) throws SQLException, RequiredColumnNotFoundException, NullBeanException, IOException
	{
		btpMovimento = (BtpMovimento) to;
		btpMovimento.setMovCodMovimento(this.getPrimaryKey("mov_movimento", "mov_cod_movimento"));

		if (btpMovimento == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getMovCodMovimento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpMovimento.getMovDatVencimento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpMovimento.getMovVlrMovimentado()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpMovimento.getMovNumParcela()))
			throw new RequiredColumnNotFoundException();

		if (btpMovimento.getBtpObjetivo() == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getBtpObjetivo().getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();

		if (btpMovimento.getBtpFormaPagamento() == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getBtpFormaPagamento().getFopCodFormaPagamento()))
			throw new RequiredColumnNotFoundException();

		if (btpMovimento.getBtpContaOrigem() == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getBtpContaOrigem().getConCodConta()))
			throw new RequiredColumnNotFoundException();

		if (btpMovimento.getBtpContaDestino() == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getBtpContaDestino().getConCodConta()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("insert into mov_movimento ( ");
		sql.append("  mov_dat_registro, ");
		sql.append("  mov_dat_vencimento, ");
		sql.append("  mov_dat_pagamento, ");
		sql.append("  mov_vlr_movimento, ");
		sql.append("  mov_num_parcela, ");
		sql.append("  obj_cod_objetivo, ");
		sql.append("  fop_cod_forma_pagamento, ");
		sql.append("  con_cod_conta_origem, ");
		sql.append("  con_cod_conta_destino ");
		sql.append(")");
		sql.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		this.dbConn.preparedStatementSetSqlScript(sql.toString());

		Constants.errorLog = "";

		this.dbConn.preparedStatementSetParameter(1, dateToTymestampConverter(btpMovimento.getMovDatRegistro()));
		this.dbConn.preparedStatementSetParameter(2, dateToTymestampConverter(btpMovimento.getMovDatVencimento()));
		this.dbConn.preparedStatementSetParameter(3, dateToTymestampConverter(btpMovimento.getMovDatPagamento()));
		this.dbConn.preparedStatementSetParameter(4, btpMovimento.getMovVlrMovimentado());
		this.dbConn.preparedStatementSetParameter(5, btpMovimento.getMovNumParcela());
		this.dbConn.preparedStatementSetParameter(6, btpMovimento.getBtpObjetivo().getObjCodObjetivo());
		this.dbConn.preparedStatementSetParameter(7, btpMovimento.getBtpFormaPagamento().getFopCodFormaPagamento());
		this.dbConn.preparedStatementSetParameter(8, btpMovimento.getBtpContaOrigem().getConCodConta());
		this.dbConn.preparedStatementSetParameter(9, btpMovimento.getBtpContaDestino().getConCodConta());

		int updatedRows = this.dbConn.preparedStatementExecuteUpdate();

		return updatedRows;
	}

	public BtpMovimento update(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException
	{
		return btpMovimento;
	}

	public BtpMovimento delete(AbstractBean to) {return null;}

	public Integer updateByTable(AbstractBean to) throws Exception
	{
		return 0;
	}

	public Integer updateByField(AbstractBean to) throws NullBeanException, SQLException
	{
		btpMovimento = (BtpMovimento) to;

		if (btpMovimento == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpMovimento.getMovCodMovimento()))
			throw new NullBeanException();

		StringBuilder values = new StringBuilder();

		if (Utils.hasValue(btpMovimento.getMovDatVencimento()))
			values.append(", mov_dat_vencimento = str_to_date('" + Utils.convertDateToString(btpMovimento.getMovDatVencimento()) + "', '%d/%m/%Y')");
		if (Utils.hasValue(btpMovimento.getMovDatPagamento()))
			values.append(", mov_dat_pagamento = str_to_date('" + Utils.convertDateToString(btpMovimento.getMovDatPagamento()) + "', '%d/%m/%Y')");
		if (Utils.hasValue(btpMovimento.getMovVlrMovimentado()))
			values.append(", mov_vlr_movimento = " + btpMovimento.getMovVlrMovimentado() + " ");
		if (Utils.hasValue(btpMovimento.getBtpObjetivo().getObjCodObjetivo()))
			values.append(", obj_cod_objetivo = " + btpMovimento.getBtpObjetivo().getObjCodObjetivo() + " ");
		if (Utils.hasValue(btpMovimento.getBtpFormaPagamento().getFopCodFormaPagamento()))
			values.append(", fop_cod_forma_pagamento = " + btpMovimento.getBtpFormaPagamento().getFopCodFormaPagamento() + " ");
		if (Utils.hasValue(btpMovimento.getMovNumParcela()))
			values.append(", mov_num_parcela = " + btpMovimento.getMovNumParcela() + " ");
		if (Utils.hasValue(btpMovimento.getBtpContaOrigem().getConCodConta()))
			values.append(", con_cod_conta_origem = " + btpMovimento.getBtpContaOrigem().getConCodConta() + " ");
		if (Utils.hasValue(btpMovimento.getBtpContaDestino().getConCodConta()))
			values.append(", con_cod_conta_destino = " + btpMovimento.getBtpContaDestino().getConCodConta() + " ");

		values.replace(0, 1, "");

		StringBuilder sql = new StringBuilder();
		sql.append("update mov_movimento set " + values + " where mov_cod_movimento = " + btpMovimento.getMovCodMovimento());

		

		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}
}