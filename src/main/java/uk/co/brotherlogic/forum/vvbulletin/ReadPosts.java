package uk.co.brotherlogic.forum.vvbulletin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Stack;
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
	static Pattern urlStart = Pattern
			.compile("\\[[uU][rR][lL]=\"(.*?)\"\\](.*?)\\[/[uU][rR][lL]\\]");

	static Pattern urlSimp = Pattern
			.compile("\\[[uU][rR][lL]\\](.*?)\\[/[uU][rR][lL]\\]");

	static Pattern imgStart = Pattern
			.compile("\\[[iI][mM][gG]\\](.*?)\\[/[iI][mM][gG]\\]");

	public static void main(String[] args)
	{
		String str = "Some info on the earlier LP's [URL=\"http://www.easyontheeye.net/albums/totp/index.html\"]here[/URL]. I ";
		Matcher m = urlStart.matcher(str);
		while (m.find())
		{
			System.err.println("HERE");
		}
	}

	private String convertPost(String in)
	{
		// Convert the carriage returns
		String ret = in.replaceAll("\n", "<BR />");

		// Convert the quotes
		Matcher finder = blockQuoteStart.matcher(ret);
		ret = finder.replaceAll("<blockquote>");
		ret = ret.replaceAll("\\[/[qQ][uU][oO][tT][eE]\\]", "</blockquote>");

		// Convert the [i]s
		ret = ret.replaceAll("\\[[iI]\\]", "<I>");
		ret = ret.replaceAll("\\[/[iI]\\]", "</I>");

		// And the bolds
		ret = ret.replaceAll("\\[[bB]\\]", "<B>");
		ret = ret.replaceAll("\\[/[bB]\\]", "</B>");

		// Replace the images
		Stack<int[]> arrList = new Stack<int[]>();
		Stack<String[]> replacer = new Stack<String[]>();
		finder = urlStart.matcher(ret);
		while (finder.find())
		{
			int[] sande = new int[]
			{ finder.start(), finder.end() };
			String[] match = new String[]
			{ finder.group(1), finder.group(2) };

			arrList.push(sande);
			replacer.push(match);
		}

		StringBuffer superStr = new StringBuffer(ret);
		while (arrList.size() > 0)
		{
			int[] sande = arrList.pop();
			String[] rep = replacer.pop();

			superStr.replace(sande[0], sande[1], "<a href=\"" + rep[0] + "\">"
					+ rep[1] + "</a>");
		}

		// Replace the images
		arrList = new Stack<int[]>();
		replacer = new Stack<String[]>();
		finder = imgStart.matcher(superStr.toString());
		while (finder.find())
		{
			int[] sande = new int[]
			{ finder.start(), finder.end() };
			String[] match = new String[]
			{ finder.group(1) };

			arrList.push(sande);
			replacer.push(match);
		}

		while (arrList.size() > 0)
		{
			int[] sande = arrList.pop();
			String[] rep = replacer.pop();

			superStr.replace(sande[0], sande[1], "<img src=\"" + rep[0]
					+ "\" />");
		}

		// Replace the simple urls
		arrList = new Stack<int[]>();
		replacer = new Stack<String[]>();
		finder = urlSimp.matcher(superStr.toString());
		while (finder.find())
		{
			int[] sande = new int[]
			{ finder.start(), finder.end() };
			String[] match = new String[]
			{ finder.group(1) };

			arrList.push(sande);
			replacer.push(match);
		}

		while (arrList.size() > 0)
		{
			int[] sande = arrList.pop();
			String[] rep = replacer.pop();

			superStr.replace(sande[0], sande[1], "<a href=\"" + rep[0] + "\">"
					+ rep[0] + "</a>");
		}
		return superStr.toString();
	}
}
