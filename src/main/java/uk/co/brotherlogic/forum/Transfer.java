package uk.co.brotherlogic.forum;

import java.sql.SQLException;
import java.util.List;

import uk.co.brotherlogic.forum.atoms.Forum;
import uk.co.brotherlogic.forum.atoms.User;
import uk.co.brotherlogic.forum.vvbulletin.ReadForums;
import uk.co.brotherlogic.forum.vvbulletin.ReadUsers;
import uk.co.brotherlogic.forum.wordpress.BBPress;
import uk.co.brotherlogic.forum.wordpress.WPForums;
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
		ReadForums rForums = new ReadForums();

		WPUsers wpUsers = new WPUsers();
		WPForums wpForums = new WPForums();

		WordPress.getConnection().prepare();
		BBPress.getConnection().prepare();

		List<User> users = rUsers.readUsers();
		for (User user : users)
		{
			System.out.println("Storing: " + user.getRealname());
			wpUsers.storeUser(user);
		}

		List<Forum> forums = rForums.readForums();
		for (Forum f : forums)
			if (f.getParentForum() == null)
			{
				System.out.println("Storing forum: " + f.getTitle());
				f.setWpID(wpForums.storeForum(f));
			}

		for (Forum f : forums)
			if (f.getParentForum() != null)
			{
				System.out.println("Storing forum: " + f.getTitle() + " => "
						+ f.getParentForum().getWpID());
				f.setWpID(wpForums.storeForum(f));
			}

	}
}
