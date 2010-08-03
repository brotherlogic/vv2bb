package uk.co.brotherlogic.forum.atoms;

public class Forum
{
	int vvID;
	int wpID;

	String description;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getWpID()
	{
		return wpID;
	}

	public void setWpID(int wpID)
	{
		this.wpID = wpID;
	}

	public int getVvID()
	{
		return vvID;
	}

	public void setVvID(int vvID)
	{
		this.vvID = vvID;
	}

	String title;
	Forum parentForum;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Forum getParentForum()
	{
		return parentForum;
	}

	public void setParentForum(Forum parentForum)
	{
		this.parentForum = parentForum;
	}
}
