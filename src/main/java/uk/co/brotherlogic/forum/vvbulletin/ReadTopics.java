package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uk.co.brotherlogic.forum.atoms.Forum;
import uk.co.brotherlogic.forum.atoms.Topic;
import uk.co.brotherlogic.forum.atoms.User;
import uk.co.brotherlogic.forum.wordpress.WPUsers;

public class ReadTopics
{
	WPUsers users = new WPUsers();

	public List<Topic> readTopics(Map<Integer, User> userMap,
			Map<Integer, Forum> forumMap) throws SQLException
	{
		List<Topic> topics = new LinkedList<Topic>();

		PreparedStatement ps = VVBulletin
				.getConnection()
				.getPreparedStatement(
						"SELECT title, postuserid,dateline,lastpost,forumid,lastpostid,sticky,replycount, postusername, threadid FROM thread where threadid = 28301");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			Topic t = new Topic();
			t.setTitle(rs.getString(1));

			if (userMap.get(rs.getInt(2)) == null)
			{
				// Can't find the user - create them on the fly
				User user = new User(rs.getInt(2), rs.getString(8), rs
						.getString(9), "noemail@noemail.com");
				t.setUser(user);
				users.storeUser(user);
			} else
				t.setUser(userMap.get(rs.getInt(2)));

			t.setPostStart(new Date(rs.getInt(3)));
			t.setLastPostTime(new Date(rs.getInt(4)));
			t.setForum(forumMap.get(rs.getInt(5)));
			t.setSticky(rs.getInt(7) == 1);
			t.setReplyCount(rs.getInt(8) + 1);
			t.setVvID(rs.getInt(10));

			topics.add(t);
		}

		rs.close();

		return topics;
	}
}
