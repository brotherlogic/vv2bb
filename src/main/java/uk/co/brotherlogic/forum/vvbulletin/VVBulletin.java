package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VVBulletin
{

	private static VVBulletin singleton;

	/**
	 * Static constructor
	 * 
	 * @return A suitable db connection
	 * @throws SQLException
	 *             if a db connection cannot be established
	 */
	public static VVBulletin getConnection() throws SQLException
	{
		if (singleton == null)
		{
			singleton = new VVBulletin();
		}
		return singleton;
	}

	private VVBulletin() throws SQLException
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
					.getConnection("jdbc:mysql://192.168.1.100/vinylvulture?user=vinylvulture&password=vinylvulture");

			// Switch off auto commit
			locDB.setAutoCommit(false);
		}
		catch (ClassNotFoundException e)
		{
			throw new SQLException(e);
		}
	}

}
