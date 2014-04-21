package by.epam.epamlab.controllers;

import java.util.List;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.beans.Passenger;

public class Controller {
	private static final Logger logger = Logger.getLogger(Controller.class
			.getName());
	
	private static final String PASSENGER_ELEVATOR_TOWARDS = "Passenger & Elevator -> TOWARDS";
	private static final String ON_STOREY = "on storey";
	private static final String PASSENGER_ARRIVED = "Passenger arrived";
	private static final String ENTER_ELEVATOR_ON_STOREY = "Enter elevator on storey: ";
	private static final String CURRENT_STOREY = "CurrentStorey: ";

	private Building building;

	public Controller(Building building) {
		super();
		this.building = building;
	}

	public void movementPassengers(Building building)
			throws InterruptedException {
		int currentStorey = building.getElevator().getCurrentNumberStorey();
		logger.info(CURRENT_STOREY + currentStorey);
		List<Passenger> storeyContainer = building.getStoreys()
				.get(currentStorey).getDispatchStoryContainer();
		List<Passenger> elevatorContainer = building.getElevator()
				.getElevatorContainer();

		synchronized (elevatorContainer) {
			while (hasArrived(elevatorContainer)) {
				elevatorContainer.notifyAll();
				elevatorContainer.wait();
			}
		}
		synchronized (storeyContainer) {
			while (towards(storeyContainer)
					&& building.getElevator().isAvailablePlace()) {
				storeyContainer.notifyAll();
				storeyContainer.wait();
			}
		}
	}

	public void enterElevator(Passenger passenger) {
		int currStoreyElevator = building.getElevator()
				.getCurrentNumberStorey();
		building.getElevator().getElevatorContainer().add(passenger);
		logger.info(ENTER_ELEVATOR_ON_STOREY + currStoreyElevator);
		building.getStoreys().get(currStoreyElevator)
				.getDispatchStoryContainer().remove(passenger);
	}

	public void leaveElevator(Passenger passenger) {
		int currStoreyElevator = building.getElevator()
				.getCurrentNumberStorey();
		building.getElevator().getElevatorContainer().remove(passenger);
		building.getStoreys().get(currStoreyElevator)
				.getArrivalStoryContainer().add(passenger);
		building.arrived();
		logger.info(PASSENGER_ARRIVED + passenger.getPassengerID()
				+ ON_STOREY + currStoreyElevator);
	}

	public boolean hasArrived(List<Passenger> arrivedPassengers) {
		boolean hasArrived = false;
		for (Passenger passenger : arrivedPassengers) {
			if (passenger.getDestinationStorey() == (building.getElevator()
					.getCurrentNumberStorey())) {
				hasArrived = true;
				break;
			}
		}
		return hasArrived;
	}

	public boolean towards(List<Passenger> passengers) {
		boolean towards = false;
		for (Passenger passenger : passengers) {
			if (building.getElevator().isConsilienceDirectionMove(passenger)) {
				towards = true;
				logger.info(PASSENGER_ELEVATOR_TOWARDS);
				break;
			}
		}
		return towards;
	}
}
