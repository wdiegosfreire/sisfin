package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectBancoParameterized extends PrsAbstract implements SelectablePersistence<BtpBanco>
{
	public PrsSelectBancoParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpBanco> execute(BtpBanco to, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpBanco> btpBancoList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getBanCodBanco()))
				cond.append(" and ban.ban_cod_banco = " + to.getBanCodBanco() + " ");
			if (Utils.hasValue(to.getBanTxtNome()))
				cond.append(" and ban.ban_txt_nome like '%" + to.getBanTxtNome() + "%' ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by ban.ban_txt_nome ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  ban.ban_cod_banco ban_cod_banco, ");
		sql.append("  ban.ban_txt_nome ban_txt_nome ");
		sql.append("from ");
		sql.append("  ban_banco ban ");
		sql.append( cond);
		sql.append( order);

		this.dbConn.statementExecuteQuery(sql.toString());

		btpBancoList = new ArrayList<BtpBanco>();

		while (this.dbConn.getResultSet().next())
		{
			BtpBanco btp = new BtpBanco();

			btp.setBanCodBanco(this.dbConn.getResultSet().getInt("ban_cod_banco"));
			btp.setBanTxtNome(this.dbConn.getResultSet().getString("ban_txt_nome"));

			btpBancoList.add(btp);
		}

		return btpBancoList;
	}
}