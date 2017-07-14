package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

import br.com.cagece.core.persistence.ConnectionManager;

public abstract class PrsAbstract
{
	protected ConnectionManager dbConn;

	public Integer getPrimaryKey(String tableName, String fieldName) throws SQLException
	{
		Integer key = new Integer(1);

		StringBuilder sql = new StringBuilder();
		sql.append("select max(" + fieldName + ") + 1 COD from " + tableName);

		this.dbConn.statementExecuteQuery(sql.toString());

		while (this.dbConn.getResultSet().next())
		{
			if (this.dbConn.getResultSet().getString("COD") != null)
				key = this.dbConn.getResultSet().getInt("COD");
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

		this.dbConn.statementExecuteQuery(sql.toString());

		String timezone = "";

		while (this.dbConn.getResultSet().next())
			timezone = this.dbConn.getResultSet().getString("timezone");

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