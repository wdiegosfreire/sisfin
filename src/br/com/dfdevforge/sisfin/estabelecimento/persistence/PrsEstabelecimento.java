package br.com.dfdevforge.sisfin.estabelecimento.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.persistence.PrsAbstract;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsEstabelecimento extends PrsAbstract
{
	private List<BtpEstabelecimento> btpEstabelecimentoList = null;

	public PrsEstabelecimento(ConnectionManager dbConn) throws SQLException
	{ 
		this.dbConn = dbConn;
	}

	public List<BtpEstabelecimento> select(BtpEstabelecimento btpEstabelecimento, int sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		if (btpEstabelecimento == null || !Utils.hasValue(btpEstabelecimento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		StringBuilder cond = new StringBuilder();

		cond.append(" and est.usu_cod_usuario = " + btpEstabelecimento.getBtpUsuario().getUsuCodUsuario() + " ");

		if (Utils.hasValue(btpEstabelecimento.getEstCodEstabelecimento()))
			cond.append(" and est.est_cod_estabelecimento = " + btpEstabelecimento.getEstCodEstabelecimento() + " ");
		if (Utils.hasValue(btpEstabelecimento.getEstNomEstabelecimento()))
			cond.append(" and upper(est.est_nom_estabelecimento) like upper('%" + btpEstabelecimento.getEstNomEstabelecimento() + "%') ");


		StringBuilder order = new StringBuilder();
		if (sqlOrder == 1)
			order.append("order by est.est_cod_estabelecimento ");
		if (sqlOrder == 2)
			order.append("order by est.est_nom_estabelecimento ");

		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("  est.est_cod_estabelecimento est_cod_estabelecimento, ");
		sql.append("  est.est_nom_estabelecimento est_nom_estabelecimento ");
		sql.append("from ");
		sql.append("  est_estabelecimento est inner join ");
		sql.append("  usu_usuario usu on est.usu_cod_usuario = usu.usu_cod_usuario ");
		sql.append("where 1 = 1 ");
		sql.append(   cond + " ");
		sql.append(   order + " ");

		this.dbConn.statementExecuteQuery(sql.toString());

		btpEstabelecimentoList = new ArrayList<BtpEstabelecimento>();
		while (this.dbConn.getResultSet().next())
		{
			BtpEstabelecimento b = new BtpEstabelecimento();

			b.setEstCodEstabelecimento(this.dbConn.getResultSet().getInt("est_cod_estabelecimento"));
			b.setEstNomEstabelecimento(this.dbConn.getResultSet().getString("est_nom_estabelecimento"));

			btpEstabelecimentoList.add(b);
		}

		return btpEstabelecimentoList;
	}

	public BtpEstabelecimento update(BtpEstabelecimento btpEstabelecimento) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		if (btpEstabelecimento == null || !Utils.hasValue(btpEstabelecimento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(btpEstabelecimento.getEstCodEstabelecimento()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpEstabelecimento.getEstNomEstabelecimento()))
			throw new RequiredColumnNotFoundException();

		StringBuilder values = new StringBuilder();

		values.append("est_nom_estabelecimento = '" + btpEstabelecimento.getEstNomEstabelecimento() + "'");

		StringBuilder sql = new StringBuilder();
		sql.append("update est_estabelecimento set " + values + " ");
		sql.append("where ");
		sql.append("  est_cod_estabelecimento = " + btpEstabelecimento.getEstCodEstabelecimento());
		sql.append("  and usu_cod_usuario = " + btpEstabelecimento.getBtpUsuario().getUsuCodUsuario() + " ");

		this.dbConn.statementExecuteUpdate(sql.toString());

		return btpEstabelecimento;
	}

	public BtpEstabelecimento delete(BtpEstabelecimento btpEstabelecimento) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException
	{
		if (btpEstabelecimento == null || !Utils.hasValue(btpEstabelecimento.getBtpUsuario().getUsuCodUsuario()))
			throw new SessionUserNotFoundException();

		if (!Utils.hasValue(btpEstabelecimento.getEstCodEstabelecimento()))
			throw new RequiredColumnNotFoundException();

		StringBuilder sql = new StringBuilder();
		sql.append("delete from ");
		sql.append("  est_estabelecimento ");
		sql.append("where ");
		sql.append("  est_cod_estabelecimento = " + btpEstabelecimento.getEstCodEstabelecimento() + " ");
		sql.append("  and usu_cod_usuario = " + btpEstabelecimento.getBtpUsuario().getUsuCodUsuario() + " ");

		this.dbConn.statementExecuteUpdate(sql.toString());

		return btpEstabelecimento;
	}
}