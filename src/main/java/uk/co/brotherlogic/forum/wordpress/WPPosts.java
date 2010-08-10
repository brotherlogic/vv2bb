package uk.co.brotherlogic.forum.wordpress;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import uk.co.brotherlogic.forum.atoms.Post;

public class WPPosts
{
	public void storePost(Set<Post> posts) throws SQLException
	{
		int count = 0;
		for (Post p : posts)
		{
			PreparedStatement ps = BBPress
					.getConnection()
					.getPreparedStatement(
							"INSERT INTO bb_posts (topic_id,forum_id,poster_id,post_time,post_text,poster_ip) VALUES (?,?,?,?,?,?)");
			ps.setInt(1, p.getTopic().getWpID());
			ps.setInt(2, p.getForum().getWpID());
			ps.setInt(3, p.getPoster().getWp_userid());
			ps.setDate(4, new Date(p.getPostTime().getTime()));
			ps.setString(5, p.getText());
			ps.setString(6, p.getIp());
			ps.execute();

			PreparedStatement ps2 = BBPress
					.getConnection()
					.getPreparedStatement(
							"SELECT topic_id FROM bb_posts where post_text = ? AND post_time = ?");
			ps2.setString(1, p.getText());
			ps2.setDate(2, new Date(p.getPostTime().getTime()));
			ResultSet rs = ps2.executeQuery();
			int res = -1;
			if (rs.next())
				res = rs.getInt(1);

			ps2.close();
			ps.close();

			p.setWpID(res);
		}
	}
}
