package cloudportfolio.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserParser
{	
	public static void main(String[] args) throws IOException
	{
		File file = new File("C:\\Users\\Mathias\\SkyDrive\\Dokument\\users.txt");
		FileReader input = new FileReader(file);
		BufferedReader bufRead = new BufferedReader(input);
		String line;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Username: ");
		String u = sc.next();
		System.out.print("Password: ");
		String p = sc.next();
		
		String username = "", password = "";
		
		while((line = bufRead.readLine()) != null)
		{   
			if(username.equals(u) && password.equals(p)) {
				System.out.println("FOUND USER \n" + u + ", " + p);
				break;
			}
			
			else if(line.matches("user:.*")) {
				username = line.substring(5);
			}
			
			else if(line.matches("password:.*")) {
				password = line.substring(9);
			}
		}
		
		System.out.println("DONE");
	}
}
