import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.Config;
import com.GeneticAlgorithmTravellingSalesman;




public class Main {

	
	
	public static final String propertiesFileName = "gats.properties";
	public static final String appDirectoryName = "gats"; //you can change this to .gats to make the directory hidden.
	
	
	public static void main(String[] args) {
		
		
		
		File settingsDirectory = getSettingsDirectory();
		
		Properties properties;
		
		Config config;
		
		if ( doesPropertiesFileExist(settingsDirectory) ) {
			System.out.println("Reading existing properties file: ");
			System.out.println();
			properties = readProperties(settingsDirectory);
			config = new Config(properties);
		}
		else {
			System.out.println("Properties file not found / Creating default properties file: ");
			System.out.println();
			
			config = new Config(null);
			
			writeProperties(settingsDirectory, config.createPropertiesFile());
			properties = readProperties(settingsDirectory);
		}
			
		for(String key : properties.stringPropertyNames()) {
			  String value = properties.getProperty(key);
			  System.out.println(key + " => " + value);
			}
		
		System.out.println();
		System.out.println("Simulation will commence in... ");

		
		//wait for 10 seconds
		for ( int i = 10; i >= 0; i --) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
			
			System.out.print(i + "... ");
		}

		GeneticAlgorithmTravellingSalesman ga = new GeneticAlgorithmTravellingSalesman(config);
			
		ga.commenceAlgorithm();
		
		System.out.println();
		
		for(String key : properties.stringPropertyNames()) {
			  String value = properties.getProperty(key);
			  System.out.println(key + " => " + value);
			}
		
		
		
		
		
	}
	
	public static File getSettingsDirectory ( ) {
		
		String userHome = System.getProperty("user.home");
		
		if ( userHome == null) {
			throw new IllegalStateException("user.home == null");
		}
		
		File home = new File(userHome);
		File settingsDirectory = new File(home,appDirectoryName);
		if (!settingsDirectory.exists()) {
			if (!settingsDirectory.mkdir()) {
				throw new IllegalStateException(settingsDirectory.toString());
			}
		}
		
		return settingsDirectory;
		
	}
	
	public static boolean doesPropertiesFileExist ( File settingsDirectory ) {
		
		System.out.println("Properties File Location: " + settingsDirectory.toString() + "/" + propertiesFileName);
		
		File propFile = new File(settingsDirectory.toString() + "/" + propertiesFileName);	
		
		return propFile.exists();
		
	}
	
	public static Properties readProperties ( File settingsDirectory ) {
		
		Properties prop = new Properties();
		
		try {
			prop.load(new FileInputStream(settingsDirectory.toString() + "/" + propertiesFileName));
		} catch ( IOException e ) {
			
		}
		
		return prop;
		
	}
	
	
	
	public static void writeProperties ( File settingsDirectory, Properties prop ) {
		
		try {
			prop.store(new FileOutputStream(settingsDirectory.toString() + "/" + propertiesFileName), null);
		} catch ( IOException e) {
			
		}

			
	}
	
	
}
