package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uk.co.brotherlogic.forum.atoms.Forum;
import uk.co.brotherlogic.forum.atoms.Post;
import uk.co.brotherlogic.forum.atoms.Topic;
import uk.co.brotherlogic.forum.atoms.User;

public class ReadPosts {
	public List<Post> readPosts(Map<Integer, User> userMap,
			Map<Integer, Forum> forumMap, Map<Integer, Topic> topicMap)
			throws SQLException {
		List<Post> posts = new LinkedList<Post>();

		PreparedStatement ps = VVBulletin
				.getConnection()
				.getPreparedStatement(
						"SELECT postid,threadid,userid,dateline,pagetext,ipaddress FROM post");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Post p = new Post();
			p.setVvID(rs.getInt(1));
			p.setTopic(topicMap.get(rs.getInt(2)));
			p.setForum(p.getTopic().getForum());
			p.setPoster(userMap.get(rs.getInt(3)));
			p.setPostTime(new Date(rs.getLong(4) * 1000));
			p.setText(rs.getString(5));
			p.setIp(rs.getString(6));

			posts.add(p);
		}

		rs.close();

		return posts;
	}
}
