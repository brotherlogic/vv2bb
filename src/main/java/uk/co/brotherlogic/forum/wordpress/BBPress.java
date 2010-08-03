package uk.co.brotherlogic.forum.wordpress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BBPress
{

	private static BBPress singleton;

	/**
	 * Static constructor
	 * 
	 * @return A suitable db connection
	 * @throws SQLException
	 *             if a db connection cannot be established
	 */
	public static BBPress getConnection() throws SQLException
	{
		if (singleton == null)
		{
			singleton = new BBPress();
		}
		return singleton;
	}

	private BBPress() throws SQLException
	{
		makeConnection();
	}

	public PreparedStatement getPreparedStatement(final String sql)
			throws SQLException
	{
		// Create the statement
		PreparedStatement ps = locDB.prepareStatement(sql);
		return ps;
	}

	/** The connection to the local DB */
	private Connection locDB;

	/**
	 * Makes the connection to the DB
	 * 
	 * @throws SQLException
	 *             if something fails
	 */
	private void makeConnection() throws SQLException
	{
		try
		{
			// Load all the drivers and initialise the database connection
			Class.forName("com.mysql.jdbc.Driver");

			System.err.println("Connecting to production database");
			locDB = DriverManager
					.getConnection("jdbc:mysql://localhost/bbpress?user=bbpress&password=bbpress");

			// Switch off auto commit
			locDB.setAutoCommit(false);
		}
		catch (ClassNotFoundException e)
		{
			throw new SQLException(e);
		}
	}

	public void prepare() throws SQLException
	{
		PreparedStatement ps = getPreparedStatement("DELETE FROM bb_forums");
		ps.execute();
		ps.close();
	}

}
