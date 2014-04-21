import java.io.IOException;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.factories.DAOFactory;
import by.epam.epamlab.readers.ConfigReader;

public class Runner {
	private static final String PASSENGER_NUMER = "PassengerNumer: ";
	private static final String ANIMATION_BOOST = "Animation boost: ";
	private static final String CAPACITY = "Capacity: ";
	private static final String STOREY = "Storey: ";
	private static final String CREATING_BUILDING = "Creating building";
	
	public static final Logger logger = Logger.getLogger(Runner.class);

	public static void main(String[] args) {

		int storeysNumber = 0;
		int elevatorCapacity = 0;
		int passengersNumber = 0;
		int animationBoost = 0;
		try {
			ConfigReader configReader = new ConfigReader(storeysNumber,
					elevatorCapacity, passengersNumber, animationBoost);
			logger.info(STOREY + configReader.getStoreysNumber()
					+ CAPACITY + configReader.getElevatorCapacity()
					+ PASSENGER_NUMER + configReader.getPassengersNumber()
					+ ANIMATION_BOOST + configReader.getAnimationBoost());
			logger.info(CREATING_BUILDING);
			final Building building = new Building(configReader);
			DAOFactory.initShower(building);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
