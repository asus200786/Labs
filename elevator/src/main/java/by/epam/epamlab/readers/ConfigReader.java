package by.epam.epamlab.readers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import by.epam.epamlab.constants.Constants;

public class ConfigReader {
	private static final String ANIMATION_BOOST = "animationBoost";
	private static final String PASSENGERS_NUMBER = "passengersNumber";
	private static final String ELEVATOR_CAPACITY = "elevatorCapacity";
	private static final String STORIES_NUMBER = "storiesNumber";
	private static final IllegalArgumentException ILLEGAL_ARGUMENT_EXCEPTION = new IllegalArgumentException(
			"\nNot correct settings in Config.property.\n"
					+ "Min values for: \n" + "storeysNumber: 2\n"
					+ "elevatorCapacity: 1\n" + "passengersNumber: 1\n"
					+ "animationBoost: 1-10");

	private static final Logger logger = Logger.getLogger(ConfigReader.class
			.getName());

	private int storeysNumber;
	private int elevatorCapacity;
	private int passengersNumber;
	private int animationBoost;

	private Properties props = new Properties();

	private static final String CONFIG_FILE = "config.property";

	public ConfigReader(int storeysNumber, int elevatorCapacity,
			int passengersNumber, int animationBoost) throws IOException {
		super();
		this.storeysNumber = storeysNumber;
		this.elevatorCapacity = elevatorCapacity;
		this.passengersNumber = passengersNumber;
		this.animationBoost = animationBoost;
		fileLoad();
		readingSourceData();
	}

	public int getStoreysNumber() {
		return storeysNumber;
	}

	public int getElevatorCapacity() {
		return elevatorCapacity;
	}

	public int getPassengersNumber() {
		return passengersNumber;
	}

	public int getAnimationBoost() {
		return animationBoost;
	}

	private void fileLoad() throws IOException {
		props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(CONFIG_FILE);
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.info(Constants.ERROR_CONFIG_FILE);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	private void readingSourceData() {
		storeysNumber = Integer.valueOf(props.getProperty(STORIES_NUMBER));
		elevatorCapacity = Integer
				.valueOf(props.getProperty(ELEVATOR_CAPACITY));
		passengersNumber = Integer
				.valueOf(props.getProperty(PASSENGERS_NUMBER));
		animationBoost = Integer.valueOf(props.getProperty(ANIMATION_BOOST));
		if (storeysNumber < 3 || elevatorCapacity < 1 || passengersNumber < 1
				|| animationBoost < 0 || animationBoost > 10)
			throw ILLEGAL_ARGUMENT_EXCEPTION;
	}

}
