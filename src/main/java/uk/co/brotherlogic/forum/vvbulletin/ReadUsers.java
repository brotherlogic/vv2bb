package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import uk.co.brotherlogic.forum.atoms.User;

public class ReadUsers
{
	public List<User> readUsers() throws SQLException
	{
		List<User> users = new LinkedList<User>();

		PreparedStatement ps = VVBulletin.getConnection().getPreparedStatement(
				"SELECT userid,username,email FROM user");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			User user = new User(rs.getInt(1), rs.getString(2),
					rs.getString(2), rs.getString(3));
			users.add(user);
		}

		return users;
	}

	public static void main(String[] args) throws SQLException
	{
		ReadUsers users = new ReadUsers();
		System.out.println("NUM USERS = " + users.readUsers().size());
	}
}
