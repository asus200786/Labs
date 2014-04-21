package by.epam.epamlab.multithreading;

import java.util.List;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.beans.Elevator;
import by.epam.epamlab.beans.Passenger;
import by.epam.epamlab.beans.Storey;
import by.epam.epamlab.constants.Constants;
import by.epam.epamlab.controllers.TransportationActions;
import by.epam.epamlab.controllers.TransportationState;
import by.epam.epamlab.events.PassengerMovingEvent;
import by.epam.epamlab.factories.DAOFactory;

public class TransportationTask implements Runnable {
	private static Logger logger = Logger.getLogger(TransportationTask.class
			.getName());

	private static final String TASK = ": task";
	private static final String DISPATCH_STOREY = "; Dispatch Storey: ";
	private static final String PASSENGER = "Passenger #:";

	private Building building;
	private Passenger passenger;
	private TransportationActions transportationActions;

	public TransportationTask(Building building, Passenger passenger) {
		super();
		this.building = building;
		this.passenger = passenger;
	}

	public TransportationActions getTransportationActions() {
		return transportationActions;
	}

	public void setTransportationActions(
			TransportationActions transportationActions) {
		this.transportationActions = transportationActions;
	}

	public void run() {
		passenger.setTransportationState(TransportationState.IN_PROGRESS);
		logger.info(PASSENGER + passenger.getPassengerID()
				+ passenger.getTransportationState());
		Storey currentStorey = building.getStoreys().get(
				passenger.getDispatchStorey());
		Elevator elevator = building.getElevator();
		logger.info(PASSENGER + passenger.getPassengerID() + DISPATCH_STOREY
				+ currentStorey.getNumberStorey());
		List<Passenger> storeyContainer = currentStorey
				.getDispatchStoryContainer();
		List<Passenger> elevatorContainer = elevator.getElevatorContainer();

		try {
			synchronized (storeyContainer) {
				while (elevator.getCurrentNumberStorey() != passenger
						.getDispatchStorey()
						|| !elevator.isAvailablePlace()
						|| !elevator.isConsilienceDirectionMove(passenger)) {
					storeyContainer.wait();
				}
				building.getController().enterElevator(passenger);

				DAOFactory.eventsSynchro.set(new PassengerMovingEvent(
						TransportationActions.BOARDING_OF_PASSENGER, passenger,
						elevator.getCurrentNumberStorey()));
				logger.info(Constants.EMPTY_STRING
						+ TransportationActions.BOARDING_OF_PASSENGER
						+ passenger.getPassengerID());
				storeyContainer.notifyAll();
			}

			synchronized (elevatorContainer) {
				while (elevator.getCurrentNumberStorey() != passenger
						.getDestinationStorey()) {
					elevatorContainer.wait();
				}
				building.getController().leaveElevator(passenger);
				DAOFactory.eventsSynchro.set(new PassengerMovingEvent(
						TransportationActions.DEBOARDING_OF_PASSENGER,
						passenger, elevator.getCurrentNumberStorey()));
				logger.info(Constants.EMPTY_STRING
						+ TransportationActions.DEBOARDING_OF_PASSENGER
						+ passenger.getPassengerID());
				passenger.setTransportationState(TransportationState.COMPLETED);
				logger.info(PASSENGER + passenger.getPassengerID()
						+ passenger.getTransportationState());
				elevatorContainer.notifyAll();
			}
		} catch (InterruptedException e) {
			passenger.setTransportationState(TransportationState.ABORTED);
			logger.info(PASSENGER + passenger.getPassengerID()
					+ Constants.SEPARATOR + passenger.getTransportationState());
			if (logger.isDebugEnabled()) {
				logger.debug(passenger + TASK + TransportationState.ABORTED);
			}
		}
	}
}
