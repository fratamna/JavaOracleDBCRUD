package com.mkyong.jdbc.preparedStatement;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCBatchUpdateExample {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:franklin";
	private static final String DB_USER = "system";
	private static final String DB_PASSWORD = "franklin";

	public static void main(String[] argv) {

		try {

			batchInsertRecordsIntoTable();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

	private static void batchInsertRecordsIntoTable() throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO DBUSER"
				+ "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) VALUES"
				+ "(?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			dbConnection.setAutoCommit(false);

			preparedStatement.setInt(1, 1010);
			preparedStatement.setString(2, "mkyong101");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.setInt(1, 1020);
			preparedStatement.setString(2, "mkyong102");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.setInt(1, 1030);
			preparedStatement.setString(2, "mkyong103");
			preparedStatement.setString(3, "system");
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());
			preparedStatement.addBatch();

			preparedStatement.executeBatch();

			dbConnection.commit();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());
			dbConnection.rollback();

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}

		try {

			dbConnection = DriverManager.getConnection(
                              DB_CONNECTION, DB_USER,DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

		return dbConnection;

	}

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}

}