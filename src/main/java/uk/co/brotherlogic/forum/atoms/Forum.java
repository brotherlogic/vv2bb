package uk.co.brotherlogic.forum.atoms;

public class Forum
{
	String description;
	Forum parentForum;

	int replyCount;

	String title;
	int topicCount;

	int vvID;

	int wpID;

	public String getDescription()
	{
		return description;
	}

	public Forum getParentForum()
	{
		return parentForum;
	}

	public int getReplyCount()
	{
		return replyCount;
	}

	public String getTitle()
	{
		return title;
	}

	public int getTopicCount()
	{
		return topicCount;
	}

	public int getVvID()
	{
		return vvID;
	}

	public int getWpID()
	{
		return wpID;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setParentForum(Forum parentForum)
	{
		this.parentForum = parentForum;
	}

	public void setReplyCount(int replyCount)
	{
		this.replyCount = replyCount;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void setTopicCount(int topicCount)
	{
		this.topicCount = topicCount;
	}

	public void setVvID(int vvID)
	{
		this.vvID = vvID;
	}

	public void setWpID(int wpID)
	{
		this.wpID = wpID;
	}
}
