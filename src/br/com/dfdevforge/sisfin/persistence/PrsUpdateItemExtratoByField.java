package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;

import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.behavior.UpdateablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsUpdateItemExtratoByField extends PrsAbstract implements UpdateablePersistence<BtpItemExtrato>
{
	public PrsUpdateItemExtratoByField(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public int execute(BtpItemExtrato to) throws SQLException, NullBeanException, RequiredColumnNotFoundException
	{
		if (to == null)
			throw new NullBeanException();
		if (!Utils.hasValue(to.getIteCodItemExtrato()))
			throw new NullBeanException();

		StringBuilder values = new StringBuilder();

		if (Utils.hasValue(to.getIteDatMovimento()))
			values.append(", ite_dat_movimento = str_to_date('" + Utils.convertDateToString(to.getIteDatMovimento()) + "', '%d/%m/%Y') ");
		if (Utils.hasValue(to.getIteTxtDescricao()))
			values.append(", ite_txt_descricao = '" + to.getIteTxtDescricao() + "' ");
		if (Utils.hasValue(to.getIteNumDocumento()))
			values.append(", ite_num_documento = '" + to.getIteNumDocumento() + "' ");
		if (Utils.hasValue(to.getIteTxtTipo()))
			values.append(", ite_txt_tipo = '" + to.getIteTxtTipo() + "' ");
		if (Utils.hasValue(to.getIteVlrMovimento()))
			values.append(", ite_vlr_movimento = " + to.getIteVlrMovimento() + " ");
		if (Utils.hasValue(to.getIteFlgExportado()))
			values.append(", ite_flg_exportado = " + (to.getIteFlgExportado() ? 1 : null) + " ");
		if (to.getBtpExtrato() != null && Utils.hasValue(to.getBtpExtrato().getExtCodExtrato()))
			values.append(", ext_cod_extrato = " + to.getBtpExtrato().getExtCodExtrato() + " ");
		if (to.getBtpObjetivo() != null && Utils.hasValue(to.getBtpObjetivo().getObjCodObjetivo()))
			values.append(", obj_cod_objetivo = " + to.getBtpObjetivo().getObjCodObjetivo() + " ");

		values.replace(0, 1, "");

		StringBuilder sql = new StringBuilder();
		sql.append("update ite_item_extrato set " + values + " where ite_cod_item_extrato = " + to.getIteCodItemExtrato());

		

		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}
}