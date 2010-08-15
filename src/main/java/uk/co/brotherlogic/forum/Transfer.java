package uk.co.brotherlogic.forum;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.forum.atoms.Forum;
import uk.co.brotherlogic.forum.atoms.Topic;
import uk.co.brotherlogic.forum.atoms.User;
import uk.co.brotherlogic.forum.vvbulletin.ReadForums;
import uk.co.brotherlogic.forum.vvbulletin.ReadPosts;
import uk.co.brotherlogic.forum.vvbulletin.ReadTopics;
import uk.co.brotherlogic.forum.vvbulletin.ReadUsers;
import uk.co.brotherlogic.forum.wordpress.BBPress;
import uk.co.brotherlogic.forum.wordpress.WPForums;
import uk.co.brotherlogic.forum.wordpress.WPPosts;
import uk.co.brotherlogic.forum.wordpress.WPTopics;
import uk.co.brotherlogic.forum.wordpress.WPUsers;
import uk.co.brotherlogic.forum.wordpress.WordPress;

/**
 * Hello world!
 * 
 */
public class Transfer
{
	public static void main(String[] args) throws SQLException, IOException
	{
		ReadUsers rUsers = new ReadUsers();
		ReadForums rForums = new ReadForums();
		ReadTopics rTopics = new ReadTopics();
		ReadPosts rPosts = new ReadPosts();

		WPUsers wpUsers = new WPUsers();
		WPForums wpForums = new WPForums();
		WPTopics wpTopics = new WPTopics();
		WPPosts wpPosts = new WPPosts();

		WordPress.getConnection().prepare();
		BBPress.getConnection().prepare();

		List<User> users = rUsers.readUsers();
		System.out.println("Read " + users.size() + " users");
		for (User user : users)
		{
			wpUsers.storeUser(user);
		}

		List<Forum> forums = rForums.readForums();
		System.out.println("Read " + forums.size() + " forums");
		for (Forum f : forums)
			if (f.getParentForum() == null)
			{
				f.setWpID(wpForums.storeForum(f));
			}
		for (Forum f : forums)
			if (f.getParentForum() != null)
			{
				f.setWpID(wpForums.storeForum(f));
			}

		// Build the user map
		Map<Integer, User> uMap = new TreeMap<Integer, User>();
		for (User user : users)
			uMap.put(user.getVv_userid(), user);

		// Build the forum map
		Map<Integer, Forum> fMap = new TreeMap<Integer, Forum>();
		for (Forum f : forums)
			fMap.put(f.getVvID(), f);

		List<Topic> topics = rTopics.readTopics(uMap, fMap);
		System.out.println("Read " + topics.size() + " topics");
		for (Topic t : topics)
			t.setWpID(wpTopics.storeTopic(t));

		// Build the topic map
		Map<Integer, Topic> tMap = new TreeMap<Integer, Topic>();
		for (Topic t : topics)
			tMap.put(t.getVvID(), t);

		rPosts.readPosts(uMap, fMap, tMap);
		System.out.println("Read  posts");
		for (Topic t : topics)
			wpPosts.storePost(t.getPosts());

	}
}
