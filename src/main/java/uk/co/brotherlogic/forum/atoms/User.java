package uk.co.brotherlogic.forum.atoms;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;

import uk.co.brotherlogic.forum.wordpress.WordPress;

public class User
{

	int vv_userid;
	int wp_userid;

	String username;
	String realname;
	String email;

	int avatar_revision;
	boolean avatar_done = false;

	private static String AV_BASE = "/home/simon/workspace/vgplus/forum/avatars/";

	public User(int id, String uName, String rName, String em)
	{
		vv_userid = id;
		username = uName;
		realname = rName;
		email = em;
	}

	public void procAvatar() throws SQLException
	{
		try
		{
			if (!avatar_done)
			{
				// Get the avatar
				String avatar_out = AV_BASE + username.toLowerCase() + ".png";

				if (!new File(avatar_out).exists())
				{
					// Load the previous URL
					String url = "http://verygoodplus.co.uk/customavatars/avatar"
							+ vv_userid + "_" + avatar_revision + ".gif";
					System.err.println(url + "=>" + avatar_out);
					RenderedImage img = ImageIO.read(new URL(url));
					ImageIO.write(img, "png", new File(avatar_out));

				}
				// Do the sql
				String sql = "INSERT INTO wp_usermeta (user_id,meta_key,meta_value) VALUES (?,?,?)";
				PreparedStatement ps = WordPress.getConnection()
						.getPreparedStatement(sql);
				ps.setInt(1, wp_userid);
				ps.setString(2, "avatar_file");
				ps.setString(3, username.toLowerCase() + ".png");
				ps.execute();
				ps.close();
				avatar_done = true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void setAvatar_revision(int avatarRevision)
	{
		avatar_revision = avatarRevision;
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
