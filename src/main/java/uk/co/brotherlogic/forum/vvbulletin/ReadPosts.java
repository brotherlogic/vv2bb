package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.brotherlogic.forum.atoms.Forum;
import uk.co.brotherlogic.forum.atoms.Post;
import uk.co.brotherlogic.forum.atoms.Topic;
import uk.co.brotherlogic.forum.atoms.User;

public class ReadPosts
{
	public void readPosts(Map<Integer, User> userMap,
			Map<Integer, Forum> forumMap, Map<Integer, Topic> topicMap)
			throws SQLException
	{
		PreparedStatement ps = VVBulletin
				.getConnection()
				.getPreparedStatement(
						"SELECT postid,threadid,userid,dateline,pagetext,ipaddress FROM post where threadid=26188");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			Post p = new Post();
			p.setVvID(rs.getInt(1));
			p.setTopic(topicMap.get(rs.getInt(2)));
			p.setForum(p.getTopic().getForum());
			p.setPoster(userMap.get(rs.getInt(3)));
			p.setPostTime(new Date(rs.getLong(4) * 1000));
			p.setText(convertPost(rs.getString(5)));
			p.setIp(rs.getString(6));

			System.err.println(rs.getString(5));

			p.getTopic().addPost(p);
		}

		rs.close();
	}

	Pattern blockQuoteStart = Pattern.compile("\\[[qQ][uU][oO][tT][eE].*?\\]");

	private String convertPost(String in)
	{
		Matcher finder = blockQuoteStart.matcher(in);
		String ret = finder.replaceAll("<blockquote>");
		ret = ret.replaceAll("\\[/QUOTE\\]", "</blockquote>");
		return ret;
	}
}
