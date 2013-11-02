package cloudportfolio.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cloudportfolio.entities.User;

public class CloudPortfolio
{
	public List<File> files = new ArrayList<File>();
	public List<User> users = new ArrayList<User>();

	private File userFile;
	private File rootDir;
	private File f;
	private FileReader input;
	private RandomAccessFile bufRead;
	private Scanner sc = new Scanner(System.in);

	private User currentUser;

	private String u = "", p = "";		// used for credentials


	public static void main(String[] args) throws IOException
	{
		CloudPortfolio cp = new CloudPortfolio();
	}

	CloudPortfolio() throws IOException 
	{
		setup();
		boot();
	}

	/*
	 * Creates root folder and the user file where all
	 * users are stored, we know this isn't secure, but with regards to
	 * our deadline I think this will do for the moment.
	 */
	private void setup() throws IOException
	{
		rootDir = new File(System.getProperty("user.dir")+"\\root");
		if(!rootDir.exists())
			rootDir.mkdir();

		userFile = new File(System.getProperty("user.dir") + "\\users.txt");
		userFile.createNewFile();

		input = new FileReader(userFile);
		bufRead = new RandomAccessFile(userFile, "r");

		readUsers();
	}

	/*
	 * At system boot, we read through the users.txt file
	 * and adds each user to a list
	 */
	private void readUsers() throws IOException
	{
		resetBuf();
		String line;
		int count = 0;
		File userDir;

		while((line = bufRead.readLine()) != null)
		{
			if(line.matches("user:.*")) {
				userDir = new File(rootDir + "\\" + line.substring(5));
				userDir.mkdir();
				users.add(new User(line.substring(5), count++, userDir));
			}

		}
	}

	/*
	 * Boot menu, could possibly be renamed
	 */
	private void boot() throws IOException
	{
		boolean keepRunning = true;
		int val = 0;

		System.out.println("###\tCloudPortfolio\t###");

		while(keepRunning) {
			displayOptions();
			val = sc.nextInt();

			switch(val) {
			case 1: // create account
				promptCredentials();
				createAccount();
				break;

			case 2: // log in
				promptCredentials();
				if(logIn(u, p)) {
					System.out.println("Log in successful!");
					userPanel();
				}
				else
					System.err.println("DID NOT FOUND USER");
				break;

			case 3: // list users
				listUsers();
				break;

			case 4:	// delete user
				listUsers();
				System.out.print("Select user for deletion: ");
				deleteUser(sc.next());
				break;

			case 5: // exit
				System.exit(0);
				break;
			}
		}

	}

	/*
	 * Needs to be better implemented, does not work in present form.
	 */
	private void deleteUser(String username) throws IOException 
	{
		for(User x : users) {
			if(x.getName().equals(username)) {
				users.remove(x.getID());

				resetBuf();
				String line;
				
//				File tmp = new File(userFile.getAbsolutePath() + ".tmp");
//				BufferedReader br = new BufferedReader(input);
//				PrintWriter pw = new PrintWriter(new FileWriter(tmp));				

				while((line = bufRead.readLine()) != null)
				{
					
					System.out.println(line);
					if(line.matches("user:" + username)) {
					}
				}
			}
		}
	}

