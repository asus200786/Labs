package by.epam.epamlab.beans;

import java.util.Random;

import org.apache.log4j.Logger;

import by.epam.epamlab.controllers.TransportationState;

public class Passenger {
	private static final Logger logger = Logger.getLogger(Passenger.class
			.getName());
	private static int uniqueId = 0;

	private final int passengerID;
	private int dispatchStorey;
	private int destinationStorey;
	private final Directions directions;
	private TransportationState transportationState;

	public Passenger(int maxStoreysNumber) {
		super();
		this.passengerID = uniqueId++;
		this.dispatchStorey = setDispatchStorey(maxStoreysNumber);
		this.destinationStorey = setDestinationStorey(maxStoreysNumber,
				dispatchStorey);
		this.setTransportationState(TransportationState.NOT_STARTED);
		String transpStatePassenger = "Passenger:" + passengerID + " State: "
				+ transportationState;
		logger.info(transpStatePassenger);

		if (dispatchStorey < destinationStorey) {
			this.directions = Directions.UP;
		} else {
			this.directions = Directions.DOWN;
		}

		String infoAboutPassenger = "PassID:" + passengerID + " dispatch: "
				+ dispatchStorey + " destin: " + destinationStorey
				+ " Direction:" + directions;
		logger.info(infoAboutPassenger);
	}

	public int getPassengerID() {
		return passengerID;
	}

	public int getDispatchStorey() {
		return dispatchStorey;
	}

	public int setDispatchStorey(int maxStoreysNumber) {
		return generateNumberStorey(maxStoreysNumber);
	}

	public int getDestinationStorey() {
		return destinationStorey;
	}

	public int setDestinationStorey(int maxStoreysNumber, int dispatchStorey) {
		int numberStorey;
		// floors in the building are numbered from the first, so +1.
		numberStorey = generateNumberStorey(maxStoreysNumber);
		while (numberStorey == dispatchStorey) {
			numberStorey = generateNumberStorey(maxStoreysNumber);
		}
		return numberStorey;
	}

	// generates a random number between zero and the maximum number of floors
	// in the building, so -1.
	private int generateNumberStorey(int maxStoreysNumber) {
		Random random = new Random();
		return random.nextInt(maxStoreysNumber - 1);
	}

	public TransportationState getTransportationState() {
		return transportationState;
	}

	public void setTransportationState(TransportationState transportationState) {
		this.transportationState = transportationState;
	}

	public Directions getDirections() {
		return directions;
	}

}
