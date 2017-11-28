package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import br.com.cagece.core.persistence.api.ConnectionManager;

public abstract class PrsAbstract
{
	protected ConnectionManager connectionManager;

	public Integer getPrimaryKey(String tableName, String fieldName) throws SQLException
	{
		Integer key = new Integer(1);

		StringBuilder sql = new StringBuilder();
		sql.append("select max(" + fieldName + ") + 1 COD from " + tableName);

		this.connectionManager.statementExecuteQuery(sql.toString());

		while (this.connectionManager.getResultSet().next())
		{
			if (this.connectionManager.getResultSet().getString("COD") != null)
				key = this.connectionManager.getResultSet().getInt("COD");
		}

		return key;
	}

	protected Timestamp dateToTymestampConverter(Date oldDate)
	{
		Timestamp timestamp = null;

		if (oldDate != null)
			timestamp = new Timestamp(oldDate.getTime());

		return timestamp;
	}

	public String getDatabaseTimezone() throws SQLException
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select @@global.time_zone timezone");

		this.connectionManager.statementExecuteQuery(sql.toString());

		String timezone = "";

		while (this.connectionManager.getResultSet().next())
			timezone = this.connectionManager.getResultSet().getString("timezone");

		return timezone;
	}

	public String getApplicationTimezone() throws SQLException
	{
		return TimeZone.getDefault().getID();
	}

	public void isTimezoneCorrect()
	{
//		if (!getDatabaseTimezone().equals("-03:00") || !getApplicationTimezone().equals("GMT-03:00"))
//			throw new TimezoneValueException();
	}
}