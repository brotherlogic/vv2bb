package uk.co.brotherlogic.forum.wordpress;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.brotherlogic.forum.atoms.Forum;

public class WPForums
{
	public int storeForum(Forum f) throws SQLException
	{
		PreparedStatement ps = BBPress
				.getConnection()
				.getPreparedStatement(
						"INSERT INTO bb_forums (forum_name,forum_desc,forum_parent) VALUES (?,?,?)");
		ps.setString(1, f.getTitle());
		ps.setString(2, f.getDescription());
		if (f.getParentForum() != null)
			ps.setInt(3, f.getParentForum().getWpID());
		else
			ps.setInt(3, 0);
		ps.execute();

		PreparedStatement ps2 = BBPress.getConnection().getPreparedStatement(
				"SELECT forum_id FROM bb_forums where forum_name = ?");
		ps2.setString(1, f.getTitle());
		ResultSet rs = ps2.executeQuery();
		int res = -1;
		if (rs.next())
			res = rs.getInt(1);

		// Deal with the meta if necessary
		if (f.getParentForum() == null)
		{
			PreparedStatement pst = BBPress
					.getConnection()
					.getPreparedStatement(
							"INSERT INTO bb_meta (object_type,object_id,meta_key,meta_value) VALUES (?,?,?,?)");
			pst.setString(1, "bb_forum");
			pst.setInt(2, res);
			pst.setString(3, "forum_is_category");
			pst.setInt(4, 1);
			pst.execute();
		}

		return res;
	}
}
