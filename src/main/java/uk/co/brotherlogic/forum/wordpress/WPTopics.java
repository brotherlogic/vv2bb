package uk.co.brotherlogic.forum.wordpress;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.brotherlogic.forum.atoms.Topic;

public class WPTopics
{
	public int storeTopic(Topic t) throws SQLException
	{
		PreparedStatement ps = BBPress
				.getConnection()
				.getPreparedStatement(
						"INSERT INTO bb_topics (topic_title,topic_poster,topic_poster_name,topic_start_time,topic_time,forum_id,topic_sticky, topic_posts) VALUES (?,?,?,?,?,?,?,?)");
		ps.setString(1, t.getTitle(100));
		ps.setInt(2, t.getUser().getWp_userid());
		ps.setString(3, t.getUser().getUsername());
		ps.setDate(4, new Date(t.getPostStart().getTime() * 1000));
		ps.setDate(5, new Date(t.getLastPostTime().getTime() * 1000));
		ps.setInt(6, t.getForum().getWpID());
		ps.setBoolean(7, t.isSticky());
		ps.setInt(8, t.getReplyCount());
		ps.execute();

		PreparedStatement ps2 = BBPress.getConnection().getPreparedStatement(
				"SELECT topic_id FROM bb_topics where topic_title = ?");
		ps2.setString(1, t.getTitle());
		ResultSet rs = ps2.executeQuery();
		int res = -1;
		if (rs.next())
			res = rs.getInt(1);

		ps2.close();
		ps.close();
		return res;
	}
}
