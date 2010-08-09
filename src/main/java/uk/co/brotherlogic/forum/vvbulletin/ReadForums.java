package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import uk.co.brotherlogic.forum.atoms.Forum;

public class ReadForums
{
	public List<Forum> readForums() throws SQLException
	{
		List<Forum> forums = new LinkedList<Forum>();
		Map<Integer, Forum> idMap = new TreeMap<Integer, Forum>();

		PreparedStatement ps = VVBulletin
				.getConnection()
				.getPreparedStatement(
						"SELECT forumid,title,description FROM forum where parentid = -1");
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			Forum f = new Forum();
			f.setParentForum(null);
			f.setTitle(rs.getString(2));
			f.setVvID(rs.getInt(1));
			f.setDescription(rs.getString(3));
			idMap.put(f.getVvID(), f);

			forums.add(f);
		}
		rs.close();

		PreparedStatement ps2 = VVBulletin
				.getConnection()
				.getPreparedStatement(
						"SELECT forumid,title,parentid,description FROM forum where parentid > -1");
		ResultSet rs2 = ps2.executeQuery();
		while (rs2.next())
		{
			Forum f = new Forum();
			f.setParentForum(idMap.get(rs2.getInt(3)));
			f.setTitle(rs2.getString(2));
			f.setVvID(rs2.getInt(1));
			f.setDescription(rs2.getString(4));

			forums.add(f);
		}
		rs2.close();

		return forums;
	}

	public static void main(String[] args) throws SQLException
	{
		ReadForums rf = new ReadForums();
		System.out.println(rf.readForums().size());
	}
}
