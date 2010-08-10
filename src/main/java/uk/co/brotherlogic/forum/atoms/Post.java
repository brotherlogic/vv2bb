package uk.co.brotherlogic.forum.atoms;

import java.util.Date;

public class Post implements Comparable<Post>
{
	@Override
	public int compareTo(Post o)
	{
		return postTime.compareTo(o.postTime);
	}

	int wpID;
	int vvID;
	Forum forum;
	Topic topic;
	User poster;
	Date postTime;

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

	public Forum getForum()
	{
		return forum;
	}

	public void setForum(Forum forum)
	{
		this.forum = forum;
	}

	public Topic getTopic()
	{
		return topic;
	}

	public void setTopic(Topic topic)
	{
		this.topic = topic;
	}

	public User getPoster()
	{
		return poster;
	}

	public void setPoster(User poster)
	{
		this.poster = poster;
	}

	public Date getPostTime()
	{
		return postTime;
	}

	public void setPostTime(Date postTime)
	{
		this.postTime = postTime;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	String text;
	String ip;
}
