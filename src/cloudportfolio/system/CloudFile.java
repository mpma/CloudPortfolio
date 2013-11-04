package cloudportfolio.system;

import java.io.File;
import java.util.Hashtable;
import java.util.Set;

import cloudportfolio.entities.User;

public class CloudFile
{	
	private File file;
	private long fileID;
	//private boolean visible; removed for the time being
	
	private Hashtable owners;
	private Hashtable r;
	private Hashtable rw;
	
	private User owner;
	
	private final int READ = 1;
	private final int READWRITE = 2;
	
	
	public CloudFile(File file, User owner)
	{
		this.file = file;
		//this.visible = visible;
		
		owners = new Hashtable<Long, User>();
		r = new Hashtable<Long, User>();
		rw = new Hashtable<Long, User>();
		
		this.owner = owner;
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
	
	public String toString()
	{
		String s = "FILE: " + file.getName()
				+ "\nOwner: " + printMembers(owners);
		
		return s;
	}

	private String printMembers(Hashtable<Long, User> table)
	{
		String s = "";
		Set<Long> keys = table.keySet();
		for(long key: keys) {
			s += table.get(key).getName() 
					+ "[" + table.get(key).getID() + "] ";
		}
			
		return s;
	}

}
