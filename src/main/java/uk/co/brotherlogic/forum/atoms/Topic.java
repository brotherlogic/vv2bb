package uk.co.brotherlogic.forum.atoms;

import java.util.Date;

public class Topic
{
	int vvID;
	int wpID;
	String title;
	User user;
	Date postStart;
	Date lastPostTime;
	Forum forum;
	boolean sticky;
	int replyCount;

	public int getVvID()
	{
		return vvID;
	}

	public void setVvID(int vvID)
	{
		this.vvID = vvID;
	}

	public int getWpID()
	{
		return wpID;
	}

	public void setWpID(int wpID)
	{
		this.wpID = wpID;
	}

	public String getTitle()
	{
		return title;
	}

	public String getTitle(int max)
	{
		if (title.length() > max)
			return title.substring(1, max - 3) + "...";
		else
			return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Date getPostStart()
	{
		return postStart;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public void setPostStart(Date postStart)
	{
		this.postStart = postStart;
	}

	public Date getLastPostTime()
	{
		return lastPostTime;
	}

	public void setLastPostTime(Date lastPostTime)
	{
		this.lastPostTime = lastPostTime;
	}

	public Forum getForum()
	{
		return forum;
	}

	public void setForum(Forum forum)
	{
		this.forum = forum;
	}

	public boolean isSticky()
	{
		return sticky;
	}

	public void setSticky(boolean sticky)
	{
		this.sticky = sticky;
	}

	public int getReplyCount()
	{
		return replyCount;
	}

	public void setReplyCount(int replyCount)
	{
		this.replyCount = replyCount;
	}
}
