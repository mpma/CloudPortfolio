package cloudportfolio.utilities;

import java.io.File;
import java.util.Arrays;

import cloudportfolio.system.CloudPortfolio;

public class Uploader
{
	CloudPortfolio portfolio;
	
	public void upload(File... files)
	{
		portfolio.files.addAll(Arrays.asList(files));
	}

}
