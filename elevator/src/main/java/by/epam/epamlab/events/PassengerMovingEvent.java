package by.epam.epamlab.events;

import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationActions;

public class PassengerMovingEvent extends AbstractEvent {

	private Passenger passenger;
	private int currentStorey;

	private static final String MESSAGE_HEADER = "Passenger #";
	private static final String MESSAGE_BODY = " on storey №";

	public PassengerMovingEvent(TransportationActions transportationActions,
			Passenger passenger, int currentStorey) {
		super(transportationActions, MESSAGE_HEADER
				+ passenger.getPassengerID() + MESSAGE_BODY + currentStorey
				+ Constants.DOT);
		this.currentStorey = currentStorey;
		this.passenger = passenger;
	}

	public int getCurrentStorey() {
		return currentStorey;
	}

	public Passenger getPassenger() {
		return passenger;
	}
}
