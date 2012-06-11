package se.kd35a.seldon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SeldonMain extends Thread {
	private static final String CONFIG_FILE = "config.ini";
	private static final String CONFIG_KEY_SERVER	= "server";
	private static final String CONFIG_KEY_PORT		= "port";
	private static final String CONFIG_KEY_CHANNEL	= "channel";
	private Properties config;
	
	public static void main(String[] args) {
		new SeldonMain().start();
	}
	
	public SeldonMain() {
		config = new Properties();
	}
	
	public void run() {
		loadConfiguration();
		checkConfiguration();
	}

	private void loadConfiguration() {
		try {
			config.load(new FileReader(CONFIG_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("File not found: " + CONFIG_FILE);
			System.err.println("Please create a configuration-file before " +
					"proceeding.");
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("An IO error occured while trying to read " +
					CONFIG_FILE);
			System.exit(2);
		}
	}

	private void checkConfiguration() {
		boolean error = false;
		
		String[] confs =
			{CONFIG_KEY_SERVER,
			 CONFIG_KEY_PORT,
			 CONFIG_KEY_CHANNEL};
		for (String conf : confs) {
			if (config.getProperty(conf) == null) {
				error = true;
				System.err.println("Could not load property '" + conf +
						"' from configuration-file " + CONFIG_FILE);
			}
		}
		
		if (error) {
			System.exit(3);
		}
	}
	
}
