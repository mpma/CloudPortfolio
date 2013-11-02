package cloudportfolio.system;

import java.io.File;
import java.util.Set;

import cloudportfolio.entities.User;

public class CloudFile
{
	private File file;
	private long fileID;
	private boolean visible;
	
	private Set<User> r;		// read
	private Set<User> rw;		// read + write
	private Set<User> owners;	
	
	public CloudFile(File file, boolean visible)
	{
		this.file = file;
		this.visible = visible;
	}
	
	private void notifyOwners()
	{
		
	}
	
	public File getFile()
	{
		return this.file;
	}
	
	public long getFileID()
	{
		return this.fileID;
	}
	
	public int getAccess(long ID)
	{
		return 0;
	}
	
	public void setAccess(long ID, int i)
	{
		
	}
	

}
