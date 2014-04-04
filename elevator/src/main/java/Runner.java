import java.io.IOException;

import by.epam.epamlab.conctants.Constants;
import by.epam.epamlab.readers.ConfigReader;

public class Runner {
	public static void main(String[] args) {

		// Reading parameters from the configuration file.
		try {
			new ConfigReader();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(Constants.ERROR_CONFIG_FILE);
		}

		//initialization of the task
		
	}
}
