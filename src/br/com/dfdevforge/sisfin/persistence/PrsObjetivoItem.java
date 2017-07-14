package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpObjetivoItem;
import br.com.dfdevforge.sisfin.behavior.Persistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsObjetivoItem extends PrsAbstract implements Persistence
{
	private BtpObjetivoItem btpObjetivoItem = null;
	private List<BtpObjetivoItem> btpObjetivoItemList = null;

	public PrsObjetivoItem(ConnectionManager conn) throws TimezoneValueException, SQLException
	{ 
		this.dbConn = conn;
		isTimezoneCorrect();
	}

	public List<BtpObjetivoItem> select(AbstractBean to, int sqlOrder) throws SQLException
	{
		return btpObjetivoItemList;
	}

	public Integer insert(AbstractBean to) throws SQLException, RequiredColumnNotFoundException, NullBeanException
	{
		btpObjetivoItem = (BtpObjetivoItem) to;
		btpObjetivoItem.setObiCodObjetivoItem(this.getPrimaryKey("obi_objetivo_item", "obi_cod_objetivo_item"));

		if (btpObjetivoItem == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivoItem.getObiCodObjetivoItem()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivoItem.getObiTxtDescricao()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivoItem.getObiNumQuantidade()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivoItem.getObiVlrUnidade()))
			throw new RequiredColumnNotFoundException();
		if (!Utils.hasValue(btpObjetivoItem.getObiNumItem()))
			throw new RequiredColumnNotFoundException();

		if (btpObjetivoItem.getBtpObjetivo() == null)
			throw new NullBeanException();
		if (!Utils.hasValue(btpObjetivoItem.getBtpObjetivo().getObjCodObjetivo()))
			throw new RequiredColumnNotFoundException();

		StringBuilder fields = new StringBuilder();
		StringBuilder values = new StringBuilder();

		fields.append("obi_cod_objetivo_item");
		values.append("" + btpObjetivoItem.getObiCodObjetivoItem() + "");

		fields.append(", obi_txt_descricao");
		values.append(", '" + btpObjetivoItem.getObiTxtDescricao() + "'");

		fields.append(", obi_num_quantidade");
		values.append(", " + btpObjetivoItem.getObiNumQuantidade() + "");

		fields.append(", obi_vlr_unidade");
		values.append(", " + btpObjetivoItem.getObiVlrUnidade() + "");

		fields.append(", obi_num_item");
		values.append(", " + btpObjetivoItem.getObiNumItem() + "");

		fields.append(", obj_cod_objetivo");
		values.append(", " + btpObjetivoItem.getBtpObjetivo().getObjCodObjetivo() + "");

		StringBuilder sql = new StringBuilder();
		sql.append("insert into obi_objetivo_item(" + fields + ") values(" + values + ")");

		int updatedRows = this.dbConn.statementExecuteUpdate(sql.toString());

		return updatedRows;
	}

	public BtpObjetivoItem update(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException
	{
		return btpObjetivoItem;
	}

	public BtpObjetivoItem delete(AbstractBean to) {return null;}

	public Integer updateByTable(AbstractBean to) throws Exception
	{
		return 0;
	}

	public Integer updateByField(AbstractBean to) throws Exception
	{
		return 0;
	}
}