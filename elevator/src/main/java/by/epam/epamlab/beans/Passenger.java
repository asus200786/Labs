package by.epam.epamlab.beans;

import by.epam.epamlab.controllers.TransportationStatePassenger;

public class Passenger {
	private static int uniqueId = 0;

	private int passengerID;
	private final int destinationStory;
	private TransportationStatePassenger transportationStatePasssenger;

	public Passenger(int destinationStory) {
		super();
		this.passengerID = uniqueId++;
		this.destinationStory = destinationStory;
		this.setTransportationStatePasssenger(TransportationStatePassenger.NOT_STARTED);
	}

	public TransportationStatePassenger getTransportationStatePasssenger() {
		return transportationStatePasssenger;
	}

	public void setTransportationStatePasssenger(
			TransportationStatePassenger transportationStatePasssenger) {
		this.transportationStatePasssenger = transportationStatePasssenger;
	}

	public int getPassengerID() {
		return passengerID;
	}

	public int getDestinationStory() {
		return destinationStory;
	}

}
