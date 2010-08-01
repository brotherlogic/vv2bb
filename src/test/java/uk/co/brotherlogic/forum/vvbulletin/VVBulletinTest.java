package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

public class VVBulletinTest extends TestCase
{
	public void testConnection()
	{
		try
		{
			PreparedStatement ps = VVBulletin.getConnection()
					.getPreparedStatement("SELECT * from user");
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				// Pass
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}
}
