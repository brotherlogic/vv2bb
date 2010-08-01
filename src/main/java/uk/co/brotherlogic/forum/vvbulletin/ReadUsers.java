package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
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

		return users;
	}
}