	/*
	 * Ask user for account credentials, like username and password and
	 * creates the entries in the users.txt file. This is part of the
	 * first iteration and will be improved upon.
	 */
	private void createAccount() throws IOException
	{
		if(checkDuplicate(u)) {
			promptCredentials();
			createAccount();
			return;
		} 

		try {
			FileWriter fw = new FileWriter(userFile, true);
			PrintWriter pw = new PrintWriter(fw);

			File userDir = new File(rootDir + "\\" + u );
			userDir.mkdir();
			
			User user = new User(u, users.size()+1, userDir);
			users.add(user);

			//pw.println("[");
			pw.println("user:" + user.getName());
			pw.println("password:" + p);
			pw.println("id:" + user.getID());
			//pw.println("]");
			pw.println();

			pw.close();

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	/*
	 * If a username already exist we prompt the user to enter a new one
	 */
	private boolean checkDuplicate(String u) throws IOException
	{
		for(User user : users) {
			if(user.getName().equals(u)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * In order to re-read files over and over again we need to reset the 
	 * reader to the start of the textfile
	 */
	private void resetBuf() throws IOException
	{
		bufRead.seek(0);
	}

	/*
	 * Ask user for username and password to be used in later methods
	 */
	private void promptCredentials()
	{
		u = p = "";

		System.out.print("\nUsername: ");
		u = sc.next();
		System.out.print("Password: ");
		p = sc.next();
	}

	/*
	 * User control panel, used only for testing at this phase.
	 */
	private void userPanel() throws IOException
	{
		System.out.println("\n###\tUser control panel\t###");

		boolean keepRunning = true;
		int val = 0;

		while(keepRunning) {
			userOptions();
			val = sc.nextInt();

			switch(val) {
			case 1:	// upload file
				System.out.print("Select file: ");
				f = new File(sc.next());
				currentUser.write(f);
				break;

			case 2:	// download file
				System.out.print("Select file: ");
				f = new File(rootDir + "\\" + sc.next());
				currentUser.read(f);
				break;

			case 3:	// list files
				//listFiles(new File(rootDir + "\\"));
				currentUser.listFiles();
				break;

			case 4:	// delete file
				currentUser.listFiles();
				System.out.print("Which file do you want to remove?: ");
				f = new File(rootDir + "\\" + sc.next());
				currentUser.delete(f);
				//deleteFile(f);
				break;

			case 5: // move file
				currentUser.listFiles();
				System.out.print("Which file do you want to move?: ");
				File oldFile = new File(rootDir + "\\" + sc.next());
				System.out.print("New location: ");
				File newFile = new File(sc.next());
				currentUser.move(oldFile, newFile);
				//moveFile(oldFile, newFile);
				break;

			case 6: // create folder
				System.out.print("Name new folder: ");
				f = new File(rootDir + "\\" + sc.next());
				createFolder(f);
				break;

			case 7: // exit
				System.exit(0);
				break;
			}
		}
	}

	private void createFolder(File file) {
		if(!file.exists()) {
			if(file.mkdir()) {
				System.out.println("Directory was created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	private boolean logIn(String username, String password) throws IOException
	{	
		String line;
		String user = "", pass = "";

		boolean found = false;

		resetBuf();
		while((line = bufRead.readLine()) != null)
		{   
			if(line.matches("user:.*")) {
				user = line.substring(5);
			}

			else if(line.matches("password:.*")) {
				pass = line.substring(9);
			}

			if(username.equals(user) && password.equals(pass)) {
				found = true;
				break;
			}
		}

		/* ugly way to find user */
		for(User x : users) {
			if(x.getName().equals(username))
				currentUser = x;
		}

		return found;	
	}

	private void listUsers() throws IOException
	{
		String line;
		String user = "";

		System.out.println("\nUSERS: ");
		resetBuf();
		while((line = bufRead.readLine()) != null)
		{   
			if(line.matches("user:.*"))
				System.out.println(line.substring(5));	
		}

	}

	/*
	 * Displays menu options for the boot menu
	 */
	private void displayOptions()
	{
		System.out.println("\n1. Create account\n"
				+ "2. Log in\n"
				+ "3. List users\n"
				+ "4. Delete user\n"
				+ "5. Exit");
		System.out.print("Select option: ");
	}

	/*
	 * Displays menu options for the user control panel
	 */
	private void userOptions()
	{
		System.out.println("\n1. Upload file\n"
				+ "2. Download file\n"
				+ "3. List files\n"
				+ "4. Delete files\n"
				+ "5. Move files\n"
				+ "6. Create folder\n"
				+ "7. Exit");
		System.out.print("Select option: ");

	}

	public String getRootDir() throws IOException 
	{ 
		return rootDir.getCanonicalPath(); 
	}
}

