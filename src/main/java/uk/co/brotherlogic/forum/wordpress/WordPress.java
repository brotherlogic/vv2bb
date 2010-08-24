package uk.co.brotherlogic.forum.wordpress;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import uk.co.brotherlogic.forum.TransferProperties;

public class WordPress
{

	private static WordPress singleton;

	/**
	 * Static constructor
	 * 
	 * @return A suitable db connection
	 * @throws SQLException
	 *             if a db connection cannot be established
	 */
	public static WordPress getConnection() throws SQLException
	{
		if (singleton == null)
		{
			singleton = new WordPress();
		}
		return singleton;
	}

	private WordPress() throws SQLException
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

			System.err.println("Connecting to production database "
					+ TransferProperties.getProperty("wphost"));
			locDB = DriverManager.getConnection("jdbc:mysql://"
					+ TransferProperties.getProperty("wphost") + "/"
					+ TransferProperties.getProperty("wpdatabase") + "?user="
					+ TransferProperties.getProperty("wpuser") + "&password="
					+ TransferProperties.getProperty("wppassword"));

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
		PreparedStatement ps = getPreparedStatement("DELETE FROM wp_users WHERE ID > 1");
		ps.execute();
		ps.close();

		PreparedStatement ps2 = getPreparedStatement("DELETE FROM wp_usermeta WHERE user_id > 1");
		ps2.execute();
		ps2.close();

	}

}
