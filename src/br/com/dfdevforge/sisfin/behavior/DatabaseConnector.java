package br.com.dfdevforge.sisfin.behavior;

import java.sql.SQLException;

public interface DatabaseConnector
{
	public void closeConnection() throws SQLException;
	public void commit() throws SQLException;
	public void rollback() throws SQLException;
	public void executeQuery(String sql) throws Exception;
	public int executeUpdate(String sql) throws Exception;
}