package uk.co.brotherlogic.forum.wordpress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.brotherlogic.forum.atoms.User;

public class WPUsers
{
	public void storeUser(User user) throws SQLException
	{
		PreparedStatement ps = WordPress
				.getConnection()
				.getPreparedStatement(
						"INSERT INTO wp_users (user_login,user_nicename,user_email) VALUES (?,?,?)");
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getUsername());
		ps.setString(3, user.getEmail());
		ps.execute();
		ps.close();

		// Get the user ID
		PreparedStatement ps2 = WordPress
				.getConnection()
				.getPreparedStatement(
						"SELECT id FROM wp_users WHERE user_login = ? AND user_email = ?");
		ps2.setString(1, user.getUsername());
		ps2.setString(2, user.getEmail());
		ResultSet rs = ps2.executeQuery();
		if (rs.next())
		{
			int id = rs.getInt(1);
			user.setWp_userid(id);
			ps2.close();

			// Insert the appropriate metadata
			PreparedStatement ps3 = WordPress
					.getConnection()
					.getPreparedStatement(
							"INSERT INTO wp_usermeta (user_id,meta_key,meta_value) VALUES (?,?,?)");
			ps3.setInt(1, user.getWp_userid());
			ps3.setString(2, "bb_capabilities");
			ps3.setString(3, "a:1:{s:6:\"member\";b:1;}");
			ps3.execute();
			ps3.close();
		} else
			System.err.println("ERR on store user: " + user);

	}
}
