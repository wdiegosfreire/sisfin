package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpUsuario;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectUsuarioParameterized extends PrsAbstract implements SelectablePersistence<BtpUsuario>
{
	public PrsSelectUsuarioParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public List<BtpUsuario> execute(BtpUsuario to, Integer sqlOrder) throws SQLException
	{
		List<BtpUsuario> btpUsuarioList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getUsuCodUsuario()))
				cond.append(" and usu.usu_cod_usuario = " + to.getUsuCodUsuario() + " ");
			if (Utils.hasValue(to.getUsuTxtNome()))
				cond.append(" and usu.usu_txt_nome = '" + to.getUsuTxtNome() + "' ");
			if (Utils.hasValue(to.getUsuTxtSenha()))
				cond.append(" and usu.usu_txt_senha = '" + to.getUsuTxtSenha() + "' ");
			if (Utils.hasValue(to.getUsuTxtEmail()))
				cond.append(" and usu.usu_txt_email = '" + to.getUsuTxtEmail() + "' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by usu.usu_txt_nome ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  usu.usu_cod_usuario usu_cod_usuario, ");
		sql.append("  usu.usu_txt_nome usu_txt_nome, ");
		sql.append("  usu.usu_txt_senha usu_txt_senha, ");
		sql.append("  usu.usu_txt_email usu_txt_email ");
		sql.append("from ");
		sql.append("  usu_usuario usu ");
		sql.append( cond);
		sql.append( order);

		this.connectionManager.statementExecuteQuery(sql.toString());

		btpUsuarioList = new ArrayList<BtpUsuario>();

		while (this.connectionManager.getResultSet().next())
		{
			BtpUsuario btp = new BtpUsuario();

			btp.setUsuCodUsuario(this.connectionManager.getResultSet().getInt("usu_cod_usuariO"));
			btp.setUsuTxtNome(this.connectionManager.getResultSet().getString("usu_txt_nome"));
			btp.setUsuTxtSenha(this.connectionManager.getResultSet().getString("usu_txt_senha"));
			btp.setUsuTxtEmail(this.connectionManager.getResultSet().getString("usu_txt_email"));

			btpUsuarioList.add(btp);
		}

		return btpUsuarioList;
	}
}