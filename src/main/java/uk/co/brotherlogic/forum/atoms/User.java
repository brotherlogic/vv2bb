package uk.co.brotherlogic.forum.atoms;

public class User
{

	int vv_userid;
	int wp_userid;

	String username;
	String realname;
	String email;

	public User(int id, String uName, String rName, String em)
	{
		vv_userid = id;
		username = uName;
		realname = rName;
		email = em;
	}

	public void setWp_userid(int wpUserid)
	{
		wp_userid = wpUserid;
	}

	public int getVv_userid()
	{
		return vv_userid;
	}

	public int getWp_userid()
	{
		return wp_userid;
	}

	public String getUsername()
	{
		return username;
	}

	public String getRealname()
	{
		return realname;
	}

	public String getEmail()
	{
		return email;
	}

}
