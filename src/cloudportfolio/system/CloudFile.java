package cloudportfolio.system;

import java.io.File;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;

import cloudportfolio.entities.User;

public class CloudFile
{	
	private File file;
	private long fileID;
	//private boolean visible; removed for the time being
	
	private Hashtable owners;
	private Hashtable r;
	private Hashtable rw;
	
	private final int READ = 1;
	private final int READWRITE = 2;
	
	
	public CloudFile(File file, User owner)
	{
		this.file = file;
		//this.visible = visible;
		
		owners = new Hashtable<Long, User>();
		r = new Hashtable<Long, User>();
		rw = new Hashtable<Long, User>();
		
		add(owners, owner);
	}
	
	// adds user to a specific Hashtable
	private boolean add(Hashtable<Long, User> table, User user)
	{
		if(table.containsKey(user.getID()))
			return false;
		else {
			table.put(user.getID(), user);
			return true;
		}
	}
	
	/**
	 * Set's access permissions for a specific user
	 * 
	 * @param user
	 * @param i - [1: READ] [2: READ/WRITE]
	 */
	public void setAccess(User user, int i)
	{
		switch(i)
		{
		case READ:
			add(r, user);
			break;
			
		case READWRITE:
			add(rw, user);
			break;
		}
	}
	
	public void setVisibility(boolean visible)
	{
		//this.visible = visible;
	}
	
	public File getFile()
	{
		return file;
	}

}
