package by.epam.epamlab.readers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import by.epam.epamlab.conctants.Constants;

public class ConfigReader {

	private int storiesNumber;
	private int elevatorCapacity;
	private int passengersNumber;
	private int animationBoost;


	private static final String CONFIG_FILE = "config.property";

	public int getStoriesNumber() {
		return storiesNumber;
	}

	public void setStoriesNumber(int storiesNumber) {
		this.storiesNumber = storiesNumber;
	}

	public int getElevatorCapacity() {
		return elevatorCapacity;
	}

	public void setElevatorCapacity(int elevatorCapacity) {
		this.elevatorCapacity = elevatorCapacity;
	}

	public int getPassengersNumber() {
		return passengersNumber;
	}

	public void setPassengersNumber(int passengersNumber) {
		this.passengersNumber = passengersNumber;
	}

	public int getAnimationBoost() {
		return animationBoost;
	}

	public void setAnimationBoost(int animationBoost) {
		this.animationBoost = animationBoost;
	}

	public ConfigReader() throws IOException {

		Properties props = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(CONFIG_FILE);
			props.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(Constants.ERROR_CONFIG_FILE);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		storiesNumber = Integer.valueOf(props.getProperty("storiesNumber",
				Constants.STORIES_NUMBER_DEFAULT));
		System.out.println(storiesNumber);
		elevatorCapacity = Integer.valueOf(props.getProperty(
				"elevatorCapacity", Constants.ELEVATOR_CAPACITY_DEFAULT));
		passengersNumber = Integer.valueOf(props.getProperty(
				"passengersNumber", Constants.PASSENGERS_NUMBER_DEFAULT));
		animationBoost = Integer.valueOf(props.getProperty("animationBoost",
				Constants.ANIMATION_BOOST_DEFAULT));
	}

}
