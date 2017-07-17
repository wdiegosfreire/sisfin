package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectTemplateParameterized extends PrsAbstract implements SelectablePersistence<BtpTemplate>
{
	public PrsSelectTemplateParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpTemplate> execute(BtpTemplate to, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpTemplate> btpTemplateList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getBtpUsuario().getUsuCodUsuario()))
				cond.append(" and tem.usu_cod_usuario = " + to.getBtpUsuario().getUsuCodUsuario() + " ");
			else
				throw new SessionUserNotFoundException();

			if (Utils.hasValue(to.getTemCodTemplate()))
				cond.append(" and tem.tem_cod_template = " + to.getTemCodTemplate() + " ");
			if (Utils.hasValue(to.getTemTxtNome()))
				cond.append(" and tem.tem_txt_nome = '" + to.getTemTxtNome() + "' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by tem.tem_txt_nome ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  tem.tem_cod_template tem_cod_template, ");
		sql.append("  tem.tem_txt_nome tem_txt_nome, ");
		sql.append("  tem.usu_cod_usuario usu_cod_usuario ");
		sql.append("from ");
		sql.append("  tem_template tem ");
		sql.append( cond);
		sql.append( order);

		this.dbConn.statementExecuteQuery(sql.toString());

		btpTemplateList = new ArrayList<BtpTemplate>();

		while (this.dbConn.getResultSet().next())
		{
			BtpTemplate btp = new BtpTemplate();

			btp.setTemCodTemplate(this.dbConn.getResultSet().getInt("tem_cod_template"));
			btp.setTemTxtNome(this.dbConn.getResultSet().getString("tem_txt_nome"));
			btp.getBtpUsuario().setUsuCodUsuario(this.dbConn.getResultSet().getInt("usu_cod_usuario"));

			btpTemplateList.add(btp);
		}

		return btpTemplateList;
	}
}