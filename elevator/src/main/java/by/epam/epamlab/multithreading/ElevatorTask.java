package by.epam.epamlab.multithreading;

import org.apache.log4j.Logger;

import by.epam.epamlab.beans.Building;
import by.epam.epamlab.controllers.TransportationActions;
import by.epam.epamlab.events.AbstractEvent;
import by.epam.epamlab.events.ElevatorMovingEvent;
import by.epam.epamlab.events.ValidationEvent;
import by.epam.epamlab.factories.DAOFactory;

public class ElevatorTask implements Runnable {

	private static Logger logger = Logger.getLogger(ElevatorTask.class
			.getName());
	
	private static final String ALL_PASSENGERS_MOVED = "All passengers moved.";
	private static final String ALL_ARIVED = "All Arived";
	
	private Building building;
	private int fromStorey, toStorey;

	public ElevatorTask(Building building) {
		super();
		this.building = building;
	}

	public void run() {
		try {
			while (!building.allArrived()
					&& !Thread.currentThread().isInterrupted()) {
				building.getController().movementPassengers(building);
				fromStorey = building.getElevator().getCurrentNumberStorey();
				building.getElevator().toNextStorey();
				toStorey = building.getElevator().getCurrentNumberStorey();
				DAOFactory.eventsSynchro.set(new ElevatorMovingEvent(
						TransportationActions.MOVING_ELEVATOR, building
								.getElevator().getElevatorContainer().size(),
						fromStorey, toStorey));
			}
			if (!Thread.currentThread().isInterrupted()) {
				DAOFactory.eventsSynchro.set(new AbstractEvent(
						TransportationActions.COMPLETION_TRANSPORTATION,
						ALL_PASSENGERS_MOVED));
				DAOFactory.eventsSynchro.set(new ValidationEvent(
						TransportationActions.VALIDATION, building));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug(building.getElevator());
			}
		}
		logger.info(ALL_ARIVED);
	}

}
