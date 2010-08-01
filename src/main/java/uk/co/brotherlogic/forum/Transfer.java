package uk.co.brotherlogic.forum;

import java.sql.SQLException;
import java.util.List;

import uk.co.brotherlogic.forum.atoms.User;
import uk.co.brotherlogic.forum.vvbulletin.ReadUsers;
import uk.co.brotherlogic.forum.wordpress.WPUsers;
import uk.co.brotherlogic.forum.wordpress.WordPress;

/**
 * Hello world!
 * 
 */
public class Transfer
{
	public static void main(String[] args) throws SQLException
	{
		ReadUsers rUsers = new ReadUsers();
		WPUsers wpUsers = new WPUsers();

		WordPress.getConnection().prepare();

		List<User> users = rUsers.readUsers();
		for (User user : users)
		{
			if (user.getUsername().equals("Brother Logic"))
			{
				System.err.println("Found me");
				wpUsers.storeUser(user);
			}
		}
	}
}
